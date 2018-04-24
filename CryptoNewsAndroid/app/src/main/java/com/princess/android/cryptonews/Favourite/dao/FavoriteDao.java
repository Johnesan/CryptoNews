package com.princess.android.cryptonews.Favourite.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.princess.android.cryptonews.model.Favorite;

import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface  FavoriteDao {

    @Insert(onConflict = REPLACE)
    long save(Favorite favorite);

    @Query("SELECT * FROM favorite")
    LiveData<List<Favorite>> queryFavorite();

    @Query(" DELETE FROM favorite WHERE id=:uid" )
    int deleteFavorite(String uid);

    @Query("SELECT * FROM favorite WHERE id=:uid LIMIT 1 ")
    LiveData<Favorite> isFavorite(String uid);
}
