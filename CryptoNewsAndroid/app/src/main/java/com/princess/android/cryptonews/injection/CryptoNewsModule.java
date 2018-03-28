package com.princess.android.cryptonews.injection;

import android.content.Context;

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.util.ConnectionClassLiveData;
import com.princess.android.cryptonews.util.PreferenceUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by numb3rs on 2/20/18.
 */

@Module
public class CryptoNewsModule {
    private AppController appController;

    public CryptoNewsModule(AppController appController) {
        this.appController = appController;
    }


    @Provides
    Context provideApplicationContext() {
        return  appController;
    }

    @Provides
    @Singleton
    PreferenceUtils providePreferenceUtil(Context context){
        return new PreferenceUtils(context);
    }

    @Provides
    @Singleton
    ConnectionClassLiveData provideLiveNetworkTest (Context context){
        return  new ConnectionClassLiveData(context);
    }



}
