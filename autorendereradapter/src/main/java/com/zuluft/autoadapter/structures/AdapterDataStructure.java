package com.zuluft.autoadapter.structures;

import android.support.v7.util.SortedList;
import android.support.v7.widget.util.SortedListAdapterCallback;

import com.zuluft.autoadapter.AutoAdapter;
import com.zuluft.autoadapter.renderables.IRenderable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;


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

    public void updateAll(List<IRenderable> list) {
        Stack<IRenderable> itemsToRemove = new Stack<>();
        AdapterDataStructure oldData = this;
        int oldSize = this.size();
        int newSize = list.size();
        ArrayList<IRenderable> newData = new ArrayList<>(list);
        Collections.sort(newData, new Comparator<IRenderable>() {
            @Override
            public int compare(IRenderable o1, IRenderable o2) {
                return o1.compareTo(o2);
            }
        });
        if (oldSize > 0) {
            IRenderable oldItem;
            IRenderable newItem;
            for (int i = 0; i < oldSize; i++) {
                oldItem = oldData.get(i);
                boolean needRemove = true;
                for (int j = 0; j < newSize; j++) {
                    newItem = newData.get(j);
                    if (oldItem.areItemsTheSame(newItem)) {
                        needRemove = false;
                        break;
                    }
                }
                if (needRemove) {
                    itemsToRemove.push(oldItem);
                }
            }
        }
        oldData.beginBatchedUpdates();
        while (!itemsToRemove.empty()) {
            oldData.remove(itemsToRemove.pop());
        }
        IRenderable item;
        int oldIndex, newIndex;
        for (int i = 0; i < newSize; i++) {
            item = newData.get(i);
            newIndex = i;
            oldIndex = findSameItem(item);
            if (oldIndex > SortedList.INVALID_POSITION) {
                oldData.updateItemAt(oldIndex, item);
                if (oldIndex != newIndex) {
                    oldData.recalculatePositionOfItemAt(oldIndex);
                }
            } else {
                oldData.add(item);
            }
        }
        oldData.endBatchedUpdates();
    }

    private int findSameItem(IRenderable item) {
        for (int pos = 0, size = this.size(); pos < size; pos++) {
            if (this.get(pos).areItemsTheSame(item)) {
                return pos;
            }
        }
        return INVALID_POSITION;
    }


}
