package edu.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import edu.udacity.gradle.builditbigger.util.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class PaidMainActivityFragment extends AbstractFragment {
    private ProgressBar progressBar;
    private Button jokeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_paid, container, false);

        jokeButton = (Button) root.findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(this);

        progressBar = (ProgressBar) root.findViewById(R.id.progress_bar);

        return root;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        jokeButton.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        jokeButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!jokeButton.isEnabled()) {
                    jokeButton.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                }
            }
        }, Constants.JOKE_FETCH_MAX_DELAY);
    }

    @Override
    public void onSuccess(String data) {
        super.onSuccess(data);
        jokeButton.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }
}
