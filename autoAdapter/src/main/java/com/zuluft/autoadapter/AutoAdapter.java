package com.zuluft.autoadapter;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zuluft.autoadapter.listeners.ItemInfo;
import com.zuluft.autoadapter.renderables.AutoViewHolder;
import com.zuluft.autoadapter.renderables.IRenderable;
import com.zuluft.autoadapter.structures.AdapterDataStructure;
import com.zuluft.autoadapter.structures.IAdapter;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;


/**
 * Created by zuluft on 11/28/16.
 */
public class AutoAdapter extends RecyclerView.Adapter<AutoViewHolder> implements IAdapter {

    private final SparseArray<Class<? extends AutoViewHolder>> mViewTypeViewHolderBinding = new SparseArray<>();
    private final SparseArray<PublishSubject> mItemClickListenerBinding = new SparseArray<>();
    private final SparseArray<Map<Integer, PublishSubject>> mChildItemClickListenerBinding = new SparseArray<>();
    protected final AdapterDataStructure mAdapterDataStructure = new AdapterDataStructure(this);

    @SuppressWarnings("unchecked")
    @Override
    public AutoViewHolder onCreateViewHolder(ViewGroup parent, @LayoutRes int layoutId) {
        return null;
    }

    @Override
    public void onBindViewHolder(AutoViewHolder holder, int position) {

    }

    protected View createView(ViewGroup parent, @LayoutRes int layoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    private static final String TAG = "AutoAdapter";

    @SuppressWarnings("unchecked")
    private Class<? extends AutoViewHolder> getViewHolderClass(Class<? extends IRenderable> clazz) {
        try {
            return (Class<? extends AutoViewHolder>)
                    ((ParameterizedType) clazz.getClass()
                            .getGenericSuperclass()).getActualTypeArguments()[0];
        } catch (Exception ignored) {
        }
        throw new RuntimeException("Renderer must have apply method, with Renderer param, which extends AutoViewHolder and has @Renderer annotation");
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

    @Override
    public void updateAll(List<IRenderable> list) {
        mAdapterDataStructure.updateAll(list);
    }
}
