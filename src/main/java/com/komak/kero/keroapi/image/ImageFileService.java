package com.komak.kero.keroapi.image;

import com.komak.kero.keroapi.error.FileException;
import com.komak.kero.keroapi.image.model.ImageCreateModel;
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

@Service
public class ImageFileService {

  private static final Logger LOG = Logger.getLogger(ImageFileService.class);

  @Value("${images.folder}")
  private String imagesFolder;

  public String saveImage(ImageCreateModel imageCreateModel) {
    String filename =
        imageCreateModel.getEventId() + "/" + RandomStringUtils.randomAlphanumeric(30) + ".jpg";
    try {
      Path eventPath = Paths.get(imagesFolder + imageCreateModel.getEventId());
      if (!Files.exists(eventPath)) {
        Files.createDirectory(eventPath);
      }
      Path file = Paths.get(imagesFolder + filename);
      Files.write(file, toJPG(imageCreateModel.getImageFile().getInputStream()));
    }
    catch (Exception e) {
      LOG.error("File operation error", e);
      throw new FileException("Failed to save image.");
    }

    return filename;
  }

  public void deleteImage(String imagePath) {
    try {
      Files.delete(Paths.get(imagesFolder + imagePath));
    }
    catch (IOException e) {
      LOG.error("File operation error", e);
      throw new FileException("Failed to delete image.");
    }
  }

  public byte[] getImage(String imagePath) {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try {
      byte[] bytes = Files.readAllBytes(Paths.get(imagesFolder + imagePath));
      bytes = Base64.encodeBase64(bytes);

      output.write(("data:image/jpg;base64,").getBytes());
      output.write(bytes);
    }
    catch (IOException e) {
      LOG.error("File operation error", e);
      throw new FileException("Failed to read image.");

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
      LOG.error("File operation error", e);
      throw new FileException("Failed to convert profile picture.");
    }
    return outputStream.toByteArray();
  }
}
