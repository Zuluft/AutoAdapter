package com.zuluft.autoadapter.structures;

import com.zuluft.autoadapter.renderables.IRenderer;
import com.zuluft.autoadapter.renderables.OrderableRenderer;

import java.util.List;

/**
 * Created by giodz on 9/23/2017.
 */

public interface ISortedAdapter {

    int compare(OrderableRenderer item1, OrderableRenderer item2);

    boolean areContentsTheSame(OrderableRenderer item1, OrderableRenderer item2);

    boolean areItemsTheSame(OrderableRenderer item1, OrderableRenderer item2);

    void beginUpdate();

    void commitUpdate();

    void updateAll(List<OrderableRenderer> list);

}
