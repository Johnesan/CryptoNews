package com.princess.android.cryptonews.util;

import android.arch.lifecycle.LiveData;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.princess.android.cryptonews.model.ConnectionModel;

import javax.inject.Inject;

import static com.princess.android.cryptonews.newslist.view.fragment.LatestNewsActivityFragment.MobileData;
import static com.princess.android.cryptonews.newslist.view.fragment.LatestNewsActivityFragment.WifiData;


/**
 * Utility Class to Provide internet Status
 * */


public class ConnectionClassLiveData extends LiveData<ConnectionModel> {

    private Context context;


    @Inject
    public ConnectionClassLiveData(Context context) {
        this.context = context;
    }


    @Override
    protected void onActive() {
        super.onActive();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(networkReceiver, filter);

    }


    @Override
    protected void onInactive() {
        super.onInactive();
        context.unregisterReceiver(networkReceiver);
    }

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null){
                NetworkInfo actvenetwork = (NetworkInfo)
                        intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                boolean isConnected = actvenetwork != null &&
                        actvenetwork.isConnectedOrConnecting();

                if (isConnected){
                    switch (actvenetwork.getType()){
                        case  ConnectivityManager.TYPE_WIFI:
                            postValue(new ConnectionModel(WifiData, true));
                            break;
                        case  ConnectivityManager.TYPE_MOBILE:
                            postValue(new ConnectionModel(MobileData, true));
                            break;

                    }
                }else {
                    postValue(new ConnectionModel(0, false));
                }
            }

        }
    };
}
