package com.zuluft.autoadapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.zuluft.autoadapter.factories.AutoViewHolderFactory;
import com.zuluft.autoadapter.renderables.IRenderer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by giodz on 9/24/2017.
 */

public class AutoAdapter
        extends
        BaseAutoAdapter<IRenderer> {

    private final List<IRenderer> mRenderers = new ArrayList<>();

    public AutoAdapter(AutoViewHolderFactory autoViewHolderFactory) {
        super(autoViewHolderFactory);
    }

    @Override
    public int getItemCount() {
        return mRenderers.size();
    }

    @Override
    public void add(@NonNull IRenderer item) {
        mRenderers.add(item);
    }

    @Override
    public void addAll(@NonNull final IRenderer[] items) {
        mRenderers.addAll(Arrays.asList(items));
    }

    @Override
    public void addAll(@NonNull final List<? extends IRenderer> items) {
        mRenderers.addAll(items);
    }

    @Override
    public void remove(int position) {
        mRenderers.remove(position);
    }

    @Override
    public void remove(@NonNull final IRenderer item) {
        mRenderers.remove(item);
    }

    @Override
    public void removeAll() {
        mRenderers.clear();
    }

    @Override
    public void update(int position, @NonNull IRenderer newItem) {
        mRenderers.remove(position);
        mRenderers.add(position, newItem);
    }

    @Override
    public int indexOf(@NonNull IRenderer item) {
        return mRenderers.indexOf(item);
    }

    @Override
    public IRenderer getItem(int position) {
        return mRenderers.get(position);
    }
}
