package com.komak.kero.keroapi.image;

import com.komak.kero.keroapi.error.UnauthorisedException;
import com.komak.kero.keroapi.image.model.ImageCreateModel;
import com.komak.kero.keroapi.image.model.ImageDeleteModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  ImageFileService imageFileService;

  public Image create(ImageCreateModel imageCreateModel) {
    String imagePath = imageFileService.saveImage(imageCreateModel);
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
      imageFileService.deleteImage(image.getImagePath());
    }
    else {
      throw new UnauthorisedException("Can't delete other user's image.");
    }
    return image;
  }

  public byte[] getImagePreview(String path) {
    return imageFileService.getImagePreview(path);
  }

  public byte[] getFullImage(String path) {
    return imageFileService.getFullImage(path);
  }

  public List<Image> list(String eventId) {
    return imageRepository.findAllByEventId(eventId);
  }
}
