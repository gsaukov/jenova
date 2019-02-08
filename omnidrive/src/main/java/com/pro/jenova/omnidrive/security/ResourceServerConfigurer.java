package com.pro.jenova.omnidrive.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Value("${security.oauth2.resource.client-id}")
    private String clientId;

    @Value("${security.oauth2.resource.client-secret}")
    private String clientSecret;

    @Value("${security.oauth2.resource.token-info-uri}")
    private String tokenInfoUri;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // No JSESSIONID Cookie
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/actuator/**").permitAll()
                .antMatchers("/omnidrive-api/**").hasAuthority("USER")
                .anyRequest().authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(remoteTokenServices());
    }

    @Bean
    public RemoteTokenServices remoteTokenServices() {
        final RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setCheckTokenEndpointUrl(tokenInfoUri);
        tokenServices.setClientId(clientId);
        tokenServices.setClientSecret(clientSecret);
        return tokenServices;
    }

}
