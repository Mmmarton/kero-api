package com.komak.kero.keroapi.validation;

import com.mongodb.DuplicateKeyException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ValidationHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public List<FieldErrorMessage> handleValidationError(MethodArgumentNotValidException ex) {
    BindingResult result = ex.getBindingResult();
    List<FieldErrorMessage> errors = new ArrayList<>();
    for (FieldError error : result.getFieldErrors()) {
      errors.add(new FieldErrorMessage(error.getField(), error.getDefaultMessage()));
    }
    return errors;
  }

  @ExceptionHandler(DuplicateKeyException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public FieldErrorMessage handleDuplicateKeyException(DuplicateKeyException ex) {
    String field = ex.getMessage().substring(110).split(" ")[0];
    return new FieldErrorMessage(field, FieldErrorMessage.DUPLICATE);
  }
}
