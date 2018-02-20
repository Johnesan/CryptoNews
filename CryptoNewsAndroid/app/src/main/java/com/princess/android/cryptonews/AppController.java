package com.princess.android.cryptonews;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.princess.android.cryptonews.injection.AppComponent;
import com.princess.android.cryptonews.injection.CryptoNewsComponents;
import com.princess.android.cryptonews.injection.CryptoNewsModule;
import com.princess.android.cryptonews.injection.DaggerAppComponent;
import com.princess.android.cryptonews.injection.DaggerCryptoNewsComponents;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by numb3rs on 2/16/18.
 */

public class AppController extends Application implements HasActivityInjector {

    static AppController INSTANCE;

    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

   private final CryptoNewsComponents cryptoNewsComponents = createCryproNewsComponent();


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        //initialise the default font
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        getAppComponent().inject(this);

    }

    public AppComponent getAppComponent(){
        return DaggerAppComponent.builder().application(this)
                .build();

    }

    public CryptoNewsComponents createCryproNewsComponent(){
        return DaggerCryptoNewsComponents.builder()
                .cryptoNewsModule(new CryptoNewsModule(this))
                .build();
    }


    public  CryptoNewsComponents getCryptoNewsComponent(){
        return  cryptoNewsComponents;
    }

    public static  synchronized  AppController getInstance(){
        return  INSTANCE;
    }

    public static Context getContextInstance(){
        return INSTANCE.getApplicationContext();
    }
    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }
}
