package com.princess.android.cryptonews.newslist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.princess.android.cryptonews.model.News;

/**
 * Created by Princess on 3/16/2018.
 */

@Database(entities = {News.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
