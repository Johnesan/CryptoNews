package com.princess.android.cryptonews.newslist.view.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.princess.android.cryptonews.databinding.FilterItemBinding;
import com.princess.android.cryptonews.newslist.Source;
import com.princess.android.cryptonews.newslist.SourceManager;
import com.princess.android.cryptonews.newslist.recyclerview.FilterSwipeDismissListener;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;


public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> implements FilterSwipeDismissListener {


    private final android.databinding.DataBindingComponent dataBindingComponent;

    final List<Source> filters;
    private final Context context;
    private @Nullable
    List<FiltersChangedCallbacks> callbacks;


    public FilterAdapter(@NonNull Context context,
                         @NonNull List<com.princess.android.cryptonews.newslist.Source> filters,
                         android.databinding.DataBindingComponent dataBindingComponent) {
        this.dataBindingComponent = dataBindingComponent;
        this.filters = filters;
        this.context = context;
        setHasStableIds(true);
    }

    public List<Source> getFilters() {
        return filters;
    }

    /**
     * Adds a new data source to the list of filters. If the source already exists then it is simply
     * activated.
     *
     * @param toAdd the source to add
     * @return whether the filter was added (i.e. if it did not already exist)
     */
    public boolean addFilter(Source toAdd) {
//        first chek if it already exists
        final int count = filters.size();
        for (int i = 0; i < count; i++) {
            Source existing = filters.get(i);
            {
                if (existing.getClass() == toAdd.getClass()
                        && existing.key.equalsIgnoreCase(toAdd.key)) {
                    if (!existing.active) {
                        existing.active = true;
                        dispatchFiltersChanged(existing);
                        notifyDataSetChanged(i, FilterAimator.FILTER_ENABLED);
                        SourceManager.updateSource(existing, context);
                    }
                    return false;
                }
            }
            filters.add(toAdd);
            Collections.sort(filters, new Source.SourceComparator());

        }

    }

    private void dispatchFiltersChanged(Source filter) {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (FiltersChangedCallbacks callback :callbacks){
                callback.onFiltersChanged(filter);
            }
        }
    }

    private void dispatchFilterRemoved(Source filter){
        if (callbacks != null && !callbacks.isEmpty()){
            for (FiltersChangedCallbacks callback : callbacks){
                callback.onFilterRemoved(filter);
            }
        }
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {

        private final FilterItemBinding mBinding;

        public FilterViewHolder(FilterItemBinding filterItemBinding) {
            super(filterItemBinding.getRoot());
            mBinding = filterItemBinding;

        }

        public void bind(Object o) {
            mBinding.executePendingBindings();
        }
    }

    public interface Listener {
        void onNewsItemClicked(String sourceKey);
    }

    @Override
    public int onItemDismiss(int position) {
        return 0;
    }


    public static abstract class FiltersChangedCallbacks {
        public void onFiltersChanged(Source changedFilter);

        public void onFilterRemoved(Source removed);
    }

    public static class FilterAnimator extends DefaultItemAnimator {

        public static final int FILTER_ENABLED = 1;
        public static final int FILTER_DISABLED = 2;
        public static final int HIGHLIGHT = 3;

        @Override
        public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder) {
            return true;
        }

        @Override
        public ItemHolderInfo obtainHolderInfo() {
            return new FilterHolderInfo();
        }

        /* package */ static class FilterHolderInfo extends ItemHolderInfo {
            boolean doEnable;
            boolean doDisable;
            boolean doHighlight;
        }

        @NonNull
        @Override
        public ItemHolderInfo recordPreLayoutInformation(@NonNull RecyclerView.State state,
                                                         @NonNull RecyclerView.ViewHolder viewHolder,
                                                         int changeFlags,
                                                         @NonNull List<Object> payloads) {
            FilterHolderInfo info = (FilterHolderInfo)
                    super.recordPreLayoutInformation(state, viewHolder, changeFlags, payloads);
            if (!payloads.isEmpty()) {
                info.doEnable = payloads.contains(FILTER_ENABLED);
                info.doDisable = payloads.contains(FILTER_DISABLED);
                info.doHighlight = payloads.contains(HIGHLIGHT);
            }
            return info;
        }

        @Override
        public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder,
                                     @NonNull RecyclerView.ViewHolder newHolder,
                                     @NonNull ItemHolderInfo preInfo,
                                     @NonNull ItemHolderInfo postInfo) {
            if (newHolder instanceof FilterViewHolder && preInfo instanceof FilterHolderInfo) {
                final FilterViewHolder holder = (FilterViewHolder) newHolder;
                final FilterHolderInfo info = (FilterHolderInfo) preInfo;

                if (info.doEnable || info.doDisable) {
                    ObjectAnimator iconAlpha = ObjectAnimator.ofInt(holder.mBinding.filterIcon,
                            ViewUtils.IMAGE_ALPHA,
                            info.doEnable ? FILTER_ICON_ENABLED_ALPHA :
                                    FILTER_ICON_DISABLED_ALPHA);
                    iconAlpha.setDuration(300L);
                    iconAlpha.setInterpolator(AnimUtils.getFastOutSlowInInterpolator(holder
                            .itemView.getContext()));
                    iconAlpha.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            dispatchChangeStarting(holder, false);
                            holder.itemView.setHasTransientState(true);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            holder.itemView.setHasTransientState(false);
                            dispatchChangeFinished(holder, false);
                        }
                    });
                    iconAlpha.start();
                } else if (info.doHighlight) {
                    int highlightColor =
                            ContextCompat.getColor(holder.itemView.getContext(), R.color.accent);
                    int fadeFromTo = ColorUtils.modifyAlpha(highlightColor, 0);
                    ObjectAnimator highlightBackground = ObjectAnimator.ofArgb(
                            holder.itemView,
                            ViewUtils.BACKGROUND_COLOR,
                            fadeFromTo,
                            highlightColor,
                            fadeFromTo);
                    highlightBackground.setDuration(1000L);
                    highlightBackground.setInterpolator(new LinearInterpolator());
                    highlightBackground.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            dispatchChangeStarting(holder, false);
                            holder.itemView.setHasTransientState(true);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            holder.itemView.setBackground(null);
                            holder.itemView.setHasTransientState(false);
                            dispatchChangeFinished(holder, false);
                        }
                    });
                    highlightBackground.start();
                }
            }
            return super.animateChange(oldHolder, newHolder, preInfo, postInfo);
        }

    }
