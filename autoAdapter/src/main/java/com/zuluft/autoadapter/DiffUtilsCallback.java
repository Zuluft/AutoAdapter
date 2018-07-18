package com.zuluft.autoadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.zuluft.autoadapter.renderables.Renderer;

import java.util.List;

public final class DiffUtilsCallback
        extends
        DiffUtil.Callback {

    private final List<Renderer> mOldItems;
    private final List<Renderer> mNewItems;

    DiffUtilsCallback(@NonNull final List<Renderer> oldItems,
                      @NonNull final List<Renderer> newItems) {
        this.mOldItems = oldItems;
        this.mNewItems = newItems;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return getNewItem(newItemPosition).getChangePayload(getOldItem(oldItemPosition));
    }

    private Renderer getOldItem(final int position) {
        return mOldItems.get(position);
    }

    private Renderer getNewItem(final int position) {
        return mNewItems.get(position);
    }

    @Override
    public int getOldListSize() {
        return mOldItems.size();
    }

    @Override
    public int getNewListSize() {
        return mNewItems.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return getNewItem(newItemPosition).areItemsTheSame(getOldItem(oldItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return getNewItem(newItemPosition).areContentsTheSame(getOldItem(oldItemPosition));
    }
}
