package com.princess.android.cryptonews.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

<<<<<<< HEAD
import io.reactivex.plugins.RxJavaPlugins;
=======
>>>>>>> 15e0d7e019c0b156913bb46387d8774f66180c4e
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Princess on 2/22/2018.
 */

public class NewsApiClient {

    public static String BASE_URL = "https://ccn.com/";

    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BASE_URL)
                    .build();
        }
        return retrofit;
    }
}
