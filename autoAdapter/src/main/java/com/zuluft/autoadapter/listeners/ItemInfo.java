package com.zuluft.autoadapter.listeners;


import com.zuluft.autoadapter.renderables.IRenderable;
import com.zuluft.autoadapter.renderables.AutoViewHolder;

/**
 * Created by zuluft on 11/28/16.
 */

public class ItemInfo<T extends IRenderable, V extends AutoViewHolder> {
    public final int position;
    public final T renderer;
    public final V viewHolder;

    public ItemInfo(int position, T renderer, V viewHolder) {
        this.position = position;
        this.renderer = renderer;
        this.viewHolder = viewHolder;
    }
}
