package com.princess.android.cryptonews.model;

import android.arch.persistence.room.Entity;

import com.github.wrdlbrnft.sortedlistadapter.SortedListAdapter;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import javax.annotation.Generated;

/**
 * Created by Princess on 3/7/2018.
 */

@Parcel
@Generated("com.robohorse.robopojogenerator")
@Entity
public class MediumLarge  {

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
                "MediumLarge{" +
                        ",source_url = '" + sourceUrl + '\'' +
                        "}";
    }

}
