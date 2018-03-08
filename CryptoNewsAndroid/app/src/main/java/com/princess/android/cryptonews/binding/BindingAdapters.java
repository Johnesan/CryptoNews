package com.princess.android.cryptonews.binding;

import android.databinding.BindingAdapter;
import android.view.View;

/**
 * Created by numb3rs on 2/16/18.
 */

public class BindingAdapters {
    @BindingAdapter("VisibleGone")
    public static void showHide (View view, boolean show){
        view.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}
