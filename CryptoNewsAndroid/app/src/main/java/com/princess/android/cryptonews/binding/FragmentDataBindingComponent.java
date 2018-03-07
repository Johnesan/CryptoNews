package com.princess.android.cryptonews.binding;

import android.databinding.DataBindingComponent;
import android.support.v4.app.Fragment;

/**
 * Created by numb3rs on 3/7/18.
 */

public class FragmentDataBindingComponent implements android.databinding.DataBindingComponent {

    private  final FragmentBindingAdapters adapter;

    public FragmentDataBindingComponent(Fragment fragment) {
        this.adapter = new FragmentBindingAdapters(fragment);
    }

    @Override
    public FragmentBindingAdapters getFragmentBindingAdapters() {
        return adapter;
    }

}