package com.komak.kero.keroapi.event;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Event {

  @Id
  private String id;
  @Indexed(unique = true)
  private String title;
  private Long date;
  private String description;
  private String authorId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getDate() {
    return date;
  }

  public void setDate(Long date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }
}
