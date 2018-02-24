package com.komak.kero.keroapi.image.model;

import com.komak.kero.keroapi.auth.Role;
import com.komak.kero.keroapi.auth.UserSession;

public class ImageDeleteModel {

  private String imageId;
  private String issuerId;
  private boolean isAdmin;

  public ImageDeleteModel(String imageId, UserSession session) {
    this.imageId = imageId;
    this.issuerId = session.getId();
    this.isAdmin = session.getRole() == Role.ROLE_ADMIN;
  }

  public String getImageId() {
    return imageId;
  }

  public void setImageId(String imageId) {
    this.imageId = imageId;
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
