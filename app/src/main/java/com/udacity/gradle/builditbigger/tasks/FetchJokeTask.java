package com.udacity.gradle.builditbigger.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.udacity.gradle.builditbigger.util.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import edu.udacity.android.android_joke_activity.JokeDisplayActivity;

public class FetchJokeTask extends AsyncTask<String, Void, String> {
    private static final String TAG = FetchJokeTask.class.getSimpleName();
    private static final String JOKE_CONTENT_ATTR_NAME = "jokeContent";
    private static final String POST_METHOD_NAME = "POST";

    private final Context context;

    public FetchJokeTask(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }

        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length == 0) {
            Log.w(TAG, "no endpoint URL provided");
            return null;
        }

        BufferedReader reader = null;
        StringBuilder resultBuilder = new StringBuilder();

        try {
            URL jokeServiceUrl = new URL(params[0]);
            HttpURLConnection httpConnection = (HttpURLConnection) jokeServiceUrl.openConnection();
            httpConnection.setRequestMethod(POST_METHOD_NAME);
            reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                resultBuilder.append(line);
            }
        } catch (MalformedURLException ex) {
            Log.e(TAG, "malformed service URL");
        } catch (IOException ex) {
            Log.i(TAG, "error while reading data from the joke service");
        } finally {
            if (reader != null) {
                IOUtils.closeQuietly(reader);
            }
        }

        return resultBuilder.toString();
    }

    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, JokeDisplayActivity.class);
        intent.putExtra(JOKE_CONTENT_ATTR_NAME, result);
        context.startActivity(intent);
    }
}
