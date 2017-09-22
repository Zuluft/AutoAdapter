package com.zuluft.giodz.autorendereradaptersample.renderers;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
public class TestRenderer {
        TestRendererViewHolder
}
