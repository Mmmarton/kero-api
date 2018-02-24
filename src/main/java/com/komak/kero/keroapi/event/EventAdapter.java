package com.komak.kero.keroapi.event;

import com.komak.kero.keroapi.event.model.EventCreateModel;
import com.komak.kero.keroapi.event.model.EventListModel;

public class EventAdapter {

  public static Event toEvent(EventCreateModel model) {
    Event event = new Event();
    event.setTitle(model.getTitle());
    event.setDate(model.getDate());
    event.setAuthorId(model.getAuthorId());
    return event;
  }

  public static EventListModel toListModel(Event event) {
    EventListModel model = new EventListModel();
    model.setId(event.getId());
    model.setTitle(event.getTitle());
    model.setDate(event.getDate());
    model.setDescription(event.getDescription());
    return model;
  }

}
