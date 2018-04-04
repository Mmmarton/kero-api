package com.komak.kero.keroapi.error;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class ErrorHandler {

  private static final Logger LOG = Logger.getLogger(ErrorHandler.class);

  private static final String INVALID_DATA = "INVALID_DATA";
  private static final String NO_INVITATION = "NO_INVITATION";
  private static final String INVALID_INVITATION = "INVALID_INVITATION";
  private static final String UNAUTHORISED = "UNAUTHORISED";
  private static final String FILE_EXCEPTION = "FILE_EXCEPTION";
  private static final String INVALID_USER = "INVALID_USER";
  private static final String INVALID_EVENT = "INVALID_EVENT";
  private static final String INVALID_FILE = "INVALID_FILE";

  @ExceptionHandler(HttpMessageNotReadableException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception) {
    LOG.warn(INVALID_DATA + " : " + exception.getMessage());
    return new ErrorMessage(HttpStatus.BAD_REQUEST, INVALID_DATA);
  }

  @ExceptionHandler(NoInvitationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleNoInvitationException(
      NoInvitationException exception) {
    LOG.info(NO_INVITATION + " : " + exception.getEmail());
    return new ErrorMessage(HttpStatus.BAD_REQUEST, NO_INVITATION);
  }

  @ExceptionHandler(InvalidInvitationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleInvalidInvitationException(
      InvalidInvitationException exception) {
    LOG.info(INVALID_INVITATION + " : " + exception.getMessage());
    return new ErrorMessage(HttpStatus.BAD_REQUEST, INVALID_INVITATION);
  }

  @ExceptionHandler(UnauthorisedException.class)
  @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
  @ResponseBody
  public ErrorMessage handleUnauthorisedException(
      UnauthorisedException exception) {
    LOG.warn(UNAUTHORISED + " : " + exception.getMessage());
    return new ErrorMessage(HttpStatus.NOT_ACCEPTABLE, UNAUTHORISED);
  }

  @ExceptionHandler(FileException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorMessage handleFileException(
      FileException exception) {
    LOG.error(FILE_EXCEPTION + " : " + exception.getMessage());
    return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR, FILE_EXCEPTION);
  }

  @ExceptionHandler(InvalidUserException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleInvalidUserException(
      InvalidUserException exception) {
    LOG.info(INVALID_USER + " : " + exception.getId());
    return new ErrorMessage(HttpStatus.BAD_REQUEST, INVALID_USER);
  }

  @ExceptionHandler(InvalidEventException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleInvalidEventException(
      InvalidEventException exception) {
    LOG.info(INVALID_EVENT + " : " + exception.getId());
    return new ErrorMessage(HttpStatus.BAD_REQUEST, INVALID_EVENT);
  }

  @ExceptionHandler(MultipartException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleMultipartException(
      MultipartException exception) {
    LOG.info(INVALID_FILE + " : " + exception.getMessage());
    return new ErrorMessage(HttpStatus.BAD_REQUEST, INVALID_FILE);
  }
}
