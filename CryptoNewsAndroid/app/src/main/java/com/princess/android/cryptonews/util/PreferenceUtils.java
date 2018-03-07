package com.princess.android.cryptonews.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.princess.android.cryptonews.R;

import javax.inject.Inject;

/**
 * Created by numb3rs on 3/7/18.
 */

public class PreferenceUtils {


    private SharedPreferences mSharedPreferences;
    private Context mContext;



    private static final String  FIRST_URL = "first_url";
    private static final String  SECOND_URL = "first_url";
    private static final String  THIRD_URL = "first_url";
    private static final String  FOURTH_URL = "first_url";



    @Inject
    public PreferenceUtils(Context context) {
        mSharedPreferences = context.getSharedPreferences("cryptoNews_key",
                Context.MODE_PRIVATE);
        this.mContext = context;

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
}
