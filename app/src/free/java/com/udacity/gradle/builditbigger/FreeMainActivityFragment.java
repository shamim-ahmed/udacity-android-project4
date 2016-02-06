package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import com.udacity.gradle.builditbigger.util.Constants;

/**
 * A placeholder fragment containing a simple view.
 */
public class FreeMainActivityFragment extends AbstractFragment {
    private InterstitialAd interstitialAd;
    private ProgressBar progressBar;
    private Button jokeButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_free, container, false);

        // register click listener for the joke button
        jokeButton = (Button) root.findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(this);

        progressBar = (ProgressBar) root.findViewById(R.id.progress_bar);

        // configure interstitial ad
        Activity activity = getActivity();
        String bannerAdUnitId = activity.getString(R.string.banner_ad_unit_id);
        String deviceId = activity.getString(R.string.device_id);

        interstitialAd = new InterstitialAd(activity);
        interstitialAd.setAdUnitId(bannerAdUnitId);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startFetchJokeTask();
                progressBar.setVisibility(View.VISIBLE);
                requestNewInterstitial();
            }
        });

        requestNewInterstitial();

        // configure ad shown at the bottom of the fragment
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(deviceId)
                .build();
        mAdView.loadAd(adRequest);
        
        return root;
    }

    @Override
    public void onClick(View v) {
        jokeButton.setEnabled(false);
        jokeButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!jokeButton.isEnabled()) {
                    jokeButton.setEnabled(true);
                }
            }
        }, Constants.JOKE_FETCH_MAX_DELAY);

        interstitialAd.show();
    }

    @Override
    public void onSuccess(String data) {
        super.onSuccess(data);
        jokeButton.setEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    private void requestNewInterstitial() {
        String deviceId = getActivity().getString(R.string.device_id);
        AdRequest request = new AdRequest.Builder().addTestDevice(deviceId).build();
        interstitialAd.loadAd(request);
    }
}
