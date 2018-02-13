package com.princess.android.cryptonews.News.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.princess.android.cryptonews.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class LatestNewsActivityFragment extends Fragment {

    public LatestNewsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_latest_news, container, false);
    }
}
