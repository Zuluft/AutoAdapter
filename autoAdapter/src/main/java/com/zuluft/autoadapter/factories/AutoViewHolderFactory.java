package com.zuluft.autoadapter.factories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuluft.autoadapter.renderables.AutoViewHolder;
import com.zuluft.autoadapter.renderables.IRenderer;

/**
 * Created by giodz on 9/24/2017.
 */

public abstract class AutoViewHolderFactory {

    public abstract int getLayoutId(IRenderer renderer);

    public abstract AutoViewHolder createViewHolder(ViewGroup parent, int layoutId);

    protected final View createView(ViewGroup parent, int layoutId) {
        return LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
    }
}
