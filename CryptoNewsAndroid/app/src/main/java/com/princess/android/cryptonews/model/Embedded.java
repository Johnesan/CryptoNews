package com.princess.android.cryptonews.model;

import android.arch.persistence.room.Entity;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
@Entity
public class Embedded{


	@SerializedName("wp:featuredmedia")
	private List<WpFeaturedmediaItem> wpFeaturedmedia;

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
			",wp:featuredmedia = '" + wpFeaturedmedia + '\'' + 
			"}";
		}
}