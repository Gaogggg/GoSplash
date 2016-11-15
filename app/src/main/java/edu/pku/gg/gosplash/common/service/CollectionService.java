package edu.pku.gg.gosplash.common.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.pku.gg.gosplash.common.Constants;
import edu.pku.gg.gosplash.common.api.CollectionApi;
import edu.pku.gg.gosplash.common.data.Collection;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by gaoge
 */
public class CollectionService {

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private CollectionApi collectionApi;

    private CollectionService() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.UNSPLASH_API_BASE_URL)
                .build();

        collectionApi = retrofit.create(CollectionApi.class);
    }

    private static class SingletonHolder{
        private static final CollectionService _instance = new CollectionService();
    }

    public static CollectionService getInstance(){
        return SingletonHolder._instance;
    }

    public void getAllCollections(Subscriber<List<Collection>> subscriber, int page, int per_page){
        collectionApi.getAllCollections(page, per_page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getCuratedCollections(Subscriber<List<Collection>> subscriber, int page, int per_page){
        collectionApi.getCuratedCollections(page, per_page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getFeaturedCollections(Subscriber<List<Collection>> subscriber, int page, int per_page){
        collectionApi.getFeaturedCollections(page, per_page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
