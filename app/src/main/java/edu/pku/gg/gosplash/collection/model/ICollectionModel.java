package edu.pku.gg.gosplash.collection.model;

/**
 * Created by gaoge
 */
public interface ICollectionModel {

    void getCollectionPhotos(int id, int page);

    void getCuratedCollectionPhotos(int id, int page);

}
