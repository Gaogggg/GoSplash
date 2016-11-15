package edu.pku.gg.gosplash.common;

/**
 * @author gaoge
 */
public class Constants {

    // Unsplash data.
    public final static String APPLICATION_ID = "eddf9dcb1dc1969acd0ce60ded64f84c56a2e382c59bb97a6a5e7b177483f1e6";
    public final static String SECRET = "7ed07be2aeb59833100871cfedc65b168cbdc41da9e77c4bb7f096cca7e6581c";
    public final static String AUTHORIZE_CODE = "7463323cf0edcc04d589605bc0803675a07ed636a0fe76f567d89d0df42f7abb";

    // Unsplash url.
    public final static String UNSPLASH_API_BASE_URL = "https://api.unsplash.com/";
    public final static String UNSPLASH_AUTH_BASE_URL = "https://unsplash.com/";
    public final static String UNSPLASH_URL = "https://unsplash.com/";
    public final static String UNSPLASH_JOIN_URL = "https://unsplash.com/join";
    public final static String UNSPLASH_LOGIN_CALLBACK = "unsplash-auth-callback";
    public final static String UNSPLASH_LOGIN_URL = UNSPLASH_AUTH_BASE_URL + "oauth/authorize"
            + "?client_id=" + APPLICATION_ID
            + "&redirect_uri=" + "mysplash%3A%2F%2F" + UNSPLASH_LOGIN_CALLBACK
            + "&response_type=" + "code"
            + "&scope=" + "public+read_user+write_user+read_photos+write_photos+write_likes+read_collections+write_collections";

    //Author data.
    public final static String AUTHOR_GITHUB = "https://github.com/Gaogggg";


    public final static String DATE_FORMAT = "yyyy/MM/dd";

}
