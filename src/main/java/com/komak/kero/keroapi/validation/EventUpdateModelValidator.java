package com.komak.kero.keroapi.validation;

import com.komak.kero.keroapi.event.model.EventUpdateModel;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class EventUpdateModelValidator implements Validator {

  public boolean supports(Class clazz) {
    return EventUpdateModel.class.isAssignableFrom(clazz);
  }

  public void validate(Object target, Errors errors) {
    EventUpdateModel event = (EventUpdateModel) target;
    if (event.getDescription() != null) {
      if (event.getDescription().length() < 10 || event.getDescription().length() > 1000) {
        errors.rejectValue("description", null, FieldErrorMessage.INVALID_LENGTH);
      }
    }
    if (event.getTitle() == null) {
      errors.rejectValue("title", null, FieldErrorMessage.EMPTY);
    }
    else if (event.getTitle().length() < 3 || event.getTitle().length() > 50) {
      errors.rejectValue("title", null, FieldErrorMessage.INVALID_LENGTH);
    }
    if (event.getId() == null) {
      errors.rejectValue("id", null, FieldErrorMessage.EMPTY);
    }
    if (event.getDate() == null) {
      errors.rejectValue("date", null, FieldErrorMessage.EMPTY);
    }
  }

}
