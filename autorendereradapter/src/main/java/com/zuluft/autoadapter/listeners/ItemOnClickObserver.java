package com.zuluft.autoadapter.listeners;

import com.zuluft.autoadapter.renderables.IRenderable;
import com.zuluft.autoadapter.renderables.AutoViewHolder;

import rx.Observer;

/**
 * Created by zuluft on 11/28/16.
 */
public class ItemOnClickObserver<T extends IRenderable, Q extends AutoViewHolder> implements Observer<ItemInfo<T, Q>> {

    private OnItemClickListener<T, Q> mOnItemClickListener;

    public ItemOnClickObserver(OnItemClickListener<T, Q> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(ItemInfo<T, Q> tqItemInfo) {
        mOnItemClickListener.onItemClicked(tqItemInfo);
    }
}
