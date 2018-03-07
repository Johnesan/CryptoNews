package com.princess.android.cryptonews.newslist.view.fragment;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.newslist.view.adapters.NewsAdapter;
import com.princess.android.cryptonews.newslist.viewmodel.NewsViewModel;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.util.ConnectionTest;
import com.princess.android.cryptonews.util.ShowAlert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class LatestNewsActivityFragment extends DaggerFragment implements SwipeRefreshLayout.OnRefreshListener{

    @Inject
    ViewModelProvider.Factory  factory;
    NewsViewModel newsViewModel;

    public NewsAdapter mAdapter;
    public List<News> newsList = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;

    RecyclerView.LayoutManager layoutManager;
    // Alert Dialog Manager
    ShowAlert alert = new ShowAlert();

    public LatestNewsActivityFragment() {
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_latest_news, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorAccent, R.color.colorAccent, R.color.colorAccent);
        setupViews();
    return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newsViewModel = ViewModelProviders.of(this, factory).get(NewsViewModel.class);
        if (checkConnection()) {
            newsViewModel.getAllLatestNews().observe(this, new Observer<List<News>>() {
                @Override
                public void onChanged(@Nullable List<News> news) {
                    newsList = news;
                    mAdapter = new NewsAdapter(getActivity(), sortDate(newsList));
                    progressBar.setVisibility(View.GONE);
                    mRecyclerView.setAdapter(mAdapter);
                }
            });
        } else {
            progressBar.setVisibility(View.GONE);
                alert.showAlertDialog(getActivity(),
                        "Network Error",
                        "Internet not available, Check your internet connectivity and try again",
                        true);
                return;
            }
    }

    private boolean checkConnection(){
        return ConnectionTest.isNetworkAvailable(getActivity());
    }

    private void setupViews(){

        layoutManager = new GridLayoutManager(getActivity(),2);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else  {
            layoutManager = new GridLayoutManager(getActivity(), 3);
        }

        mRecyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void onRefresh() {
        newsViewModel.refresh();
        swipeRefreshLayout.setRefreshing(false);
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
}
