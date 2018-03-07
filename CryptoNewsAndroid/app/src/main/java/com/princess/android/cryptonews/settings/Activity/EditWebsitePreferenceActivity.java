package com.princess.android.cryptonews.settings.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.settings.fragment.EditWebsitePreferenceFragment;

import dagger.android.support.DaggerAppCompatActivity;

import static com.princess.android.cryptonews.settings.fragment.ManageBlogWebsiteFragment.URL_KEY;

/**
 * Created by numb3rs on 3/6/18.
 */

public class EditWebsitePreferenceActivity  extends DaggerAppCompatActivity{

    EditWebsitePreferenceFragment editWebsitePreferenceFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_settings);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        if (getIntent().hasExtra(URL_KEY)){
            startFragment(getIntent().getStringExtra(URL_KEY));
        }


    }

    private void startFragment(String s) {
        if (editWebsitePreferenceFragment == null){
            editWebsitePreferenceFragment = EditWebsitePreferenceFragment.newInstance(s);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.edit_website, editWebsitePreferenceFragment, EditWebsitePreferenceFragment.class.getSimpleName())
            .commit();
        }

    }
}
