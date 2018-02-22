package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.auth.AuthService;
import com.komak.kero.keroapi.auth.Role;
import com.komak.kero.keroapi.auth.UserSession;
import com.komak.kero.keroapi.user.model.UserCreateModel;
import com.komak.kero.keroapi.user.model.UserDeleteModel;
import com.komak.kero.keroapi.user.model.UserInviteModel;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private AuthService authService;

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
      return new ResponseEntity(UserAdapter.toUserListModel(userService.list()), HttpStatus.OK);
    }
    else {
      return new ResponseEntity("Not authorised.", HttpStatus.UNAUTHORIZED);
    }
  }

  @RequestMapping(value = "/", method = RequestMethod.DELETE)
  public ResponseEntity<Object> delete(@RequestBody UserDeleteModel user) {
    UserSession session = authService.getSession();
    if (session.getRole() == Role.ROLE_ADMIN) {
      userService.delete(user.getEmail());
      return new ResponseEntity("Done.", HttpStatus.OK);
    }
    else {
      return new ResponseEntity("Not authorised.", HttpStatus.UNAUTHORIZED);
    }
  }
}