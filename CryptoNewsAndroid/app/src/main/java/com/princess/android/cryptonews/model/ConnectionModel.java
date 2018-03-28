package com.princess.android.cryptonews.model;

/**
 * Created by numb3rs on 3/27/18.
 */

public class ConnectionModel {

    private  int type;
    private  boolean isConnected;

    public ConnectionModel(int type, boolean isConnected) {
        this.type = type;
        this.isConnected = isConnected;
    }


    public int getType() {
        return type;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
