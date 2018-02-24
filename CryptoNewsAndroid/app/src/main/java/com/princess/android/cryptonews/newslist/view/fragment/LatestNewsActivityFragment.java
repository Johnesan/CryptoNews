package com.princess.android.cryptonews.newslist.view.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.newslist.view.adapters.NewsAdapter;
import com.princess.android.cryptonews.newslist.viewmodel.NewsViewModel;
import com.princess.android.cryptonews.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.DaggerFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class LatestNewsActivityFragment extends Fragment {

//    @Inject
//    ViewModelProvider.Factory factory;

    public NewsViewModel newsViewModel;

    private NewsAdapter mAdapter;
    private List<News> newsList = new ArrayList<>();
    @BindView(R.id.recyclerView)
    RecyclerView mRecylerView;

    public LatestNewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_latest_news, container, false);
        ButterKnife.bind(this, view);
    return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new NewsAdapter(getActivity(), newsList);
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.getAllLatestNews().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> news) {
                Log.e("RESULT", news.get(0).getTitle().toString());
                //newsList = news.
            }
        });
    }
}
