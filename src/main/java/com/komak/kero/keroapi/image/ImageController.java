package com.komak.kero.keroapi.image;

import com.komak.kero.keroapi.auth.AuthService;
import com.komak.kero.keroapi.auth.Role;
import com.komak.kero.keroapi.auth.UserSession;
import com.komak.kero.keroapi.event.Event;
import com.komak.kero.keroapi.event.EventService;
import com.komak.kero.keroapi.image.model.ImageCreateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/image")
public class ImageController {

  @Autowired
  private ImageService imageService;

  @Autowired
  private AuthService authService;

  @Autowired
  private EventService eventService;

  @RequestMapping(value = "/{eventId}", method = RequestMethod.POST)
  public ResponseEntity<Object> create(@PathVariable String eventId,
      @RequestParam("image") MultipartFile imageFile) {
    UserSession session = authService.getSession();
    if (session.getRole() == Role.ROLE_ADMIN || session.getRole() == Role.ROLE_MEMBER) {
      Event event = eventService.getEvent(eventId);
      ImageCreateModel model = new ImageCreateModel(eventId, imageFile, session.getId());
      Image image = imageService.create(model);
      eventService.addImage(event, image);
      return new ResponseEntity("Done.", HttpStatus.OK);
    }
    else {
      return new ResponseEntity("Not authorised.", HttpStatus.UNAUTHORIZED);
    }
  }

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public ResponseEntity<Object> getList() {
    //    List<EventListModel> list = imageService.list().stream().map(ImageAdapter::toListModel)
    //        .collect(Collectors.toList());
    //    return new ResponseEntity(list, HttpStatus.OK);
    return new ResponseEntity("Done.", HttpStatus.OK);
  }

  @RequestMapping(value = "/{eventId}", method = RequestMethod.DELETE)
  public ResponseEntity<Object> delete(@PathVariable String eventId) {
    //    UserSession session = authService.getSession();
    //    EventDeleteModel eventDelete = new EventDeleteModel(eventId, session);
    //    imageService.delete(eventDelete);
    return new ResponseEntity("Done.", HttpStatus.OK);
  }

}
