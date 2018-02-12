package com.komak.kero.keroapi.user;

import java.util.Objects;

public class UserViewModel {

  private String token;
  private String nickname;
  private String firstName;
  private String lastName;
  private String email;
  private String picture;

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
