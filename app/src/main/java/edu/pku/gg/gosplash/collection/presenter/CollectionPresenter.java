package edu.pku.gg.gosplash.collection.presenter;

import java.util.List;

import edu.pku.gg.gosplash.collection.model.CollectionModel;
import edu.pku.gg.gosplash.collection.model.ICollectionModel;
import edu.pku.gg.gosplash.collection.view.ICollectionView;
import edu.pku.gg.gosplash.common.data.Photo;

/**
 * Created by gaoge
 */
public class CollectionPresenter implements ICollectionPresenter, CollectionModel.CollectionPhotoOnListener{

    private ICollectionModel mICollectionModel;
    private ICollectionView mICollectionView;

    public CollectionPresenter(ICollectionView iCollectionView) {

        mICollectionView = iCollectionView;
        mICollectionModel = new CollectionModel(this);
        mICollectionView.setPresenter(this);
    }

    @Override
    public void start(){
        mICollectionView.showLoading();
    }

    @Override
    public void getCollectionPhotos(int id, int page){
        mICollectionModel.getCollectionPhotos(id,page);
    }

    @Override
    public void getCuratedCollectionPhotos(int id, int page){
        mICollectionModel.getCuratedCollectionPhotos(id,page);
    }



    public void onSuccess(List<Photo> photos){
        mICollectionView.loadingSuccess(photos);
    }

    public void onSuccessLoadMore(List<Photo> photos){
        mICollectionView.loadingMoreSuccess(photos);
    }


    public void onFailure(Throwable e){
        mICollectionView.loadingFailed();
    }

}
