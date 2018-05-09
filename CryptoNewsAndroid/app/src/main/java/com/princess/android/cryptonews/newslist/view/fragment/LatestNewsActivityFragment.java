package com.princess.android.cryptonews.newslist.view.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.binding.FragmentDataBindingComponent;
import com.princess.android.cryptonews.commons.AutoClearedValue;
import com.princess.android.cryptonews.databinding.FragmentLatestNewsBinding;
import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.newslist.view.adapters.NewsAdapter;
import com.princess.android.cryptonews.newslist.viewmodel.NewsViewModel;
import com.princess.android.cryptonews.newswebsite.view.ui.NewsWebPageActivity;
import com.princess.android.cryptonews.util.ConnectionClassLiveData;
import com.princess.android.cryptonews.util.PreferenceUtils;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

/**
 * A placeholder fragment containing a simple view.
 */
public class LatestNewsActivityFragment
        extends DaggerFragment
        implements
        SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener,
        SortedListAdapter.Callback{


    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    private static final String BUNDLE_KEY_SEARCH =  "SEARCH_QUERY";
    String mSearchQuery;


    public static LatestNewsActivityFragment newInstance() {
        
        Bundle args = new Bundle();
        
        LatestNewsActivityFragment fragment = new LatestNewsActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //this is responsible for ordering our news
    private  static final  Comparator<News> COMPARATOR =
            new SortedListAdapter.ComparatorBuilder<News>()
            .setOrderForModel(News.class, new Comparator<News>() {
                @Override
                public int compare(News a, News b) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
                    Date mDate1 = null;
                    Date mDate2 = null;

                    try {
                        mDate1 = simpleDateFormat.parse(a.getDate());
                        mDate2 = simpleDateFormat.parse(b.getDate());

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (mDate1 == null || mDate2 == null )
                        return  0;
                    return mDate2.compareTo(mDate1);
                }
            })
            .build();

    @Inject
    ViewModelProvider.Factory  factory;
    NewsViewModel newsViewModel;


    @Inject
    PreferenceUtils preferenceUtils;


    public NewsAdapter mAdapter;
    AutoClearedValue<FragmentLatestNewsBinding> binding;
    private Animator mAnimator;

    public List<News> newsList = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;

    String mCurrentFontSize = null;
    int mFontSizeTitle;
    int mFontSizeDetails;

    public  static  final int MobileData = 2;
    public static final int WifiData = 1;

    boolean isConnected;
    boolean hasFetchedFromDatabase;

    LiveData<List<News>> news;

    @Inject
    ConnectionClassLiveData connectionClassLiveData;

    public LatestNewsActivityFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null){
            mSearchQuery = savedInstanceState.getString(BUNDLE_KEY_SEARCH);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentLatestNewsBinding fragmentLatestNewsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_latest_news, container, false, dataBindingComponent);

        //init the datbinding for ths view
        binding= new AutoClearedValue<>(this, fragmentLatestNewsBinding);
        setHasOptionsMenu(true);
        initSwipeToRefresh();
        setupViews();
        return fragmentLatestNewsBinding.getRoot();
    }

    private void initSwipeToRefresh() {
        binding.get().swipeContainer.setOnRefreshListener(this);
        binding.get().swipeContainer.setRefreshing(true);
        binding.get().swipeContainer.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        newsViewModel = ViewModelProviders.of(this, factory).get(NewsViewModel.class);

        if (mAdapter == null) {
            mAdapter = new NewsAdapter(getContext(),
                   COMPARATOR, this::onClick, dataBindingComponent);
        }
        mAdapter.addCallback(this);
        binding.get().recyclerView.setAdapter(mAdapter);

        //Connection Listener to give us real time internet connection status
        connectionClassLiveData.observe(this, connectionModel -> {
            if (connectionModel.isConnected()) {
                isConnected = true;
                if (hasFetchedFromDatabase && newsList.size() == 0) {
                        newsViewModel.refresh();
                }
            } else {
                isConnected = false;
                Snackbar.make(binding.get().newsContainer,
                        R.string.error, Snackbar.LENGTH_LONG).show();

            }
        });
        getNews();


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(BUNDLE_KEY_SEARCH, mSearchQuery);
        super.onSaveInstanceState(outState);


    }

    //Make a call to the repository to get the available News
    private void getNews() {
        newsViewModel.getAllLatestNews()
            .observe(this, news -> {
                if ( news != null && news.size() == 0){

                    hasFetchedFromDatabase= true;
                }
                if (news != null && news.size() >0)
                    newsList= news;
                    mAdapter.edit().
                            replaceAll(news)
                            .commit();

                binding.get().swipeContainer.setRefreshing(false);
            });
    }

    private void setupViews(){
        //This layout controls how many items are shown in portrait and Landscape
        layoutManager = new GridLayoutManager(getActivity(),2);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else  {
            layoutManager = new GridLayoutManager(getActivity(), 3);
        }

        binding.get().recyclerView.setLayoutManager(layoutManager);
        // we want to watch if the user scrolls down  when there is no internet connection
        // to tell the user the news will not be loaded
        binding.get().recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!isConnected){
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
                        Snackbar.make(binding.get().newsContainer, R.string.error, Snackbar.LENGTH_LONG).show();
                    }
                }
            }
        });
    }


    @Override
    public void onRefresh() {
        if(isConnected){
            newsViewModel.refresh();
            binding.get().swipeContainer.setRefreshing(false);
        } else {

            //Show Snackbar item when there is no internet
            Snackbar.make(binding.get().newsContainer, R.string.error, Snackbar.LENGTH_LONG).show();
            binding.get().swipeContainer.setRefreshing(false);
        }
    }



    @Override
    public void onResume() {
        super.onResume();

        //refresh if font size Changed

        if(refreshFontSize()){

                mAdapter.setFontSizes(mFontSizeTitle, mFontSizeDetails);
                mAdapter.notifyDataSetChanged();

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

    public void onClick(News data) {
        Intent intent = new Intent(getContext(), NewsWebPageActivity.class);
        //Get the link of the website to be opened on the Web page
        String link = data.getGuid().getRendered();

        if (isConnected){

        if (!preferenceUtils.getViewNewsWithIn().equals("0")){
            openURLInBrowser(link);
        }else {
            //Pass the title and link to the next activity
            intent.putExtra("NEWS", Parcels.wrap(data));
            startActivity(intent);
        }}else {
            Snackbar.make(binding.get().newsContainer, R.string.error, Snackbar.LENGTH_LONG).show();
        }
    }


    // method to open the the browser to read news
    private void openURLInBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final  SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
       if (mSearchQuery != null && !mSearchQuery.isEmpty()){
           String s = mSearchQuery;
           searchItem.expandActionView();
           searchView.setQuery(s, false);
            searchView.clearFocus();
        }

    }

    @Override
    public void onEditStarted() {

        if (binding.get().editProgressBar.getVisibility() != View.VISIBLE) {
            binding.get().editProgressBar.setVisibility(View.VISIBLE);
            binding.get().editProgressBar.setAlpha(0.0f);
        }

        if (mAnimator != null){
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(binding.get().editProgressBar, View.ALPHA, 1.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.start();

        binding.get().recyclerView.animate().alpha(0.5f);
    }

    @Override
    public void onEditFinished() {
        binding.get().recyclerView.scrollToPosition(0);
        binding.get().recyclerView.animate().alpha(1.0f);


        if (mAnimator != null) {
            mAnimator.cancel();
        }

        mAnimator = ObjectAnimator.ofFloat(binding.get().editProgressBar, View.ALPHA, 0.0f);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {

            private boolean mCanceled = false;

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                mCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (!mCanceled) {
                    binding.get().editProgressBar.setVisibility(View.GONE);
                }
            }
        });
        mAnimator.start();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mSearchQuery = newText;
        final  List<News> filteredNewsList = filter(newsList,  newText);
        if (filteredNewsList.size() == 0){
            binding.get().emptyView.setVisibility(View.VISIBLE);
        }else {
            binding.get().emptyView.setVisibility(View.INVISIBLE);
        }

        mAdapter.edit()
                .replaceAll(filteredNewsList)
                .commit();

        return  true;
        }

    private  static List<News> filter(List<News> news, String query){
        final String lowerCaseQuery = query.toLowerCase();
        final List<News> filteredNewsList = new ArrayList<>();
        for (News news1 : news){
            final  String text = news1.getTitle().getRendered().toLowerCase();
            if (text.contains(lowerCaseQuery)){
                filteredNewsList.add(news1);
            }
        }
        return  filteredNewsList;
    }

}
