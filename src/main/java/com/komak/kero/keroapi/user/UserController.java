package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.auth.AuthService;
import com.komak.kero.keroapi.auth.Role;
import com.komak.kero.keroapi.auth.UserSession;
import com.komak.kero.keroapi.user.model.UserCreateModel;
import com.komak.kero.keroapi.user.model.UserInviteModel;
import com.komak.kero.keroapi.user.model.UserListModel;
import com.komak.kero.keroapi.user.model.UserRoleModel;
import com.komak.kero.keroapi.user.model.UserUpdateModel;
import com.komak.kero.keroapi.validation.FieldErrorMessage;
import com.komak.kero.keroapi.validation.UserUpdateModelValidator;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private AuthService authService;

  @Autowired
  UserUpdateModelValidator userUpdateModelValidator;

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ResponseEntity<Object> register(@RequestBody @Valid UserCreateModel user) {

    userService.create(UserAdapter.toUser(user), user.getInviteCode());

    return new ResponseEntity("Done.", HttpStatus.OK);
  }

  @RequestMapping(value = "/invite", method = RequestMethod.POST)
  public ResponseEntity<Object> invite(@RequestBody @Valid UserInviteModel user) {
    UserSession session = authService.getSession();
    if (session.getRole() == Role.ROLE_ADMIN) {
      userService.invite(UserAdapter.toUser(user));
      return new ResponseEntity("Done.", HttpStatus.OK);
    }
    else {
      return new ResponseEntity("Not authorised.", HttpStatus.UNAUTHORIZED);
    }
  }

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public ResponseEntity<Object> list() {
    UserSession session = authService.getSession();
    if (session.getRole() == Role.ROLE_ADMIN) {
      List<UserListModel> list = userService.list().stream().map(UserAdapter::toListModel)
          .collect(Collectors.toList());
      return new ResponseEntity(list, HttpStatus.OK);
    }
    else {
      return new ResponseEntity("Not authorised.", HttpStatus.UNAUTHORIZED);
    }
  }

  @RequestMapping(value = "/role", method = RequestMethod.PUT)
  public ResponseEntity<Object> update(@RequestBody @Valid UserRoleModel userRole) {
    UserSession session = authService.getSession();
    if (session.getRole() == Role.ROLE_ADMIN) {
      userService.changeRole(userRole);
      return new ResponseEntity("Done.", HttpStatus.OK);
    }
    else {
      return new ResponseEntity("Not authorised.", HttpStatus.UNAUTHORIZED);
    }
  }

  @RequestMapping(value = "/", method = RequestMethod.PUT)
  public ResponseEntity<Object> update(
      @RequestBody @Valid UserUpdateModel user, BindingResult result) {

    userUpdateModelValidator.validate(user, result);
    if (result.hasFieldErrors()) {
      List<FieldErrorMessage> fieldErrors = result.getFieldErrors().stream()
          .map(FieldErrorMessage::new).collect(Collectors.toList());
      return new ResponseEntity(fieldErrors, HttpStatus.BAD_REQUEST);
    }

    UserSession session = authService.getSession();
    if (!session.getEmail().equals(user.getEmail())) {
      return new ResponseEntity("Not authorised.", HttpStatus.UNAUTHORIZED);
    }
    userService.update(user);
    return new ResponseEntity("Done.", HttpStatus.OK);
  }

  @RequestMapping(value = "/picture", method = RequestMethod.PUT)
  public ResponseEntity<Object> updatePicture(
      @RequestParam("picture") MultipartFile picture) {
    UserSession session = authService.getSession();
    userService.updatePicture(picture, session.getEmail());
    return new ResponseEntity("Done.", HttpStatus.OK);
  }

  @RequestMapping(value = "/picture/{email:.+}", method = RequestMethod.GET)
  byte[] getPicture(@PathVariable("email") String email) {
    return userService.getPicture(email);
  }

  @RequestMapping(value = "/{email:.+}", method = RequestMethod.DELETE)
  public ResponseEntity<Object> delete(@PathVariable("email") String email) {
    UserSession session = authService.getSession();
    if (session.getRole() == Role.ROLE_ADMIN) {
      userService.delete(email);
      return new ResponseEntity("Done.", HttpStatus.OK);
    }
    else {
      return new ResponseEntity("Not authorised.", HttpStatus.UNAUTHORIZED);
    }
  }
}