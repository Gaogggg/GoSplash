package edu.pku.gg.gosplash.common.api;



import edu.pku.gg.gosplash.common.data.AccessToken;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Authorize api.
 * */

public interface AuthorizeApi {

    @POST("oauth/token")
    Observable<AccessToken> getAccessToken(@Query("client_id") String client_id,
                                           @Query("client_secret") String client_secret,
                                           @Query("redirect_uri") String redirect_uri,
                                           @Query("code") String code,
                                           @Query("grant_type") String grant_type);
}
