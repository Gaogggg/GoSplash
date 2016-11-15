package edu.pku.gg.gosplash.photo.view;

import android.view.View;

import java.util.List;

import edu.pku.gg.gosplash.IBaseView;
import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.photo.presenter.PhotoPresenter;

/**
 * Created by gaoge
 */
public interface IPhotoView extends IBaseView<PhotoPresenter> {

    void showLoading();

    void loadingSuccess(List<Photo> list);

    void loadingFailed();

    void init(View v);

    void initAdapter();

    void loadingMoreSuccess(List<Photo> list);

}
