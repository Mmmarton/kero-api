package com.komak.kero.keroapi.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
class TokenAuthProvider implements AuthenticationProvider {

  @Autowired
  private AuthService authService;

  @Override
  public Authentication authenticate(Authentication auth)
      throws AuthenticationException {

    String token = auth.getCredentials().toString();
    UserSession session = authService.getSession(token);

    if (session == null) {
      throw new BadCredentialsException("Invalid token " + token);
    }

    UserAuthentication userAuthentication = new UserAuthentication(session);
    userAuthentication.setAuthenticated(true);

    return userAuthentication;
  }

  @Override
  public boolean supports(Class<?> arg0) {
    return (UserAuthentication.class.isAssignableFrom(arg0));

  }
}