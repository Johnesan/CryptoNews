
package com.princess.android.cryptonews.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Content {

    @SerializedName("protected")
    private Boolean mProtected;
    @SerializedName("rendered")
    private String mRendered;

    public Boolean getProtected() {
        return mProtected;
    }


    public void setProtected(Boolean protect) {
        mProtected = protect;
    }

    public String getRendered() {
        return mRendered;
    }

    public void setRendered(String rendered) {
        mRendered = rendered;
    }

}
