package com.princess.android.cryptonews.newslist.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.princess.android.cryptonews.model.ccn.News;
import com.princess.android.cryptonews.newslist.view.adapters.NewsAdapter;
import com.princess.android.cryptonews.newslist.viewmodel.NewsViewModel;
import com.princess.android.cryptonews.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class LatestNewsActivityFragment extends DaggerFragment {

    @Inject
    ViewModelProvider.Factory  factory;
    NewsViewModel newsViewModel;

    private NewsAdapter mAdapter;
    private List<News> newsList = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_progress_bar)
    ProgressBar progressBar;

    RecyclerView.LayoutManager layoutManager;

    public LatestNewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_latest_news, container, false);
        ButterKnife.bind(this, view);
        setupViews();
    return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        newsViewModel = ViewModelProviders.of(this, factory).get(NewsViewModel.class);
        newsViewModel.getAllLatestNews().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> news) {
                //Log.e("RESULT", news.get(0).getTitle().toString());
                newsList = news;
                mAdapter = new NewsAdapter(getActivity(), newsList);
                progressBar.setVisibility(View.GONE);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }

    private void setupViews(){

        layoutManager = new GridLayoutManager(getActivity(),2);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 4);
        }

        mRecyclerView.setLayoutManager(layoutManager);
    }
}
