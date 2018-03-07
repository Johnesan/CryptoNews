package com.princess.android.cryptonews.model;

import javax.annotation.Generated;

import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Sizes {

    @SerializedName("medium")
    private Medium medium;

    @SerializedName("medium_large")
    private MediumLarge mediumLarge;


    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Medium getMedium() {
        return medium;
    }

    public MediumLarge getMediumLarge() {
        return mediumLarge;
    }

    public void setMediumLarge(MediumLarge mediumLarge) {
        this.mediumLarge = mediumLarge;
    }

    @Override
    public String toString() {
        return
                "Sizes{" +
                        ",medium = '" + medium + '\'' +
                        ",medium_large = '" + mediumLarge + '\'' +
                        "}";
    }
}