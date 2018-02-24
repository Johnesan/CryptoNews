package com.princess.android.cryptonews.newslist.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.princess.android.cryptonews.api.NewsApiClient;
import com.princess.android.cryptonews.api.NewsApiService;
import com.princess.android.cryptonews.model.News;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Princess on 2/23/2018.
 */

public class NewsRepository {

    private NewsApiService newsApiService = NewsApiClient.getClient().create(NewsApiService.class);

    public LiveData<List<News>> getAllNews(){
        MutableLiveData<List<News>> newsMutableLiveData = new MutableLiveData<>();
        newsApiService.getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news ->{
                    newsMutableLiveData.setValue(news);
                    Log.e("TAG", news.toString());
                });
        return newsMutableLiveData;
    }


}
