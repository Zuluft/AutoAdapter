package com.zuluft.autoadapter.renderables;

/**
 * Created by zuluft on 11/28/16.
 */

public interface IRenderable<T extends AutoViewHolder> {

    void apply(T viewHolder);

    int compareTo(IRenderable item);

    boolean areContentsTheSame(IRenderable item);

    boolean areItemsTheSame(IRenderable item);
}
