package com.princess.android.cryptonews.di;

import android.arch.lifecycle.ViewModelProvider;

import com.princess.android.cryptonews.Favourite.FavoriteActivityFragment;
import com.princess.android.cryptonews.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by numb3rs on 4/20/18.
 */

@Module
abstract  class FavoriteFragmentModule {

    @ContributesAndroidInjector()
    abstract  FavoriteActivityFragment favoriteActivityFragment();

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory (ViewModelFactory viewModelFactory);

}
