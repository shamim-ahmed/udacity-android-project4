package edu.udacity.gradle.builditbigger;

import android.support.multidex.MultiDexApplication;
import android.util.Log;

public class JokeApplication extends MultiDexApplication {
    private static final String TAG = JokeApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "The application is being loaded");
    }

}
