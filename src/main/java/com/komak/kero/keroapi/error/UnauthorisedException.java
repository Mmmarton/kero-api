package com.komak.kero.keroapi.error;

public class UnauthorisedException extends RuntimeException {

  public UnauthorisedException(String message) {
    super(message);
  }
}
