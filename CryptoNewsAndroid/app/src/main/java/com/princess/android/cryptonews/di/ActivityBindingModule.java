package com.princess.android.cryptonews.di;

import com.princess.android.cryptonews.newslist.view.activity.LatestNewsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by numb3rs on 2/24/18.
 */


@Module
public abstract  class ActivityBindingModule {

   @ContributesAndroidInjector(modules = NewsFragmentModule.class)
   abstract LatestNewsActivity latestNewsActivity();
}
