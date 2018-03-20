package com.princess.android.cryptonews.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by numb3rs on 3/7/18.
 */

public class PreferenceUtils {


    private SharedPreferences mSharedPreferences;

    public static final String  FIRST_URL = "first_url";
    public static final String  SECOND_URL = "second_url";
    public static final String  THIRD_URL = "third_url";
    public static final String  FOURTH_URL = "fourth_url";
    public static  final String TEST_URL = "test_url";


    public static final String  FIRST_TITLE = "first_title";
    public static final String  SECOND_TITLE = "second_title";
    public static final String  THIRD_THIRD = "third_title";
    public static final String  FOURTH_FOURTH = "fourth_title";

    public static final String NEWS_WAS_STORED = "news_was_Stored";


    @Inject
    public PreferenceUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences("cryptoNews_key",
                Context.MODE_PRIVATE);
    }


    public void storeFirstUrl(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(FIRST_URL, url);
        editor.apply();
    } public void storeSecondUrl(String url) {
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
    } public void storeTestUrl(String url) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(TEST_URL, url);
        editor.apply();
    }

//    public String getFirstTitle (){
//
//
//    }

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

    public long getLastTimeNewsWasStored() {
        return mSharedPreferences.getLong(NEWS_WAS_STORED, 0);
    }

    public void storeTimeNewswasReceived() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putLong(NEWS_WAS_STORED, Calendar.getInstance().getTimeInMillis());
        editor.apply();
    }
}
