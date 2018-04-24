package com.princess.android.cryptonews.Favourite;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.binding.FragmentDataBindingComponent;
import com.princess.android.cryptonews.commons.AutoClearedValue;
import com.princess.android.cryptonews.databinding.FragmentFavoriteBinding;

import dagger.android.support.DaggerFragment;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * Created by numb3rs on 4/20/18.
 */

public class FavoriteActivityFragment extends DaggerFragment


{



    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<FragmentFavoriteBinding> binding;
    FlexibleAdapter<IFlexible> adapter;
    RecyclerView.LayoutManager layoutManager;
    Parcelable mListState;
    private ActionMode mActionMode;


    public static FavoriteActivityFragment newInstance() {

        Bundle args = new Bundle();

        FavoriteActivityFragment fragment = new FavoriteActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Restore previous state

        if (savedInstanceState != null) {

            if (adapter == null) {
                adapter = new FlexibleAdapter<IFlexible>(null, getContext());

            }
            mListState = savedInstanceState.getParcelable("REC_STATE");

            if (layoutManager == null) {
                layoutManager = new GridLayoutManager(getActivity(), 2);
            }
            layoutManager.onRestoreInstanceState(mListState);
            adapter.onRestoreInstanceState(savedInstanceState);

            if (adapter.getSelectedItemCount() > 0) {
//                mActionMode = getActivity().startActionMode(this);
                setContextTitle(adapter.getSelectedItemCount());
            }
        } else {
            adapter = new FlexibleAdapter<>(null, getContext());
            adapter.addListener(this);
//            setupViews();

        }
        binding.get().emptyBar.setVisibility(View.INVISIBLE);
        binding.get().recyclerView.setAdapter(adapter);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentFavoriteBinding fragmentFavoriteBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_favorite, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, fragmentFavoriteBinding);


        return fragmentFavoriteBinding.getRoot();
    }




    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        adapter.onSaveInstanceState(outState);
        if (mListState == null) {
            mListState = layoutManager.onSaveInstanceState();
            outState.putParcelable("REC_STATE", mListState);
        }

        super.onSaveInstanceState(outState);
    }

    private void setContextTitle(int count) {
        mActionMode.setTitle(String.valueOf(count) + " " + (count == 1 ?
                getString(R.string.action_selected_one) :
                getString(R.string.action_selected_many)));
    }



    public void destroyActionMode(){
        if (mActionMode != null) {
            mActionMode.finish();
        }
    }
}
