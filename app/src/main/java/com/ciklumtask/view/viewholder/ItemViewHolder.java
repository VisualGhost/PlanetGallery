package com.ciklumtask.view.viewholder;


import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ciklumtask.R;
import com.ciklumtask.networking.Item;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView mPhotoView;
    private TextView mNameTextView;
    private TextView mDescriptionTextView;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mPhotoView = (ImageView) itemView.findViewById(R.id.photo_id);
        mNameTextView = (TextView) itemView.findViewById(R.id.name_id);
        mDescriptionTextView = (TextView) itemView.findViewById(R.id.description_id);
    }

    public void bindModel(Item item) {
        mNameTextView.setText(item.getName());
        mDescriptionTextView.setText(item.getDescription());

        String photoUrl = item.getImage_url();

        if (!TextUtils.isEmpty(photoUrl)) {
            Glide
                    .with(mPhotoView.getContext().getApplicationContext())
                    .load(photoUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_insert_photo_black_24dp)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(mPhotoView);
        }
    }
}
