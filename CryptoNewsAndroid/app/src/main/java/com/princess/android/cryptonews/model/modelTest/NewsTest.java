package com.princess.android.cryptonews.model.modelTest;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.google.gson.annotations.SerializedName;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.model.Embedded;
import com.princess.android.cryptonews.model.Guid;
import com.princess.android.cryptonews.model.Title;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.flexibleadapter.items.IFlexible;
import eu.davidea.viewholders.FlexibleViewHolder;

/**
 * Created by numb3rs on 4/21/18.
 */

public class NewsTest extends AbstractFlexibleItem<NewsTest.NewsViewHolder> {

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
        private String id;

        @SerializedName("guid")
        private Guid guid;

    public NewsTest(String date, String link, Title title, Embedded embedded, @NonNull String id, Guid guid) {
        this.date = date;
        this.link = link;
        this.title = title;
        this.embedded = embedded;
        this.id = id;
        this.guid = guid;
    }

    public void setDate(String date){
            this.date = date;
        }

        public String getDate(){
            return date;
        }

        public void setLink(String link){
            this.link = link;
        }

        public String getLink(){
            return link;
        }

        public void setTitle(Title title){
            this.title = title;
        }

        public Title getTitle(){
            return title;
        }

        public void setEmbedded(Embedded embedded){
            this.embedded = embedded;
        }

        public Embedded getEmbedded(){
            return embedded;
        }

        public void setId(String id){
            this.id = id;
        }

        public String getId(){
            return id;
        }

        public void setGuid(Guid guid){
            this.guid = guid;
        }

        public Guid getGuid(){
            return guid;
        }

        @Override
        public String toString(){
            return
                    "News{" +
                            "date = '" + date + '\'' +
                            ",link = '" + link + '\'' +
                            ",title = '" + title + '\'' +
                            ",_embedded = '" + embedded + '\'' +
                            ",id = '" + id + '\'' +
                            ",guid = '" + guid + '\'' +
                            "}";
        }




    @Override
    public boolean equals(Object o) {
        if (o instanceof NewsTest){
            NewsTest newsTest = (NewsTest) o;
            return this.id.equals(newsTest.id);

        }
        return false;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.news_list_item;
    }

    @Override
    public NewsTest.NewsViewHolder createViewHolder(View view, FlexibleAdapter<IFlexible> adapter) {
        return new NewsViewHolder(view, adapter);
    }

    @Override
    public void bindViewHolder(FlexibleAdapter<IFlexible> adapter, NewsTest.NewsViewHolder holder, int position, List<Object> payloads) {

        String getTheTitle = title.getRendered();
        //Replace ASCII codes with proper Characters
        String formatTitle = String.valueOf(Html.fromHtml(getTheTitle));
        holder.mTitle.setText(formatTitle);
//        Glide.with()
//                .load(R.mipmap.placeholder)
//                .placeholder(R.mipmap.placeholder)
//                .into(holder.thumbnail);

    }


    public class NewsViewHolder extends FlexibleViewHolder {

        public  TextView mTitle;
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
