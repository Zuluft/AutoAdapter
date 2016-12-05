package com.zuluft.autoadapter.structures;


import com.zuluft.autoadapter.renderables.IRenderable;

import java.util.List;

/**
 * Created by zuluft on 11/28/16.
 */

public interface IAdapter {
    int compare(IRenderable item1, IRenderable item2);

    boolean areContentsTheSame(IRenderable item1, IRenderable item2);

    boolean areItemsTheSame(IRenderable item1, IRenderable item2);

    void add(IRenderable item);

    void addAll(IRenderable... items);

    void addAll(List<? extends IRenderable> items);

    void remove(int position);

    void remove(IRenderable item);

    void removeAll();

    void update(int position, IRenderable newItem);

    int indexOf(IRenderable item);

    IRenderable getItem(int position);
}
