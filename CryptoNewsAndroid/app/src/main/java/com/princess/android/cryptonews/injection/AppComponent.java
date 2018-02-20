package com.princess.android.cryptonews.injection;

import android.app.Application;

import com.princess.android.cryptonews.AppController;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by numb3rs on 2/20/18.
 */

@Component (modules = {AndroidSupportInjectionModule.class})
@Singleton
public interface AppComponent extends AndroidInjector<AppController> {

    @Override
    void inject(AppController instance);


    @Component.Builder
    interface Builder{
        @BindsInstance
        AppComponent.Builder application(Application application);
        AppComponent build();

    }

}
