package com.princess.android.cryptonews.newswebsite.view.ui.repository;

import android.arch.lifecycle.LiveData;

import com.princess.android.cryptonews.AppExecutors;
import com.princess.android.cryptonews.Favourite.dao.FavoriteDao;
import com.princess.android.cryptonews.model.Favorite;
import com.princess.android.cryptonews.newslist.database.NewsDao;

import javax.inject.Inject;

/**
 * Created by numb3rs on 4/21/18.
 */

public class NewsWebpageRepository {

    AppExecutors appExecutors;
    FavoriteDao favoriteDao;

    @Inject
    public NewsWebpageRepository(AppExecutors appExecutors, FavoriteDao favoriteDao) {
        this.appExecutors = appExecutors;
        this.favoriteDao = favoriteDao;
    }



    public  long setFavorite(Favorite favorite){
      return favoriteDao.save(favorite);
    }

    public LiveData<Favorite> isFavorite(String uid){
        return  favoriteDao.isFavorite(uid);
    }


    public int deleteFavorte(String uid){
        return favoriteDao.deleteFavorite(uid);
    }


}
