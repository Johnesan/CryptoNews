package com.princess.android.cryptonews.model;

import android.arch.persistence.room.Entity;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
@Generated("com.robohorse.robopojogenerator")
@Entity
public class Title{

	@SerializedName("rendered")
	private String rendered;

	public void setRendered(String rendered){
		this.rendered = rendered;
	}

	public String getRendered(){
		return rendered;
	}

	public Title() {
	}

	public Title(String rendered) {
        this.rendered = rendered;
    }

    @Override
 	public String toString(){
		return 
			"Title{" + 
			"rendered = '" + rendered + '\'' + 
			"}";
		}
}