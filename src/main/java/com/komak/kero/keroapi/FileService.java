package com.komak.kero.keroapi;

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
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

  private static final Logger LOG = Logger.getLogger(FileService.class);
  private static final int PROFILE_SIZE = 400;
  private static final int PREVIEW_SIZE = 400;

  @Value("${images.folder}")
  private String imagesFolder;

  @Value("${profile.pictures.folder}")
  private String profilePicturesFolder;

  public String savePicture(MultipartFile picture) {
    String filename = RandomStringUtils.randomAlphanumeric(30) + ".jpg";

    InputStream inputStream;
    try {
      inputStream = picture.getInputStream();
      createScaledFile(profilePicturesFolder, filename, inputStream);
      inputStream.close();
    }
    catch (IOException e) {
      LOG.error("File operation error", e);
      throw new FileException("Invalid picture.");
    }
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
      createFile(imagesFolder, filename, inputStream);
      inputStream.close();
    }
    catch (IOException e) {
      LOG.error("File operation error", e);
      throw new FileException("Invalid image.");
    }
    return filename;
  }

  public void deleteImage(String imagePath) {
    deleteFile(imagesFolder, imagePath);
  }

  public byte[] getImagePreview(String imagePath) {
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

  public void deleteDirectory(String path) {
    Path eventPath = Paths.get(imagesFolder + path);
    try {
      if (Files.exists(eventPath)) {
        FileUtils.deleteDirectory(eventPath.toFile());
      }
    }
    catch (IOException e) {
      e.printStackTrace();
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
    byte[] bytes;
    try {
      if (scaledown) {
        InputStream inputStream = Files.newInputStream(Paths.get(folder + filePath));
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        bufferedImage = scale(bufferedImage, PREVIEW_SIZE);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "jpg", outputStream);
        bytes = outputStream.toByteArray();
        outputStream.close();
        inputStream.close();
      }
      else {
        bytes = Files.readAllBytes(Paths.get(folder + filePath));
      }
    }
    catch (IOException e) {
      LOG.error("File operation error", e);
      throw new FileException("Failed to read image.");

    }
    return bytes;
  }

  private byte[] toJPG(InputStream inputStream, boolean scaleDown) {
    BufferedImage bufferedImage;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    byte[] bytes;

    try {
      bufferedImage = ImageIO.read(inputStream);
      if (scaleDown) {
        bufferedImage = scale(bufferedImage, PROFILE_SIZE);
      }
      ImageIO.write(bufferedImage, "jpg", outputStream);
      bytes = outputStream.toByteArray();
      outputStream.close();
    }
    catch (IOException e) {
      LOG.error("File operation error", e);
      throw new FileException("Failed to convert profile picture.");
    }
    return bytes;
  }

  private BufferedImage scale(BufferedImage bufferedImage, double size) {
    double boundary = Math.max(bufferedImage.getHeight(), bufferedImage.getWidth());
    if (boundary > size) {
      double scale = size / boundary;
      AffineTransform tx = new AffineTransform();
      tx.scale(scale, scale);
      AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
      BufferedImage last = new BufferedImage((int) (bufferedImage.getWidth() * scale),
          (int) (bufferedImage.getHeight() * scale), bufferedImage.getType());
      return op.filter(bufferedImage, last);
    }
    return bufferedImage;
  }
}
