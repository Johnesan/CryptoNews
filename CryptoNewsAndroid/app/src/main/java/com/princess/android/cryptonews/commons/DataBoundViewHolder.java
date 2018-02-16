package com.princess.android.cryptonews.commons;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

/**
 * Created by numb3rs on 2/8/18.
 */

public class DataBoundViewHolder<T extends
        ViewDataBinding>  extends RecyclerView.ViewHolder{

    public final T binding;

    DataBoundViewHolder(T binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
