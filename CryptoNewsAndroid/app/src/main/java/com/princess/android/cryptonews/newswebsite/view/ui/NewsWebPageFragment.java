package com.princess.android.cryptonews.newswebsite.view.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.settings.Activity.ManageBlogSettings;
import com.princess.android.cryptonews.util.ConnectionTest;
import com.princess.android.cryptonews.util.ShowAlert;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsWebPageFragment extends Fragment {

    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.empty_progress_bar)
    ProgressBar progressBar;
    String url;
    String title;
    ShowAlert alert = new ShowAlert();

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

    public void getUrl() {
        if (getActivity().getIntent().hasExtra("url")) {
            url = getActivity().getIntent().getExtras().getString("url");


            if (ConnectionTest.isNetworkAvailable(getActivity())) {
                // Enable Javascript
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setLoadsImagesAutomatically(true);

                // Force links and redirects to open in the WebView instead of in a browser
                webView.setWebViewClient(new myWebClient());

                // Use remote resource
                webView.loadUrl(String.valueOf(url));
            } else {
                progressBar.setVisibility(View.GONE);
                alert.showAlertDialog(getActivity(),
                        "Network Error",
                        "Internet not available, Check your internet connectivity and try again",
                        true, false, null, null);
            }
        }
    }

    //Load WebView with Progress bar
    public class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            progressBar.setVisibility(View.VISIBLE);
//            view.loadUrl(String.valueOf(url));

            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }

    public void getTitle() {
        if (getActivity().getIntent().hasExtra("title")) {
            title = getActivity().getIntent().getExtras().getString("title");
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
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