package com.komak.kero.keroapi.image;

import com.komak.kero.keroapi.error.UnauthorisedException;
import com.komak.kero.keroapi.image.model.ImageCreateModel;
import com.komak.kero.keroapi.image.model.ImageDeleteModel;
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
    return imageRepository.save(image);
  }

  public void delete(ImageDeleteModel imageDelete) {
    Image image = imageRepository.findOne(imageDelete.getImageId());
    if (image.getAuthorId().equals(imageDelete.getIssuerId()) || imageDelete.isAdmin()) {
      imageRepository.delete(imageDelete.getImageId());
      imageFileService.deleteImage(image.getImagePath());
    }
    else {
      throw new UnauthorisedException("Can't delete other user's image.");
    }
  }

  public byte[] getImageFile(String path) {
    return imageFileService.getImage(path);
  }
}
