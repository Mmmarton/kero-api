package com.komak.kero.keroapi.image.model;

import org.springframework.web.multipart.MultipartFile;

public class ImageCreateModel {

  private String eventId;
  private MultipartFile imageFile;
  private String authorId;

  public ImageCreateModel() {
  }

  public ImageCreateModel(String eventId, MultipartFile imageFile, String authorId) {
    this.eventId = eventId;
    this.imageFile = imageFile;
    this.authorId = authorId;
  }

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public MultipartFile getImageFile() {
    return imageFile;
  }

  public void setImageFile(MultipartFile imageFile) {
    this.imageFile = imageFile;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }
}
