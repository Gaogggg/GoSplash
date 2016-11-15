package edu.pku.gg.gosplash.search.presenter;

import edu.pku.gg.gosplash.IBasePresenter;

/**
 * Created by gaoge_sx on 2016/10/26.
 */
public interface ISearchPresenter extends IBasePresenter{

    void searchPhotos(String query, int page);

}
