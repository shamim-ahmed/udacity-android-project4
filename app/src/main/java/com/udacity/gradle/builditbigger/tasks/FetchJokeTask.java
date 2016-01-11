package com.udacity.gradle.builditbigger.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import edu.udacity.android.android_joke_activity.JokeDisplayActivity;
import edu.udacity.model.Joke;
import edu.udacity.provider.jokes.JokeProvider;

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
        Joke joke = JokeProvider.getJoke();
        Log.i(TAG, String.format("The retrieved joke is : %s", joke));

        return joke != null ? joke.getContent() : "";
    }

    protected void onPostExecute(String result) {
        Intent intent = new Intent(context, JokeDisplayActivity.class);
        intent.putExtra(JOKE_CONTENT_ATTR_NAME, result);
        context.startActivity(intent);
    }
}
