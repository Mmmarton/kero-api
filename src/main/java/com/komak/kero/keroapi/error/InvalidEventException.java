package com.komak.kero.keroapi.error;

public class InvalidEventException extends RuntimeException {

  private String id;

  public InvalidEventException(String id) {
    super();
    this.id = id;
  }

  public String getId() {
    return id;
  }
}
