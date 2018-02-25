package com.komak.kero.keroapi.event;

import com.komak.kero.keroapi.error.InvalidEventException;
import com.komak.kero.keroapi.error.UnauthorisedException;
import com.komak.kero.keroapi.event.model.EventDeleteModel;
import com.komak.kero.keroapi.image.Image;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  @Autowired
  private EventRepository eventRepository;

  public void create(Event event) {
    System.out.println(event.getDate());
    eventRepository.save(event);
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

  public void addImage(Event event, Image image) {
    if (event.getImages() == null) {
      event.setImages(new ArrayList<>());
    }
    event.getImages().add(image);
    eventRepository.save(event);
  }

  public void removeImage(String eventId, Image image) {
    Event event = eventRepository.findOne(eventId);
    if (event == null) {
      throw new InvalidEventException();
    }
    event.getImages().remove(image);
    eventRepository.save(event);
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
