package com.komak.kero.keroapi.auth;

import com.komak.kero.keroapi.user.User;
import com.komak.kero.keroapi.user.UserAdapter;
import com.komak.kero.keroapi.user.UserService;
import com.komak.kero.keroapi.user.UserViewModel;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  @Autowired
  private UserService userService;

  private ConcurrentHashMap<String, UserSession> tokens = new ConcurrentHashMap<>();
  private TokenGenerator tokenGenerator;

  public AuthService(@Value("${auth.token.length}") int tokenLength) {
    tokenGenerator = new TokenGenerator(tokenLength, TokenGenerator.ALPHANUMERIC);
  }

  public UserViewModel authenticate(Credentials credentials) {
    User user = userService.getByCredentials(credentials);
    if (user == null) {
      return null;
    }
    UserSession session = UserAdapter.toSession(user);
    if (tokens.contains(session)) {
      tokens.values().remove(session);
    }

    String token = tokenGenerator.getToken();
    tokens.put(token, session);
    UserViewModel userViewModel = UserAdapter.toViewModel(user);
    userViewModel.setToken(token);
    return userViewModel;
  }

  public UserSession getSession() {
    return (UserSession) SecurityContextHolder.getContext().getAuthentication().getCredentials();
  }

  public UserSession getSession(String token) {
    return tokens.get(token);
  }

}
