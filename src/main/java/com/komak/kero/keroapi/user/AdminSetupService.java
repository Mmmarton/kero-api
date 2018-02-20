package com.komak.kero.keroapi.user;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminSetupService {

  private PasswordEncoder passwordEncoder;
  private UserRepository userRepository;

  @Autowired
  public AdminSetupService(PasswordEncoder passwordEncoder, UserRepository userRepository)
      throws IOException {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;

    if (userRepository.count() == 0) {
      Resource resource = new ClassPathResource("admins");
      InputStream resourceInputStream = resource.getInputStream();
      Scanner scanner = new Scanner(resourceInputStream);
      while (scanner.hasNextLine()) {
        insertUser(scanner.nextLine());
      }
    }
  }

  private void insertUser(String email) {
    String password = passwordEncoder.encode("secret");
    User user = new User();
    user.setEmail(email);
    user.setUsername(RandomStringUtils.random(10));
    user.setPassword(password);
    userRepository.save(user);
  }
}
