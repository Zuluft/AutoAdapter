package com.zuluft.giodz.autorendereradaptersample.simpleSample.renderers;


import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.zuluft.autoadapter.renderables.Renderer;
import com.zuluft.autoadapterannotations.Render;
import com.zuluft.autoadapterannotations.ViewField;
import com.zuluft.generated.FootballerRendererViewHolder;
import com.zuluft.giodz.autorendereradaptersample.R;
import com.zuluft.giodz.autorendereradaptersample.models.FootballerModel;

import java.util.List;

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
        implements
        Renderer<FootballerRendererViewHolder> {

    public final FootballerModel footballerModel;

    public FootballerRenderer(final FootballerModel footballerModel) {
        this.footballerModel = footballerModel;
    }

    @Override
    public void apply(@NonNull final FootballerRendererViewHolder vh,
                      @NonNull final List<Object> payloads) {
        final Context context = vh.getContext();
        vh.tvName.setText(footballerModel.getName());
        vh.tvClub.setText(footballerModel.getClub());

        if (payloads.isEmpty()) {
            String numText = context.getString(R.string.footballer_number_template,
                    footballerModel.getNumber());
            vh.tvNumber.setText(numText);
        } else {
            Bundle bundle = (Bundle) payloads.get(0);
            int oldNumber = bundle.getInt("number");

            ValueAnimator valueAnimator = ValueAnimator.ofInt(oldNumber,
                    footballerModel.getNumber());
            valueAnimator.setDuration(2000);
            valueAnimator.addUpdateListener(animation -> {
                String numText = context.getString(R.string.footballer_number_template,
                        (int) animation.getAnimatedValue());
                vh.tvNumber.setText(numText);
            });
            valueAnimator.start();
        }
    }

    @Override
    public Object getChangePayload(@NonNull Renderer renderer) {
        Bundle bundle = new Bundle();
        bundle.putInt("number", ((FootballerRenderer) renderer).footballerModel.getNumber());
        return bundle;
    }

    @Override
    public boolean areItemsTheSame(@NonNull Renderer renderer) {
        return ((FootballerRenderer) renderer).footballerModel.getName()
                .equals(footballerModel.getName());
    }

    @Override
    public boolean areContentsTheSame(@NonNull Renderer renderer) {
        return ((FootballerRenderer) renderer).footballerModel.getNumber() ==
                footballerModel.getNumber();
    }
}
