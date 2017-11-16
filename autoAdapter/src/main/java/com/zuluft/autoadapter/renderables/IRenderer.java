package com.zuluft.autoadapter.renderables;

public interface IRenderer<T extends AutoViewHolder> {

    void apply(T viewHolder);

}
