package com.princess.android.cryptonews.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.princess.android.cryptonews.util.PreferenceUtils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.princess.android.cryptonews.di.NetworkModule.providesOkHttpClientBuilder;

/**
 * Created by numb3rs on 3/8/18.
 */

public class TestWebServiceNoDagger {

    public static  TestApiService provideTestWebService(String testUrl){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(testUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(providesOkHttpClientBuilder());

        return  builder.build().create(TestApiService.class);
    }

}
