package com.example.librarytolocal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adcolony.sdk.AdColony;
import com.adcolony.sdk.AdColonyAdOptions;
import com.adcolony.sdk.AdColonyAdSize;
import com.adcolony.sdk.AdColonyAdView;
import com.adcolony.sdk.AdColonyAdViewListener;
import com.adcolony.sdk.AdColonyAppOptions;
import com.adcolony.sdk.AdColonyZone;

public class BannerActivity extends AppCompatActivity {
    private final String APP_ID = "INSERT_YOUR_APP_ID_HERE";
    private final String ZONE_ID = "INSERT_YOUR_BANNER_ZONE_ID_HERE";
    final private String TAG = "AdColonyBannerDemo";

    private AdColonyAdViewListener listener;
    private AdColonyAdOptions adOptions;
    private RelativeLayout adContainer;
    private ProgressBar progressBar;
    private Button buttonLoad;
    private AdColonyAdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        adContainer =findViewById(R.id.ad_container);
        progressBar = findViewById(R.id.progress);
        buttonLoad = findViewById(R.id.load_button);
        buttonLoad.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(adContainer.getChildCount() > 0) {
                    adContainer.removeView(adView);
                }

                progressBar.setVisibility(View.VISIBLE);
                buttonLoad.setEnabled(false);
                buttonLoad.setAlpha(0.5f);

                requestBannerAd();
            }
        });

        AdColonyAppOptions appOptions = new AdColonyAppOptions();

        AdColony.configure(this, appOptions, APP_ID, ZONE_ID);

        listener = new AdColonyAdViewListener() {
            @Override
            public void onRequestFilled(AdColonyAdView adColonyAdView) {
                resetUI();
                adContainer.addView(adColonyAdView);
                adView = adColonyAdView;
            }
            @Override
            public void onRequestNotFilled(AdColonyZone zone) {
                super.onRequestNotFilled(zone);
                Log.d(TAG, "onRequestNotFilled");
                resetUI();
            }

            @Override
            public void onOpened(AdColonyAdView ad) {
                super.onOpened(ad);
                Log.d(TAG, "onOpened");
            }

            @Override
            public void onClosed(AdColonyAdView ad) {
                super.onClosed(ad);
                Log.d(TAG, "onClosed");
            }

            @Override
            public void onClicked(AdColonyAdView ad) {
                super.onClicked(ad);
                Log.d(TAG, "onClicked");
            }

            @Override
            public void onLeftApplication(AdColonyAdView ad) {
                super.onLeftApplication(ad);
                Log.d(TAG, "onLeftApplication");
            }
        };

    }

    private void resetUI() {
        progressBar.setVisibility(View.GONE);
        buttonLoad.setEnabled(true);
        buttonLoad.setAlpha(1.0f);
    }

    private void requestBannerAd() {
        adOptions = new AdColonyAdOptions();
        AdColony.requestAdView(ZONE_ID, listener, AdColonyAdSize.BANNER, adOptions);
    }
}