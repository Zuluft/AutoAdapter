package com.zuluft.autoadapter.renderables;

import android.support.annotation.NonNull;

/**
 * Created by giodz on 9/23/2017.
 */

public abstract class OrderableRenderer<T extends AutoViewHolder>
        implements
        IRenderer<T> {

    public final int compareOrderIds(@NonNull OrderableRenderer item) {
        int result = Integer.valueOf(getOrderId()).compareTo(item.getOrderId());
        if (result == 0) {
            return compareTo(item);
        }
        return result;
    }

    public final boolean hasSameOrderIds(@NonNull OrderableRenderer item) {
        int result = Integer.valueOf(getOrderId()).compareTo(item.getOrderId());
        if (result == 0) {
            return areItemsTheSame(item);
        }
        return false;
    }

    public abstract int compareTo(@NonNull OrderableRenderer item);

    public abstract boolean areContentsTheSame(@NonNull OrderableRenderer item);

    public abstract boolean areItemsTheSame(@NonNull OrderableRenderer item);

    public int getOrderId() {
        return -1;
    }
}
