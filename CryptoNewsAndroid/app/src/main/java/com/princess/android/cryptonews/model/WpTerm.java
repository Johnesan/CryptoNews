
package com.princess.android.cryptonews.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class WpTerm {

    @SerializedName("embeddable")
    private Boolean mEmbeddable;
    @SerializedName("href")
    private String mHref;
    @SerializedName("taxonomy")
    private String mTaxonomy;

    public Boolean getEmbeddable() {
        return mEmbeddable;
    }

    public void setEmbeddable(Boolean embeddable) {
        mEmbeddable = embeddable;
    }

    public String getHref() {
        return mHref;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public String getTaxonomy() {
        return mTaxonomy;
    }

    public void setTaxonomy(String taxonomy) {
        mTaxonomy = taxonomy;
    }

}
