package com.komak.kero.keroapi.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullValidator implements ConstraintValidator<NotNull, Object> {

  public NotNullValidator() {

  }

  public void initialize(NotNull constraint) {
  }

  public boolean isValid(Object field, ConstraintValidatorContext context) {
    return field != null;
  }

}