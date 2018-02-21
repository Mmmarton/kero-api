package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.validation.FieldErrorMessage;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

class UserCreateModel {

  @NotEmpty(message = FieldErrorMessage.EMPTY)
  @Length(min = 8, max = 30, message = FieldErrorMessage.INVALID_LENGTH)
  private String username;
  @NotEmpty(message = FieldErrorMessage.EMPTY)
  @Length(min = 12, max = 30, message = FieldErrorMessage.INVALID_LENGTH)
  private String password;
  @NotEmpty(message = FieldErrorMessage.EMPTY)
  @Length(min = 5, max = 20, message = FieldErrorMessage.INVALID_LENGTH)
  private String inviteCode;
  @NotEmpty(message = FieldErrorMessage.EMPTY)
  @Length(min = 3, max = 20, message = FieldErrorMessage.INVALID_LENGTH)
  private String nickname;
  @NotEmpty(message = FieldErrorMessage.EMPTY)
  @Length(min = 5, max = 50, message = FieldErrorMessage.INVALID_LENGTH)
  private String email;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getInviteCode() {
    return inviteCode;
  }

  public void setInviteCode(String inviteCode) {
    this.inviteCode = inviteCode;
  }

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

  @Override
  public String toString() {
    return "UserCreateModel{" +
        "username='" + username + '\'' +
        ", password='" + password + '\'' +
        ", nickname='" + nickname + '\'' +
        ", email='" + email +
        '}';
  }
}
