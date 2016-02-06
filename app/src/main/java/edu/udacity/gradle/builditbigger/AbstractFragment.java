package edu.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;

import edu.udacity.android.android_joke_activity.JokeDisplayActivity;

public abstract class AbstractFragment extends Fragment implements View.OnClickListener, AsyncTaskListener<String> {
    public static final String JOKE_CONTENT_ATTR_NAME = "jokeContent";

    @Override
    public void onClick(View v) {
        startFetchJokeTask();
    }

    protected void startFetchJokeTask() {
        Activity activity = getActivity();
        String urlString = activity.getString(R.string.joke_service_endpoint_url);

        FetchJokeTask task = new FetchJokeTask(this);
        task.execute(urlString);
    }

    @Override
    public void onSuccess(String data) {
        Activity activity = getActivity();
        Intent intent = new Intent(activity, JokeDisplayActivity.class);
        intent.putExtra(JOKE_CONTENT_ATTR_NAME, data);
        startActivity(intent);
    }
}
