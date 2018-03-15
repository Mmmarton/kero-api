package com.komak.kero.keroapi.user.model;

import com.komak.kero.keroapi.auth.Role;

public class UserRoleModel {

  private String id;
  private Role role;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
