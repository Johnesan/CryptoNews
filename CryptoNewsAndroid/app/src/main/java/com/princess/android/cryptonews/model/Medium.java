package com.princess.android.cryptonews.model;

import android.arch.persistence.room.Entity;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
@Generated("com.robohorse.robopojogenerator")
@Entity
public class Medium{

	@SerializedName("source_url")
	private String sourceUrl;

	public void setSourceUrl(String sourceUrl){
		this.sourceUrl = sourceUrl;
	}

	public String getSourceUrl(){
		return sourceUrl;
	}


	@Override
 	public String toString(){
		return 
			"Medium{" +
			",source_url = '" + sourceUrl + '\'' +
			"}";
		}
}