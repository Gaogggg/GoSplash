package edu.pku.gg.gosplash.photo.presenter;

import edu.pku.gg.gosplash.IBasePresenter;

/**
 * Created by gaoge
 */
public interface IPhotoPresenter extends IBasePresenter {

    void getNewPhotos(int page);

    void getPopularPhotos(int page);

    void getOldPhotos(int page);
}
