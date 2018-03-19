package com.princess.android.cryptonews.newslist.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.curioustechizen.ago.RelativeTimeTextView;
import com.princess.android.cryptonews.AppController;
import com.princess.android.cryptonews.newswebsite.view.ui.NewsWebPageActivity;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.util.PreferenceUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Princess on 2/23/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    int mFontSizeTitle;
    int mFontSizeDetails;


    PreferenceUtils preferenceUtils = new PreferenceUtils(AppController.getInstance());


    private Context context;
    private List<News> newsList;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    @Override
    public NewsAdapter.NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.NewsViewHolder holder, int position) {

        News result = newsList.get(position);
        //Set the image
        if (result.getEmbedded().getWpFeaturedmedia().get(0).getMediaDetails() != null) {
            if (result.getEmbedded().getWpFeaturedmedia().get(0)
                    .getMediaDetails().getSizes().getMediumLarge() != null) {

                String thumbnail_url = result.getEmbedded().getWpFeaturedmedia().get(0)
                        .getMediaDetails().getSizes().getMediumLarge().getSourceUrl();
                Glide.with(context)
                        .load(thumbnail_url)
                        .placeholder(R.mipmap.placeholder)
                        .into(holder.thumbnail);
            } else {
                if (result.getEmbedded().getWpFeaturedmedia().get(0)
                        .getMediaDetails().getSizes().getMedium() != null){
                String thumbnail_url = result.getEmbedded().getWpFeaturedmedia().get(0)
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
        //Set the title
        String getTheTitle = result.getTitle().getRendered();
        //Replace ASCII codes with proper Characters
        String formatTitle = String.valueOf(Html.fromHtml(getTheTitle));
        holder.title.setText(formatTitle);
        holder.title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, mFontSizeTitle);


        //Set the date
//        holder.date.setReferenceTime(Long.parseLong(result.getDate()));
        try {
            String date = result.getDate();

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

        //Set the website
        String websiteName = result.getGuid().getRendered();
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

    @Override
    public int getItemCount() {
        if (newsList == null)
            return 0;
        return newsList.size();
    }

    public void setFontSizes(int mCurrentFontSize, int mFontSizeDetails) {
        this.mFontSizeTitle = mCurrentFontSize;
        this.mFontSizeDetails = mFontSizeDetails;
    }

    public  void setItems (List<News> newsList){
        this.newsList = newsList;
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.news_layout)
        CardView newsLayout;
        @BindView(R.id.news_image)
        ImageView thumbnail;
        @BindView(R.id.news_title)
        TextView title;
        @BindView(R.id.news_date)
        RelativeTimeTextView date;
        @BindView(R.id.news_site)
        TextView website;

        public NewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, NewsWebPageActivity.class);

            News data = newsList.get(getLayoutPosition());

            //Get the link of the website to be opened on the Web page
            String link = data.getGuid().getRendered();
            //Get the title of each news and format it to normal characters
            String title = String.valueOf(Html.fromHtml(data.getTitle().getRendered()));

            if (!preferenceUtils.getViewNewsWithIn().equals("0")){
                openURLInBrowser(link);
            }else {
            //Pass the title and link to the next activity
            intent.putExtra("url", link);
            intent.putExtra("title", title);
            context.startActivity(intent);
            }

        }

        private void openURLInBrowser(String url) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(browserIntent);
        }
    }
}
