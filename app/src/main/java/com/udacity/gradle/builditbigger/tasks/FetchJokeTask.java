package com.udacity.gradle.builditbigger.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import edu.udacity.android.android_joke_activity.JokeDisplayActivity;

public class FetchJokeTask extends AsyncTask<String, Void, String> {
    private static final String TAG = FetchJokeTask.class.getSimpleName();
    private static final String JOKE_CONTENT_ATTR_NAME = "jokeContent";

    private final Context context;

    public FetchJokeTask(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("context cannot be null");
        }

        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        return "";
    }

    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, JokeDisplayActivity.class);
        intent.putExtra(JOKE_CONTENT_ATTR_NAME, result);
        context.startActivity(intent);
    }
}
