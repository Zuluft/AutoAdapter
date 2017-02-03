package com.zuluft.autoadapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuluft.autoadapter.annotations.Renderer;
import com.zuluft.autoadapter.annotations.ViewHolder;
import com.zuluft.autoadapter.listeners.ItemInfo;
import com.zuluft.autoadapter.listeners.ItemOnClickObserver;
import com.zuluft.autoadapter.listeners.OnItemClickListener;
import com.zuluft.autoadapter.renderables.AutoViewHolder;
import com.zuluft.autoadapter.renderables.IRenderable;
import com.zuluft.autoadapter.structures.AdapterDataStructure;
import com.zuluft.autoadapter.structures.IAdapter;

import java.lang.reflect.Constructor;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by zuluft on 11/28/16.
 */
public class AutoAdapter extends RecyclerView.Adapter<AutoViewHolder> implements IAdapter {

    private final SparseArray<Class<? extends AutoViewHolder>> mViewTypeViewHolderBinding = new SparseArray<>();
    private final SparseArray<OnItemClickListener> mItemClickListenerBinding = new SparseArray<>();
    private final SparseArray<Pair<Integer, OnItemClickListener>> mChildItemClickListenerBinding = new SparseArray<>();
    protected final AdapterDataStructure mAdapterDataStructure = new AdapterDataStructure(this);

    @Override
    public AutoViewHolder onCreateViewHolder(ViewGroup parent, @LayoutRes int layoutId) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        final AutoViewHolder autoViewHolder = createViewHolderInstance(mViewTypeViewHolderBinding.get(layoutId), view);
        final OnItemClickListener onItemClickListener = mItemClickListenerBinding.get(layoutId);
        if (onItemClickListener != null) {
            setupOnClickObservers(autoViewHolder, onItemClickListener);
        }
        final Pair<Integer, OnItemClickListener> pair = mChildItemClickListenerBinding.get(layoutId);
        if (pair != null) {
            setupOnChildClickObserver(autoViewHolder, pair);
        }
        return autoViewHolder;
    }

    private void setupOnChildClickObserver(AutoViewHolder autoViewHolder,
                                           Pair<Integer, OnItemClickListener> pair) {
        mapAndSubscribeObservable(autoViewHolder.getViewHolderOnChildClickObservable(pair.first), pair.second);
    }

    public <Q extends AutoViewHolder, T extends IRenderable<Q>> void bindListener(final Class<T> clazz, final OnItemClickListener<T, Q> onItemClickListener) {
        final Renderer renderer = getRenderableAnnotation(clazz);
        final ViewHolder viewHolder = getViewHolderAnnotation(renderer.value());
        mItemClickListenerBinding.put(viewHolder.value(), onItemClickListener);
    }

    public <Q extends AutoViewHolder, T extends IRenderable<Q>> void bindListener(final Class<T> clazz,
                                                                                  final int viewId,
                                                                                  final OnItemClickListener<T, Q> onItemClickListener) {
        final Renderer renderer = getRenderableAnnotation(clazz);
        final ViewHolder viewHolder = getViewHolderAnnotation(renderer.value());
        mChildItemClickListenerBinding.put(viewHolder.value(), Pair.create(viewId, onItemClickListener));
    }


    private void setupOnClickObservers(final AutoViewHolder autoViewHolder, final OnItemClickListener onItemClickListener) {
        mapAndSubscribeObservable(autoViewHolder.getViewHolderOnClickObservable(), onItemClickListener);
    }

    @SuppressWarnings("unchecked")
    private void mapAndSubscribeObservable(final Observable<? extends AutoViewHolder> observable, final OnItemClickListener onItemClickListener) {
        observable.map(new Func1<AutoViewHolder, ItemInfo>() {
            @Override
            public ItemInfo call(AutoViewHolder autoViewHolder) {
                int position = autoViewHolder.getAdapterPosition();
                return new ItemInfo(position, mAdapterDataStructure.get(position), autoViewHolder);
            }
        }).subscribe(new ItemOnClickObserver<>(onItemClickListener));
    }

    private AutoViewHolder createViewHolderInstance(final Class<? extends AutoViewHolder> viewHolderClass, final View view) {
        try {
            Constructor<? extends AutoViewHolder> constructor = viewHolderClass.getDeclaredConstructor(View.class);
            return constructor.newInstance(view);
        } catch (Exception e) {
            throw new RuntimeException(String.format("%s must have 1 argument constructor and the argument must be a View", viewHolderClass.getSimpleName()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(final AutoViewHolder holder, final int position) {
        final IRenderable IRenderable = mAdapterDataStructure.get(position);
        IRenderable.apply(holder);
    }

    @Override
    @LayoutRes
    public int getItemViewType(final int position) {
        final Class<? extends IRenderable> renderableClass = mAdapterDataStructure.get(position).getClass();
        final Renderer renderer = getRenderableAnnotation(renderableClass);
        final Class<? extends AutoViewHolder> viewHolderClass = renderer.value();
        final ViewHolder viewHolder = getViewHolderAnnotation(viewHolderClass);
        final int layoutId = viewHolder.value();
        if (mViewTypeViewHolderBinding.indexOfKey(layoutId) < 0) {
            mViewTypeViewHolderBinding.put(layoutId, viewHolderClass);
        }
        return layoutId;
    }

    private ViewHolder getViewHolderAnnotation(Class<? extends AutoViewHolder> clazz) {
        ViewHolder viewHolder = clazz.getAnnotation(ViewHolder.class);
        if (viewHolder == null) {
            throw new RuntimeException(String.format("%s must be annotated with @ViewHolder annotation", clazz.getSimpleName()));
        }
        return viewHolder;
    }

    private Renderer getRenderableAnnotation(Class<? extends IRenderable> clazz) {
        Renderer renderer = clazz.getAnnotation(Renderer.class);
        if (renderer == null) {
            throw new RuntimeException(String.format("%s must be annotated with @Renderer annotation", clazz.getSimpleName()));
        }
        return renderer;
    }

    @Override
    public int getItemCount() {
        return mAdapterDataStructure.size();
    }

    @Override
    public int compare(IRenderable item1, IRenderable item2) {
        return item1.compareTo(item2);
    }

    @Override
    public boolean areContentsTheSame(IRenderable item1, IRenderable item2) {
        return item1.areContentsTheSame(item2);
    }

    @Override
    public boolean areItemsTheSame(IRenderable item1, IRenderable item2) {
        return item1.areItemsTheSame(item2);
    }

    @Override
    public void add(IRenderable item) {
        mAdapterDataStructure.add(item);
    }

    @Override
    public void addAll(IRenderable[] items) {
        mAdapterDataStructure.addAll(items);
    }

    @Override
    public void addAll(List<? extends IRenderable> items) {
        IRenderable[] tmp = new IRenderable[items.size()];
        mAdapterDataStructure.addAll(items.toArray(tmp));
    }

    @Override
    public void remove(int position) {
        mAdapterDataStructure.removeItemAt(position);
    }

    @Override
    public void remove(IRenderable item) {
        mAdapterDataStructure.remove(item);
    }

    @Override
    public void removeAll() {
        mAdapterDataStructure.clear();
    }

    @Override
    public void update(int position, IRenderable newItem) {
        mAdapterDataStructure.updateItemAt(position, newItem);
    }

    @Override
    public int indexOf(IRenderable item) {
        return mAdapterDataStructure.indexOf(item);
    }

    @Override
    public IRenderable getItem(int position) {
        return mAdapterDataStructure.get(position);
    }

    @Override
    public void beginUpdate() {
        mAdapterDataStructure.beginBatchedUpdates();
    }

    @Override
    public void commitUpdate() {
        mAdapterDataStructure.endBatchedUpdates();
    }
}
