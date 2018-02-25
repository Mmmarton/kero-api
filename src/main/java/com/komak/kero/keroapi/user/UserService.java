package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.auth.Credentials;
import com.komak.kero.keroapi.auth.Role;
import com.komak.kero.keroapi.error.InvalidInvitationException;
import com.komak.kero.keroapi.error.UnauthorisedException;
import com.komak.kero.keroapi.error.InvalidUserException;
import com.komak.kero.keroapi.error.NoInvitationException;
import com.komak.kero.keroapi.image.ImageFileService;
import com.komak.kero.keroapi.user.model.UserRoleModel;
import com.komak.kero.keroapi.user.model.UserUpdateModel;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private Md5PasswordEncoder usernameEncoder;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private InvitationMailService mailService;

  @Autowired
  private ImageFileService imageFileService;

  @Value("auth.user.salt")
  private String userSalt;

  public User getByCredentials(Credentials credentials) {
    User user = userRepository.findByUsername(
        usernameEncoder.encodePassword(credentials.getUsername(), userSalt));
    if (user != null && passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
      return user;
    }
    return null;
  }

  public void create(User user, String inviteCode) {
    user.setUsername(usernameEncoder.encodePassword(user.getUsername(), userSalt));
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User invitedUser = userRepository.findByEmail(user.getEmail());
    if (invitedUser == null) {
      throw new NoInvitationException();
    }

    if (!invitedUser.getUsername().equals(inviteCode)) {
      throw new InvalidInvitationException();
    }
    userRepository.save(merge(invitedUser, user));
  }

  public void invite(User user) {
    user.setUsername(RandomStringUtils.randomAlphanumeric(20));
    userRepository.save(user);
    mailService.sendInvitation(user.getEmail(), user.getUsername());
  }

  public long getUserCount() {
    return userRepository.count();
  }

  public List<User> list() {
    return userRepository.list();
  }

  public void changeRole(UserRoleModel userRole) {
    User user = userRepository.findByEmail(userRole.getEmail());
    if (user.getRole() != Role.ROLE_ADMIN) {
      user.setRole(userRole.getRole());
      userRepository.save(user);
    }
    else {
      throw new UnauthorisedException("Can't change an admin's role.");
    }
  }

  public void delete(String email) {
    User user = userRepository.findByEmail(email);
    if (user.getRole() != Role.ROLE_ADMIN) {
      userRepository.delete(user.getId());
    }
    else {
      throw new UnauthorisedException("Can't remove an admin.");
    }
  }

  public void update(UserUpdateModel newUser) {
    User user = userRepository.findByEmail(newUser.getEmail());
    if (newUser.getOldPassword() != null || newUser.getPassword() != null) {
      if (!passwordEncoder.matches(newUser.getOldPassword(), user.getPassword())) {
        throw new UnauthorisedException("Wrong password.");
      }
      newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    }
    userRepository.save(update(user, newUser));
  }

  private User update(User oldUser, UserUpdateModel newUser) {
    if (newUser.getLastName() != null) {
      oldUser.setLastName(newUser.getLastName());
    }
    if (newUser.getFirstName() != null) {
      oldUser.setFirstName(newUser.getFirstName());
    }
    if (newUser.getNickname() != null) {
      oldUser.setNickname(newUser.getNickname());
    }
    if (newUser.getPassword() != null) {
      oldUser.setPassword(newUser.getPassword());
    }
    return oldUser;
  }

  private User merge(User oldUser, User newUser) {
    oldUser.setNickname(newUser.getNickname());
    oldUser.setUsername(newUser.getUsername());
    oldUser.setPassword(newUser.getPassword());
    if (oldUser.getRole() == null) {
      oldUser.setRole(Role.ROLE_GUEST);
    }

    return oldUser;
  }

  public void updatePicture(MultipartFile picture, String email) {
    User user = userRepository.findByEmail(email);
    if (user.getPicture() != null) {
      imageFileService.deletePicture(user.getPicture());
    }
    user.setPicture(imageFileService.savePicture(picture));
    userRepository.save(user);
  }

  public byte[] getPicture(String email) {
    User user = userRepository.findByEmail(email);
    byte[] picture = null;
    if (user == null) {
      throw new InvalidUserException();
    }
    if (user.getPicture() != null) {
      picture = imageFileService.getPicture(user.getPicture());
    }
    return picture;
  }
}
