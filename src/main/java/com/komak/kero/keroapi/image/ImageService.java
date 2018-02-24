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

  public void create(ImageCreateModel imageCreateModel) {
    String imagePath = imageFileService.saveImage(imageCreateModel);
    Image image = new Image();
    image.setImagePath(imagePath);
    image.setAuthorId(imageCreateModel.getAuthorId());
    imageRepository.save(image);
  }

  public List<Image> list() {
    return imageRepository.findAll();
  }

  public void delete(ImageDeleteModel imageDelete) {
    String authorId = imageRepository.findOne(imageDelete.getImageId()).getAuthorId();
    if (authorId.equals(imageDelete.getIssuerId()) || imageDelete.isAdmin()) {
      imageRepository.delete(imageDelete.getImageId());
    }
    else {
      throw new UnauthorisedException("Can't delete other user's image.");
    }
  }
}
