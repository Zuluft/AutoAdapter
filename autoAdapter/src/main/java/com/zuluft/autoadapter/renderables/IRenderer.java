package com.zuluft.autoadapter.renderables;

/**
 * Created by zuluft on 11/28/16.
 */

public interface IRenderer<T extends AutoViewHolder> {

    void apply(T viewHolder);

}
