package com.udacity.gradle.builditbigger.tasks;

import android.content.Context;
import android.content.Intent;
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

import edu.udacity.android.android_joke_activity.JokeDisplayActivity;

public class FetchJokeTask extends AsyncTask<String, Void, String> {
    private static final String TAG = FetchJokeTask.class.getSimpleName();
    private static final String JOKE_CONTENT_ATTR_NAME = "jokeContent";
    private static final String POST_METHOD_NAME = "POST";
    private static final String JSON_FIELD_NAME = "content";

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
            jokeContent = jsonObject.getString(JSON_FIELD_NAME);
        } catch (JSONException ex) {
            Log.e(TAG, String.format("Error while parsing JSON string : %s", jsonStr));
        }

        Intent intent = new Intent(context, JokeDisplayActivity.class);
        intent.putExtra(JOKE_CONTENT_ATTR_NAME, jokeContent);
        context.startActivity(intent);
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
