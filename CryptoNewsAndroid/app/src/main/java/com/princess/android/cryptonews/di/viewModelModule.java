package com.princess.android.cryptonews.di;

import android.arch.lifecycle.ViewModel;

import com.princess.android.cryptonews.ViewModelKey;
import com.princess.android.cryptonews.newslist.viewmodel.NewsViewModel;
import com.princess.android.cryptonews.settings.viewmodel.ValidUrlViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * This Class will be used to bind all our ViewModels
 */

@Module
public abstract class viewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    abstract ViewModel bindNewsViewModel(NewsViewModel newsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ValidUrlViewModel.class)
    abstract  ViewModel bindSettingsViewModel (ValidUrlViewModel validUrlViewModel);

}
