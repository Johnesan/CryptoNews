package com.princess.android.cryptonews.Favourite;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.binding.FragmentDataBindingComponent;
import com.princess.android.cryptonews.commons.AutoClearedValue;
import com.princess.android.cryptonews.databinding.FragmentFavoriteBinding;
import com.princess.android.cryptonews.model.Title;
import com.princess.android.cryptonews.model.modelTest.NewsTest;

import java.util.ArrayList;
import java.util.List;

import dagger.android.support.DaggerFragment;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * Created by numb3rs on 4/20/18.
 */

public class FavoriteActivityFragment extends DaggerFragment

{

    //Compose the initial List
    List<IFlexible> myItems  = getDatabaseList();

    android.databinding.DataBindingComponent dataBindingComponent = new FragmentDataBindingComponent(this);
    AutoClearedValue<FragmentFavoriteBinding> binding;
    FlexibleAdapter<IFlexible> adapter;
    RecyclerView.LayoutManager layoutManager;



    public static FavoriteActivityFragment newInstance() {

        Bundle args = new Bundle();

        FavoriteActivityFragment fragment = new FavoriteActivityFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        adapter = new FlexibleAdapter<>(myItems, getContext());
        binding.get().recyclerView.setAdapter(adapter);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        FragmentFavoriteBinding fragmentFavoriteBinding = DataBindingUtil.
                inflate(inflater, R.layout.fragment_favorite, container, false, dataBindingComponent);
        binding = new AutoClearedValue<>(this, fragmentFavoriteBinding);

        setupViews();
        return  fragmentFavoriteBinding.getRoot();
    }

    private void setupViews() {
        layoutManager = new GridLayoutManager(getActivity(),2);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else  {
            layoutManager = new GridLayoutManager(getActivity(), 3);
        }

        binding.get().recyclerView.setLayoutManager(layoutManager);
        binding.get().emptyProgressBar.setVisibility(View.INVISIBLE);
    }


    public  List<IFlexible> getDatabaseList(){
        List<IFlexible> list = new ArrayList<>();
        list.add(new NewsTest("now","ccn.com", new Title("The best News of the century"), null, "21", null));
        return list;
    }
}
