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

@Renderer(FootballerRenderer.FootballerViewHolder.class)
public class FootballerRenderer extends Renderable<FootballerRenderer.FootballerViewHolder> {

    public final FootballerModel footballerModel;

    public String getUsername() {
        return footballerModel.name;
    }

    public FootballerRenderer(FootballerModel footballerModel) {
        this.footballerModel = footballerModel;
    }

    @Override
    public void apply(FootballerViewHolder viewHolder) {
        viewHolder.tvName.setText(footballerModel.name);
    }

    @Override
    public int compareTo(IRenderable item) {
        FootballerModel otherFootballer = ((FootballerRenderer) item).footballerModel;
        return Integer.compare(footballerModel.number, otherFootballer.number);
    }

    @ViewHolder(R.layout.item_footballer)
    public static class FootballerViewHolder extends AutoViewHolder {
        private final TextView tvName;

        public FootballerViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) findViewById(R.id.tvName);
        }
    }
}
