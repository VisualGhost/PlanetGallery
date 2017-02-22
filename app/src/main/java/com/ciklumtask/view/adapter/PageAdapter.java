package com.ciklumtask.view.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ciklumtask.R;
import com.ciklumtask.networking.Item;
import com.ciklumtask.view.viewholder.ItemViewHolder;

import java.util.List;

public class PageAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<Item> mItemList;

    public void setItemList(List<Item> itemList) {
        mItemList = itemList;
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,
                parent,
                false));
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindModel(mItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mItemList != null ? mItemList.size() : 0;
    }
}
