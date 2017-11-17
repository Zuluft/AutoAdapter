package com.zuluft.giodz.autorendereradaptersample.sortedAutoAdapterSample.renderers;


import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.zuluft.autoadapter.renderables.OrderableRenderer;
import com.zuluft.autoadapterannotations.Render;
import com.zuluft.autoadapterannotations.ViewField;
import com.zuluft.generated.FootballerOrderableRendererViewHolder;
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
public class FootballerOrderableRenderer
        extends
        OrderableRenderer<FootballerOrderableRendererViewHolder> {

    public final FootballerModel footballerModel;

    public FootballerOrderableRenderer(final FootballerModel footballerModel) {
        this.footballerModel = footballerModel;
    }

    @Override
    public void apply(final FootballerOrderableRendererViewHolder vh) {
        final Context context = vh.getContext();
        vh.tvName.setText(footballerModel.getName());
        vh.tvClub.setText(footballerModel.getClub());
        vh.tvNumber.setText(context.getString(R.string.footballer_number_template,
                footballerModel.getNumber()));
    }

    @Override
    public int compareTo(@NonNull OrderableRenderer item) {
        return Integer.valueOf(footballerModel.getNumber())
                .compareTo(getFootballerModel(item).getNumber());
    }

    private FootballerModel getFootballerModel(@NonNull final OrderableRenderer orderableRenderer) {
        return ((FootballerOrderableRenderer) orderableRenderer).footballerModel;
    }

    @Override
    public boolean areContentsTheSame(@NonNull OrderableRenderer item) {
        final FootballerModel otherFootballer = getFootballerModel(item);
        return footballerModel.getClub().equals(otherFootballer.getClub())
                && footballerModel.getNumber() == otherFootballer.getNumber();
    }

    @Override
    public boolean areItemsTheSame(@NonNull OrderableRenderer item) {
        return footballerModel.getName().equals(getFootballerModel(item).getName());
    }
}
