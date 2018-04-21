package com.princess.android.cryptonews.newswebsite.view.ui.viewModel;

import android.arch.lifecycle.AndroidViewModel;

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.injection.CryptoNewsComponents;

import javax.inject.Inject;

/**
 * Created by numb3rs on 4/21/18.
 */

public class NewsWebpageViewModel  extends AndroidViewModel implements CryptoNewsComponents.Injectable{


    @Inject
    public NewsWebpageViewModel() {
        super(AppController.getInstance());
    }

    @Override
    public void inject(CryptoNewsComponents cryptoNewsComponents) {
        cryptoNewsComponents.inject(this);
    }
}
