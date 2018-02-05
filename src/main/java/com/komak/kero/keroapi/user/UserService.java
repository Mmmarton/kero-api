package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.auth.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User getByCredentials(Credentials credentials) {
        User user = userRepository.findByUsername(credentials.getUsername());
        if (passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            return user;
        }
        return null;
    }

    public void create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
