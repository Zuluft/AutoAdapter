package com.zuluft.autoadapter;

import android.annotation.SuppressLint;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.zuluft.autoadapter.factories.AutoViewHolderFactory;
import com.zuluft.autoadapter.listeners.ItemInfo;
import com.zuluft.autoadapter.renderables.AutoViewHolder;
import com.zuluft.autoadapter.renderables.Renderer;
import com.zuluft.autoadapter.structures.IAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

@SuppressWarnings({"unused", "WeakerAccess"})
public abstract class BaseAutoAdapter<T extends Renderer>
        extends
        RecyclerView.Adapter<AutoViewHolder>
        implements
        IAdapter<T> {

    private final AutoViewHolderFactory mAutoViewHolderFactory;

    private final Map<Class<? extends Renderer>, PublishSubject>
            mItemViewClickBinding = new HashMap<>();
    private final Map<Class<? extends Renderer>, Map<Integer, PublishSubject>>
            mChildViewsClickBinding = new HashMap<>();
    private final SparseArray<Class<? extends Renderer>>
            mLayoutRendererMapping = new SparseArray<>();

    public BaseAutoAdapter(final AutoViewHolderFactory autoViewHolderFactory) {
        this.mAutoViewHolderFactory = autoViewHolderFactory;
    }

    @NonNull
    @Override
    public AutoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int layoutId) {
        AutoViewHolder viewHolder = mAutoViewHolderFactory.createViewHolder(parent, layoutId);
        Class rendererClass = mLayoutRendererMapping.get(layoutId);
        final PublishSubject itemClickPublishSubject = mItemViewClickBinding.get(rendererClass);
        if (itemClickPublishSubject != null) {
            viewHolder.getViewHolderOnClickObservable()
                    .subscribe(new Observer<AutoViewHolder>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            itemClickPublishSubject.onSubscribe(d);
                        }

                        @SuppressWarnings("unchecked")
                        @Override
                        public void onNext(AutoViewHolder autoViewHolder) {
                            int position = autoViewHolder.getAdapterPosition();
                            ItemInfo info =
                                    new ItemInfo(position, getItem(position), autoViewHolder);
                            itemClickPublishSubject.onNext(info);
                        }

                        @Override
                        public void onError(Throwable e) {
                            itemClickPublishSubject.onError(e);
                        }

                        @Override
                        public void onComplete() {
                            itemClickPublishSubject.onComplete();
                        }
                    });
        }
        final Map<Integer, PublishSubject> childViewsClickMap
                = mChildViewsClickBinding.get(rendererClass);
        if (childViewsClickMap != null) {
            for (Map.Entry<Integer, PublishSubject> entry :
                    childViewsClickMap.entrySet()) {
                final PublishSubject publishSubject = entry.getValue();
                viewHolder.getViewHolderOnChildClickObservable(entry.getKey())
                        .subscribe(new Observer<AutoViewHolder>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                publishSubject.onSubscribe(d);
                            }

                            @SuppressWarnings("unchecked")
                            @Override
                            public void onNext(AutoViewHolder autoViewHolder) {
                                int position = autoViewHolder.getAdapterPosition();
                                ItemInfo info =
                                        new ItemInfo(position, getItem(position), autoViewHolder);
                                publishSubject.onNext(info);
                            }

                            @Override
                            public void onError(Throwable e) {
                                publishSubject.onError(e);
                            }

                            @Override
                            public void onComplete() {
                                publishSubject.onComplete();
                            }
                        });
            }
        }
        return viewHolder;
    }

    public final <X extends AutoViewHolder, Y extends Renderer<X>> PublishSubject<ItemInfo<Y, X>>
    clicks(@NonNull final Class<Y> clazz) {
        PublishSubject<ItemInfo<Y, X>> publishSubject = PublishSubject.create();
        mItemViewClickBinding.put(clazz, publishSubject);
        return publishSubject;
    }

    @SuppressLint("UseSparseArrays")
    public final <X extends AutoViewHolder, Y extends Renderer<X>> PublishSubject<ItemInfo<Y, X>>
    clicks(@NonNull final Class<Y> clazz, @IdRes final int viewId) {
        PublishSubject<ItemInfo<Y, X>> publishSubject = PublishSubject.create();
        Map<Integer, PublishSubject> map = mChildViewsClickBinding.get(clazz);
        if (map == null) {
            map = new HashMap<>();
            mChildViewsClickBinding.put(clazz, map);
        }
        map.put(viewId, publishSubject);
        return publishSubject;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull AutoViewHolder holder, int position,
                                 @NonNull List<Object> payloads) {
        getItem(position).apply(holder, payloads);
    }

    @Override
    public void onBindViewHolder(@NonNull AutoViewHolder holder, int position) {

    }

    @Override
    @LayoutRes
    public int getItemViewType(final int position) {
        final T item = getItem(position);
        final int layoutId = mAutoViewHolderFactory.getLayoutId(getItem(position));
        if (mLayoutRendererMapping.indexOfKey(layoutId) < 0) {
            mLayoutRendererMapping.put(layoutId, item.getClass());
        }
        return layoutId;
    }
}
