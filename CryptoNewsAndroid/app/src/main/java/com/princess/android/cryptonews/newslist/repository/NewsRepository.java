package com.princess.android.cryptonews.newslist.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.AppExecutors;
import com.princess.android.cryptonews.AppModule;
import com.princess.android.cryptonews.api.NewsApiService;
import com.princess.android.cryptonews.di.NetworkModule;
import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.newslist.database.NewsDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function4;
import io.reactivex.schedulers.Schedulers;
import kotlin.jvm.functions.Function2;
import retrofit2.Retrofit;

/**
 * Created by Princess on 2/23/2018.
 */

public class NewsRepository {

    @Inject
    NewsApiService newsApiService;

    AppExecutors appExecutors;
    NewsDao newsDao;

    @Inject
    NewsRepository(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;

    }

    public LiveData<List<News>> getAllNews() {
        MutableLiveData<List<News>> newsMutableLiveData = new MutableLiveData<>();

        Observable<List<News>> getNews = NetworkModule.api.getLatestNews().subscribeOn(Schedulers.io());
        Observable<List<News>> getNews2 = NetworkModule.api2.getLatestNews().subscribeOn(Schedulers.io());
        Observable<List<News>> getNews3 = NetworkModule.api3.getLatestNews().subscribeOn(Schedulers.io());
        Observable<List<News>> getNews4 = NetworkModule.api4.getLatestNews().subscribeOn(Schedulers.io());

        Observable.zip(getNews, getNews2, getNews3, getNews4,
                new Function4<List<News>, List<News>, List<News>, List<News>, List<News>>() {
                    @Override
                    public List<News> apply(List<News> news, List<News> news2, List<News> news3, List<News> news4) throws Exception {
                        List<News> newsList = new ArrayList<>();
                        for (News newsObj : news) {

                            Executors.newSingleThreadExecutor().execute(() -> {
                                newsDao.save(newsObj);
                            });
                            newsList.add(newsObj);
                        }
                        for (News newsObj : news2) {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                newsDao.save(newsObj);
                            });
                            newsList.add(newsObj);
                        }
                        for (News newsObj : news3) {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                newsDao.save(newsObj);
                            });
                            newsList.add(newsObj);
                        }
                        for (News newsObj : news4) {
                            Executors.newSingleThreadExecutor().execute(() -> {
                                newsDao.save(newsObj);
                                //AppController.db.newsDao().save(newsObj);
                            });
                            newsList.add(newsObj);
                        }

                        return newsList;
                    }
                })
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(news -> {
                    newsMutableLiveData.setValue(news);
                });

        //return AppController.db.newsDao().queryNews();
        return newsDao.queryNews();

    }
}
