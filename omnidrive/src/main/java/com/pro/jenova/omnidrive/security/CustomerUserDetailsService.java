package com.pro.jenova.omnidrive.security;

import org.slf4j.Logger;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private static final Logger logger = getLogger(CustomerUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to load user by username [{}].", username);

        return new User("dimitrios", "password", createAuthorityList("MANAGE_USER"));
    }

}
