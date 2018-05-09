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
import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.gson.annotations.SerializedName;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.model.modelTest.NewsTest;

import org.parceler.Parcel;

import java.io.Serializable;
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
import eu.davidea.viewholders.FlexibleViewHolder;

@Parcel
@Entity(tableName = "news")
public class News
        implements SortedListAdapter.ViewModel {

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

    public News() {
    }

    public News(String date, String link, Title title, Embedded embedded, int id, Guid guid) {
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

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
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
        if (o instanceof News){
            News news = (News) o;
            return this.id ==(news.id);
        }
        return false;
    }




    @Override
    public <T> boolean isSameModelAs(@NonNull T model) {
        if (model instanceof  News){
            final  News news = (News) model;
            return  news.id == id;
        }
        return  false;
    }

    @Override
    public <T> boolean isContentTheSameAs(@NonNull T model) {
        if (model instanceof  News){
            final  News other = (News)model;
            if (link != other.link){
                return  false;
            }
            return  link != null ? link.equals(other.link) : other.link == null;
        }
        return  false;
    }
}