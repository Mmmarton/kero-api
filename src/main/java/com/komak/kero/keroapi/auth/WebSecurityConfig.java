package com.komak.kero.keroapi.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthFilter tokenAuthFilter;

    @Autowired
    private TokenAuthProvider tokenAuthProvider;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/auth/status").hasRole(AuthRoles.ROLE_MEMBER)
                .and()
                .authorizeRequests().anyRequest().fullyAuthenticated()
                .and()
                .csrf()
                .ignoringAntMatchers("/auth/login")
                .ignoringAntMatchers("/user/register")
                .and()
                .authenticationProvider(tokenAuthProvider)
                .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }
}