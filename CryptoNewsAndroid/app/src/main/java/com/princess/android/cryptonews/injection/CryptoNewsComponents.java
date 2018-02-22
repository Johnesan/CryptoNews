package com.princess.android.cryptonews.injection;

import javax.inject.Singleton;

import dagger.Component;

/**
 * This is a generic class to inject all our vieModels
 */

@Singleton
@Component (modules = {CryptoNewsModule.class})
public interface CryptoNewsComponents {


    interface  Injectable{
        void inject (CryptoNewsComponents cryptoNewsComponents);
    }
}
