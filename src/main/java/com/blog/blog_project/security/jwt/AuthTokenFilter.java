package com.blog.blog_project.security.jwt;

import com.blog.blog_project.security.services.UserDetailsServiceImpl;
import io.jsonwebtoken.Jwt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class)


    /**
     * Get JWT from the Authorization header by removing the Bearer Prefix
     * if the request has JWT, validate it, parse the username from it
     * from username, get UserDetails to create an Authentication object
     * set the current UserDetails to create an Authentication object
     * set the current UserDetails in SecurityContext using setAuthentication(authentication) method
     *
     * Anytime we need to get UserDetails after this, just use SecurityCOntext like:
     *
     * UserDetails userDetails=
     * (userDetails) SecurityContextHolder.getContest().getAuthentication().getPrincipal();
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = parseJwt(request);
        }
    }
}
