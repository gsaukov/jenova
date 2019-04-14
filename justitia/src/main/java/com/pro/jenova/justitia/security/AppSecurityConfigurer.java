package com.pro.jenova.justitia.security;

import com.pro.jenova.justitia.data.repository.ChallengeRepository;
import com.pro.jenova.justitia.data.repository.LoginRepository;
import com.pro.jenova.justitia.security.sca.StrongCustomerAuthenticationFilter;
import com.pro.jenova.justitia.security.sca.StrongCustomerAuthenticationProvider;
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class AppSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // No JSESSIONID Cookie
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.cors().and().csrf().disable()
                // Actuator
                .antMatcher("/actuator/**").authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .anyRequest().authenticated()
                // Local API
                .and()
                .antMatcher("/justitia-api/**").authorizeRequests()
                .antMatchers("/justitia-api/login/**").permitAll()
                .antMatchers("/justitia-api/**").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                // Default (Block All)
                .and()
                .antMatcher("/**").authorizeRequests()
                .anyRequest().authenticated()
                // Security Filters
                .and().httpBasic()
                .and().addFilterBefore(strongCustomerAuthenticationFilter(), BasicAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider())
                .authenticationProvider(strongCustomerAuthenticationProvider());
    }

    @Bean
    public StrongCustomerAuthenticationFilter strongCustomerAuthenticationFilter() throws Exception {
        StrongCustomerAuthenticationFilter strongCustomerAuthenticationFilter = new StrongCustomerAuthenticationFilter();
        strongCustomerAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        strongCustomerAuthenticationFilter.setLoginRepository(loginRepository);
        return strongCustomerAuthenticationFilter;
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public StrongCustomerAuthenticationProvider strongCustomerAuthenticationProvider() {
        StrongCustomerAuthenticationProvider strongCustomerAuthenticationProvider = new StrongCustomerAuthenticationProvider();
        strongCustomerAuthenticationProvider.setLoginRepository(loginRepository);
        strongCustomerAuthenticationProvider.setChallengeRepository(challengeRepository);
        strongCustomerAuthenticationProvider.setUserDetailsService(userDetailsService);
        strongCustomerAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return strongCustomerAuthenticationProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
