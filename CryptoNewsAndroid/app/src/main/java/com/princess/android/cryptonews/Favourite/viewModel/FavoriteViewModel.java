package com.princess.android.cryptonews.Favourite.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.Favourite.repository.FavoriteRepository;
import com.princess.android.cryptonews.injection.CryptoNewsComponents;
import com.princess.android.cryptonews.model.Favorite;
import com.princess.android.cryptonews.model.News;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.functions.Action;

/**
 * Created by numb3rs on 4/20/18.
 */

public class FavoriteViewModel extends AndroidViewModel implements CryptoNewsComponents.Injectable{

    FavoriteRepository favoriteRepository;

    @Inject
    public FavoriteViewModel(FavoriteRepository favoriteRepository) {
        super(AppController.getInstance());
        this.favoriteRepository = favoriteRepository;
    }


    public LiveData<List<Favorite>> getAllFavoriteNews(){
        return  favoriteRepository.getFavoriteNews();
    }

    public Completable deletFavorite (String uid){

        return  Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                int deleted = favoriteRepository.deleteFavorte(uid);
                Log.e("FV", "number of rows deleted" + deleted);
            }
        });

    }


    @Override
    public void inject(CryptoNewsComponents cryptoNewsComponents) {
        cryptoNewsComponents.inject(this);
    }
}
