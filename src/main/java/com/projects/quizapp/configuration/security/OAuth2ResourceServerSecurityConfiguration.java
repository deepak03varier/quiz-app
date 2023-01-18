package com.projects.quizapp.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class OAuth2ResourceServerSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AwsCognitoJwtAuthFilter awsCognitoJwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.headers().cacheControl();
        http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .and()
            .addFilterBefore(awsCognitoJwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling();
    }
}
