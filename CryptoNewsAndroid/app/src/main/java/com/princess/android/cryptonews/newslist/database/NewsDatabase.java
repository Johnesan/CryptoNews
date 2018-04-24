package com.princess.android.cryptonews.newslist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.princess.android.cryptonews.Favourite.dao.FavoriteDao;
import com.princess.android.cryptonews.model.Favorite;
import com.princess.android.cryptonews.model.News;

/**
 * Created by Princess on 3/16/2018.
 */

@Database(entities = {News.class, Favorite.class}, version = 2)
@TypeConverters({Converters.class})
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
    public abstract FavoriteDao favoriteDao();
}
