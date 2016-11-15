package edu.pku.gg.gosplash.collection.model;

import java.util.List;

import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.common.service.PhotoService;
import rx.Subscriber;

/**
 * Created by gaoge
 */
public class CollectionModel implements ICollectionModel{

    private Subscriber subscriber;
    private CollectionPhotoOnListener mCollectionPhotoOnListener;
    private boolean loadMore = false;

    public CollectionModel(CollectionPhotoOnListener mCollectionPhotoOnListener) {
        this.mCollectionPhotoOnListener = mCollectionPhotoOnListener;
    }


    public void getCollectionPhotos(int id,int page){

        subscriber = new Subscriber<List<Photo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mCollectionPhotoOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Photo> photos) {

                if(!loadMore) {
                    mCollectionPhotoOnListener.onSuccess(photos);
                    loadMore = true;
                }else {
                    mCollectionPhotoOnListener.onSuccessLoadMore(photos);
                }
            }
        };
        PhotoService.getInstance().getCollectionPhotos(subscriber, id, page, 10);

    }

    public void getCuratedCollectionPhotos(int id,int page){

        subscriber = new Subscriber<List<Photo>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mCollectionPhotoOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Photo> photos) {

                if(!loadMore) {
                    mCollectionPhotoOnListener.onSuccess(photos);
                    loadMore = true;
                }else {
                    mCollectionPhotoOnListener.onSuccessLoadMore(photos);
                }
            }
        };
        PhotoService.getInstance().getCuratedCollectionPhotos(subscriber, id, page, 10);

    }




    public interface CollectionPhotoOnListener{
        void onSuccess(List<Photo> s);
        void onFailure(Throwable e);
        void onSuccessLoadMore(List<Photo> s);
    }

}
