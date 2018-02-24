package com.princess.android.cryptonews.newslist.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.princess.android.cryptonews.AppExecutors;
import com.princess.android.cryptonews.api.NewsApiClient;
import com.princess.android.cryptonews.api.NewsApiService;
import com.princess.android.cryptonews.model.News;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Princess on 2/23/2018.
 */

public class NewsRepository {

    private NewsApiService newsApiService = NewsApiClient.getClient().create(NewsApiService.class);

    AppExecutors appExecutors;

    @Inject
    public NewsRepository(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;

    }

    public LiveData<List<News>> getAllNews(){
        MutableLiveData<List<News>> newsMutableLiveData = new MutableLiveData<>();
        newsApiService.getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news ->{
                    newsMutableLiveData.setValue(news);
                });
        return newsMutableLiveData;
    }


}
