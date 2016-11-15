package edu.pku.gg.gosplash.main.presenter;

import android.support.annotation.NonNull;


import java.util.List;

import edu.pku.gg.gosplash.common.data.Collection;
import edu.pku.gg.gosplash.main.model.IMainModel;
import edu.pku.gg.gosplash.main.model.MainModel;
import edu.pku.gg.gosplash.main.view.IMainView;

/**
 * Created by gaoge
 */
public class MainPresenter implements IMainPresenter, MainModel.CollectionOnListener{

    private IMainModel mIMainModel;
    private IMainView mIMainView;

    public MainPresenter(IMainView iMainView) {

        mIMainView = iMainView;
        mIMainModel = new MainModel(this);
        mIMainView.setPresenter(this);
    }


    @Override
    public void start(){
        mIMainView.showLoading();
    }

    @Override
    public void getCollections(int page){
        mIMainModel.getAllCollections(page);
    }

    @Override
    public void getCuratedCollections(int page){
        mIMainModel.getCuratedCollections(page);
    }

    @Override
    public void getFeaturedCollections(int page){
        mIMainModel.getFeaturedCollections(page);
    }

    public void onSuccess(List<Collection> collections){
        mIMainView.loadingSuccess(collections);
    }

    public void onSuccessLoadMore(List<Collection> collections){
        mIMainView.loadingMoreSuccess(collections);
    }


    public void onFailure(Throwable e){
            mIMainView.loadingFailed();
    }

}
