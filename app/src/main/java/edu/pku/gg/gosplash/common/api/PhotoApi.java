package edu.pku.gg.gosplash.common.api;

import java.util.List;

import edu.pku.gg.gosplash.common.data.Photo;
import edu.pku.gg.gosplash.common.data.PhotoDetails;
import edu.pku.gg.gosplash.common.data.PhotoStats;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by gaoge
 */
public interface PhotoApi {
    // data.
    String ORDER_BY_LATEST = "latest";
    String ORDER_BY_OLDEST = "oldest";
    String ORDER_BY_POPULAR = "popular";

    String LANDSCAPE_ORIENTATION = "landscape";
    String PORTRAIT_ORIENTATION = "portrait";
    String SQUARE_ORIENTATION = "square";

    /** <br> interface. */
    @Headers("Authorization: Client-ID eddf9dcb1dc1969acd0ce60ded64f84c56a2e382c59bb97a6a5e7b177483f1e6")
    @GET("photos")
    Observable<List<Photo>> getPhotos(@Query("page") int page,
                                      @Query("per_page") int per_page,
                                      @Query("order_by") String order_by);

    @Headers("Authorization: Client-ID eddf9dcb1dc1969acd0ce60ded64f84c56a2e382c59bb97a6a5e7b177483f1e6")
    @GET("photos/curated")
    Observable<List<Photo>> getCuratedPhotos(@Query("page") int page,
                                       @Query("per_page") int per_page,
                                       @Query("order_by") String order_by);

    @Headers("Authorization: Client-ID eddf9dcb1dc1969acd0ce60ded64f84c56a2e382c59bb97a6a5e7b177483f1e6")
    @GET("photos/search")
    Observable<List<Photo>> searchPhotos(@Query("query") String query,
                                   @Query("orientation") String orientation,
                                   @Query("page") int page,
                                   @Query("per_page") int per_page);

    @GET("photos/{id}/stats")
    Observable<PhotoStats> getPhotoStats(@Path("id") String id);

    @GET("categories/{id}/photos")
    Observable<List<Photo>> getPhotosInAGivenCategory(@Path("id") int id,
                                                @Query("page") int page,
                                                @Query("per_page") int per_page);

    @Headers("Authorization: Client-ID eddf9dcb1dc1969acd0ce60ded64f84c56a2e382c59bb97a6a5e7b177483f1e6")
    @GET("photos/{id}")
    Observable<PhotoDetails> getAPhoto(@Path("id") String id);

    @GET("users/{username}/photos")
    Observable<List<Photo>> getUserPhotos(@Path("username") String username,
                                    @Query("page") int page,
                                    @Query("per_page") int per_page,
                                    @Query("order_by") String order_by);

    @GET("users/{username}/likes")
    Observable<List<Photo>> getUserLikes(@Path("username") String username,
                                   @Query("page") int page,
                                   @Query("per_page") int per_page,
                                   @Query("order_by") String order_by);

    @Headers("Authorization: Client-ID eddf9dcb1dc1969acd0ce60ded64f84c56a2e382c59bb97a6a5e7b177483f1e6")
    @GET("collections/{id}/photos")
    Observable<List<Photo>> getCollectionPhotos(@Path("id") int id,
                                          @Query("page") int page,
                                          @Query("per_page") int per_page);

    @Headers("Authorization: Client-ID eddf9dcb1dc1969acd0ce60ded64f84c56a2e382c59bb97a6a5e7b177483f1e6")
    @GET("collections/curated/{id}/photos")
    Observable<List<Photo>> getCuratedCollectionPhotos(@Path("id") int id,
                                                 @Query("page") int page,
                                                 @Query("per_page") int per_page);
}
