package com.princess.android.cryptonews.injection;

import com.princess.android.cryptonews.newslist.viewmodel.NewsViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * This is a generic class to inject all our vieModels
 */

@Singleton
@Component (modules = {CryptoNewsModule.class})
public interface CryptoNewsComponents {

    void inject(NewsViewModel newsViewModel);

    interface  Injectable{
        void inject (CryptoNewsComponents cryptoNewsComponents);
    }
}