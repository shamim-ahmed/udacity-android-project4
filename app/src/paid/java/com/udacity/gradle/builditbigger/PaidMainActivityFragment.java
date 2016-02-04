package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * A placeholder fragment containing a simple view.
 */
public class PaidMainActivityFragment extends AbstractFragment {
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_paid, container, false);

        Button jokeButton = (Button) root.findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(this);

        progressBar = (ProgressBar) root.findViewById(R.id.progress_bar);

        return root;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(String data) {
        super.onSuccess(data);
        progressBar.setVisibility(View.GONE);
    }
}
