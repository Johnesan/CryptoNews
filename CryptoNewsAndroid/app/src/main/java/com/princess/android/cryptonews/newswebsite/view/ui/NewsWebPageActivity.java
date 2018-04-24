package com.princess.android.cryptonews.newswebsite.view.ui;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.model.Favorite;
import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.newswebsite.view.ui.viewModel.NewsWebpageViewModel;

import org.parceler.Parcels;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import eu.davidea.flexibleadapter.utils.Log;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewsWebPageActivity extends DaggerAppCompatActivity {

    String title;
    String url;
    int uid;

    News news;

    //this is necessary to change the icons
    boolean favorited = false;

    MenuItem notFavorite;

    @Inject
    ViewModelProvider.Factory factory;

    NewsWebpageViewModel newsWebpageViewModel;

    private  final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_web_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

       if (getIntent().hasExtra("NEWS")){
            news  = Parcels.unwrap(getIntent().getParcelableExtra("NEWS"));
                uid = news.getId();
                url = news.getLink();
                title = String.valueOf(Html.fromHtml(news.getTitle().getRendered()));

       }

        newsWebpageViewModel = ViewModelProviders.of(this, factory).get(NewsWebpageViewModel.class);
        newsWebpageViewModel.setId(uid);
    }

    @Override
    protected void onStart() {
        super.onStart();

        newsWebpageViewModel.isFavorite().observe(this, favorite -> {
            if (favorite != null){
                //this is necesssary to change the icon
                favorited = true;
                this.invalidateOptionsMenu();

            }else {

                favorited = false;
                this.invalidateOptionsMenu();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news_webpage, menu);
        notFavorite = menu.findItem(R.id.action_notFavorite);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {

            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = title + " " + url;
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));

            return true;

        } else if (id == android.R.id.home) {
            //This method returns the User to the same state of the previous  Activity

          super.onBackPressed();
            return true;
        }
        else  if (id == R.id.action_notFavorite){
            Favorite favorite = new Favorite(news.getDate(), news.getLink(), news.getTitle(), news.getEmbedded(), news.getId(), news.getGuid());

            if (!favorited) {
               mDisposable.add(newsWebpageViewModel.setFavorite(favorite)
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() ->
                                        Toast.makeText(this, "Added To Favorite", Toast.LENGTH_SHORT).show(),
                                throwable -> Log.e("TAG", "Unable to update id", throwable)
                        ));
            }else {
                mDisposable.add(newsWebpageViewModel.deletFavorite(String.valueOf(favorite.getId()))
                        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {

                                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();

                                },

                                throwable -> android.util.Log.e("TAG", "Unable to update id")
                        ));
            }

            return  true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mDisposable.clear();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (favorited){

            notFavorite.setIcon(R.drawable.ic_icon_star);
        }else{

            notFavorite.setIcon(R.drawable.ic_icon_star_empty);

        }

        return super.onPrepareOptionsMenu(menu);
    }
}
