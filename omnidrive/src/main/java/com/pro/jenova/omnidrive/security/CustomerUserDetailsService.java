package com.pro.jenova.omnidrive.security;

import com.pro.jenova.omnidrive.data.entity.Authority;
import com.pro.jenova.omnidrive.data.entity.User;
import com.pro.jenova.omnidrive.data.repository.AuthorityRepository;
import com.pro.jenova.omnidrive.data.repository.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;
import static org.slf4j.LoggerFactory.getLogger;
import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private static final Logger logger = getLogger(CustomerUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("admin".equals(username)) {
            return loadAdminUserDetails();
        }

        Optional<User> user = userRepository.findByUsername(username);

        if (!user.isPresent()) {
            throw new UsernameNotFoundException(format("Unknown user with username [{0}].", username));
        }

        List<Authority> authorities = authorityRepository.findByUser(user.get());

        return toUserDetails(user.get(), authorities);
    }

    private org.springframework.security.core.userdetails.User toUserDetails(User user, List<Authority> authorities) {
        String[] assigned = authorities.stream().map(Authority::getAuthority).toArray(size -> new String[size]);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                createAuthorityList(assigned));
    }

    private org.springframework.security.core.userdetails.User loadAdminUserDetails() {
        return new org.springframework.security.core.userdetails.User("admin",
                "$2a$10$bKV1xuUfAxJHDECvfQGXV.5pOGkOMGfIYr1jDS4FzuGc.OlQ44V2O",
                createAuthorityList("ADMIN"));
    }

}
