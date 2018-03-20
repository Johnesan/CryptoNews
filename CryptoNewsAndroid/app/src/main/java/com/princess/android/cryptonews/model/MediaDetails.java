package com.princess.android.cryptonews.model;

import android.arch.persistence.room.Entity;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
@Entity
public class MediaDetails{

	@SerializedName("sizes")
	private Sizes sizes;


	public void setSizes(Sizes sizes){
		this.sizes = sizes;
	}

	public Sizes getSizes(){
		return sizes;
	}

	@Override
 	public String toString(){
		return 
			"MediaDetails{" +
			",sizes = '" + sizes + '\'' +
			"}";
		}
}