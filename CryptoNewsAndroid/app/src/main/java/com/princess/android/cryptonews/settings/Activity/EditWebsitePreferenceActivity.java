package com.princess.android.cryptonews.settings.Activity;

import android.content.Intent;
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

public class EditWebsitePreferenceActivity  extends DaggerAppCompatActivity implements  EditWebsitePreferenceFragment.fragmentInteractionListener{

    EditWebsitePreferenceFragment editWebsitePreferenceFragment;
    boolean hasSaved;

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

    @Override
    public void onBackPressed(boolean isSaved) {
        hasSaved = isSaved;

    }

    @Override
    public void onBackPressed() {
        if (hasSaved){
            Intent intent = new Intent(this, ManageBlogSettings.class);
            startActivity(intent);
        }
        else {
            super.onBackPressed();
        }

    }
}
