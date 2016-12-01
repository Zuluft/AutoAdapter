package com.zuluft.autoadapter.renderables;

/**
 * Created by zuluft on 11/28/16.
 */

public abstract class Renderable<T extends AutoViewHolder> implements IRenderable<T> {

    @Override
    public <T1 extends IRenderable> int compareTo(T1 item2) {
        return 0;
    }

    @Override
    public <T1 extends IRenderable> boolean areContentsTheSame(T1 item) {
        return false;
    }

    @Override
    public <T1 extends IRenderable> boolean areItemsTheSame(T1 item) {
        return false;
    }
}
