package com.komak.kero.keroapi.auth;

import com.komak.kero.keroapi.user.User;
import com.komak.kero.keroapi.user.UserService;
import com.komak.kero.keroapi.user.UserViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
class AuthService {

    @Autowired
    private UserService userService;

    private ConcurrentHashMap<String, User> tokens = new ConcurrentHashMap<>();
    private TokenGenerator tokenGenerator;

    public AuthService() {
        tokenGenerator = new TokenGenerator(25, TokenGenerator.ALPHANUMERIC);
    }

    public UserViewModel authenticate(Credentials credentials) {
        User user = userService.getByCredentials(credentials);
        String token;
        if (user == null) {
            return null;
        }
        if (tokens.contains(user)) {
            tokens.values().remove(user);
        }

        token = tokenGenerator.getToken();
        tokens.put(token, user);
        System.out.println(tokens.size());
        UserViewModel userViewModel = new UserViewModel(user);
        userViewModel.setToken(token);
        return userViewModel;
    }

    public boolean isAuthenticated(String token) {
        return tokens.get(token) != null;
    }

}
