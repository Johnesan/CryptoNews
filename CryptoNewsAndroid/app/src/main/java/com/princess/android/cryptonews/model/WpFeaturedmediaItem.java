package com.princess.android.cryptonews.model;

import android.arch.persistence.room.Entity;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
@Entity
public class WpFeaturedmediaItem{

	@SerializedName("media_details")
	private MediaDetails mediaDetails;


	public void setMediaDetails(MediaDetails mediaDetails){
		this.mediaDetails = mediaDetails;
	}

	public MediaDetails getMediaDetails(){
		return mediaDetails;
	}


	@Override
 	public String toString(){
		return 
			"WpFeaturedmediaItem{" +
			",media_details = '" + mediaDetails + '\'' +
			"}";
		}
}