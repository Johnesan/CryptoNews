package com.princess.android.cryptonews.newswebsite.view.ui.viewModel;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.injection.CryptoNewsComponents;
import com.princess.android.cryptonews.model.Favorite;
import com.princess.android.cryptonews.newswebsite.view.ui.repository.NewsWebpageRepository;
import com.princess.android.cryptonews.util.AbsentLiveData;

import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.functions.Action;

/**
 * Created by numb3rs on 4/21/18.
 */

public class NewsWebpageViewModel  extends AndroidViewModel implements CryptoNewsComponents.Injectable{

    final MutableLiveData<FavoriteId> favId;

    private LiveData<Favorite> favorite;

    private NewsWebpageRepository newsWebpageRepository;

    @Inject
    public NewsWebpageViewModel(NewsWebpageRepository newsWebpageRepository) {
        super(AppController.getInstance());
        this.favId = new MutableLiveData<>();
        this.newsWebpageRepository =  newsWebpageRepository;

        favorite = Transformations.switchMap(favId, input -> {
            if (input.isEmpty()){
                return  AbsentLiveData.create();
            }
            return  newsWebpageRepository.isFavorite(input.uid);
        });

        

    }

    public LiveData<Favorite> isFavorite (){
        return  favorite;
    }

    public Completable setFavorite(Favorite favorite){

        return  Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

               long rowsUpdated = newsWebpageRepository.setFavorite(favorite);
                Log.e("vC", "number of rows updated" + rowsUpdated);
            }
        });

    }

    @Override
    public void inject(CryptoNewsComponents cryptoNewsComponents) {
        cryptoNewsComponents.inject(this);
    }


    public void setId(int uid) {
        FavoriteId favoriteId = new FavoriteId(String.valueOf(uid));
        if (Objects.equals(favId.getValue(), favoriteId)){
            return;
        }
        favId.setValue(favoriteId);


    }

    public Completable deletFavorite(String s) {

        return  Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

                int deleted = newsWebpageRepository.deleteFavorte(s);
                Log.e("FV", "number of rows deleted" + deleted);
            }
        });

    }

    @VisibleForTesting
    static  class FavoriteId{
        public  final String uid;

        public FavoriteId(String uid) {
            this.uid = uid;
        }

        boolean isEmpty (){
            return  uid == null ||
                    uid.length() ==0;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            FavoriteId favoriteId = (FavoriteId) o;

            return uid != null ? !uid.equals(favoriteId.uid) : favoriteId.uid != null;

        }

    }
}
