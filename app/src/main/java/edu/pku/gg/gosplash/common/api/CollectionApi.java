package edu.pku.gg.gosplash.common.api;

import java.util.List;

import edu.pku.gg.gosplash.common.data.Collection;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by gaoge
 */
public interface CollectionApi {
    @Headers("Authorization: Client-ID eddf9dcb1dc1969acd0ce60ded64f84c56a2e382c59bb97a6a5e7b177483f1e6")
    @GET("collections")
    Observable<List<Collection>> getAllCollections(@Query("page") int page,
                                                   @Query("per_page") int per_page);

    @Headers("Authorization: Client-ID eddf9dcb1dc1969acd0ce60ded64f84c56a2e382c59bb97a6a5e7b177483f1e6")
    @GET("collections/curated")
    Observable<List<Collection>> getCuratedCollections(@Query("page") int page,
                                                 @Query("per_page") int per_page);

    @Headers("Authorization: Client-ID eddf9dcb1dc1969acd0ce60ded64f84c56a2e382c59bb97a6a5e7b177483f1e6")
    @GET("collections/featured")
    Observable<List<Collection>> getFeaturedCollections(@Query("page") int page,
                                                  @Query("per_page") int per_page);

    @Headers("Authorization: Client-ID eddf9dcb1dc1969acd0ce60ded64f84c56a2e382c59bb97a6a5e7b177483f1e6")
    @GET("users/{username}/collections")
    Observable<List<Collection>> getUserCollections(@Path("username") String username,
                                              @Query("page") int page,
                                              @Query("per_page") int per_page);

}
