package com.komak.kero.keroapi.image;

import com.komak.kero.keroapi.error.FileException;
import com.komak.kero.keroapi.image.model.ImageCreateModel;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
public class ImageFileService {

  private static final Logger LOG = Logger.getLogger(ImageFileService.class);
  private static final int PROFILE_SIZE = 500;
  private static final int PREVIEW_SIZE = 500;

  @Value("${images.folder}")
  private String imagesFolder;

  @Value("${profile.pictures.folder}")
  private String profilePicturesFolder;

  public String savePicture(MultipartFile picture) {
    String filename = RandomStringUtils.randomAlphanumeric(30) + ".jpg";

    InputStream inputStream;
    try {
      inputStream = picture.getInputStream();
    }
    catch (IOException e) {
      LOG.error("File operation error", e);
      throw new FileException("Invalid picture.");
    }
    createScaledFile(profilePicturesFolder, filename, inputStream);
    return filename;
  }

  public void deletePicture(String picturePath) {
    deleteFile(profilePicturesFolder, picturePath);
  }

  public byte[] getPicture(String picturePath) {
    return getFile(profilePicturesFolder, picturePath, false);
  }

  public String saveImage(ImageCreateModel imageCreateModel) {
    String filename =
        imageCreateModel.getEventId() + "/" + RandomStringUtils.randomAlphanumeric(30) + ".jpg";

    InputStream inputStream;
    try {
      inputStream = imageCreateModel.getImageFile().getInputStream();
    }
    catch (IOException e) {
      LOG.error("File operation error", e);
      throw new FileException("Invalid image.");
    }
    createFile(imagesFolder, filename, inputStream);
    return filename;
  }

  public void deleteImage(String imagePath) {
    deleteFile(imagesFolder, imagePath);
  }

  public byte[] getImage(String imagePath) {
    return getFile(imagesFolder, imagePath, true);
  }

  public byte[] getFullImage(String imagePath) {
    return getFile(imagesFolder, imagePath, false);
  }

  public void createDirectory(String path) {
    try {
      Path eventPath = Paths.get(imagesFolder + path);
      if (!Files.exists(eventPath)) {
        Files.createDirectory(eventPath);
      }
    }
    catch (Exception e) {
      LOG.error("File operation error", e);
      throw new FileException("Failed to create event directory.");
    }
  }

  private void createFile(String folder, String filePath, InputStream stream) {
    try {
      Path file = Paths.get(folder + filePath);
      Files.write(file, toJPG(stream, false));
    }
    catch (Exception e) {
      LOG.error("File operation error", e);
      throw new FileException("Failed to save image.");
    }
  }

  private void createScaledFile(String folder, String filePath, InputStream stream) {
    try {
      Path file = Paths.get(folder + filePath);
      Files.write(file, toJPG(stream, true));
    }
    catch (Exception e) {
      LOG.error("File operation error", e);
      throw new FileException("Failed to save image.");
    }
  }

  private void deleteFile(String folder, String filePath) {
    try {
      Files.delete(Paths.get(folder + filePath));
    }
    catch (IOException e) {
      LOG.error("File operation error", e);
      throw new FileException("Failed to delete image.");
    }
  }

  private byte[] getFile(String folder, String filePath, boolean scaledown) {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try {
      byte[] bytes;
      if (scaledown) {
        BufferedImage bufferedImage = ImageIO
            .read(Files.newInputStream(Paths.get(folder + filePath)));
        bufferedImage = scale(bufferedImage, PROFILE_SIZE);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);
        bytes = outputStream.toByteArray();
      }
      else {
        bytes = Files.readAllBytes(Paths.get(folder + filePath));
      }
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

  private byte[] toJPG(InputStream inputStream, boolean scaleDown) {
    BufferedImage bufferedImage;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    try {
      bufferedImage = ImageIO.read(inputStream);
      if (scaleDown) {
        bufferedImage = scale(bufferedImage, PROFILE_SIZE);
      }
      ImageIO.write(bufferedImage, "jpg", outputStream);
    }
    catch (IOException e) {
      LOG.error("File operation error", e);
      throw new FileException("Failed to convert profile picture.");
    }
    return outputStream.toByteArray();
  }

  private BufferedImage scale(BufferedImage bufferedImage, double size) {
    double boundary = Math.max(bufferedImage.getHeight(), bufferedImage.getWidth());
    if (boundary > size) {
      double scale = size / boundary;
      AffineTransform tx = new AffineTransform();
      tx.scale(scale, scale);
      AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
      BufferedImage last = new BufferedImage((int) (bufferedImage.getWidth() * scale),
          (int) (bufferedImage.getHeight() * scale), bufferedImage.getType());
      return op.filter(bufferedImage, last);
    }
    return bufferedImage;
  }
}
