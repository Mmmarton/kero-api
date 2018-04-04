package com.komak.kero.keroapi.error;

public class NoInvitationException extends RuntimeException {

  private String email;

  public NoInvitationException(String email) {
    super();
    this.email = email;
  }

  public String getEmail() {
    return email;
  }
}
