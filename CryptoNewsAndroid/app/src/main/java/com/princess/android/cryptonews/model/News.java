package com.princess.android.cryptonews.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "news")
public class News {

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

    @NonNull
	public Boolean favorite;

    public News() {
    }

    public News(String date, String link, Title title, Embedded embedded, @NonNull int id, Guid guid, @NonNull Boolean favourite) {
		this.date = date;
		this.link = link;
		this.title = title;
		this.embedded = embedded;
		this.id = id;
		this.guid = guid;
		this.favorite = favourite;
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

    @NonNull
	public Boolean isFavourite() {
		return favorite;
	}
	public void setFavourite(@NonNull Boolean favourite) {
		this.favorite = favourite;
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

}