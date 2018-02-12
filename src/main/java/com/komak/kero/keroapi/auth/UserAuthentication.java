package com.komak.kero.keroapi.auth;

import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
class UserAuthentication implements Authentication {

  private static final long serialVersionUID = 1L;

  private Object credentials;

  public UserAuthentication() {
  }

  public UserAuthentication(Object credentials) {
    this.credentials = credentials;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return new ArrayList<>();
  }

  @Override
  public Object getCredentials() {
    return credentials;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public Object getPrincipal() {
    return null;
  }

  @Override
  public boolean isAuthenticated() {
    return false;
  }

  @Override
  public void setAuthenticated(boolean authenticated) {
  }
}