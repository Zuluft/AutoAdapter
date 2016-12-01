package com.zuluft.autoadapter.renderables;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import rx.Subscriber;

/**
 * Created by zuluft on 11/28/16.
 */

public class AutoViewHolder extends RecyclerView.ViewHolder {

    private rx.Observable<? extends AutoViewHolder> mViewHolderOnClickObservable;
    private rx.Observable<? extends AutoViewHolder> mViewHolderOnChildClickObservable;

    public AutoViewHolder(View itemView) {
        super(itemView);

    }

    private void handleSubscriber(Subscriber<? super AutoViewHolder> subscriber) {
        if (!subscriber.isUnsubscribed()) {
            subscriber.onNext(this);
        }
    }

    public rx.Observable<? extends AutoViewHolder> getViewHolderOnClickObservable() {
        if (mViewHolderOnClickObservable == null) {
            mViewHolderOnClickObservable = rx.Observable.create(subscriber -> itemView.setOnClickListener(view -> handleSubscriber(subscriber)));
        }
        return mViewHolderOnClickObservable;
    }

    public rx.Observable<? extends AutoViewHolder> getViewHolderOnChildClickObservable(int viewId) {
        if (mViewHolderOnChildClickObservable == null) {
            View childView = itemView.findViewById(viewId);
            if (childView == null) {
                throw new RuntimeException(String.format("can't found view in %s with this Id: %d", getClass().getSimpleName(), viewId));
            }
            mViewHolderOnChildClickObservable = rx.Observable.create(subscriber -> childView.setOnClickListener(view -> handleSubscriber(subscriber)));
        }
        return mViewHolderOnChildClickObservable;
    }
}
