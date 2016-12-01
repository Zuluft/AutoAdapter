package com.zuluft.autoadapter.renderables;

/**
 * Created by zuluft on 11/28/16.
 */

public interface IRenderable<T extends AutoViewHolder> {

    void apply(T viewHolder);

    <T extends IRenderable> int compareTo(T item2);

    <T extends IRenderable> boolean areContentsTheSame(T item);

    <T extends IRenderable> boolean areItemsTheSame(T item);
}
