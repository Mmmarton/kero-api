package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.auth.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${encoder.salt}")
    private String salt;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Md5PasswordEncoder usernameEncoder;

    @Autowired
    private UserRepository userRepository;

    public User getByCredentials(Credentials credentials) {
        User user = userRepository.findByUsername(usernameEncoder.encodePassword(credentials.getUsername(), salt));
        if (user != null && passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            return user;
        }
        return null;
    }

    public void create(User user) {
        user.setUsername(usernameEncoder.encodePassword(user.getUsername(), salt));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
