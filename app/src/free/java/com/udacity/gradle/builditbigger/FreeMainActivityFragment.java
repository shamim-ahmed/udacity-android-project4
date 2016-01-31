package com.udacity.gradle.builditbigger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * A placeholder fragment containing a simple view.
 */
public class FreeMainActivityFragment extends AbstractFragment {
    private InterstitialAd interstitialAd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main_free, container, false);

        // register click listener for the joke button
        Button jokeButton = (Button) root.findViewById(R.id.joke_button);
        jokeButton.setOnClickListener(this);

        // configure interstitial ad
        interstitialAd = new InterstitialAd(getActivity());
        interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startFetchJokeTask();
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
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    @Override
    public void onClick(View v) {
        interstitialAd.show();
    }

    private void requestNewInterstitial() {
        AdRequest request = new AdRequest.Builder().addTestDevice("D55E75550E2CA393D1269880F1D4D4FC").build();
        interstitialAd.loadAd(request);
    }
}
