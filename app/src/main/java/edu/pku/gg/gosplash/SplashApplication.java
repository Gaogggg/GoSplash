package edu.pku.gg.gosplash;

import android.app.Application;
import android.util.Log;

/**
 * @author gaoge
 */
public class SplashApplication extends Application {

    private static final String TAG = "SplashApplication";

    private static SplashApplication _instance = new SplashApplication();

    public static String currentActiviy = "main";

    // Singleton
    public synchronized static SplashApplication getInstance() { return _instance;}

    private SplashApplication(){

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"onCreate");
        initialize();
        //readPhotoCount();
    }

    public void initialize(){
        _instance = this;
    }

}
