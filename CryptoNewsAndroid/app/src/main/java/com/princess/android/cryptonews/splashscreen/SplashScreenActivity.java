package com.princess.android.cryptonews.splashscreen;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.princess.android.cryptonews.News.Activity.LatestNewsActivity;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {

    ActivitySplashScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);
        // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource.
        binding.onBoardingAnimation.setBackgroundResource(R.drawable.animation);

        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable animationDrawable = (AnimationDrawable) binding.onBoardingAnimation.getBackground();
        animationDrawable.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent intent = new Intent(SplashScreenActivity.this, LatestNewsActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3500);
    }
}
