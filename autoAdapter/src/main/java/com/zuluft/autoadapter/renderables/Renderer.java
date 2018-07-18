package com.zuluft.autoadapter.renderables;

import android.support.annotation.NonNull;

import java.util.List;

@SuppressWarnings("unused")
public interface Renderer<T extends AutoViewHolder> {

    void apply(@NonNull T viewHolder,
               @NonNull List<Object> payload);

    Object getChangePayload(@NonNull Renderer renderer);

    boolean areItemsTheSame(@NonNull Renderer renderer);

    boolean areContentsTheSame(@NonNull Renderer renderer);

}
