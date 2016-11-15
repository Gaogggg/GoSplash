package edu.pku.gg.gosplash.search.view;

import java.util.List;

import edu.pku.gg.gosplash.IBaseView;
import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.search.presenter.SearchPresenter;

/**
 * Created by gaoge_sx on 2016/10/26.
 */
public interface ISearchView extends IBaseView<SearchPresenter>{

    void showLoading();

    void loadingSuccess(List<Photo> list);

    void loadingFailed();

    void init();

    void initAdapter();

    void loadingMoreSuccess(List<Photo> list);

}
