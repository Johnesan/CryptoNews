package com.princess.android.cryptonews.di;

import com.princess.android.cryptonews.Favourite.FavoriteActivity;
import com.princess.android.cryptonews.newslist.view.activity.LatestNewsActivity;
import com.princess.android.cryptonews.settings.Activity.EditWebsitePreferenceActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by numb3rs on 2/24/18.
 */


@Module
public abstract  class ActivityBindingModule {

   @ContributesAndroidInjector(modules = NewsFragmentModule.class)
   abstract LatestNewsActivity latestNewsActivity();

   @ContributesAndroidInjector(modules = TestWebsiteFragmentModule.class)
   abstract EditWebsitePreferenceActivity editWebsitePreferenceActivity();

   @ContributesAndroidInjector (modules =  FavoriteFragmentModule.class)
   abstract FavoriteActivity favoriteActivity();
}
