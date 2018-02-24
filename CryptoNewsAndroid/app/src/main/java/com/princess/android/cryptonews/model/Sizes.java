package com.princess.android.cryptonews.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Sizes{

	@SerializedName("medium")
	private Medium medium;


	public void setMedium(Medium medium){
		this.medium = medium;
	}

	public Medium getMedium(){
		return medium;
	}


	@Override
 	public String toString(){
		return 
			"Sizes{" +
			",medium = '" + medium + '\'' +
			"}";
		}
}