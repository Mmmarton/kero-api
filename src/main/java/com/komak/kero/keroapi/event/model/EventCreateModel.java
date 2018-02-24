package com.komak.kero.keroapi.event.model;

import com.komak.kero.keroapi.validation.FieldErrorMessage;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class EventCreateModel {

  @NotEmpty(message = FieldErrorMessage.EMPTY)
  @Length(min = 3, max = 40, message = FieldErrorMessage.INVALID_LENGTH)
  private String title;
  private long date;
  private String authorId;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public long getDate() {
    return date;
  }

  public void setDate(long date) {
    this.date = date;
  }

  public String getAuthorId() {
    return authorId;
  }

  public void setAuthorId(String authorId) {
    this.authorId = authorId;
  }
}
