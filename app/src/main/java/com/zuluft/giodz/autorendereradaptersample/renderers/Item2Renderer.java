package com.zuluft.giodz.autorendereradaptersample.renderers;

import android.view.View;

import com.zuluft.autoadapter.annotations.Renderer;
import com.zuluft.autoadapter.annotations.ViewHolder;
import com.zuluft.autoadapter.renderables.AutoViewHolder;
import com.zuluft.autoadapter.renderables.Renderable;
import com.zuluft.giodz.autorendereradaptersample.R;

/**
 * Created by giodz on 12/1/2016.
 */
@Renderer(Item2Renderer.Item2ViewHolder.class)
public class Item2Renderer extends Renderable<Item2Renderer.Item2ViewHolder> {

    @Override
    public void apply(Item2ViewHolder viewHolder) {

    }

    @ViewHolder(R.layout.item_2)
    public static class Item2ViewHolder extends AutoViewHolder {
        public Item2ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
