package com.zuluft.giodz.autorendereradaptersample.simpleSample.renderers;


import android.content.Context;
import android.widget.TextView;

import com.zuluft.autoadapter.renderables.Renderer;
import com.zuluft.autoadapterannotations.Render;
import com.zuluft.autoadapterannotations.ViewField;
import com.zuluft.generated.FootballerRendererViewHolder;
import com.zuluft.giodz.autorendereradaptersample.R;
import com.zuluft.giodz.autorendereradaptersample.models.FootballerModel;

@Render(layout = R.layout.item_footballer,
        views = {
                @ViewField(
                        id = R.id.tvName,
                        name = "tvName",
                        type = TextView.class
                ),
                @ViewField(
                        id = R.id.tvNumber,
                        name = "tvNumber",
                        type = TextView.class
                ),
                @ViewField(
                        id = R.id.tvClub,
                        name = "tvClub",
                        type = TextView.class
                )
        })
public class FootballerRenderer
        extends
        Renderer<FootballerRendererViewHolder> {

    public final FootballerModel footballerModel;

    public FootballerRenderer(final FootballerModel footballerModel) {
        this.footballerModel = footballerModel;
    }

    @Override
    public void apply(final FootballerRendererViewHolder vh) {
        final Context context = vh.getContext();
        vh.tvName.setText(footballerModel.getName());
        vh.tvClub.setText(footballerModel.getClub());
        vh.tvNumber.setText(context.getString(R.string.footballer_number_template,
                footballerModel.getNumber()));
    }
}
