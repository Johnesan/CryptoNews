package com.princess.android.cryptonews.Favourite;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.princess.android.cryptonews.Favourite.viewModel.FavoriteViewModel;
import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.databinding.ActivityFavoriteBinding;
import com.princess.android.cryptonews.model.Favorite;
import com.princess.android.cryptonews.model.News;
import com.princess.android.cryptonews.newswebsite.view.ui.NewsWebPageActivity;
import com.princess.android.cryptonews.util.Utils;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.SelectableAdapter;
import eu.davidea.flexibleadapter.helpers.ActionModeHelper;
import eu.davidea.flexibleadapter.items.IFlexible;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class FavoriteActivity extends DaggerAppCompatActivity implements
        FlexibleAdapter.OnItemClickListener,
        FlexibleAdapter.OnItemLongClickListener,
        android.support.v7.view.ActionMode.Callback {

    @Inject
    ViewModelProvider.Factory factory;

    FavoriteViewModel favoriteViewModel;

    private FlexibleAdapter<IFlexible> mAdapter;
    private ActionModeHelper mActionModeHelper;
    List<IFlexible> list = new ArrayList<>();


    ActivityFavoriteBinding binding;
    private final CompositeDisposable mDisposable = new CompositeDisposable();

    RecyclerView.LayoutManager layoutManager;


    private void initialzeActionModeHelper(@SelectableAdapter.Mode int mode) {
        mActionModeHelper = new ActionModeHelper(mAdapter, R.menu.menu_context, this) {
            @Override
            public void updateContextTitle(int count) {
                if (mActionMode != null) {
                    mActionMode.setTitle(count == 1 ? getString(R.string.action_selected_one, count) : getString(R.string.action_selected_many, count));
                }
            }
        }.withDefaultMode(mode);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);

        mAdapter = new FlexibleAdapter<>(list);
        mAdapter.addListener(this);
        initialzeActionModeHelper(SelectableAdapter.Mode.IDLE);
        binding.recyclerView.setAdapter(mAdapter);
        setupViews();
        favoriteViewModel = ViewModelProviders.of(this, factory).get(FavoriteViewModel.class);


    }

    @Override
    public void onStart() {
        super.onStart();

        favoriteViewModel.getAllFavoriteNews().observe(this, this::getDatabaseList);

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mAdapter.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null && mAdapter != null) {
            mAdapter.onRestoreInstanceState(savedInstanceState);
            mActionModeHelper.restoreSelection(this);
        }
    }

    @Override
    public boolean onItemClick(View view, int position) {
        // Action on elements are allowed if Mode is IDLE, otherwise selection has priority
        if (mAdapter.getMode() != SelectableAdapter.Mode.IDLE && mActionModeHelper != null) {
            boolean activate = mActionModeHelper.onClick(position);
            // Last activated position is now available
            Log.d("Tag", "Last activated position " + mActionModeHelper.getActivatedPosition());
            return activate;
        } else {
            startWebpageActivity(position);
            // Handle the item click listener
            // We don't need to activate anything
            return false;
        }
    }

    private void startWebpageActivity(int position) {
        Intent intent = new Intent(this, NewsWebPageActivity.class);
        IFlexible flexibleItem = mAdapter.getItem(position);
        News news = (News) flexibleItem;
        if (news != null) {
            intent.putExtra("NEWS", Parcels.wrap(news));
        }
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position) {
        mActionModeHelper.onLongClick(this, position);

    }

    @Override
    public void onBackPressed() {
        if (mActionModeHelper.destroyActionModeIfCan()) return;
        super.onBackPressed();
    }


    private void setupViews() {
        layoutManager = new GridLayoutManager(this, 2);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(this, 2);
        } else {
            layoutManager = new GridLayoutManager(this, 3);
        }

        binding.recyclerView.setLayoutManager(layoutManager);
        binding.emptyBar.setVisibility(View.INVISIBLE);
        binding.recyclerView.setAdapter(mAdapter);
    }

    public void getDatabaseList(List<Favorite> news) {

        if (news.size() == 0){
            binding.emptyView.setVisibility(View.VISIBLE);
        }
        List<News> favNews = new ArrayList<>();
        if (news != null && news.size() > 0) {
            for (Favorite favorite : news) {

                News currentNews =
                        new News(favorite.getDate(),
                                favorite.getLink(),
                                favorite.getTitle(),
                                favorite.getEmbedded(),
                                favorite.getId(),
                                favorite.getGuid());
                favNews.add(currentNews);
            }

        }


        //something has been deleted, refresh List
        if (list.size() > favNews.size()) {
            list.clear();
            list.addAll(favNews);
            mAdapter.updateDataSet(list);
        } else if
            // list is still the same, do nothing
                (list.size() == favNews.size()) {
            if (list.equals(news)) {}
        } else {

            list.addAll(favNews);
            mAdapter.updateDataSet(list);
        }

    }


    @Override
    public void onStop() {
        super.onStop();
        mDisposable.clear();
    }

    @Override
    public boolean onCreateActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
        if (com.princess.android.cryptonews.util.Utils.hasMarshmallow()) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent, this.getTheme()));
        } else if (com.princess.android.cryptonews.util.Utils.hasLollipop()) {
            //noinspection deprecation
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }
        return true;
    }

    @Override
    public boolean onPrepareActionMode(android.support.v7.view.ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(android.support.v7.view.ActionMode mode, MenuItem item) {
        Log.e("Action called", "this are the items" + item.getItemId());
        if (item.getItemId() == R.id.action_delete) {
            deleteFavorite();
            Toast.makeText(this, "Deleted" + " " + mAdapter.getSelectedItemCount(), Toast.LENGTH_LONG).show();
            return true;

        }

        return false;
    }

    public void deleteFavorite() {
        new News();
        News saveNews;
        List<Integer> test = new ArrayList<>();
        test = mAdapter.getSelectedPositions();
        if (test != null && test.size() > 0) {
            if (test.size() > 1) {
                Toast.makeText(this, "Multi Delete Feature coming soon", Toast.LENGTH_SHORT).show();
            }
            IFlexible flexibleItem = mAdapter.getItem(test.get(0));
            saveNews = (News) flexibleItem;
            Favorite favorite = new Favorite(saveNews.getId());

            mDisposable.add(favoriteViewModel.deletFavorite(String.valueOf(favorite.getId()))
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {

                                Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                                mActionModeHelper.destroyActionModeIfCan();

                            },

                            throwable -> eu.davidea.flexibleadapter.utils.Log.e("TAG", "Unable to update id", throwable)
                    ));
        }
    }

    @Override
    public void onDestroyActionMode(android.support.v7.view.ActionMode mode) {
        if (Utils.hasMarshmallow()) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark, this.getTheme()));
        } else if (Utils.hasLollipop()) {
            //noinspection deprecation
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

    }
}