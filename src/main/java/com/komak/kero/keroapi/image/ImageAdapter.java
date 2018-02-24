package com.komak.kero.keroapi.image;

import com.komak.kero.keroapi.image.model.ImageListModel;

public class ImageAdapter {

  public static ImageListModel toListModel(Image image) {
    ImageListModel model = new ImageListModel();
    model.setId(image.getId());
    model.setAuthorId(image.getAuthorId());
    model.setImagePath(image.getImagePath());
    return model;
  }
}
