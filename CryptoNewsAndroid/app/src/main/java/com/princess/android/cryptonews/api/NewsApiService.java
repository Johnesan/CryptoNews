package com.princess.android.cryptonews.api;

import com.princess.android.cryptonews.model.News;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;


/**
 * Created by Princess on 2/22/2018.
 */

public interface NewsApiService {

    @GET("wp-json/wp/v2/posts?_embed")
    Observable<List<News>> getLatestNews();
}
