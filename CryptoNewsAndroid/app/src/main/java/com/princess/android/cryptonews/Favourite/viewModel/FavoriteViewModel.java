package com.princess.android.cryptonews.Favourite.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.injection.CryptoNewsComponents;

/**
 * Created by numb3rs on 4/20/18.
 */

public class FavoriteViewModel extends AndroidViewModel implements CryptoNewsComponents.Injectable{


    public FavoriteViewModel() {
        super(AppController.getInstance());
    }

    @Override
    public void inject(CryptoNewsComponents cryptoNewsComponents) {
        cryptoNewsComponents.inject(this);
    }
}
