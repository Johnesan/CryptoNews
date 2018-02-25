package com.princess.android.cryptonews.newswebsite.view.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.newslist.view.fragment.LatestNewsActivityFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsWebPageFragment extends Fragment {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.empty_progress_bar)
    ProgressBar progressBar;
    String url;
    String title;

    public NewsWebPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_web_page, container, false);

        //Initializing the Web view
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getUrl();
        getTitle();
    }

    public void getUrl(){
        if(getActivity().getIntent().hasExtra("url")) {
            url = getActivity().getIntent().getExtras().getString("url");
            progressBar.setVisibility(View.GONE);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadsImagesAutomatically(true);
            webView.setWebViewClient(new WebViewClient());
            webView.loadUrl(String.valueOf(url));
        }
    }

    public void getTitle(){
        if(getActivity().getIntent().hasExtra("title")) {
            title = getActivity().getIntent().getExtras().getString("title");
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(title);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
