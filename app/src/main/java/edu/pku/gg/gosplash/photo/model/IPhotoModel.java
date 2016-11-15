package edu.pku.gg.gosplash.photo.model;

/**
 * Created by gaoge
 */
public interface IPhotoModel {
    void getNewPhotos(int page);

    void getPopularPhotos(int page);

    void getOldPhotos(int page);
}
