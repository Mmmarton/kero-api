package com.komak.kero.keroapi.validation;

import com.komak.kero.keroapi.user.model.UserUpdateModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserUpdateModelValidator implements Validator {

  public boolean supports(Class clazz) {
    return UserUpdateModel.class.isAssignableFrom(clazz);
  }

  public void validate(Object target, Errors errors) {
    UserUpdateModel user = (UserUpdateModel) target;
    if (user.getPassword() != null && !user.getPassword().isEmpty()) {
      if (user.getPassword().length() < 12 || user.getPassword().length() > 30) {
        errors.rejectValue("password", null, FieldErrorMessage.INVALID_LENGTH);
      }
      if (user.getOldPassword() == null || user.getOldPassword().isEmpty()) {
        errors.rejectValue("oldPassword", null, FieldErrorMessage.EMPTY);
      }
    }
    if (user.getOldPassword() != null && !user.getOldPassword().isEmpty()) {
      if (user.getOldPassword().length() < 12 || user.getOldPassword().length() > 30) {
        errors.rejectValue("oldPassword", null, FieldErrorMessage.INVALID_LENGTH);
      }
      if (user.getPassword() == null || user.getPassword().isEmpty()) {
        errors.rejectValue("password", null, FieldErrorMessage.EMPTY);
      }
    }
    if (user.getNickname() != null && !user.getNickname().isEmpty()) {
      if (user.getNickname().length() < 3 || user.getNickname().length() > 20) {
        errors.rejectValue("nickname", null, FieldErrorMessage.INVALID_LENGTH);
      }
    }
    if (user.getFirstName() != null && !user.getFirstName().isEmpty()) {
      if (user.getFirstName().length() < 3 || user.getFirstName().length() > 30) {
        errors.rejectValue("firstName", null, FieldErrorMessage.INVALID_LENGTH);
      }
    }
    if (user.getLastName() != null && !user.getLastName().isEmpty()) {
      if (user.getLastName().length() < 3 || user.getLastName().length() > 30) {
        errors.rejectValue("lastName", null, FieldErrorMessage.INVALID_LENGTH);
      }
    }
    if (user.getEmail() != null && !user.getEmail().isEmpty()) {
      if (user.getEmail().length() < 5 || user.getEmail().length() > 50) {
        errors.rejectValue("email", null, FieldErrorMessage.INVALID_LENGTH);
      }
    }
  }

}
