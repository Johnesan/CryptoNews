package com.princess.android.cryptonews.settings.fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.view.MenuItem;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.settings.Activity.ManageBlogSettings;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;


public class GeneralPreferenceFragment extends PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = findPreference(s);
        if (null != preference) {
            if (!(preference instanceof CheckBoxPreference)) {
                setPreferenceSummary(preference, sharedPreferences.getString(s, ""));
            }
        }
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPrefs = getDefaultSharedPreferences(getContext());
        onSharedPreferenceChanged(sharedPrefs, "font_size");
        onSharedPreferenceChanged(sharedPrefs, "view_via");
        onSharedPreferenceChanged(sharedPrefs, "view_within");


    }

    private void  setPreferenceSummary(Preference preference, Object value ) {
        String stringValue = value.toString();
        String key = preference.getKey();
        if (preference instanceof ListPreference) {
            /* For list preferences, look up the correct display value in */
            /* the preference's 'entries' list (since they have separate labels/values). */
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            } else {
                // For other preferences, set the summary to the value's simple string representation.
                preference.setSummary(stringValue);
            }

        }
    }


    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
       addPreferencesFromResource(R.xml.pref_general);


       SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
       PreferenceScreen prefScreen = getPreferenceScreen();
       int count = prefScreen.getPreferenceCount();
       for (int i = 0; i< count; i++){
           Preference p = prefScreen.getPreference(i);
           if (!(p instanceof CheckBoxPreference) ){
               String value = sharedPreferences.getString(p.getKey(), "");
               setPreferenceSummary(p, value);
           }

       }


        Preference  manageBlogWebsite = getPreferenceManager().findPreference(getActivity().getString(R.string.manage_blog_title));
        manageBlogWebsite.setOnPreferenceClickListener(
                preference -> {

                        try {
                            startActivity(new Intent(getActivity(), ManageBlogSettings.class));
                        }catch (ActivityNotFoundException ignored){

                        }
                    return  false;
                }
        );
    }


    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }


    @Override
    public void onStop() {
        super.onStop();
          /* Unregister the preference change listener */
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {

        }
        return super.onOptionsItemSelected(item);
    }
}
