package com.zuluft.autoadapter.listeners;

import com.zuluft.autoadapter.renderables.IRenderable;
import com.zuluft.autoadapter.renderables.AutoViewHolder;

import rx.Observer;
import rx.functions.Action1;

/**
 * Created by zuluft on 11/28/16.
 */
public class ItemOnClickObserver<T extends IRenderable, Q extends AutoViewHolder> implements Action1<ItemInfo<T, Q>> {

    private OnItemClickListener<T, Q> mOnItemClickListener;

    public ItemOnClickObserver(OnItemClickListener<T, Q> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void call(ItemInfo<T, Q> tqItemInfo) {
        if (tqItemInfo != null) {
            mOnItemClickListener.onItemClicked(tqItemInfo);
        }
    }
}
