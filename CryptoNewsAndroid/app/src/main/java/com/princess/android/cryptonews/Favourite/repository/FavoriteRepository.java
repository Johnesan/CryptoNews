package com.princess.android.cryptonews.Favourite.repository;

import android.arch.lifecycle.LiveData;

import com.princess.android.cryptonews.AppExecutors;
import com.princess.android.cryptonews.Favourite.dao.FavoriteDao;
import com.princess.android.cryptonews.model.Favorite;
import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.newslist.database.NewsDao;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by numb3rs on 4/20/18.
 */

public class FavoriteRepository {


    AppExecutors appExecutors;
    FavoriteDao favoriteDao;

    @Inject
    public FavoriteRepository(AppExecutors appExecutors,FavoriteDao  favoriteDao) {
        this.appExecutors = appExecutors;
        this.favoriteDao = favoriteDao;
    }


    public LiveData<List<Favorite>> getFavoriteNews(){
        return  favoriteDao.queryFavorite();
    }

    public int deleteFavorte(String uid){
        return favoriteDao.deleteFavorite(uid);
    }


}
