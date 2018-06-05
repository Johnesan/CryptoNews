package com.princess.android.cryptonews.newslist.view.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.princess.android.cryptonews.R;
import com.princess.android.cryptonews.databinding.FilterItemBinding;
import com.princess.android.cryptonews.newslist.Source;
import com.princess.android.cryptonews.newslist.SourceManager;
import com.princess.android.cryptonews.newslist.recyclerview.FilterSwipeDismissListener;
import com.princess.android.cryptonews.util.AnimUtils;
import com.princess.android.cryptonews.util.ColorUtils;
import com.princess.android.cryptonews.util.ViewUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;


public class FilterAdapter extends
        RecyclerView.Adapter<FilterAdapter.FilterViewHolder>
        implements FilterSwipeDismissListener {


    private final android.databinding.DataBindingComponent dataBindingComponent;

    private static final int FILTER_ICON_ENABLED_ALPHA = 179; // 70%
    private static final int FILTER_ICON_DISABLED_ALPHA = 51; // 20%

    final List<Source> filters;
    private final Context context;
    private @Nullable
    List<FiltersChangedCallbacks> callbacks;
    int last_selected_position = -1;


    public FilterAdapter(@NonNull Context context,
                         @NonNull List<Source> filters,
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

            if (existing.getClass() == toAdd.getClass()
                    && existing.key.equalsIgnoreCase(toAdd.key)) {
                if (!existing.active) {
                    existing.active = true;
                    dispatchFiltersChanged(existing);
                    notifyItemChanged(i, FilterAnimator.FILTER_ENABLED);
                    SourceManager.updateSource(existing, context);
                }
                return false;
            }
        }
        filters.add(toAdd);
        Collections.sort(filters, new Source.SourceComparator());
        dispatchFiltersChanged(toAdd);
        notifyDataSetChanged();
        SourceManager.addSource(toAdd, context);
        return true;


    }

    public void removeFilter(Source removing) {
        int position = filters.indexOf(removing);
        filters.remove(position);
        notifyItemRemoved(position);
        dispatchFilterRemoved(removing);
        SourceManager.removeSource(removing, context);
    }

    public int getFilterPosition(Source filter) {
        return filters.indexOf(filter);

    }

    public void enableFilterByKey(@NonNull String key, @NonNull Context context) {
        final int count = filters.size();
        for (int i = 0; i < count; i++) {
            Source filter = filters.get(i);
            if (filter.key.equals(key)) {
                if (!filter.active) {
                    filter.active = true;
                    notifyItemChanged(i, FilterAnimator.FILTER_ENABLED);
                    dispatchFiltersChanged(filter);
                    SourceManager.updateSource(filter, context);
                }
                return;
            }
        }
    }


    public void highlightFilter(int adapterosition) {
        notifyItemChanged(adapterosition, FilterAnimator.HIGHLIGHT);
    }

    private void dispatchFiltersChanged(Source filter) {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (FiltersChangedCallbacks callback : callbacks) {
                callback.onFiltersChanged(filter);
            }
        }
    }

    private void dispatchLastFilter(Source filter) {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (FiltersChangedCallbacks callback : callbacks) {
                callback.onLastFilterTriedToChanged(filter);
            }
        }
    }

    private void dispatchFilterRemoved(Source filter) {
        if (callbacks != null && !callbacks.isEmpty()) {
            for (FiltersChangedCallbacks callback : callbacks) {
                callback.onFilterRemoved(filter);
            }
        }
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final FilterItemBinding binding = FilterItemBinding.
                inflate(LayoutInflater.from(parent.getContext()), parent, false, dataBindingComponent);

        return new FilterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position) {
        final Source filter = filters.get(position);
        holder.isSwipeable = filter.isSwipeDismissable();
        holder.mBinding.filterName.setText(filter.name);
        holder.mBinding.filterName.setEnabled(filter.active);
        if (filter.active) {
            last_selected_position = getFilterPosition(filter);
            Log.e(this.getClass().getSimpleName(), "Filter Position " + last_selected_position);
        }


        if (filter.iconRes > 0) {
            holder.mBinding.filterIcon.setImageDrawable(
                    holder.itemView.getContext().getResources().getDrawable(filter.iconRes));
        }

        holder.mBinding.filterIcon.setImageAlpha(filter.active ? FILTER_ICON_ENABLED_ALPHA :
                FILTER_ICON_DISABLED_ALPHA);


        holder.itemView.setOnClickListener(v -> {
                    final int positions = holder.getAdapterPosition();
                    if (positions == RecyclerView.NO_POSITION) return;


                    if (positions == last_selected_position) {

                        //if its the last Activated positon, Tell the user that he needs to make another
                        //selection
                        if (filter.active) {
                            dispatchLastFilter(filter);

                        }

                    } else {


                        //Two things to be done here
                        //1. Disable the last selected Icon
                        //2. Enable the new Selected position

                        //STEP 1
                        Source lastSelectedFilter = filters.get(last_selected_position);
                        lastSelectedFilter.active = false;
                        holder.mBinding.filterName.setEnabled(false);
                        notifyItemChanged(last_selected_position, lastSelectedFilter.active ? FilterAnimator.FILTER_ENABLED
                                : FilterAnimator.FILTER_DISABLED);
                        SourceManager.updateSource(lastSelectedFilter, holder.itemView.getContext());
                        dispatchFiltersChanged(lastSelectedFilter);

                        //STEP 2
                        filter.active = !filter.active;
                        holder.mBinding.filterName.setEnabled(filter.active);
                        notifyItemChanged(position, filter.active ? FilterAnimator.FILTER_ENABLED
                                : FilterAnimator.FILTER_DISABLED);
                        SourceManager.updateSource(filter, holder.itemView.getContext());
                        dispatchFiltersChanged(filter);
                        last_selected_position = positions;

                    }

                }

        );

    }

    private void clearEnabledFilter(FilterViewHolder holder) {

    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder, int position, @NonNull List<Object> payloads) {
        if (!payloads.isEmpty()) {
            // if we're doing a partial re-bind i.e. an item is enabling/disabling or being
            // highlighted then data hasn't changed. Just set state based on the payload
            boolean filterEnabled = payloads.contains(FilterAnimator.FILTER_ENABLED);
            boolean filterDisabled = payloads.contains(FilterAnimator.FILTER_DISABLED);
            if (filterEnabled || filterDisabled) {
                holder.mBinding.filterName.setEnabled(filterEnabled);
                // icon is handled by the animator
            }
            // nothing to do for highlight
        } else {
            onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return filters.size();
    }


    @Override
    public long getItemId(int position) {
        return filters.get(position).key.hashCode();

    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {

        private final FilterItemBinding mBinding;
        public boolean isSwipeable;

        public FilterViewHolder(FilterItemBinding filterItemBinding) {
            super(filterItemBinding.getRoot());
            mBinding = filterItemBinding;

        }

        public void bind(Source source) {

            mBinding.setSource(source);
            mBinding.executePendingBindings();
        }
    }


    @Override
    public void onItemDismiss(int position) {
        Source removing = filters.get(position);
        if (removing.isSwipeDismissable()) {
            removeFilter(removing);
        }
    }

    public int getEnabledSourcesCount() {
        int count = 0;
        for (Source source : filters) {
            if (source.active) {
                count++;
            }
        }
        return count;
    }

    public void registerFilterChangedCallback(FiltersChangedCallbacks filtersChangedCallbacks) {
        if (callbacks == null) {
            callbacks = new ArrayList<>();
        }
        callbacks.add(filtersChangedCallbacks);
    }


    public static abstract class FiltersChangedCallbacks {
        public void onFiltersChanged(Source changedFilter) {
        }

        public void onFilterRemoved(Source removed) {
        }

        public void onLastFilterTriedToChanged(Source remain) {
        }

        ;
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
                            ContextCompat.getColor(holder.itemView.getContext(), R.color.colorAccent);
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
}
