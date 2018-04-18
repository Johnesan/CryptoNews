package com.princess.android.cryptonews.newslist.view.fragment;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.newslist.view.adapters.NewsAdapter;
import com.princess.android.cryptonews.newslist.viewmodel.NewsViewModel;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.newswebsite.view.ui.NewsWebPageActivity;
import com.princess.android.cryptonews.util.ConnectionClassLiveData;
import com.princess.android.cryptonews.util.ConnectionTest;
import com.princess.android.cryptonews.util.PreferenceUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class LatestNewsActivityFragment extends DaggerFragment
        implements SwipeRefreshLayout.OnRefreshListener,
        NewsAdapter.ItemClicked {

    @Inject
    ViewModelProvider.Factory  factory;
    NewsViewModel newsViewModel;

    @Inject
    PreferenceUtils preferenceUtils;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.news_container)
    View mContainer;

    public NewsAdapter mAdapter;
    public List<News> newsList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    String mCurrentFontSize = null;
    int mFontSizeTitle;
    int mFontSizeDetails;

    public  static  final int MobileData = 2;
    public static final int WifiData = 1;

    boolean isConnected;

    LiveData<List<News>> news;

    @Inject
    ConnectionClassLiveData connectionClassLiveData;

    public LatestNewsActivityFragment() {
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_latest_news, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorAccent, R.color.colorAccent, R.color.colorAccent);
        setupViews();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsViewModel = ViewModelProviders.of(this, factory).get(NewsViewModel.class);
        if (mAdapter == null) {
            mAdapter = new NewsAdapter(getContext(), newsList, this);
        }

        //Connection Listener to give us real time internet connection status
        connectionClassLiveData.observe(this, connectionModel -> {
            if (connectionModel.isConnected()) {
                isConnected = true;
                if (newsList.size() == 0) {
                    newsViewModel.refresh();
                }
            } else {
                isConnected = false;
                Snackbar.make(mContainer, R.string.error, Snackbar.LENGTH_LONG).show();

            }
        });
        getNews();


    }

    //Make a call to the repository to get the available News
    private void getNews() {
        newsViewModel.getAllLatestNews()
            .observe(this, news -> {
                newsList = news;
                mAdapter.setItems(sortDate(newsList));
                mRecyclerView.setAdapter(mAdapter);
                swipeRefreshLayout.setRefreshing(false);
            });
    }

    private boolean checkConnection(){
        return ConnectionTest.isNetworkAvailable(getActivity());
    }

    private void setupViews(){
        //This layout controls how many items are shown in portrait and Landscape
        layoutManager = new GridLayoutManager(getActivity(),2);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else  {
            layoutManager = new GridLayoutManager(getActivity(), 3);
        }

        mRecyclerView.setLayoutManager(layoutManager);


        // we want to watch if the user scrolls down  when there is no internet connection
        // to tell the user the news will not be loaded
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isConnected){
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
                        Snackbar.make(mContainer, R.string.error, Snackbar.LENGTH_LONG).show();

                    }
                }
            }
        });


    }


    @Override
    public void onRefresh() {
        if(isConnected){
            newsViewModel.refresh();
            swipeRefreshLayout.setRefreshing(false);
        } else {

            //Show Snackbar item when there is no internet
            Snackbar.make(mContainer, R.string.error, Snackbar.LENGTH_LONG).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    public List<News> sortDate(List<News> list) {

        Collections.sort(list, new Comparator<News>() {
            @Override
            public int compare(News o1, News o2) {

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
                Date mDate1 = null;
                Date mDate2 = null;

                try {
                    mDate1 = simpleDateFormat.parse(o1.getDate());
                    mDate2 = simpleDateFormat.parse(o2.getDate());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (mDate1 != null && mDate1.after(mDate2)) {
                    return -1;
                } else {

                    return 1;
                }
            }

        });
        return list;
    }

    @Override
    public void onResume() {
        super.onResume();

        //refresh if font size Changed

        if(refreshFontSize()){

                mAdapter.setFontSizes(mFontSizeTitle, mFontSizeDetails);
                mAdapter.notifyDataSetChanged();
//                Toast.makeText(getContext(), preferenceUtils.getFontSize(), Toast.LENGTH_SHORT).show();

        }

    }

    private boolean refreshFontSize() {
        final String fontSize = preferenceUtils.getFontSize();
        if ((mCurrentFontSize == null || (!mCurrentFontSize.equals(fontSize)))){
            mCurrentFontSize = fontSize;

            if (fontSize.equals("0")){
                mFontSizeTitle = 10;
                mFontSizeDetails = 9;
            }else  if (fontSize.equals("1"))
            {mFontSizeTitle = 13;
                mFontSizeDetails = 10;}
                else {
                mFontSizeTitle = 14;
                mFontSizeDetails = 11;
            }
            return true;
        }else {

            return false;
        }
    }

    @Override
    public void onClick(News data) {
        Intent intent = new Intent(getContext(), NewsWebPageActivity.class);
        //Get the link of the website to be opened on the Web page
        String link = data.getGuid().getRendered();
        //Get the title of each news and format it to normal characters
        String title = String.valueOf(Html.fromHtml(data.getTitle().getRendered()));

        if (isConnected){

        if (!preferenceUtils.getViewNewsWithIn().equals("0")){
            openURLInBrowser(link);
        }else {
            //Pass the title and link to the next activity
            intent.putExtra("url", link);
            intent.putExtra("title", title);
            startActivity(intent);
        }}else {
            Snackbar.make(mContainer, R.string.error, Snackbar.LENGTH_LONG).show();
        }
    }


    // method to open the the browser to read news
    private void openURLInBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_latest_news, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }
}
