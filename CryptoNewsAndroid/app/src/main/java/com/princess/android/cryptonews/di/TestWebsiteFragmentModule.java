package com.princess.android.cryptonews.di;

import android.arch.lifecycle.ViewModelProvider;

import com.princess.android.cryptonews.ViewModelFactory;
import com.princess.android.cryptonews.settings.fragment.EditWebsitePreferenceFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by numb3rs on 3/7/18.
 */

@Module
public abstract class TestWebsiteFragmentModule {

    @ContributesAndroidInjector()
    abstract EditWebsitePreferenceFragment editWebsitePreferenceFragment();

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory (ViewModelFactory viewModelFactory);

}
