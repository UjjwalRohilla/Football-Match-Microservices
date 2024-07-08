package com.test.checkJWT.config;

import com.test.checkJWT.filters.ValidateJWTTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class securityConfigs {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        return http
                .csrf(csrf->csrf.disable())
                .addFilterBefore(new ValidateJWTTokenFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(auth->auth.requestMatchers("/check_jwt/**").authenticated())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(login->login.disable())
                .httpBasic(basic->basic.disable())
                .build();
    }

}
