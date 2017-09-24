package com.zuluft.giodz.autorendereradaptersample.renderers;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zuluft.autoadapter.renderables.AutoViewHolder;
import com.zuluft.autoadapter.renderables.IRenderer;
import com.zuluft.autoadapter.renderables.OrderableRenderer;
import com.zuluft.autoadapterannotations.Render;
import com.zuluft.autoadapterannotations.ViewField;
import com.zuluft.generated.TestRendererViewHolder;
import com.zuluft.giodz.autorendereradaptersample.R;

/**
 * Created by giodz on 9/20/2017.
 */
@Render(
        layout = R.layout.item_footballer,
        views = {
                @ViewField(id = R.id.tvName, name = "tvName", type = TextView.class),
                @ViewField(id = R.id.ivDelete, name = "ivDelete", type = ImageView.class),
        }
)
public class TestRenderer extends OrderableRenderer<TestRendererViewHolder> {


    @Override
    public int compareTo(@NonNull OrderableRenderer item) {
        return 0;
    }

    @Override
    public boolean areContentsTheSame(@NonNull OrderableRenderer item) {
        return false;
    }

    @Override
    public boolean areItemsTheSame(@NonNull OrderableRenderer item) {
        return false;
    }

    @Override
    public void apply(TestRendererViewHolder viewHolder) {

    }
}
