package com.komak.kero.keroapi.event;

import com.komak.kero.keroapi.error.InvalidEventException;
import com.komak.kero.keroapi.error.UnauthorisedException;
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
      throw new UnauthorisedException("Can't delete other user's event.");
    }
  }

  public void update(Event event) {
    Event oldEvent = eventRepository.findOne(event.getId());
    if (oldEvent == null) {
      throw new InvalidEventException();
    }
    eventRepository.save(update(oldEvent, event));
  }

  private Event update(Event oldEvent, Event newEvent) {
    oldEvent.setTitle(newEvent.getTitle());
    oldEvent.setDate(newEvent.getDate());
    if (newEvent.getDescription() != null) {
      oldEvent.setDescription(newEvent.getDescription());
    }
    return oldEvent;
  }
}
