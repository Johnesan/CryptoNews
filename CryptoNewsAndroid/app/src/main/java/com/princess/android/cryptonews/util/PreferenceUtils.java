package com.princess.android.cryptonews.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.princess.android.cryptonews.R;

import javax.inject.Inject;

/**
 * Created by numb3rs on 3/7/18.
 */

public class PreferenceUtils {


    private SharedPreferences mSharedPreferences;
    private SharedPreferences mSharedPreferencesTest;

    public static final String  FIRST_URL = "first_url";
    public static final String  SECOND_URL = "second_url";
    public static final String  THIRD_URL = "third_url";
    public static final String  FOURTH_URL = "fourth_url";
    public static  final String TEST_URL = "test_url";


    public static final String  FIRST_TITLE = "first_title";
    public static final String  SECOND_TITLE = "second_title";
    public static final String  THIRD_TITLE = "third_title";
    public static final String  FOURTH_TITLE = "fourth_title";

    public static  final String URLCHANGED = "url_change";


    @Inject
    public PreferenceUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences("cryptoNews_key",
                Context.MODE_PRIVATE);
        mSharedPreferencesTest = PreferenceManager.getDefaultSharedPreferences(context);
    }


    public void storeFirstUrl(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(FIRST_URL, url);
        editor.apply();
    }
    public void storeSecondUrl(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(SECOND_URL, url);
        editor.apply();
    } public void storeThirdUrl(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(THIRD_URL, url);
        editor.apply();
    } public void storeFourthUrl(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(FOURTH_URL, url);
        editor.apply();
    }

    public void storeTestUrl(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(TEST_URL, url);
        editor.apply();
    }

    public void storeFirstTitle(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(FIRST_TITLE, url);
        editor.apply();
    }  public void storeSecondTitle(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(SECOND_TITLE, url);
        editor.apply();
    }  public void storeThirdTitle(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(THIRD_TITLE, url);
        editor.apply();
    }  public void storeFourthTitle(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(FOURTH_TITLE, url);
        editor.apply();
    }

    public String getFirstTitle (){
        return  mSharedPreferences.getString(FIRST_TITLE, "CCN");

    }
    public String getSecondTitle (){
        return  mSharedPreferences.getString(SECOND_TITLE, "Crypto Recorder");

    }
    public String getThirdTitle (){
        return  mSharedPreferences.getString(THIRD_TITLE, "Crypto Clarified");

    }
    public String getFourthTitle (){
        return  mSharedPreferences.getString(FOURTH_TITLE, "Crypto Scoop");

    }



    public String getFirstUrl(){
        return  mSharedPreferences.getString(FIRST_URL, "https://ccn.com");
    }


    public String getSecondUrl() {
        return mSharedPreferences.getString(SECOND_URL, "https://cryptorecorder.com");
    }

    public  String getThirdUrl(){
        return  mSharedPreferences.getString(THIRD_URL, "https://cryptoclarified.com");
    }

    public String getFourthUrl(){
        return  mSharedPreferences.getString(FOURTH_URL, "https://cryptoscoop.net");
    }

    public String getTestUrl(){
        return  mSharedPreferences.getString(TEST_URL, "https://ccn.com");
    }


    public boolean hasPreferenceUrlChanged(){
        return mSharedPreferences.getBoolean(URLCHANGED, false);
    }

    public void storeUrlPreference(boolean changed){
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(URLCHANGED, changed);
        editor.apply();
    }

    public String getFontSize(){
        return  mSharedPreferencesTest.getString("font_size", "");
    }

    public String getViewNewsWithIn(){
        return  mSharedPreferencesTest.getString("view_within", "");
    }

    public String getViewNewsAppVia(){
        return  mSharedPreferencesTest.getString("view_via", "");
    }

}
