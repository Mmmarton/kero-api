package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.auth.Role;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

@Service
public class AdminSetupService {

  @Autowired
  public AdminSetupService(UserService userService)
      throws IOException {

    if (userService.getUserCount() == 0) {
      Resource resource = new ClassPathResource("admins");
      InputStream resourceInputStream = resource.getInputStream();
      Scanner scanner = new Scanner(resourceInputStream);
      while (scanner.hasNextLine()) {
        User user = new User();
        user.setEmail(scanner.nextLine());
        user.setRole(Role.ROLE_ADMIN);
        userService.invite(user);
      }
      resourceInputStream.close();
    }
  }
}
