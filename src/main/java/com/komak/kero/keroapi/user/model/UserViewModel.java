package com.komak.kero.keroapi.user.model;

import com.komak.kero.keroapi.auth.Role;
import java.util.Objects;

public class UserViewModel {

  private String id;
  private String token;
  private String nickname;
  private String firstName;
  private String lastName;
  private String email;
  private String picture;
  private Role role;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
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
    UserViewModel that = (UserViewModel) o;
    return Objects.equals(token, that.token) &&
        Objects.equals(email, that.email);
  }

  @Override
  public int hashCode() {

    return Objects.hash(token, email);
  }
}
