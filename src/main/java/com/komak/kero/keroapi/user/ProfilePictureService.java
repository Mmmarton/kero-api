package com.komak.kero.keroapi.user;

import com.komak.kero.keroapi.error.FileException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProfilePictureService {

  private static final Logger LOG = Logger.getLogger(ProfilePictureService.class);

  @Value("${profile.pictures.folder}")
  private String profilePicturesFolder;

  public String savePicture(MultipartFile picture) {
    String filename = RandomStringUtils.randomAlphanumeric(30) + ".jpg";

    try {
      Path file = Paths.get(profilePicturesFolder + filename);
      Files.write(file, toJPG(picture.getInputStream()));
    }
    catch (Exception e) {
      LOG.error("Failed to save profile picture.", e);
      throw new FileException("Failed to save profile picture.");
    }

    return filename;
  }

  public void deletePicture(String picture) {
    try {
      Files.delete(Paths.get(profilePicturesFolder + picture));
    }
    catch (IOException e) {
      LOG.error("Failed to delete profile picture.", e);
      throw new FileException("Failed to delete profile picture.");
    }
  }

  public byte[] getPicture(String picture) {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try {
      byte[] bytes = Files.readAllBytes(Paths.get(profilePicturesFolder + picture));
      bytes = Base64.encodeBase64(bytes);

      output.write(("data:image/jpg;base64,").getBytes());
      output.write(bytes);
    }
    catch (IOException e) {
      LOG.error("Failed to read profile picture.", e);
      throw new FileException("Failed to read profile picture.");

    }
    return output.toByteArray();
  }

  private byte[] toJPG(InputStream inputStream) {
    BufferedImage bufferedImage;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    try {
      bufferedImage = ImageIO.read(inputStream);
      BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
          bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
      newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
      ImageIO.write(newBufferedImage, "jpg", outputStream);
    }
    catch (IOException e) {
      LOG.error("Failed to convert profile picture.", e);
      throw new FileException("Failed to convert profile picture.");
    }
    return outputStream.toByteArray();
  }
}
