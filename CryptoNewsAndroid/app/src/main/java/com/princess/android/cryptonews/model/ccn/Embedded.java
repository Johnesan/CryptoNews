package com.princess.android.cryptonews.model.ccn;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Embedded{

	@SerializedName("author")
	private List<AuthorItem> author;

	@SerializedName("wp:term")
	private List<List<WpTermItemItem>> wpTerm;

	@SerializedName("wp:featuredmedia")
	private List<WpFeaturedmediaItem> wpFeaturedmedia;

	public void setAuthor(List<AuthorItem> author){
		this.author = author;
	}

	public List<AuthorItem> getAuthor(){
		return author;
	}

	public void setWpTerm(List<List<WpTermItemItem>> wpTerm){
		this.wpTerm = wpTerm;
	}

	public List<List<WpTermItemItem>> getWpTerm(){
		return wpTerm;
	}

	public void setWpFeaturedmedia(List<WpFeaturedmediaItem> wpFeaturedmedia){
		this.wpFeaturedmedia = wpFeaturedmedia;
	}

	public List<WpFeaturedmediaItem> getWpFeaturedmedia(){
		return wpFeaturedmedia;
	}

	@Override
 	public String toString(){
		return 
			"Embedded{" + 
			"author = '" + author + '\'' + 
			",wp:term = '" + wpTerm + '\'' + 
			",wp:featuredmedia = '" + wpFeaturedmedia + '\'' + 
			"}";
		}
}