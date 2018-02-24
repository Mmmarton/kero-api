package com.komak.kero.keroapi.event;

import com.komak.kero.keroapi.event.model.EventCreateModel;

public class EventAdapter {

  public static Event toEvent(EventCreateModel model) {
    Event event = new Event();
    event.setTitle(model.getTitle());
    event.setDate(model.getDate());
    return event;
  }

}
