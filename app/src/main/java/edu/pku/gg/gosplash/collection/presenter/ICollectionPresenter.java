package edu.pku.gg.gosplash.collection.presenter;

import edu.pku.gg.gosplash.IBasePresenter;

/**
 * Created by gaoge
 */
public interface ICollectionPresenter extends IBasePresenter{

    void getCollectionPhotos(int id,int page);

    void getCuratedCollectionPhotos(int id,int page);

}
