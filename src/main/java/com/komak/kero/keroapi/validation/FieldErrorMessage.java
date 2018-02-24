package com.komak.kero.keroapi.validation;

import java.util.Objects;
import org.springframework.validation.FieldError;

public class FieldErrorMessage {

  public static final String EMPTY = "EMPTY";
  public static final String INVALID_LENGTH = "INVALID_LENGTH";
  public static final String DUPLICATE = "DUPLICATE";
  public static final String NULL = "NULL";

  private String field;
  private String error;

  public FieldErrorMessage() {
  }

  public FieldErrorMessage(FieldError error) {
    field = error.getField();
    this.error = error.getDefaultMessage();
  }

  public FieldErrorMessage(String field, String error) {
    this.field = field;
    this.error = error;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FieldErrorMessage that = (FieldErrorMessage) o;
    return Objects.equals(field, that.field) &&
        Objects.equals(error, that.error);
  }

  @Override
  public int hashCode() {

    return Objects.hash(field, error);
  }

  @Override
  public String toString() {
    return "FieldErrorMessage{" +
        "field='" + field + '\'' +
        ", error='" + error + '\'' +
        '}';
  }
}
