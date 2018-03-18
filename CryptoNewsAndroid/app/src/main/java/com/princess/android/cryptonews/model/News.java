package com.princess.android.cryptonews.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Generated("com.robohorse.robopojogenerator")
@Entity(tableName = "news")
public class News{

	@SerializedName("date")
	private String date;

	@SerializedName("link")
	private String link;

	@SerializedName("title")
	private Title title;

	@SerializedName("_embedded")
	private Embedded embedded;

	@PrimaryKey(autoGenerate = true)
	@SerializedName("id")
	private int id;

	@SerializedName("guid")
	private Guid guid;

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

}