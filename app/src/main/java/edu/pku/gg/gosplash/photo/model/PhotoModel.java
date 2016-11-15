package edu.pku.gg.gosplash.photo.model;

import java.util.List;

import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.common.service.PhotoService;
import rx.Subscriber;

/**
 * Created by gaoge
 */
public class PhotoModel implements IPhotoModel {
    private Subscriber subscriber;
    private PhotoOnListener mPhotoOnListener;
    private boolean loadMore = false;

    public PhotoModel(PhotoOnListener mPhotoOnListener) {
        this.mPhotoOnListener = mPhotoOnListener;
    }


    public void getNewPhotos(int page){

        subscriber = new Subscriber<List<Photo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mPhotoOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Photo> photos) {

                if(!loadMore) {
                    mPhotoOnListener.onSuccess(photos);
                    loadMore = true;
                }else {
                    mPhotoOnListener.onSuccessLoadMore(photos);
                }
            }
        };
        PhotoService.getInstance().getNewPhotos(subscriber, page, 10);

    }


    public void getPopularPhotos(int page){

        subscriber = new Subscriber<List<Photo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mPhotoOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Photo> photos) {

                if(!loadMore) {
                    mPhotoOnListener.onSuccess(photos);
                    loadMore = true;
                }else {
                    mPhotoOnListener.onSuccessLoadMore(photos);
                }
            }
        };
        PhotoService.getInstance().getPopularPhotos(subscriber, page, 10);

    }

    public void getOldPhotos(int page){

        subscriber = new Subscriber<List<Photo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mPhotoOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Photo> photos) {

                if(!loadMore) {
                    mPhotoOnListener.onSuccess(photos);
                    loadMore = true;
                }else {
                    mPhotoOnListener.onSuccessLoadMore(photos);
                }
            }
        };
        PhotoService.getInstance().getOldPhotos(subscriber, page, 10);

    }






    public interface PhotoOnListener{
        void onSuccess(List<Photo> s);
        void onFailure(Throwable e);
        void onSuccessLoadMore(List<Photo> s);
    }

}
