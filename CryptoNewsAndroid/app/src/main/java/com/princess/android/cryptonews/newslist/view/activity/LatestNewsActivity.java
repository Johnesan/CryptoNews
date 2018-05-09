package com.princess.android.cryptonews.newslist.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.princess.android.cryptonews.Favourite.FavoriteActivity;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.newslist.view.fragment.LatestNewsActivityFragment;
import com.princess.android.cryptonews.settings.Activity.SettingsActivity;

import dagger.android.support.DaggerAppCompatActivity;

public class LatestNewsActivity extends DaggerAppCompatActivity {

    LatestNewsActivityFragment activityFragment;
    private static final String STATE_ACTIVE_FRAGMENT = "active_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_latest_news);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showNewsListFragment(savedInstanceState);

    }

    private void showNewsListFragment(Bundle saveInstanceState) {
       if (saveInstanceState != null){
           activityFragment = (LatestNewsActivityFragment) getSupportFragmentManager().getFragment(saveInstanceState, STATE_ACTIVE_FRAGMENT);
       }

        if (activityFragment == null){
            activityFragment = LatestNewsActivityFragment.newInstance();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, activityFragment, LatestNewsActivityFragment.class.getSimpleName()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_latest_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            //noinspection SimplifiableIfStatement

            case R.id.action_settings:
                startSettingsActivity();
                return true;
            case R.id.action_favorite:
                startFavoriteActivity();
                return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startFavoriteActivity() {
        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }


    private void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        getSupportFragmentManager().putFragment(outState,STATE_ACTIVE_FRAGMENT, activityFragment);
    }
}
