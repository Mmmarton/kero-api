package com.komak.kero.keroapi.auth;

import java.util.Objects;

public class UserSession {

  private String email;
  private Role role;

  public UserSession() {
  }

  public UserSession(String email, Role role) {
    this.email = email;
    this.role = role;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserSession user = (UserSession) o;
    return Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email);
  }
}
