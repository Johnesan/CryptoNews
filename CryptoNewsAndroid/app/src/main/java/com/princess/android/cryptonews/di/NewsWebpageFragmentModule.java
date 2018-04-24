package com.princess.android.cryptonews.di;

import android.arch.lifecycle.ViewModelProvider;

import com.princess.android.cryptonews.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

/**
 * Created by numb3rs on 4/21/18.
 */
@Module
abstract class NewsWebpageFragmentModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory (ViewModelFactory viewModelFactory);

}
