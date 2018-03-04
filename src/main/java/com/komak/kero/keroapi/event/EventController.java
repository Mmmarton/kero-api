package com.komak.kero.keroapi.event;

import com.komak.kero.keroapi.auth.AuthService;
import com.komak.kero.keroapi.auth.Role;
import com.komak.kero.keroapi.auth.UserSession;
import com.komak.kero.keroapi.event.model.EventCreateModel;
import com.komak.kero.keroapi.event.model.EventDeleteModel;
import com.komak.kero.keroapi.event.model.EventUpdateModel;
import com.komak.kero.keroapi.event.model.EventViewModel;
import com.komak.kero.keroapi.image.ImageService;
import com.komak.kero.keroapi.validation.EventUpdateModelValidator;
import com.komak.kero.keroapi.validation.FieldErrorMessage;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/api/event")
public class EventController {

  @Autowired
  private EventService eventService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private AuthService authService;

  @Autowired
  private EventUpdateModelValidator eventUpdateModelValidator;

  @RequestMapping(value = "/", method = RequestMethod.POST)
  public ResponseEntity<Object> create(@RequestBody @Valid EventCreateModel eventCreateModel) {
    UserSession session = authService.getSession();
    if (session.getRole() == Role.ROLE_ADMIN || session.getRole() == Role.ROLE_MEMBER) {
      eventCreateModel.setAuthorId(session.getId());
      String eventId = eventService.create(EventAdapter.toEvent(eventCreateModel));
      return new ResponseEntity(eventId, HttpStatus.OK);
    }
    else {
      return new ResponseEntity("Not authorised.", HttpStatus.UNAUTHORIZED);
    }
  }

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public ResponseEntity<Object> update(@RequestBody EventUpdateModel eventUpdateModel,
      BindingResult result) {
    eventUpdateModelValidator.validate(eventUpdateModel, result);
    if (result.hasFieldErrors()) {
      List<FieldErrorMessage> fieldErrors = result.getFieldErrors().stream()
          .map(FieldErrorMessage::new).collect(Collectors.toList());
      return new ResponseEntity(fieldErrors, HttpStatus.BAD_REQUEST);
    }

    UserSession session = authService.getSession();
    if (session.getRole() == Role.ROLE_ADMIN || session.getRole() == Role.ROLE_MEMBER) {
      eventService.update(EventAdapter.toEvent(eventUpdateModel));
      return new ResponseEntity("Done.", HttpStatus.OK);
    }
    else {
      return new ResponseEntity("Not authorised.", HttpStatus.UNAUTHORIZED);
    }
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity<Object> getList() {
    List<EventViewModel> list = eventService.list().stream().map(EventAdapter::toListModel)
        .collect(Collectors.toList());
    return new ResponseEntity(list, HttpStatus.OK);
  }

  @RequestMapping(value = "/{eventId}", method = RequestMethod.GET)
  public ResponseEntity<Object> get(@PathVariable String eventId) {
    EventViewModel model = EventAdapter.toListModel(eventService.getEvent(eventId));
    return new ResponseEntity(model, HttpStatus.OK);
  }

  @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
  public ResponseEntity<Object> delete(@PathVariable String eventId) {
    UserSession session = authService.getSession();
    EventDeleteModel eventDelete = new EventDeleteModel(eventId, session);
    eventService.delete(eventDelete);
    imageService.deleteRelated(eventId);
    return new ResponseEntity("Done.", HttpStatus.OK);
  }

}
