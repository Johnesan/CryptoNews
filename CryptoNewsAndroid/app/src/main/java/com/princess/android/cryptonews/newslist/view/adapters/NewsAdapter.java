package com.princess.android.cryptonews.newslist.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.princess.android.cryptonews.NewWebsite.view.ui.NewsWebPageActivity;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.api.NewsApiClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Princess on 2/23/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<News> newsList;
    private NewsApiClient apiClient;

    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
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
        String thumbnail_url = "https://www.ccn.com/wp-content/uploads/";
                //+ result.getFeaturedMedia().;
        Glide.with(context)
                .load(thumbnail_url)
                .placeholder(R.mipmap.placeholder)
                .into(holder.thumbnail);
        //
        holder.title.setText(result.getTitle().getRendered());
        //Set the website
        String websiteName = apiClient.BASE_URL;
        try {
            URL url = new URL(websiteName);
            String host = url.getHost();
            holder.website.setText(host.toUpperCase());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        if(newsList == null)
            return 0;
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.news_layout)
        CardView newsLayout;
        @BindView(R.id.news_image)
        ImageView thumbnail;
        @BindView(R.id.news_title)
        TextView title;
        @BindView(R.id.news_date)
        TextView date;
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
            String link = data.getGuid().getRendered();
            String title = data.getTitle().getRendered();
            intent.putExtra("url", link);
            intent.putExtra("title", title);
            context.startActivity(intent);
        }
    }
}
