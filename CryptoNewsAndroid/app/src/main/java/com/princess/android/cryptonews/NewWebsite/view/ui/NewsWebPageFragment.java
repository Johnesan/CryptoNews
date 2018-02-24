package com.princess.android.cryptonews.NewWebsite.view.ui;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.api.NewsApiClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsWebPageFragment extends Fragment {

    @BindView(R.id.webView)
    WebView webView;
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
        if(getActivity().getIntent().hasExtra("data")) {
            url = getActivity().getIntent().getExtras().getString("url");
            Log.e("URL", url);

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

            if(getActivity().getActionBar() != null){
                getActivity().getActionBar().setTitle(title);
            }
        }
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {

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
