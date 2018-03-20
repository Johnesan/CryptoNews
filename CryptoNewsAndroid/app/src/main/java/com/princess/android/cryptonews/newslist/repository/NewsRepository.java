package com.princess.android.cryptonews.newslist.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.princess.android.cryptonews.AppExecutors;
import com.princess.android.cryptonews.api.NewsApiService;
import com.princess.android.cryptonews.di.NetworkModule;
import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.newslist.database.NewsDao;
import com.princess.android.cryptonews.util.PreferenceUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NewsRepository {

    @Inject
    NewsApiService newsApiService;

    @Inject
    PreferenceUtils preferenceUtils;

    private Executor diskExecutor;
    private Executor mainThreadExecutor;
    AppExecutors appExecutors;
    NewsDao newsDao;

    private MediatorLiveData<List<News>> newsLiveData = new MediatorLiveData<>();
    private LiveData<List<News>> newdDbSource;
    private static  final String TAG = NewsRepository.class.getSimpleName();



    @Inject
    NewsRepository(AppExecutors appExecutors, NewsDao newsDao) {
        this.appExecutors = appExecutors;
        diskExecutor = appExecutors.getDiskExecutor();
        mainThreadExecutor = appExecutors.getMainThreadExecutor();
        this.newsDao = newsDao;
        newdDbSource = loadUserNewsFromDb();
        setupNewsListener();

    }

    @WorkerThread
    private LiveData<List<News>> loadUserNewsFromDb() {
        Log.d("TAG", "Load User Deals from DB");
        return  newsDao.queryNews();
    }

    public void getAllNews() {

        Observable<List<News>> getNews = NetworkModule.api.getLatestNews().subscribeOn(Schedulers.io());
        Observable<List<News>> getNews2 = NetworkModule.api2.getLatestNews().subscribeOn(Schedulers.io());
        Observable<List<News>> getNews3 = NetworkModule.api3.getLatestNews().subscribeOn(Schedulers.io());
        Observable<List<News>> getNews4 = NetworkModule.api4.getLatestNews().subscribeOn(Schedulers.io());

        Observable.zip(getNews, getNews2, getNews3, getNews4,
                (news, news2, news3, news4) -> {
                    List<News> newsList = new ArrayList<>();
                    for (News newsObj : news) {

                        newsList.add(newsObj);
                    }
                    for (News newsObj : news2) {
                        newsList.add(newsObj);
                    }
                    for (News newsObj : news3) {
                        newsList.add(newsObj);
                    }
                    for (News newsObj : news4) {
                        newsList.add(newsObj);
                    }

                    return newsList;
                })
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<News>>() {
                               @Override
                               public void onSubscribe(Disposable d) {

                               }

                               @Override
                               public void onNext(List<News> newsList) {
                                    getNewsForuser(newsList);

                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onComplete() {

                               }
                           });
    }


    private void getNewsForuser(List<News> newsList) {
        Log.e(TAG, "Loading News for user was successful");
        Log.e(TAG, "Now Saving");
        preferenceUtils.storeTimeNewswasReceived();
        diskExecutor.execute(() -> {
            saveUserDealsTODb(newsList);
            mainThreadExecutor.execute(() -> {
                newsLiveData.addSource(loadUserNewsFromDb(), this::setValueNews);
            });
        });

    }


    @WorkerThread
    private void saveUserDealsTODb(List<News> newsList) {
        Log.d("TAG", "Saving User Deals to DB");

            for (News news : newsList){
                newsDao.save(news);
            }

    }

    private void fetchFromServer(){
        Log.d("TAG", "Fetching Deals From Server");
        getAllNews();
    }

    private void setupNewsListener(){
        newsLiveData.addSource(newdDbSource,  newsList -> {
            Log.d("TAG", "News Livedata Listener");
            if (shouldFetchNews(newsList)){
                fetchFromServer();
            }else {
                setValueNews(newsList);
            }
        });

    }

    private void setValueNews(List<News> newsValue) {
        if (newsValue != null){
            mainThreadExecutor.execute(() -> newsLiveData.setValue(newsValue));
        }
    }

    private boolean shouldFetchNews(List<News> newsList){
        long lastFetched = preferenceUtils.getLastTimeNewsWasStored();
        long timeOut = TimeUnit.MINUTES.toMillis(30) + lastFetched;
        return  newsList.size() <1 || Calendar.getInstance().getTimeInMillis() > timeOut;
    }

    public LiveData<List<News>> asLiveData(){
        return newsLiveData;
    }

}
