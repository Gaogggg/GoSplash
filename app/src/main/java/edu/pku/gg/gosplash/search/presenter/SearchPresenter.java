package edu.pku.gg.gosplash.search.presenter;

import java.util.List;

import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.search.model.ISearchModel;
import edu.pku.gg.gosplash.search.model.SearchModel;
import edu.pku.gg.gosplash.search.view.ISearchView;

/**
 * Created by gaoge
 */
public class SearchPresenter implements ISearchPresenter, SearchModel.SearchOnListener{

    private ISearchModel mISearchModel;
    private ISearchView mISearchView;

    public SearchPresenter(ISearchView iSearchView) {

        mISearchView = iSearchView;
        mISearchModel = new SearchModel(this);
        mISearchView.setPresenter(this);
    }

    @Override
    public void start(){
        mISearchView.showLoading();
    }

    @Override
    public void searchPhotos(String query, int page){
        mISearchModel.searchPhotos(query,page);
    }



    public void onSuccess(List<Photo> photos){
        mISearchView.loadingSuccess(photos);
    }

    public void onSuccessLoadMore(List<Photo> photos){
        mISearchView.loadingMoreSuccess(photos);
    }


    public void onFailure(Throwable e){
        mISearchView.loadingFailed();
    }

}
