package com.komak.kero.keroapi.error;

public class InvalidUserException extends RuntimeException {

  private String id;

  public InvalidUserException(String id) {
    super();
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
