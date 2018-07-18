package com.zuluft.autoadapter.structures;


import android.support.annotation.NonNull;

import com.zuluft.autoadapter.renderables.Renderer;

import java.util.List;

@SuppressWarnings({"unchecked", "unused"})
public interface IAdapter<T extends Renderer> {

    void add(@NonNull T item);

    void addAll(@NonNull T... items);

    void addAll(@NonNull List<? extends T> items);

    void remove(int position);

    void remove(@NonNull T item);

    void removeAll();

    void update(int position, @NonNull T newItem);

    void updateAll(@NonNull List<T> items);

    void updateAll(@NonNull List<T> items, boolean reorder);

    void updateAllAsync(@NonNull List<T> items);

    int indexOf(@NonNull T item);

    T getItem(int position);

}
