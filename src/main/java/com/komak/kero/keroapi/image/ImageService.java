package com.komak.kero.keroapi.image;

import com.komak.kero.keroapi.FileService;
import com.komak.kero.keroapi.error.UnauthorisedException;
import com.komak.kero.keroapi.image.model.ImageCreateModel;
import com.komak.kero.keroapi.image.model.ImageDeleteModel;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  FileService fileService;

  private Random random = new Random();

  public Image create(ImageCreateModel imageCreateModel) {
    String imagePath = fileService.saveImage(imageCreateModel);
    Image image = new Image();
    image.setImagePath(imagePath);
    image.setAuthorId(imageCreateModel.getAuthorId());
    image.setEventId(imageCreateModel.getEventId());
    return imageRepository.save(image);
  }

  public Image delete(ImageDeleteModel imageDelete) {
    Image image = imageRepository.findOne(imageDelete.getImageId());
    if (image.getAuthorId().equals(imageDelete.getIssuerId()) || imageDelete.isAdmin()) {
      imageRepository.delete(imageDelete.getImageId());
      fileService.deleteImage(image.getImagePath());
    }
    else {
      throw new UnauthorisedException("Can't delete other user's image.");
    }
    return image;
  }

  public byte[] getImagePreview(String path) {
    return fileService.getImagePreview(path);
  }

  public byte[] getFullImage(String path) {
    return fileService.getFullImage(path);
  }

  public List<Image> list(String eventId) {
    return imageRepository.findAllByEventId(eventId);
  }

  public void deleteRelated(String eventId) {
    imageRepository.findAllByEventId(eventId).forEach(i -> imageRepository.delete(i.getId()));
  }

  public Set<String> randomList(String eventId) {
    List<Image> list = imageRepository.findAllByEventId(eventId);
    Set<String> result = new HashSet<>();
    for (int i = 0; i < Math.min(3, list.size()); i++) {
      result.add(list.get(random.nextInt(list.size())).getImagePath());
    }
    return result;
  }
}
