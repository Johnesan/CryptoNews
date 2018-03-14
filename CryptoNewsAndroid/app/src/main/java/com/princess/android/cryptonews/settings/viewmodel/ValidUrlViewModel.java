package com.princess.android.cryptonews.settings.viewmodel;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.injection.CryptoNewsComponents;
import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.settings.repository.CheckValidUrlRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by numb3rs on 3/7/18.
 */

public class ValidUrlViewModel extends AndroidViewModel implements CryptoNewsComponents.Injectable {
    private  CheckValidUrlRepository checkValidUrlRepository;

    @Inject
    public ValidUrlViewModel() {
        super(AppController.getInstance());

    }

    public LiveData<List<News>> isValidUrl(){
        checkValidUrlRepository = new CheckValidUrlRepository();
        return  checkValidUrlRepository.isValidUrl();
    }


    @Override
    public void inject(CryptoNewsComponents cryptoNewsComponents) {
        cryptoNewsComponents.inject(this);

    }
}
