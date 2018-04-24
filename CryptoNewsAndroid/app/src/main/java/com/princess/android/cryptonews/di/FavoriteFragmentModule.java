package com.princess.android.cryptonews.di;

import android.arch.lifecycle.ViewModelProvider;

import com.princess.android.cryptonews.ViewModelFactory;

import dagger.Binds;
import dagger.Module;

/**
 * Created by numb3rs on 4/20/18.
 */

@Module
abstract  class FavoriteFragmentModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory (ViewModelFactory viewModelFactory);

}
