package com.princess.android.cryptonews.api;

import com.princess.android.cryptonews.model.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Princess on 2/22/2018.
 */

public interface NewsApiService {

    @GET("wp-json/wp/v2/posts/")
    Call<News> getLatestNews(@Path("id") int id);
}
