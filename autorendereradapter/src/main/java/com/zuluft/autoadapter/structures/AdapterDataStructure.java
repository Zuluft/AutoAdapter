package com.zuluft.autoadapter.structures;

import android.support.v7.util.SortedList;
import android.support.v7.widget.util.SortedListAdapterCallback;

import com.zuluft.autoadapter.AutoAdapter;
import com.zuluft.autoadapter.renderables.IRenderable;


/**
 * Created by zuluft on 11/28/16.
 */
public class AdapterDataStructure extends SortedList<IRenderable> {

    public AdapterDataStructure(AutoAdapter autoAdapter) {
        super(IRenderable.class, new SortedListAdapterCallback<IRenderable>(autoAdapter) {
            @Override
            public int compare(IRenderable o1, IRenderable o2) {
                return autoAdapter.compare(o1, o2);
            }

            @Override
            public boolean areContentsTheSame(IRenderable oldItem, IRenderable newItem) {
                return autoAdapter.areContentsTheSame(oldItem, newItem);
            }

            @Override
            public boolean areItemsTheSame(IRenderable item1, IRenderable item2) {
                return autoAdapter.areItemsTheSame(item1, item2);
            }
        });
    }


}
