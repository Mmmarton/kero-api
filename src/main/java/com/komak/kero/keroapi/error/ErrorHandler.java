package com.komak.kero.keroapi.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandler {

  private static final String INVALID_DATA = "INVALID_DATA";
  private static final String NO_INVITATION = "NO_INVITATION";
  private static final String INVALID_INVITATION = "INVALID_INVITATION";
  private static final String INVALID_OPERATION = "INVALID_OPERATION";

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception) {
    return new ErrorMessage(HttpStatus.BAD_REQUEST, INVALID_DATA);
  }

  @ExceptionHandler(NoInvitationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleNoInvitationException(
      NoInvitationException exception) {
    return new ErrorMessage(HttpStatus.BAD_REQUEST, NO_INVITATION);
  }

  @ExceptionHandler(InvalidInvitationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleInvalidInvitationException(
      InvalidInvitationException exception) {
    return new ErrorMessage(HttpStatus.BAD_REQUEST, INVALID_INVITATION);
  }

  @ExceptionHandler(InvalidOperationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleInvalidOperationException(
      InvalidOperationException exception) {
    return new ErrorMessage(HttpStatus.NOT_ACCEPTABLE, INVALID_OPERATION);
  }
}
