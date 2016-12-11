package com.zuluft.giodz.autorendereradaptersample.renderers;

import android.view.View;
import android.widget.TextView;

import com.zuluft.autoadapter.annotations.Renderer;
import com.zuluft.autoadapter.annotations.ViewHolder;
import com.zuluft.autoadapter.renderables.AutoViewHolder;
import com.zuluft.autoadapter.renderables.IRenderable;
import com.zuluft.autoadapter.renderables.Renderable;
import com.zuluft.giodz.autorendereradaptersample.R;
import com.zuluft.giodz.autorendereradaptersample.models.FootballerModel;

/**
 * Created by user on 12/4/16.
 */

@Renderer(FootballerRenderer.FootballerViewHolder.class)
public class FootballerRenderer extends Renderable<FootballerRenderer.FootballerViewHolder> {

    public final FootballerModel mFootballerModel;

    public String getUsername() {
        return mFootballerModel.getName();
    }

    public FootballerRenderer(FootballerModel footballerModel) {
        this.mFootballerModel = footballerModel;
    }

    @Override
    public void apply(FootballerViewHolder viewHolder) {
        viewHolder.tvName.setText(mFootballerModel.getName());
    }

    @Override
    public boolean areItemsTheSame(IRenderable item) {
        return super.areItemsTheSame(item);
    }

    @Override
    public boolean areContentsTheSame(IRenderable item) {
        return super.areContentsTheSame(item);
    }

    @Override
    public int compareTo(IRenderable item) {
        FootballerModel otherFootballer = ((FootballerRenderer) item).mFootballerModel;
        return Integer.compare(mFootballerModel.getNumber(), otherFootballer.getNumber());
    }

    @ViewHolder(R.layout.item_footballer)
    public static class FootballerViewHolder extends AutoViewHolder {
        private TextView tvName;

        public FootballerViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) findViewById(R.id.tvName);
        }
    }
}
