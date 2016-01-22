package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import java.io.IOException;
import java.util.Properties;

public class JokeApplication extends MultiDexApplication {
    private static final String TAG = JokeApplication.class.getSimpleName();
    private final Properties configProperties = new Properties();

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();

        try {
            configProperties.load(context.getResources().openRawResource(R.raw.config));
        } catch (IOException ex) {
            Log.e(TAG, "error while loading app configuration properties");
        }
    }

    public String getConfigProperty(String key) {
        return configProperties.getProperty(key);
    }
}
