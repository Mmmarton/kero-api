package com.komak.kero.keroapi.event;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

  public void create(Event event) {
    eventRepository.save(event);
  }

  public List<Event> list() {
    return eventRepository.findAll();
  }
}
