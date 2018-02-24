package com.komak.kero.keroapi.user.model;

import com.komak.kero.keroapi.auth.Role;

public class UserListModel {

  private String nickname;
  private String email;
  private Role role;

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
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
}
