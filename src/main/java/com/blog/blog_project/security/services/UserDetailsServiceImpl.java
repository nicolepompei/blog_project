package com.blog.blog_project.security.services;

import com.blog.blog_project.entities.User;
import com.blog.blog_project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.assertj.core.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * This class overrides the loadUserByUnser() method which is used by Spring Security to fetch the user details. Inside the
     * method, query the UserReposstory to fetch the user details and wrapping them in another User object that implements the
     * UserDetails interface
     */
    @Override
    @VisibleForTesting
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        User user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username " + username));

        return new org.springframework.security
                .core.userdetails.User(user.getUsername(),
                                    user.getPassword(),
                                    true, true, true, true,
                                    getAuthorities("USER"));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String role){
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
