package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.auth.UserSession;

public class UserAdapter {

  public static UserViewModel toViewModel(User user) {
    UserViewModel model = new UserViewModel();
    model.setNickname(user.getNickname());
    model.setFirstName(user.getFirstName());
    model.setLastName(user.getLastName());
    model.setEmail(user.getEmail());
    model.setPicture(user.getPicture());
    model.setRole(user.getRole());
    return model;
  }

  public static UserViewModel toViewModel(UserCreateModel user) {
    UserViewModel model = new UserViewModel();
    model.setNickname(user.getNickname());
    model.setEmail(user.getEmail());
    return model;
  }

  public static UserSession toSession(User user) {
    UserSession session = new UserSession();
    session.setId(user.getId());
    session.setRole(user.getRole());
    return session;
  }

  public static User toUser(UserCreateModel model) {
    User user = new User();
    user.setUsername(model.getUsername());
    user.setPassword(model.getPassword());
    user.setNickname(model.getNickname());
    user.setEmail(model.getEmail());
    return user;
  }

  public static User toUser(UserInviteModel model) {
    User user = new User();
    user.setNickname(model.getNickname());
    user.setEmail(model.getEmail());
    return user;
  }
}
