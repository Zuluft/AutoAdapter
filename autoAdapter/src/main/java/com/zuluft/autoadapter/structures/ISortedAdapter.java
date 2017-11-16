package com.zuluft.autoadapter.structures;

import android.support.annotation.NonNull;

import com.zuluft.autoadapter.renderables.OrderableRenderer;

import java.util.List;

@SuppressWarnings("unused")
public interface ISortedAdapter {

    int compare(@NonNull OrderableRenderer item1,
                @NonNull OrderableRenderer item2);

    boolean areContentsTheSame(@NonNull OrderableRenderer item1,
                               @NonNull OrderableRenderer item2);

    boolean areItemsTheSame(@NonNull OrderableRenderer item1,
                            @NonNull OrderableRenderer item2);

    void beginUpdate();

    void commitUpdate();

    void updateAll(List<OrderableRenderer> list);

}
