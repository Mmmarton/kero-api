package com.komak.kero.keroapi.user.model;

import com.komak.kero.keroapi.auth.Role;

public class UserRoleModel {

  private String email;
  private Role role;

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
}
