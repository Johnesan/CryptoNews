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

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.settings.Activity.EditWebsitePreferenceActivity;
import com.princess.android.cryptonews.util.PreferenceUtils;
import com.princess.android.cryptonews.util.ShowAlert;

import javax.inject.Inject;

import static com.princess.android.cryptonews.util.PreferenceUtils.FIRST_TITLE;
import static com.princess.android.cryptonews.util.PreferenceUtils.FIRST_URL;
import static com.princess.android.cryptonews.util.PreferenceUtils.FOURTH_URL;
import static com.princess.android.cryptonews.util.PreferenceUtils.THIRD_URL;

/**
 * Created by numb3rs on 3/6/18.
 */

public class ManageBlogWebsiteFragment extends
        PreferenceFragmentCompat
        implements SharedPreferences.OnSharedPreferenceChangeListener{

    ShowAlert alert = new ShowAlert();

    PreferenceUtils preferenceUtils = new PreferenceUtils(AppController.getContextInstance());



    public  static  String URL_KEY = "url_key";

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
        editWebsite.setTitle(preferenceUtils.getFirstTitle());
        editWebsite.setSummary(preferenceUtils.getFirstUrl());
        editWebsite.setOnPreferenceClickListener(
                preference -> {

                    try {
                        Intent intent = new Intent(getActivity(), EditWebsitePreferenceActivity.class);
                        intent.putExtra(URL_KEY, FIRST_URL);
                        startActivity(intent);
                    }catch (ActivityNotFoundException ignored){

                    }
                    return  false;
                }
        );




        Preference editWebsite1 = getPreferenceManager().findPreference("example_text1");
        editWebsite1.setTitle(preferenceUtils.getSecondTitle());
        editWebsite1.setSummary(preferenceUtils.getSecondUrl());
        editWebsite1.setOnPreferenceClickListener(
                preference -> {

                    try {
                        Intent  intent = new Intent(getActivity(), EditWebsitePreferenceActivity.class);
                        intent.putExtra(URL_KEY, getString(R.string.pref_second_url));
                        startActivity(intent);
                    }catch (ActivityNotFoundException ignored){

                    }
                    return  false;
                }
        );
        Preference editWebsite2 = getPreferenceManager().findPreference("example_text2");
        editWebsite2.setTitle(preferenceUtils.getThirdTitle());
        editWebsite2.setSummary(preferenceUtils.getThirdUrl());
        editWebsite2.setOnPreferenceClickListener(
                preference -> {

                    try {
                        Intent intent = new Intent(getActivity(), EditWebsitePreferenceActivity.class);
                        intent.putExtra(URL_KEY, THIRD_URL);
                        startActivity(intent);
                    }catch (ActivityNotFoundException ignored){

                    }
                    return  false;
                }
        );


        Preference editWebsite3 = getPreferenceManager().findPreference("example_text3");
        editWebsite3.setTitle(preferenceUtils.getFourthTitle());
        editWebsite3.setSummary(preferenceUtils.getFourthUrl());
        editWebsite3.setOnPreferenceClickListener(
                preference -> {

                    try {
                        Intent  intent= new Intent(getActivity(), EditWebsitePreferenceActivity.class);
                        intent.putExtra(URL_KEY, FOURTH_URL);
                        startActivity(intent);
                    }catch (ActivityNotFoundException ignored){

                    }
                    return  false;
                }
        );


        Preference clearPreference = getPreferenceManager().findPreference("button_preference");
        clearPreference.setOnPreferenceClickListener(
                preference -> {

                    //reset the Preference url to default value
                    preferenceUtils.storeFourthUrl(null);
                    preferenceUtils.storeThirdUrl(null);
                    preferenceUtils.storeSecondUrl(null);
                    preferenceUtils.storeFirstUrl(null);

                    //reset the prference title to default values
                    preferenceUtils.storeFourthTitle(null);
                    preferenceUtils.storeThirdTitle(null);
                    preferenceUtils.storeSecondTitle(null);
                    preferenceUtils.storeFirstTitle(null);

                    alert.showAlertDialog(getContext(), "Successful", "Website Preference restored back to default", true);

                return false;
                }
        );


    }



}
