package edu.pku.gg.gosplash.photo.presenter;

import java.util.List;

import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.photo.model.IPhotoModel;
import edu.pku.gg.gosplash.photo.model.PhotoModel;
import edu.pku.gg.gosplash.photo.view.IPhotoView;

/**
 * Created by gaoge_sx on 2016/10/24.
 */
public class PhotoPresenter implements IPhotoPresenter, PhotoModel.PhotoOnListener {
    private IPhotoModel mIPhotoModel;
    private IPhotoView mIPhotoView;

    public PhotoPresenter(IPhotoView iPhotoView) {

        mIPhotoView = iPhotoView;
        mIPhotoModel = new PhotoModel(this);
        mIPhotoView.setPresenter(this);
    }


    @Override
    public void start(){
        mIPhotoView.showLoading();
    }

    @Override
    public void getNewPhotos(int page){
        mIPhotoModel.getNewPhotos(page);
    }

    @Override
    public void getPopularPhotos(int page){
        mIPhotoModel.getPopularPhotos(page);
    }

    @Override
    public void getOldPhotos(int page){
        mIPhotoModel.getOldPhotos(page);
    }

    public void onSuccess(List<Photo> photos){
        mIPhotoView.loadingSuccess(photos);
    }

    public void onSuccessLoadMore(List<Photo> photos){
        mIPhotoView.loadingMoreSuccess(photos);
    }


    public void onFailure(Throwable e){
        mIPhotoView.loadingFailed();
    }
}
