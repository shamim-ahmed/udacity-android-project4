package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchJokeTask extends AsyncTask<String, Void, String> {
    private static final String TAG = FetchJokeTask.class.getSimpleName();
    private static final String POST_METHOD_NAME = "POST";
    private static final String JOKE_CONTENT_FIELD_NAME = "content";

    private final AsyncTaskListener<String> asyncListener;

    public FetchJokeTask(AsyncTaskListener<String> asyncListener) {
        if (asyncListener == null) {
            throw new IllegalArgumentException("listener cannot be null");
        }

        this.asyncListener = asyncListener;
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
                closeQuietly(reader);
            }
        }

        return resultBuilder.toString();
    }

    protected void onPostExecute(String jsonStr) {
        if (jsonStr == null || jsonStr.trim().equals("")) {
            Log.w(TAG, "no joke retrieved from service endpoint");
            return;
        }

        String jokeContent = null;

        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            jokeContent = jsonObject.getString(JOKE_CONTENT_FIELD_NAME);
            asyncListener.onSuccess(jokeContent);
        } catch (JSONException ex) {
            Log.e(TAG, String.format("Error while parsing JSON string : %s", jsonStr));
        }
    }

    public void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ex) {
                // ignore silently
            }
        }
    }
}
