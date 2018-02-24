package com.komak.kero.keroapi.event.model;

import com.komak.kero.keroapi.auth.Role;
import com.komak.kero.keroapi.auth.UserSession;

public class EventDeleteModel {

  private String eventId;
  private String issuerId;
  private boolean isAdmin;

  public EventDeleteModel(String eventId, UserSession session) {
    this.eventId = eventId;
    this.issuerId = session.getId();
    this.isAdmin = session.getRole() == Role.ROLE_ADMIN;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public String getIssuerId() {
    return issuerId;
  }

  public void setIssuerId(String issuerId) {
    this.issuerId = issuerId;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }
}
