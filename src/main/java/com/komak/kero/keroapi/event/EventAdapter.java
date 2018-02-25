package com.komak.kero.keroapi.event;

import com.komak.kero.keroapi.event.model.EventCreateModel;
import com.komak.kero.keroapi.event.model.EventUpdateModel;
import com.komak.kero.keroapi.event.model.EventViewModel;

public class EventAdapter {

  public static Event toEvent(EventCreateModel model) {
    Event event = new Event();
    event.setTitle(model.getTitle());
    event.setDate(model.getDate());
    event.setAuthorId(model.getAuthorId());
    return event;
  }

  public static Event toEvent(EventUpdateModel model) {
    Event event = new Event();
    event.setId(model.getId());
    event.setTitle(model.getTitle());
    event.setDate(model.getDate());
    event.setDescription(model.getDescription());
    return event;
  }

  public static EventViewModel toListModel(Event event) {
    EventViewModel model = new EventViewModel();
    model.setId(event.getId());
    model.setAuthorId(event.getAuthorId());
    model.setTitle(event.getTitle());
    model.setDate(event.getDate());
    model.setDescription(event.getDescription());
    return model;
  }

}
