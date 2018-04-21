package com.princess.android.cryptonews.Favourite;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.databinding.ActivityFavoriteBinding;

import dagger.android.support.DaggerAppCompatActivity;


public class FavoriteActivity extends DaggerAppCompatActivity {


    ActivityFavoriteBinding binding;
    FavoriteActivityFragment fragment;
    Fragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);
        if (savedInstanceState == null){
            showFragment();
        } else  {

            //we are doing this to ensure that the selected state of the recyclerview  remains when the device is rotated
            fragment = (FavoriteActivityFragment) getSupportFragmentManager().getFragment(savedInstanceState, "fav_fragment");
            getSupportFragmentManager().beginTransaction().replace(R.id.container_for_favorite, fragment).commit();

        }

    }

    // start The Fragment Activityi

    private void showFragment() {
        if (fragment == null){
            fragment = FavoriteActivityFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container_for_favorite, fragment).commit();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "fav_fragment", fragment);
    }


    @Override
    public void onBackPressed() {
        if (fragment  != null){
            fragment.destroyActionMode();
        }
        super.onBackPressed();
    }
}