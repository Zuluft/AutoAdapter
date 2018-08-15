package com.zuluft.autoadapter.structures;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;
import android.support.v7.widget.util.SortedListAdapterCallback;

import com.zuluft.autoadapter.SortedAutoAdapter;
import com.zuluft.autoadapter.renderables.OrderableRenderer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;


public class SortedAdapterDataStructure extends SortedList<OrderableRenderer> {


    public SortedAdapterDataStructure(@NonNull final SortedAutoAdapter autoAdapter) {
        super(OrderableRenderer.class, new SortedListAdapterCallback<OrderableRenderer>(autoAdapter) {
            @Override
            public int compare(OrderableRenderer o1, OrderableRenderer o2) {
                return autoAdapter.compare(o1, o2);
            }

            @Override
            public boolean areContentsTheSame(OrderableRenderer oldItem, OrderableRenderer newItem) {
                return autoAdapter.areContentsTheSame(oldItem, newItem);
            }

            @Override
            public boolean areItemsTheSame(OrderableRenderer item1, OrderableRenderer item2) {
                return autoAdapter.areItemsTheSame(item1, item2);
            }
        });
    }

    public void updateAll(List<OrderableRenderer> list) {
        Stack<OrderableRenderer> itemsToRemove = new Stack<>();
        SortedAdapterDataStructure oldData = this;
        int oldSize = this.size();
        int newSize = list.size();
        ArrayList<OrderableRenderer> newData = new ArrayList<>(list);
        Collections.sort(newData, new Comparator<OrderableRenderer>() {
            @Override
            public int compare(OrderableRenderer o1, OrderableRenderer o2) {
                return o1.compareTo(o2);
            }
        });
        if (oldSize > 0) {
            OrderableRenderer oldItem;
            OrderableRenderer newItem;
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
        OrderableRenderer item;
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

    private int findSameItem(OrderableRenderer item) {
        for (int pos = 0, size = this.size(); pos < size; pos++) {
            if (this.get(pos).areItemsTheSame(item)) {
                return pos;
            }
        }
        return INVALID_POSITION;
    }

}
