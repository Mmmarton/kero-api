package com.komak.kero.keroapi.user.model;

import com.komak.kero.keroapi.validation.FieldErrorMessage;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UserInviteModel {

  @NotEmpty(message = FieldErrorMessage.EMPTY)
  @Length(min = 3, max = 20, message = FieldErrorMessage.INVALID_LENGTH)
  private String firstName;
  @NotEmpty(message = FieldErrorMessage.EMPTY)
  @Length(min = 5, max = 50, message = FieldErrorMessage.INVALID_LENGTH)
  private String email;

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
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
        ", firstName='" + firstName + '\'' +
        ", email='" + email + '\'' +
        '}';
  }
}
