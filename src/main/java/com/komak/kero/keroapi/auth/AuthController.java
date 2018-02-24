package com.komak.kero.keroapi.auth;

import com.komak.kero.keroapi.user.model.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
class AuthController {

  @Autowired
  private AuthService authService;

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<Object> login(@RequestBody Credentials credentials) {
    UserViewModel user = authService.authenticate(credentials);
    if (user == null) {
      return new ResponseEntity("Invalid credentials.", HttpStatus.UNAUTHORIZED);
    }
    else {
      return new ResponseEntity(user, HttpStatus.OK);
    }
  }

  @RequestMapping(value = "/session", method = RequestMethod.GET)
  public String getSession() {
    return "Here you are.";
  }
}
