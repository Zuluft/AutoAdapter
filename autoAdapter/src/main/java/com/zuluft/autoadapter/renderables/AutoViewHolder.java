package com.zuluft.autoadapter.renderables;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;
import io.reactivex.functions.Function;


@SuppressWarnings({"unused", "WeakerAccess"})
public class AutoViewHolder extends RecyclerView.ViewHolder {


    public AutoViewHolder(View itemView) {
        super(itemView);

    }

    public Observable<AutoViewHolder> getViewHolderOnClickObservable() {
        return RxView.clicks(itemView).map(new Function<Object, AutoViewHolder>() {
            @Override
            public AutoViewHolder apply(Object view) {
                return AutoViewHolder.this;
            }
        });
    }

    public Observable<AutoViewHolder> getViewHolderOnLongClickObservable() {
        return RxView.longClicks(itemView).map(new Function<Object, AutoViewHolder>() {
            @Override
            public AutoViewHolder apply(Object o) {
                return AutoViewHolder.this;
            }
        });
    }

    public Observable<AutoViewHolder> getViewHolderOnChildLongClickObservable(@IdRes int viewId) {
        return RxView.longClicks(itemView.findViewById(viewId))
                .map(new Function<Object, AutoViewHolder>() {
                    @Override
                    public AutoViewHolder apply(Object o) {
                        return AutoViewHolder.this;
                    }
                });
    }

    public final Observable<AutoViewHolder> getViewHolderOnChildClickObservable(@IdRes int viewId) {
        return RxView.clicks(itemView.findViewById(viewId))
                .map(new Function<Object, AutoViewHolder>() {
                    @Override
                    public AutoViewHolder apply(Object o) {
                        return AutoViewHolder.this;
                    }
                });

    }

    public Context getContext() {
        return itemView.getContext();
    }

    public View findViewById(int id) {
        return itemView.findViewById(id);
    }
}
