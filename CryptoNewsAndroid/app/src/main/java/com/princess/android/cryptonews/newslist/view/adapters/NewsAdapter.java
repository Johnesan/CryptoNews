package com.princess.android.cryptonews.newslist.view.adapters;

import android.content.Context;
import android.databinding.DataBindingComponent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.databinding.NewsListItemBinding;
import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.util.PreferenceUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsAdapter extends
        SortedListAdapter<News> {

    private final DataBindingComponent dataBindingComponent;

    private final String TAG = NewsAdapter.class.getSimpleName();

    int mFontSizeTitle;
    int mFontSizeDetails;

    public interface Listener {
        void onNewsItemClicked(News news);
    }

    private Listener mListener;


    public NewsAdapter(Context context,
                       Comparator<News> comparator,
                       Listener listener, DataBindingComponent dataBindingComponent) {
        super(context, News.class, comparator);
        mListener = listener;
        this.dataBindingComponent = dataBindingComponent;
    }

    @NonNull
    @Override
    protected ViewHolder<? extends News> onCreateViewHolder(LayoutInflater layoutInflater, ViewGroup viewGroup, int i) {
        final  NewsListItemBinding binding = NewsListItemBinding.inflate(layoutInflater, viewGroup, false, dataBindingComponent);
        return  new NewsViewHolder(binding, mListener);
    }




    public void setFontSizes(int mCurrentFontSize, int mFontSizeDetails) {
        this.mFontSizeTitle = mCurrentFontSize;
        this.mFontSizeDetails = mFontSizeDetails;
    }



    public class NewsViewHolder extends SortedListAdapter.ViewHolder<News>{


        private final NewsListItemBinding mBinding;


        @Override
        protected void performBind(News news) {
            mBinding.setNews(news);
        }

        @Override
        protected void onAttach() {
            mBinding.newsTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mFontSizeTitle);
            mBinding.newsDate.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mFontSizeDetails);
            mBinding.newsSite.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mFontSizeDetails);

        }

        public NewsViewHolder(NewsListItemBinding binding, NewsAdapter.Listener listener) {
            super(binding.getRoot());
            binding.setListener(listener);
            mBinding = binding;


        }


}
}
