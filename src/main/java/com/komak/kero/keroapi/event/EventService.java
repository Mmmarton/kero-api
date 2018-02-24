package com.komak.kero.keroapi.event;

import com.komak.kero.keroapi.error.InvalidOperationException;
import com.komak.kero.keroapi.event.model.EventDeleteModel;
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

  public void delete(EventDeleteModel eventDelete) {
    String authorId = eventRepository.findOne(eventDelete.getEventId()).getAuthorId();
    if (authorId.equals(eventDelete.getIssuerId()) || eventDelete.isAdmin()) {
      eventRepository.delete(eventDelete.getEventId());
    }
    else {
      throw new InvalidOperationException("Can't delete other user's event.");
    }
  }
}
