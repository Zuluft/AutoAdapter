package com.zuluft.autoadapter.listeners;


import com.zuluft.autoadapter.renderables.IRenderer;
import com.zuluft.autoadapter.renderables.AutoViewHolder;


@SuppressWarnings("WeakerAccess")
public final class ItemInfo<T extends IRenderer, V extends AutoViewHolder> {
    public final int position;
    public final T renderer;
    public final V viewHolder;

    public ItemInfo(int position, T renderer, V viewHolder) {
        this.position = position;
        this.renderer = renderer;
        this.viewHolder = viewHolder;
    }
}
