package com.zuluft.autoadapter.listeners;


import com.zuluft.autoadapter.renderables.IRenderable;
import com.zuluft.autoadapter.renderables.AutoViewHolder;

/**
 * Created by zuluft on 11/29/16.
 */
@FunctionalInterface
public interface OnItemClickListener<T extends IRenderable, Q extends AutoViewHolder> {
    void onItemClicked(ItemInfo<T, Q> itemInfo);
}
