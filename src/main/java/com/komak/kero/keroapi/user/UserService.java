package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.auth.Credentials;
import com.komak.kero.keroapi.auth.Role;
import com.komak.kero.keroapi.error.InvalidInvitationException;
import com.komak.kero.keroapi.error.InvalidOperationException;
import com.komak.kero.keroapi.error.NoInvitationException;
import com.komak.kero.keroapi.user.model.UserRoleModel;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

  public User getByCredentials(Credentials credentials) {
    User user = userRepository.findByUsername(
        usernameEncoder.encodePassword(credentials.getUsername(), credentials.getPassword()));
    if (user != null && passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
      return user;
    }
    return null;
  }

  public void create(User user, String inviteCode) {
    user.setUsername(usernameEncoder.encodePassword(user.getUsername(), user.getPassword()));
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

  private User merge(User oldUser, User newUser) {
    oldUser.setNickname(newUser.getNickname());
    oldUser.setUsername(newUser.getUsername());
    oldUser.setPassword(newUser.getPassword());
    if (oldUser.getRole() == null) {
      oldUser.setRole(Role.ROLE_GUEST);
    }

    return oldUser;
  }

  public void changeRole(UserRoleModel userRole) {
    User user = userRepository.findByEmail(userRole.getEmail());
    if (user.getRole() != Role.ROLE_ADMIN) {
      user.setRole(userRole.getRole());
      userRepository.save(user);
    }
    else {
      throw new InvalidOperationException("Can't change an admin's role.");
    }
  }

  public void delete(String email) {
    User user = userRepository.findByEmail(email);
    if (user.getRole() != Role.ROLE_ADMIN) {
      userRepository.delete(user.getId());
    }
    else {
      throw new InvalidOperationException("Can't remove an admin.");
    }
  }
}
