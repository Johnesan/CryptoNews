package com.princess.android.cryptonews.settings.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.princess.android.cryptonews.AppExecutors;
import com.princess.android.cryptonews.api.NewsApiService;
import com.princess.android.cryptonews.model.News;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by numb3rs on 3/7/18.
 */

public class CheckValidUrlRepository {

    @Inject
    NewsApiService newsApiService;

    boolean isValidUrl;

    AppExecutors appExecutors;

    @Inject
    CheckValidUrlRepository(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;

    }

    public boolean isValidUrl(String url){
        MutableLiveData<List<News>> newsMutableLiveData = new MutableLiveData<>();
        newsApiService.getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<News>>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<News> news) {
                        isValidUrl = true;
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        isValidUrl = false;

                    }
                });

        return isValidUrl;
    }
}
