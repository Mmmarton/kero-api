package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.error.FileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfilePictureService {

  @Value("${profile.picture.location}")
  private String directory;

  public String saveImage(MultipartFile picture) {
    String extension = picture.getOriginalFilename().split("\\.")[1];
    String filename = RandomStringUtils.randomAlphanumeric(30) + "." + extension;

    try {
      Path file = Paths.get(directory + filename);
      Files.write(file, picture.getBytes());
    }
    catch (Exception e) {
      throw new FileException("Failed to save profile picture.");
    }

    return filename;
  }

  public void deleteImage(String picture) {
    try {
      Files.delete(Paths.get(directory + picture));
    }
    catch (IOException e) {
      throw new FileException("Failed to delete profile picture.");
    }
  }
}
