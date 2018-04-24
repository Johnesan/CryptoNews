package com.princess.android.cryptonews.util;

import android.arch.lifecycle.LiveData;

/**
 * Created by numb3rs on 4/24/18.
 */

public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(null);
    }
    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}
