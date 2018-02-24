package com.princess.android.cryptonews.injection;

import android.app.Application;

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.AppModule;
import com.princess.android.cryptonews.di.ActivityBindingModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;


@Component (modules = {AppModule.class,
        AndroidSupportInjectionModule.class,
ActivityBindingModule.class})
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
