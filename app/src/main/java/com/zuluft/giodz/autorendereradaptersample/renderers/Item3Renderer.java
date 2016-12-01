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

@Renderer(Item3Renderer.Item3ViewHolder.class)
public class Item3Renderer extends Renderable<Item3Renderer.Item3ViewHolder> {


    @Override
    public void apply(Item3ViewHolder viewHolder) {

    }

    @ViewHolder(R.layout.item_3)
    public static class Item3ViewHolder extends AutoViewHolder {

        public Item3ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
