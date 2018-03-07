package com.princess.android.cryptonews.api;

import com.princess.android.cryptonews.model.News;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by numb3rs on 3/7/18.
 */

public interface TestApiService {
    @GET("wp-json/wp/v2/posts?_embed")
    Observable<List<News>> getLatestNews();
}
