package com.zuluft.giodz.autorendereradaptersample.simpleSample;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zuluft.autoadapter.annotations.Renderer;
import com.zuluft.autoadapter.annotations.ViewHolder;
import com.zuluft.autoadapter.renderables.AutoViewHolder;
import com.zuluft.autoadapter.renderables.Renderable;
import com.zuluft.giodz.autorendereradaptersample.R;

/**
 * Created by user on 12/4/16.
 */

@Renderer(UserRenderer.UserViewHolder.class)
public class UserRenderer extends Renderable<UserRenderer.UserViewHolder> {

    private UserModel mUserModel;

    public UserRenderer(UserModel mUserModel) {
        this.mUserModel = mUserModel;
    }

    @Override
    public void apply(UserViewHolder viewHolder) {
        viewHolder.tvName.setText(mUserModel.getUsername());
        Glide.with(viewHolder.getContext()).load(mUserModel.getImageUrl()).into(viewHolder.ivImage);
    }

    @ViewHolder(R.layout.item_user)
    public static class UserViewHolder extends AutoViewHolder {
        private TextView tvName;
        private ImageView ivImage;

        public UserViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) findViewById(R.id.tvName);
            ivImage = (ImageView) findViewById(R.id.ivImage);

        }
    }
}
