package com.zuluft.autoadapter.renderables;

/**
 * Created by zuluft on 11/28/16.
 */

public abstract class Renderable<T extends AutoViewHolder> implements IRenderable<T> {

    @Override
    public int compareTo(IRenderable item) {
        return 0;
    }

    @Override
    public boolean areContentsTheSame(IRenderable item) {
        return false;
    }

    @Override
    public boolean areItemsTheSame(IRenderable item) {
        return false;
    }
}
