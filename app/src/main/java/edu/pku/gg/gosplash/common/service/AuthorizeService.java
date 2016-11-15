package edu.pku.gg.gosplash.common.service;

import java.util.concurrent.TimeUnit;

import edu.pku.gg.gosplash.common.Constants;
import edu.pku.gg.gosplash.common.api.AuthorizeApi;
import edu.pku.gg.gosplash.common.api.CollectionApi;
import edu.pku.gg.gosplash.common.data.AccessToken;
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
public class AuthorizeService {

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private AuthorizeApi authorizeApi;

    private AuthorizeService() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constants.UNSPLASH_AUTH_BASE_URL)
                .build();

        authorizeApi = retrofit.create(AuthorizeApi.class);
    }

    private static class SingletonHolder{
        private static final AuthorizeService _instance = new AuthorizeService();
    }

    public static AuthorizeService getInstance(){
        return SingletonHolder._instance;
    }

    public void getAccessToken(Subscriber<AccessToken> subscriber, String client_id, String client_secret,String redirect_uri,
                               String code,String grant_type){
        authorizeApi.getAccessToken(client_id, client_secret, redirect_uri, code, grant_type)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
