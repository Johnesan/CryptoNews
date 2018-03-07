package com.princess.android.cryptonews.commons;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by numb3rs on 3/7/18.
 */

public class AutoClearedValue<T> {
    private T value;
    public  AutoClearedValue(Fragment fragment, T value){
        FragmentManager fragmentManager = fragment.getFragmentManager();
        fragmentManager.registerFragmentLifecycleCallbacks(
                new FragmentManager.FragmentLifecycleCallbacks() {
                    @Override
                    public void onFragmentDestroyed(FragmentManager fm, Fragment f) {
                        if (f == fragment){
                            AutoClearedValue.this.value= null;
                            fragmentManager.unregisterFragmentLifecycleCallbacks(this);
                        }
                    }
                }, false);
        this.value = value;
    }

    public  T get() {
        return value;
    }
}
