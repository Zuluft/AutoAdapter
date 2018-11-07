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
import com.zuluft.autoadapter.listeners.ViewHolderCreationListener;
import com.zuluft.autoadapter.renderables.AutoViewHolder;
import com.zuluft.autoadapter.renderables.IRenderer;
import com.zuluft.autoadapter.structures.IAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

@SuppressWarnings({"WeakerAccess"})
public abstract class BaseAutoAdapter<T extends IRenderer>
        extends
        RecyclerView.Adapter<AutoViewHolder>
        implements
        IAdapter<T> {

    private final AutoViewHolderFactory mAutoViewHolderFactory;

    private final Map<Class<? extends IRenderer>, PublishSubject>
            mItemViewClickBinding = new HashMap<>();
    private final Map<Class<? extends IRenderer>, Map<Integer, PublishSubject>>
            mChildViewsClickBinding = new HashMap<>();

    private final Map<Class<? extends IRenderer>, PublishSubject>
            mItemViewLongClickBinding = new HashMap<>();
    private final Map<Class<? extends IRenderer>, Map<Integer, PublishSubject>>
            mChildViewsLongClickBinding = new HashMap<>();

    private final SparseArray<Class<? extends IRenderer>>
            mLayoutRendererMapping = new SparseArray<>();

    private final List<ViewHolderCreationListener> mViewHolderCreationListeners = new ArrayList<>();

    public BaseAutoAdapter(final AutoViewHolderFactory autoViewHolderFactory) {
        this.mAutoViewHolderFactory = autoViewHolderFactory;
    }

    @SuppressWarnings("unused")
    public final void addViewHolderCreationListener(@NonNull final ViewHolderCreationListener
                                                            viewHolderCreationListener) {
        mViewHolderCreationListeners.add(viewHolderCreationListener);
    }

    @NonNull
    @Override
    public AutoViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int layoutId) {
        AutoViewHolder viewHolder = mAutoViewHolderFactory.createViewHolder(parent, layoutId);
        Class rendererClass = mLayoutRendererMapping.get(layoutId);
        final PublishSubject itemLongClickPublishSubject =
                mItemViewLongClickBinding.get(rendererClass);
        if (itemLongClickPublishSubject != null) {
            viewHolder.getViewHolderOnLongClickObservable()
                    .subscribe(new Observer<AutoViewHolder>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            itemLongClickPublishSubject.onSubscribe(d);
                        }

                        @Override
                        @SuppressWarnings("unchecked")
                        public void onNext(AutoViewHolder autoViewHolder) {
                            int position = autoViewHolder.getAdapterPosition();
                            ItemInfo info =
                                    new ItemInfo(position, getItem(position), autoViewHolder);
                            itemLongClickPublishSubject.onNext(info);
                        }

                        @SuppressWarnings("unchecked")
                        @Override
                        public void onError(Throwable e) {
                            itemLongClickPublishSubject.onNext(e);
                        }

                        @Override
                        public void onComplete() {
                            itemLongClickPublishSubject.onComplete();
                        }
                    });
        }
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

        final Map<Integer, PublishSubject> childViewsLongClickMap =
                mChildViewsLongClickBinding.get(rendererClass);
        if (childViewsLongClickMap != null) {
            for (Map.Entry<Integer, PublishSubject> entry :
                    childViewsLongClickMap.entrySet()) {
                final PublishSubject publishSubject = entry.getValue();
                viewHolder.getViewHolderOnChildLongClickObservable(entry.getKey())
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
        for (ViewHolderCreationListener mViewHolderCreationListener :
                mViewHolderCreationListeners) {
            mViewHolderCreationListener.onViewHolderCreated(viewHolder);
        }
        return viewHolder;
    }

    public final <X extends AutoViewHolder, Y extends IRenderer<X>> PublishSubject<ItemInfo<Y, X>>
    clicks(@NonNull final Class<Y> clazz) {
        PublishSubject<ItemInfo<Y, X>> publishSubject = PublishSubject.create();
        mItemViewClickBinding.put(clazz, publishSubject);
        return publishSubject;
    }

    @SuppressLint("UseSparseArrays")
    public final <X extends AutoViewHolder, Y extends IRenderer<X>> PublishSubject<ItemInfo<Y, X>>
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

    @SuppressWarnings("unused")
    public final <X extends AutoViewHolder, Y extends IRenderer<X>> PublishSubject<ItemInfo<Y, X>>
    longClicks(@NonNull final Class<Y> clazz) {
        PublishSubject<ItemInfo<Y, X>> publishSubject = PublishSubject.create();
        mItemViewLongClickBinding.put(clazz, publishSubject);
        return publishSubject;
    }


    @SuppressWarnings("unused")
    @SuppressLint("UseSparseArrays")
    public final <X extends AutoViewHolder, Y extends IRenderer<X>> PublishSubject<ItemInfo<Y, X>>
    longClicks(@NonNull final Class<Y> clazz, @IdRes final int viewId) {
        PublishSubject<ItemInfo<Y, X>> publishSubject = PublishSubject.create();
        Map<Integer, PublishSubject> map = mChildViewsLongClickBinding.get(clazz);
        if (map == null) {
            map = new HashMap<>();
            mChildViewsLongClickBinding.put(clazz, map);
        }
        map.put(viewId, publishSubject);
        return publishSubject;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull final AutoViewHolder holder,
                                 final int position) {
        getItem(position).apply(holder);
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
