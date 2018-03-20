package com.princess.android.cryptonews.settings.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import android.widget.Toast;

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.AppExecutors;
import com.princess.android.cryptonews.api.TestApiService;
import com.princess.android.cryptonews.api.TestWebServiceNoDagger;
import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.util.PreferenceUtils;

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


    PreferenceUtils preferenceUtils = new PreferenceUtils(AppController.getContextInstance());


    TestApiService newsApiService  = TestWebServiceNoDagger.
            provideTestWebService(preferenceUtils.getTestUrl());

    boolean isValidUrl;


    public CheckValidUrlRepository() {

    }

    public LiveData<List<News>> isValidUrl(){
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
                        newsMutableLiveData.setValue(news);

                    }

                    @Override
                    public void onError(Throwable e) {
                        newsMutableLiveData.setValue(null);
                    }

                    @Override
                    public void onComplete() {
                        Log.e("TAG", "onComplete was reached");

                    }
                });
        return  newsMutableLiveData;

    }
}
