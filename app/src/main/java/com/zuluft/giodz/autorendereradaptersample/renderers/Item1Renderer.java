package com.zuluft.giodz.autorendereradaptersample.renderers;

import android.view.View;

import com.zuluft.autoadapter.annotations.Renderer;
import com.zuluft.autoadapter.annotations.ViewHolder;
import com.zuluft.autoadapter.renderables.Renderable;
import com.zuluft.autoadapter.renderables.AutoViewHolder;
import com.zuluft.giodz.autorendereradaptersample.R;

/**
 * Created by giodz on 11/30/2016.
 */

@Renderer(Item1Renderer.Item1ViewHolder.class)
public class Item1Renderer extends Renderable<Item1Renderer.Item1ViewHolder> {

    @Override
    public void apply(Item1ViewHolder viewHolder) {

    }

    @ViewHolder(R.layout.item_1)
    public static class Item1ViewHolder extends AutoViewHolder {
        public Item1ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
