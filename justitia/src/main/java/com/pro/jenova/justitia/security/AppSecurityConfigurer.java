package com.pro.jenova.justitia.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class AppSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // No JSESSIONID Cookie
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/justitia-api/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationDetailsSource(customWebAuthenticationDetailsSource());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public CustomWebAuthenticationDetailsSource customWebAuthenticationDetailsSource() {
        return new CustomWebAuthenticationDetailsSource();
    }

    @Bean
    public CustomAuthenticationProvider authenticationProvider() {
        CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider();
        customAuthenticationProvider.setUserDetailsService(userDetailsService);
        customAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return customAuthenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
