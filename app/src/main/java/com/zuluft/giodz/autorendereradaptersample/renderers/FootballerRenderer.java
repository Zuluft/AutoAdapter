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

@Renderer(FootballerRenderer.UserViewHolder.class)
public class FootballerRenderer extends Renderable<FootballerRenderer.UserViewHolder> {

    public final FootballerModel mFootballerModel;

    public String getUsername() {
        return mFootballerModel.getName();
    }

    public FootballerRenderer(FootballerModel mFootballerModel) {
        this.mFootballerModel = mFootballerModel;
    }

    @Override
    public void apply(UserViewHolder viewHolder) {
        viewHolder.tvName.setText(mFootballerModel.getName());
    }

    @ViewHolder(R.layout.item_user)
    public static class UserViewHolder extends AutoViewHolder {
        private TextView tvName;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) findViewById(R.id.tvName);
        }
    }

    @Override
    public <T1 extends IRenderable> int compareTo(T1 item) {
        FootballerRenderer footballerRenderer = (FootballerRenderer) item;
        return Integer.compare(mFootballerModel.getNumber(), footballerRenderer.mFootballerModel.getNumber());
    }

    @Override
    public <T1 extends IRenderable> boolean areItemsTheSame(T1 item) {
        FootballerRenderer footballerRenderer = (FootballerRenderer) item;
        return footballerRenderer.mFootballerModel.getNumber() == mFootballerModel.getNumber();
    }
}
