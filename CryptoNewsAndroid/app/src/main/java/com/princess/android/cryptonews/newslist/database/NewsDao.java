package com.princess.android.cryptonews.newslist.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.princess.android.cryptonews.model.News;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Princess on 3/16/2018.
 */

@Dao
public interface NewsDao {

    @Insert(onConflict = REPLACE)
    void save(News news);

    @Query("SELECT * FROM news")
    LiveData<List<News>> queryNews();

}
