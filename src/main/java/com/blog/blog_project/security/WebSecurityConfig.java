package com.blog.blog_project.security;

import com.blog.blog_project.security.jwt.AuthEntryPointJwt;
import com.blog.blog_project.security.jwt.AuthTokenFilter;
import com.blog.blog_project.security.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//allows spring to find and automatically apply the class to the global web security
@EnableWebSecurity
//provides AOP security on methods. it enables @PreAuthorize, @PostAuthorize, it also supports JSR-250
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Spring Security will load User details to perform authentication & authorization. So it has UserDetailsService
     * interface that we need to implement
     */

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter(){ return new AuthTokenFilter();}

    /**
     * The implementation of UserDetailsService will be used for configuring DaoAuthenticationProvider by
     * AuthentictionManagerBuilder.userDetailsService() method
     */

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    /**
     * PasswordEncoder is needed for the DaoAuthenticationProvider. If this is not specified, it will use plain text
     */
    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();
    }

    /**
     * We override the configure(HttpSecurity http) method from WebSecurityConfigureAdapter intergace.
     * It tells Spring Security how we configure CORS and CSRF, when we want to require all users to be authticated
     * or not, which filter (AuthTokenFilter) and when we want it to work (filter BEFORE UsernamePasswordAuthenticationFilter),
     * which Exception Handler is chose (AuthEntryPointJwt)
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers(HttpMethod.GET,"/posts").permitAll()
                .antMatchers(HttpMethod.GET,"/posts/**").permitAll()
                .anyRequest().authenticated();
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }




}
