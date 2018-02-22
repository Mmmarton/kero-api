package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.auth.UserSession;
import com.komak.kero.keroapi.user.model.UserCreateModel;
import com.komak.kero.keroapi.user.model.UserInviteModel;
import com.komak.kero.keroapi.user.model.UserListModel;
import com.komak.kero.keroapi.user.model.UserViewModel;
import java.util.ArrayList;
import java.util.List;

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
    session.setEmail(user.getEmail());
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

  public static List<UserListModel> toUserListModel(List<User> users) {
    ArrayList<UserListModel> models = new ArrayList<>();

    for (User user : users) {
      UserListModel model = new UserListModel();
      model.setEmail(user.getEmail());
      model.setNickname(user.getNickname());
      model.setPicture(user.getPicture());
      model.setRole(user.getRole());
      models.add(model);
    }

    return models;
  }
}
