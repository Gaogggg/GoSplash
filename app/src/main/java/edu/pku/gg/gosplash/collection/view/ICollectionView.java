package edu.pku.gg.gosplash.collection.view;

import android.view.View;

import java.util.List;

import edu.pku.gg.gosplash.IBaseView;
import edu.pku.gg.gosplash.collection.presenter.CollectionPresenter;
import edu.pku.gg.gosplash.common.data.Photo;

/**
 * Created by gaoge
 */
public interface ICollectionView extends IBaseView<CollectionPresenter>{

    void showLoading();

    void loadingSuccess(List<Photo> list);

    void loadingFailed();

    void init();

    void initAdapter();

    void loadingMoreSuccess(List<Photo> list);
}
