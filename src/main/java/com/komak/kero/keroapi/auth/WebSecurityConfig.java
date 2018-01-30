package com.komak.kero.keroapi.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private TokenAuthFilter tokenAuthFilter;

    @Autowired
    private TokenAuthProvider tokenAuthProvider;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("username")
                .password("password")
                .authorities("ROLE_USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/login").hasRole("PUBLIC")
                .and().authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .and()
                //.csrf().disable()
                .authenticationProvider(tokenAuthProvider)
                .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }
}
