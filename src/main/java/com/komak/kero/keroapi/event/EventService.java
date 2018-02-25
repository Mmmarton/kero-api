package com.komak.kero.keroapi.event;

import com.komak.kero.keroapi.error.InvalidEventException;
import com.komak.kero.keroapi.error.UnauthorisedException;
import com.komak.kero.keroapi.event.model.EventDeleteModel;
import com.komak.kero.keroapi.image.ImageFileService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

  @Autowired
  private ImageFileService imageFileService;

  public String create(Event event) {
    String eventId = eventRepository.save(event).getId();
    imageFileService.createDirectory(eventId);
    return eventId;
  }

  public List<Event> list() {
    return eventRepository.findAll();
  }

  public void delete(EventDeleteModel eventDelete) {
    Event event = eventRepository.findOne(eventDelete.getEventId());
    if (event == null) {
      throw new InvalidEventException();
    }
    String authorId = event.getAuthorId();
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

  public Event getEvent(String id) {
    Event event;
    if (id == null) {
      throw new InvalidEventException();
    }
    event = eventRepository.findOne(id);
    if (event == null) {
      throw new InvalidEventException();
    }
    return event;
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
