package com.zuluft.autoadapter.renderables;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import rx.Subscriber;
import rx.subjects.PublishSubject;

/**
 * Created by zuluft on 11/28/16.
 */

public class AutoViewHolder extends RecyclerView.ViewHolder {


    public AutoViewHolder(View itemView) {
        super(itemView);

    }

    public rx.Observable<AutoViewHolder> getViewHolderOnClickObservable() {
        final PublishSubject<AutoViewHolder> publishSubject = PublishSubject.create();
        itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            publishSubject.onNext(AutoViewHolder.this);
                                        }
                                    }
        );
        return publishSubject.asObservable();
    }

    public final rx.Observable<AutoViewHolder> getViewHolderOnChildClickObservable(int viewId) {
        View childView = itemView.findViewById(viewId);
        if (childView == null) {
            throw new RuntimeException(String.format("can't found view in %s with this Id: %d", getClass().getSimpleName(), viewId));
        }
        final PublishSubject<AutoViewHolder> publishSubject = PublishSubject.create();
        childView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishSubject.onNext(AutoViewHolder.this);
            }
        });
        return publishSubject.asObservable();
    }

    public Context getContext() {
        return itemView.getContext();
    }

    public View findViewById(int id) {
        return itemView.findViewById(id);
    }
}
