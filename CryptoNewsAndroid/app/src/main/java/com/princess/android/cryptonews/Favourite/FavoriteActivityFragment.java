package com.princess.android.cryptonews.Favourite;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.items.IFlexible;

/**
 * Created by numb3rs on 4/20/18.
 */

public class FavoriteActivityFragment extends DaggerFragment implements
        ActionMode.Callback,
        FlexibleAdapter.OnItemClickListener,
        FlexibleAdapter.OnItemLongClickListener

{

    //Compose the initial List
    List<IFlexible> myItems = getDatabaseList();

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
                adapter = new FlexibleAdapter<IFlexible>(myItems, getContext());

            }
            mListState = savedInstanceState.getParcelable("REC_STATE");

            if (layoutManager == null) {
                layoutManager = new GridLayoutManager(getActivity(), 2);
            }
            layoutManager.onRestoreInstanceState(mListState);
            adapter.onRestoreInstanceState(savedInstanceState);

            if (adapter.getSelectedItemCount() > 0) {
                mActionMode = getActivity().startActionMode(this);
                setContextTitle(adapter.getSelectedItemCount());
            }
        } else {
            adapter = new FlexibleAdapter<>(myItems, getContext());
            adapter.addListener(this);
            setupViews();

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

    private void setupViews() {
        layoutManager = new GridLayoutManager(getActivity(), 2);
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
        } else {
            layoutManager = new GridLayoutManager(getActivity(), 3);
        }

        binding.get().recyclerView.setLayoutManager(layoutManager);
        binding.get().emptyBar.setVisibility(View.INVISIBLE);
    }


    public List<IFlexible> getDatabaseList() {
        List<IFlexible> list = new ArrayList<>();
        list.add(new NewsTest("now", "ccn.com", new Title("The best News of the century"), null, "21", null));
        return list;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_context, menu);
        adapter.setMode(SelectableAdapter.Mode.MULTI);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

        if (item.getItemId() == R.id.action_delete) {
            Toast.makeText(getContext(), "Deleted" + " " + adapter.getSelectedItemCount(), Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        adapter.setMode(SelectableAdapter.Mode.IDLE);
        mActionMode = null;
    }

    @Override
    public boolean onItemClick(View view, int position) {
        if (mActionMode != null && position != RecyclerView.NO_POSITION) {
            toggleSelection(position);
            return true;
        } else {

            return false;
        }
    }

    @Override
    public void onItemLongClick(int position) {
        if (mActionMode == null) {
            mActionMode = getActivity().startActionMode(this);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        //Mark the position selected
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();

        if (count == 0) {
            mActionMode.finish();
        } else {
            setContextTitle(count);
        }
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
