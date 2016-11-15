package edu.pku.gg.gosplash.main.view;

import android.view.View;

import java.util.List;

import edu.pku.gg.gosplash.IBaseView;
import edu.pku.gg.gosplash.common.data.Collection;
import edu.pku.gg.gosplash.main.presenter.MainPresenter;

/**
 * Created by gaoge
 */
public interface IMainView extends IBaseView<MainPresenter>{

    void showLoading();

    void loadingSuccess(List<Collection> list);

    void loadingFailed();

    void init(View v);

    void initAdapter();

    void loadingMoreSuccess(List<Collection> list);

}
