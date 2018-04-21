package com.princess.android.cryptonews.Favourite;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.databinding.ActivityFavoriteBinding;

import dagger.android.support.DaggerAppCompatActivity;


public class FavoriteActivity extends DaggerAppCompatActivity {


    ActivityFavoriteBinding binding;
    FavoriteActivityFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);

        showFragment();
    }

    // start The Fragment Activity
    private void showFragment() {
        if (fragment == null){
            fragment = FavoriteActivityFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_for_favorite, fragment).commit();
        }
    }
}