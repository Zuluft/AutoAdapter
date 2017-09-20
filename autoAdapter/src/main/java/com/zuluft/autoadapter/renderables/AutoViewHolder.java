package com.zuluft.autoadapter.renderables;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.subjects.PublishSubject;


/**
 * Created by zuluft on 11/28/16.
 */

public class AutoViewHolder extends RecyclerView.ViewHolder {


    public AutoViewHolder(View itemView) {
        super(itemView);

    }

    public Observable<AutoViewHolder> getViewHolderOnClickObservable() {
        return RxView.clicks(itemView).map(new Function<Object, AutoViewHolder>() {
            @Override
            public AutoViewHolder apply(Object view) throws Exception {
                return AutoViewHolder.this;
            }
        });
    }

    public final Observable<AutoViewHolder> getViewHolderOnChildClickObservable(@IdRes int viewId) {
        return RxView.clicks(itemView.findViewById(viewId))
                .map(new Function<Object, AutoViewHolder>() {
                    @Override
                    public AutoViewHolder apply(Object o) throws Exception {
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
