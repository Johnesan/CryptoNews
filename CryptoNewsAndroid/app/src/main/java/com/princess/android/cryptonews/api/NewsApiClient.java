package com.princess.android.cryptonews.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Princess on 2/22/2018.
 */

public class NewsApiClient {

    private static final String BASE_URL = "https://ccn.com/";

    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }
}
