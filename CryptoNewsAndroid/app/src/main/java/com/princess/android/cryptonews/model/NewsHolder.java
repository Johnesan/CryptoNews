package com.princess.android.cryptonews.model;

import android.arch.persistence.room.Ignore;
import android.content.Context;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.princess.android.cryptonews.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFilterable;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.flexibleadapter.items.IHolder;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by numb3rs on 4/29/18.
 */

public class NewsHolder extends AbstractFlexibleItem<NewsHolder.NewsViewHolder> implements
        IFilterable<String>, IHolder<News>{

    private  News news;


    @Ignore
    int mFontSizeTitle = 13;
    @Ignore
    int mFontSizeDetails = 10;


    public NewsHolder(News news) {
        this.news = news;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof  NewsHolder){
            NewsHolder newsHolder = (NewsHolder) o;
            return  news.equals(newsHolder.getModel());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return news.hashCode();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.news_list_item;
    }

    @Override
    public NewsViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new NewsViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, NewsViewHolder holder, int position, List<Object> payloads) {

        String getTheTitle = news.getTitle().getRendered();
        //Replace ASCII codes with proper Characters
        String formatTitle = String.valueOf(Html.fromHtml(getTheTitle));
        holder.mTitle.setText(formatTitle);
        setImage(holder);
        setDateOn(holder);
        setWebsite(holder);
        holder.mTitle.setEnabled(true);
        holder.thumbnail.setEnabled(true);

    }


    private void setWebsite(NewsViewHolder holder) {
        String websiteName = news.getGuid().getRendered();
        try {
            URL url = new URL(websiteName);
            String host = url.getHost();
            String[] array = host.split("\\.");
            if (array[0].equals("www")) {
                holder.website.setText(array[1].toLowerCase());
            } else
                holder.website.setText(array[0].toLowerCase());
            holder.website.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mFontSizeDetails);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }


    private void setDateOn(NewsViewHolder holder) {
        try {
            String date = news.getDate();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd'T'HH:mm:ss");
            Date mDate = null;
            long timeInMilliseconds = 0;
            try {
                mDate = simpleDateFormat.parse(date);

                timeInMilliseconds = mDate.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.date.setReferenceTime(timeInMilliseconds);
            holder.date.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mFontSizeDetails);
        } catch (NumberFormatException nfe) {

        }
    }

    private void setImage (NewsViewHolder holder) {
        Context context = holder.itemView.getContext();
        if (news.getEmbedded().getWpFeaturedmedia().get(0).getMediaDetails() != null) {
            if (news.getEmbedded().getWpFeaturedmedia().get(0)
                    .getMediaDetails().getSizes().getMediumLarge() != null) {

                String thumbnail_url = news.getEmbedded().getWpFeaturedmedia().get(0)
                        .getMediaDetails().getSizes().getMediumLarge().getSourceUrl();
                Glide.with(context)
                        .load(thumbnail_url)
//                        .placeholder(R.mipmap.placeholder)
                        .into(holder.thumbnail);
            } else {
                if (news.getEmbedded().getWpFeaturedmedia().get(0)
                        .getMediaDetails().getSizes().getMedium() != null){

                    String thumbnail_url = news.getEmbedded().getWpFeaturedmedia().get(0)
                            .getMediaDetails().getSizes().getMedium().getSourceUrl();
                    Glide.with(context)
                            .load(thumbnail_url)
//                            .placeholder(R.mipmap.placeholder)
                            .into(holder.thumbnail);
                }
            }
        } else {
            Glide.with(context)
                    .load(R.mipmap.placeholder)
                    .into(holder.thumbnail);
        }
    }

    @Override
    public boolean filter(String constraint) {
        return news.getLink() != null && news.getLink().equals(constraint);
    }

    @Override
    public News getModel() {
        return news;
    }

    static  class  NewsViewHolder extends FlexibleViewHolder {
        public TextView mTitle;
        ImageView thumbnail;
        RelativeTimeTextView date;
        TextView website;


        public NewsViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mTitle = view.findViewById(R.id.news_title);
            website = view.findViewById(R.id.news_site);
            thumbnail = view.findViewById(R.id.news_image);
            date =view.findViewById(R.id.news_date);

        }
    }

    public void setFontSizes(int mCurrentFontSize, int mFontSizeDetails) {
        this.mFontSizeTitle = mCurrentFontSize;
        this.mFontSizeDetails = mFontSizeDetails;
    }
}
