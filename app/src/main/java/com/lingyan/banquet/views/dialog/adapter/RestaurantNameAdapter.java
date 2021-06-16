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

public class RestaurantNameAdapter extends BaseQuickAdapter<TwoLineSelectBean, BaseViewHolder> {
    public RestaurantNameAdapter(@Nullable List<TwoLineSelectBean> data) {
        super(R.layout.item_restaurant_name,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, TwoLineSelectBean bean) {
        boolean selected = bean.isSelected();
        holder.setText(R.id.tv_name,bean.getTitle());
        if(selected){
            holder.setTextColor(R.id.tv_name, Color.parseColor("#DFBB83"));
            holder.setBackgroundRes(R.id.tv_name,R.drawable.shape_rect_light_gold_stroke_dark_gray_corners_90);
        }else {
            holder.setTextColor(R.id.tv_name, Color.parseColor("#666666"));
            holder.setBackgroundRes(R.id.tv_name,R.drawable.shape_rect_gray_corners_90);
        }
    }
}
