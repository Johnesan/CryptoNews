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

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.settings.Activity.EditWebsitePreferenceActivity;

/**
 * Created by numb3rs on 3/6/18.
 */

public class ManageBlogWebsiteFragment extends
        PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener{
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = findPreference(s);
        if (null != preference) {
            if (!(preference instanceof CheckBoxPreference)) {
                setPreferenceSummary(preference, sharedPreferences.getString(s, ""));
            }
        }
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
        addPreferencesFromResource(R.xml.pref_manage_blog);

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
        Preference editWebsite = getPreferenceManager().findPreference("example_text");
        editWebsite.setOnPreferenceClickListener(
                preference -> {

                    try {
                        startActivity(new Intent(getActivity(), EditWebsitePreferenceActivity.class));
                    }catch (ActivityNotFoundException ignored){

                    }
                    return  false;
                }
        );

        Preference editWebsite1 = getPreferenceManager().findPreference("example_text1");
        editWebsite1.setOnPreferenceClickListener(
                preference -> {

                    try {
                        startActivity(new Intent(getActivity(), EditWebsitePreferenceActivity.class));
                    }catch (ActivityNotFoundException ignored){

                    }
                    return  false;
                }
        );
        Preference editWebsite2 = getPreferenceManager().findPreference("example_text2");
        editWebsite2.setOnPreferenceClickListener(
                preference -> {

                    try {
                        startActivity(new Intent(getActivity(), EditWebsitePreferenceActivity.class));
                    }catch (ActivityNotFoundException ignored){

                    }
                    return  false;
                }
        );


        Preference editWebsite3 = getPreferenceManager().findPreference("example_text3");
        editWebsite3.setOnPreferenceClickListener(
                preference -> {

                    try {
                        startActivity(new Intent(getActivity(), EditWebsitePreferenceActivity.class));
                    }catch (ActivityNotFoundException ignored){

                    }
                    return  false;
                }
        );


    }



}
