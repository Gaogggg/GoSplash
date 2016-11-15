package edu.pku.gg.gosplash.main.presenter;

import edu.pku.gg.gosplash.IBasePresenter;

/**
 * Created by gaoge
 */
public interface IMainPresenter extends IBasePresenter {

    void getCollections(int page);

    void getCuratedCollections(int page);

    void getFeaturedCollections(int page);
}
