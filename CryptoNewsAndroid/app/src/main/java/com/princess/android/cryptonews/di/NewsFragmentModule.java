package com.princess.android.cryptonews.di;

import android.arch.lifecycle.ViewModelProvider;

import com.princess.android.cryptonews.ViewModelFactory;
import com.princess.android.cryptonews.newslist.view.fragment.LatestNewsActivityFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by numb3rs on 2/24/18.
 */

@Module
public abstract  class NewsFragmentModule {

    @ContributesAndroidInjector()
    abstract LatestNewsActivityFragment latestNewsActivityFragment();

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory (ViewModelFactory viewModelFactory);
}
