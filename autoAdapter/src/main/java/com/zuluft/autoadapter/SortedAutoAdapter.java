package com.zuluft.autoadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zuluft.autoadapter.factories.AutoViewHolderFactory;
import com.zuluft.autoadapter.renderables.OrderableRenderer;
import com.zuluft.autoadapter.structures.ISortedAdapter;
import com.zuluft.autoadapter.structures.SortedAdapterDataStructure;

import java.util.Arrays;
import java.util.List;

public class SortedAutoAdapter
        extends
        BaseAutoAdapter<OrderableRenderer>
        implements
        ISortedAdapter {

    private final SortedAdapterDataStructure mSortedAdapterDataStructure;

    public SortedAutoAdapter(@NonNull final AutoViewHolderFactory autoViewHolderFactory) {
        super(autoViewHolderFactory);
        mSortedAdapterDataStructure = new SortedAdapterDataStructure(this);
    }

    @Override
    public int getItemCount() {
        return mSortedAdapterDataStructure.size();
    }

    @Override
    public int compare(@NonNull final OrderableRenderer item1,
                       @NonNull final OrderableRenderer item2) {
        return item1.compareOrderIds(item2);
    }

    @Override
    public boolean areContentsTheSame(@NonNull final OrderableRenderer item1,
                                      @NonNull final OrderableRenderer item2) {
        return item1.areContentsTheSame(item2);
    }

    @Override
    public boolean areItemsTheSame(@NonNull final OrderableRenderer item1,
                                   @NonNull final OrderableRenderer item2) {
        return item1.hasSameOrderIds(item2);
    }

    @Override
    public void beginUpdate() {
        mSortedAdapterDataStructure.beginBatchedUpdates();
    }

    @Override
    public void commitUpdate() {
        mSortedAdapterDataStructure.endBatchedUpdates();
    }

    @Override
    public void updateAll(@NonNull final List<OrderableRenderer> list) {
        mSortedAdapterDataStructure.updateAll(list);
    }

    @Override
    public void add(@NonNull final OrderableRenderer item) {
        mSortedAdapterDataStructure.add(item);
    }

    @Override
    public void addAll(@NonNull final OrderableRenderer[] items) {
        mSortedAdapterDataStructure.addAll(Arrays.asList(items));
    }

    @Override
    public void addAll(@NonNull final List<? extends OrderableRenderer> items) {
        OrderableRenderer[] renderables = new OrderableRenderer[items.size()];
        mSortedAdapterDataStructure.addAll(items.toArray(renderables));
    }

    @Override
    public void remove(final int position) {
        mSortedAdapterDataStructure.removeItemAt(position);
    }

    @Override
    public void remove(@NonNull final OrderableRenderer item) {
        mSortedAdapterDataStructure.remove(item);
    }

    @Override
    public void removeAll() {
        mSortedAdapterDataStructure.clear();
    }

    @Override
    public void update(final int position, @NonNull final OrderableRenderer newItem) {
        mSortedAdapterDataStructure.updateItemAt(position, newItem);
    }

    @Override
    public int indexOf(@NonNull final OrderableRenderer item) {
        return mSortedAdapterDataStructure.indexOf(item);
    }

    @Nullable
    @Override
    public OrderableRenderer getItem(final int position) {
        return mSortedAdapterDataStructure.get(position);
    }
}
