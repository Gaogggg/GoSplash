package edu.pku.gg.gosplash.main.model;

import java.util.ArrayList;
import java.util.List;


import edu.pku.gg.gosplash.common.data.Collection;
import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.common.service.CollectionService;
import edu.pku.gg.gosplash.common.service.PhotoService;
import rx.Subscriber;

/**
 * Created by gaoge
 */
public class MainModel implements IMainModel{

    private Subscriber subscriber;
    private CollectionOnListener mCollectionOnListener;
    private boolean loadMore = false;
    private List<Collection> filterCollections;


    public MainModel(CollectionOnListener mCollectionOnListener) {
        this.mCollectionOnListener = mCollectionOnListener;
    }


    public void getAllCollections(int page){

        subscriber = new Subscriber<List<Collection>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                    mCollectionOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Collection> collections) {
                filterCollections = new ArrayList<>();
                for(int i = 0; i <collections.size(); i++) {
                    if(collections.get(i).cover_photo!=null){
                        filterCollections.add(collections.get(i));
                    }
                }
                if(!loadMore) {
                    mCollectionOnListener.onSuccess(filterCollections);
                    loadMore = true;
                }else {
                    mCollectionOnListener.onSuccessLoadMore(filterCollections);
                }
            }
        };
        CollectionService.getInstance().getAllCollections(subscriber, page, 10);

    }

    public void getCuratedCollections(int page){
        subscriber = new Subscriber<List<Collection>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                    mCollectionOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Collection> collections) {
                filterCollections = new ArrayList<>();
                for(int i = 0; i <collections.size(); i++) {
                    if(collections.get(i).cover_photo!=null){
                        filterCollections.add(collections.get(i));
                    }
                }

                if(!loadMore) {
                    mCollectionOnListener.onSuccess(filterCollections);
                    loadMore = true;
                }else {
                    mCollectionOnListener.onSuccessLoadMore(filterCollections);
                }
            }
        };
        CollectionService.getInstance().getCuratedCollections(subscriber, page, 10);
    }

    public void getFeaturedCollections(int page){
        subscriber = new Subscriber<List<Collection>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                    mCollectionOnListener.onFailure(e);
            }

            @Override
            public void onNext(List<Collection> collections) {
                filterCollections = new ArrayList<>();
                for(int i = 0; i <collections.size(); i++) {
                    if(collections.get(i).cover_photo!=null){
                        filterCollections.add(collections.get(i));
                    }
                }

                if(!loadMore) {
                    mCollectionOnListener.onSuccess(filterCollections);
                    loadMore = true;
                }else {
                    mCollectionOnListener.onSuccessLoadMore(filterCollections);
                }
            }
        };
        CollectionService.getInstance().getFeaturedCollections(subscriber, page, 10);
    }










    /**
     *回调接口
     */
    public interface CollectionOnListener{
        void onSuccess(List<Collection> s);
        void onFailure(Throwable e);
        void onSuccessLoadMore(List<Collection> s);
    }


}
