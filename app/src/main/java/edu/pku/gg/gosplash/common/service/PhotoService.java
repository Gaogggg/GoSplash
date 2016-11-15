package edu.pku.gg.gosplash.common.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.pku.gg.gosplash.common.Constants;
import edu.pku.gg.gosplash.common.api.PhotoApi;
import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.common.data.PhotoDetails;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gaoge
 */
public class PhotoService {
    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private PhotoApi photoApi;

    private PhotoService() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.UNSPLASH_API_BASE_URL)
                .build();

        photoApi = retrofit.create(PhotoApi.class);
    }

    private static class SingletonHolder{
        private static final PhotoService _instance = new PhotoService();
    }

    public static PhotoService getInstance(){
        return SingletonHolder._instance;
    }

    public void getNewPhotos(Subscriber<List<Photo>> subscriber, int page, int per_page){
        photoApi.getPhotos(page, per_page,PhotoApi.ORDER_BY_LATEST)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getPopularPhotos(Subscriber<List<Photo>> subscriber, int page, int per_page){
        photoApi.getPhotos(page, per_page,PhotoApi.ORDER_BY_POPULAR)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getOldPhotos(Subscriber<List<Photo>> subscriber, int page, int per_page){
        photoApi.getPhotos(page, per_page,PhotoApi.ORDER_BY_OLDEST)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getCollectionPhotos(Subscriber<List<Photo>> subscriber, int id,int page, int per_page){
        photoApi.getCollectionPhotos(id ,page, per_page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getCuratedCollectionPhotos(Subscriber<List<Photo>> subscriber, int id,int page, int per_page){
        photoApi.getCuratedCollectionPhotos(id ,page, per_page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void searchPhotos(Subscriber<List<Photo>> subscriber, String query,int page, int per_page){
        photoApi.searchPhotos(query,PhotoApi.PORTRAIT_ORIENTATION ,page, per_page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getAPhoto(Subscriber<PhotoDetails> subscriber, String id){
        photoApi.getAPhoto(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
