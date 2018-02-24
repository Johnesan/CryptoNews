package com.princess.android.cryptonews.newslist.viewmodel;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.injection.CryptoNewsComponents;
import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.newslist.repository.NewsRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Princess on 2/22/2018.
 */

public class NewsViewModel extends AndroidViewModel implements CryptoNewsComponents.Injectable{

   private  NewsRepository newsRepository;

   @Inject
    public NewsViewModel(NewsRepository newsRepository) {
       super(AppController.getInstance());
        this.newsRepository = newsRepository;
    }

    public LiveData<List<News>> getAllLatestNews(){
        return newsRepository.getAllNews();
    }

    @Override
    public void inject(CryptoNewsComponents cryptoNewsComponents) {
        cryptoNewsComponents.inject(this);
    }
}
