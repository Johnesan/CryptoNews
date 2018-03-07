package com.princess.android.cryptonews.di;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.princess.android.cryptonews.BuildConfig;
import com.princess.android.cryptonews.api.NewsApiService;
import com.princess.android.cryptonews.api.TestApiService;
import com.princess.android.cryptonews.util.PreferenceUtils;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Helper Class to provide Network resources to our ViewModels
 */


@Module
public class NetworkModule {

    public static String BASE_URL = "https://ccn.com/";
    public static NewsApiService api, api2, api3, api4;

    @Provides
    public static OkHttpClient providesOkHttpClientBuilder() {
        Interceptor interceptor
                = chain -> {
            Request.Builder builder = chain.request().newBuilder();
            return chain.proceed(builder.build());
        };

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //adding this bit of code to test if caching with retrofit  will work

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(interceptor);

        httpClient.addNetworkInterceptor(loggingInterceptor);
        //httpClient.cache(cache);
        return httpClient.readTimeout(1200, TimeUnit.SECONDS)
                .connectTimeout(1200, TimeUnit.SECONDS).build();

    }

    @Provides
    public static NewsApiService provideWebService(PreferenceUtils preferenceUtils){
        Retrofit.Builder builder = new Retrofit.Builder()
                //.baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(providesOkHttpClientBuilder());


        api = builder.baseUrl(preferenceUtils.getFirstUrl()).build().create(NewsApiService.class);
        api2 = builder.baseUrl(preferenceUtils.getSecondUrl()).build().create(NewsApiService.class);
        api3 = builder.baseUrl(preferenceUtils.getThirdUrl()).build().create(NewsApiService.class);
        api4 = builder.baseUrl(preferenceUtils.getFourthUrl()).build().create(NewsApiService.class);

        return builder.build().create(NewsApiService.class);
//      Retrofit retrofit = builder
//              .client(providesOkHttpClientBuilder());
//              //.build();
      //return  retrofit.create(NewsApiService.class);
    }

    @Provides
    public  static TestApiService provideTestWebService(PreferenceUtils preferenceUtils){
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(preferenceUtils.getTestUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(providesOkHttpClientBuilder());

        return  builder.build().create(TestApiService.class);
    }



    @Provides
    Retrofit provideRetrofit(){
        return  new Retrofit.Builder()
                     .client(providesOkHttpClientBuilder())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
}
