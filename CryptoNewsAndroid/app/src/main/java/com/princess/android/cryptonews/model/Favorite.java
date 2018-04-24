package com.princess.android.cryptonews.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.gson.annotations.SerializedName;
import com.princess.android.cryptonews.R;

import org.parceler.Parcel;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by numb3rs on 4/24/18.
 */

@Entity(tableName = "favorite")
public class Favorite extends AbstractFlexibleItem<Favorite.NewsViewHolder> {

    @SerializedName("date")
    private String date;

    @SerializedName("link")
    private String link;

    @SerializedName("title")
    private Title title;

    @SerializedName("_embedded")
    private Embedded embedded;

    @NonNull
    @PrimaryKey()
    @SerializedName("id")
    private int id;

    @SerializedName("guid")
    private Guid guid;

    @Ignore
    int mFontSizeTitle = 13;
    @Ignore
    int mFontSizeDetails = 10;

    public Favorite(String date, String link, Title title, Embedded embedded, @NonNull int id, Guid guid) {
        this.date = date;
        this.link = link;
        this.title = title;
        this.embedded = embedded;
        this.id = id;
        this.guid = guid;
    }

    public Favorite(@NonNull int id) {
        this.id = id;
    }

    public Favorite() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Embedded getEmbedded() {
        return embedded;
    }

    public void setEmbedded(Embedded embedded) {
        this.embedded = embedded;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Guid getGuid() {
        return guid;
    }

    public void setGuid(Guid guid) {
        this.guid = guid;
    }


    @Override
    public String toString() {
        return "Favorite{" +
                "date='" + date + '\'' +
                ", link='" + link + '\'' +
                ", title=" + title +
                ", embedded=" + embedded +
                ", id=" + id +
                ", guid=" + guid +
                ", mFontSizeTitle=" + mFontSizeTitle +
                ", mFontSizeDetails=" + mFontSizeDetails +
                '}';
    }

    @Override
    public int getLayoutRes() {
        return R.layout.news_list_item;
    }

    @Override
    public Favorite.NewsViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new Favorite.NewsViewHolder(view, adapter);
    }
    @Override
    public boolean equals(Object o) {
        if (o instanceof Favorite){
            Favorite news = (Favorite) o;
            return this.id ==(news.id);

        }
        return false;
    }
    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, Favorite.NewsViewHolder holder, int position, List<Object> payloads) {

        String getTheTitle = title.getRendered();
        //Replace ASCII codes with proper Characters
        String formatTitle = String.valueOf(Html.fromHtml(getTheTitle));
        holder.mTitle.setText(formatTitle);
        setImage(holder);
        setDateOn(holder);
        setWebsite(holder);
        holder.mTitle.setEnabled(true);
        holder.thumbnail.setEnabled(true);
//        Glide.with()
//                .load(R.mipmap.placeholder)
//                .placeholder(R.mipmap.placeholder)
//                .into(holder.thumbnail);

    }

    private void setWebsite(NewsViewHolder holder) {
        String websiteName = getGuid().getRendered();
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
            String date = getDate();

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
        if (getEmbedded().getWpFeaturedmedia().get(0).getMediaDetails() != null) {
            if (getEmbedded().getWpFeaturedmedia().get(0)
                    .getMediaDetails().getSizes().getMediumLarge() != null) {

                String thumbnail_url = getEmbedded().getWpFeaturedmedia().get(0)
                        .getMediaDetails().getSizes().getMediumLarge().getSourceUrl();
                Glide.with(context)
                        .load(thumbnail_url)
                        .placeholder(R.mipmap.placeholder)
                        .into(holder.thumbnail);
            } else {
                if (getEmbedded().getWpFeaturedmedia().get(0)
                        .getMediaDetails().getSizes().getMedium() != null){

                    String thumbnail_url = getEmbedded().getWpFeaturedmedia().get(0)
                            .getMediaDetails().getSizes().getMedium().getSourceUrl();
                    Glide.with(context)
                            .load(thumbnail_url)
                            .placeholder(R.mipmap.placeholder)
                            .into(holder.thumbnail);
                }
            }
        } else {
            Glide.with(context)
                    .load(R.mipmap.placeholder)
                    .into(holder.thumbnail);
        }
    }

    public class NewsViewHolder extends FlexibleViewHolder {

        public TextView mTitle;
        ImageView thumbnail;
        RelativeTimeTextView date;

        @Override
        public float getActivationElevation() {
            return 2f;
        }

        TextView website;

        public NewsViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            mTitle = view.findViewById(R.id.news_title);
            website = view.findViewById(R.id.news_site);
            thumbnail = view.findViewById(R.id.news_image);
            date =view.findViewById(R.id.news_date);
        }
    }


}
