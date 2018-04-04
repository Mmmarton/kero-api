package com.komak.kero.keroapi.auth;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
        .cors()
        .and()
        .authorizeRequests()
        .antMatchers("/*").permitAll()
        .antMatchers("/api/auth/login").permitAll()
        .antMatchers("/api/user/register").permitAll()
        .antMatchers("/assets/img/*").permitAll()
        .antMatchers("/user/**").permitAll()
        .antMatchers("/event/**").permitAll()
        .antMatchers("/register/**").permitAll()
        .antMatchers("/upload/**").permitAll()
        .and()
        .authorizeRequests().anyRequest().fullyAuthenticated()
        .and()
        .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
        .authenticationProvider(tokenAuthProvider)
        .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class)
        .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowCredentials(true);
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
