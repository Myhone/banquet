package com.lingyan.banquet.views.dialog.adapter;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.views.dialog.bean.TwoLineSelectBean;

import java.util.List;

/**
 * Created by _hxb on 2021/1/14.
 */

public class RestaurantTitleAdapter extends BaseQuickAdapter<TwoLineSelectBean, BaseViewHolder> {
    public RestaurantTitleAdapter(@Nullable List<TwoLineSelectBean> data) {
        super(R.layout.item_restaurant_title,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, TwoLineSelectBean bean) {
        boolean selected = bean.isSelected();
        holder.setText(R.id.tv_title,bean.getTitle());
        if(selected){
            holder.setTextColor(R.id.tv_title, Color.parseColor("#C08348"));
            holder.setBackgroundColor(R.id.tv_title,Color.WHITE);
        }else {
            holder.setTextColor(R.id.tv_title, Color.parseColor("#333333"));
            holder.setBackgroundColor(R.id.tv_title,Color.parseColor("#F2F2F2"));
        }
    }
}
