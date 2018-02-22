
package com.princess.android.cryptonews.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Cury {

    @SerializedName("href")
    private String mHref;
    @SerializedName("name")
    private String mName;
    @SerializedName("templated")
    private Boolean mTemplated;

    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Boolean getTemplated() {
        return mTemplated;
    }

    public void setTemplated(Boolean templated) {
        mTemplated = templated;
    }

}
