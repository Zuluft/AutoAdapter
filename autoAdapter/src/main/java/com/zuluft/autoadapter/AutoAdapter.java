package com.zuluft.autoadapter;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.zuluft.autoadapter.factories.AutoViewHolderFactory;
import com.zuluft.autoadapter.renderables.Renderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoAdapter
        extends
        BaseAutoAdapter<Renderer> {

    private final List<Renderer> mRenderers = new ArrayList<>();

    public AutoAdapter(final @NonNull AutoViewHolderFactory autoViewHolderFactory) {
        super(autoViewHolderFactory);
    }

    @Override
    public int getItemCount() {
        return mRenderers.size();
    }

    @Override
    public void add(@NonNull final Renderer item) {
        mRenderers.add(item);
    }

    @Override
    public void addAll(@NonNull final Renderer[] items) {
        mRenderers.addAll(Arrays.asList(items));
    }

    @Override
    public void addAll(@NonNull final List<? extends Renderer> items) {
        mRenderers.addAll(items);
    }

    @Override
    public void remove(final int position) {
        mRenderers.remove(position);
    }

    @Override
    public void remove(@NonNull final Renderer item) {
        mRenderers.remove(item);
    }

    @Override
    public void removeAll() {
        mRenderers.clear();
    }

    @Override
    public void update(final int position, @NonNull final Renderer newItem) {
        mRenderers.remove(position);
        mRenderers.add(position, newItem);
    }

    @Override
    public void updateAll(@NonNull final List<Renderer> items) {
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(new DiffUtilsCallback(mRenderers, items));
        diffResult.dispatchUpdatesTo(this);
        mRenderers.clear();
        mRenderers.addAll(items);
    }

    @Override
    public void updateAll(@NonNull final List<Renderer> items,
                          final boolean reorder) {
        DiffUtil.DiffResult diffResult =
                DiffUtil.calculateDiff(new DiffUtilsCallback(mRenderers, items), reorder);
        diffResult.dispatchUpdatesTo(this);
        mRenderers.clear();
        mRenderers.addAll(items);
    }

    @Override
    public void updateAllAsync(@NonNull List<Renderer> items) {

    }

    @Override
    public int indexOf(@NonNull Renderer item) {
        return mRenderers.indexOf(item);
    }

    @Override
    public Renderer getItem(final int position) {
        return mRenderers.get(position);
    }
}
