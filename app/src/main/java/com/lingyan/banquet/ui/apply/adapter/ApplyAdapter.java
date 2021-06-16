package com.lingyan.banquet.ui.apply.adapter;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.apply.bean.NetExamineIndex;

import java.util.List;

/**
 * Created by _hxb on 2021/1/4.
 */

public class ApplyAdapter extends BaseQuickAdapter<NetExamineIndex.DataDTO.ListDTO, BaseViewHolder> {
    public ApplyAdapter(@Nullable List<NetExamineIndex.DataDTO.ListDTO> data) {
        super(R.layout.item_apply, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, NetExamineIndex.DataDTO.ListDTO dto) {

        //0-审批中 1-审批通过 2-审批驳回 3-撤销
        baseViewHolder
                .setText(R.id.tv_title, dto.getTitle())
                .setText(R.id.tv_check_status, dto.getCheck_status_name())
                .setText(R.id.tv_create_time, dto.getCreate_time())
                .setText(R.id.tv_create_name, dto.getCreate_name());
        String checkStatus = dto.getCheck_status();
        if(StringUtils.equals("0",checkStatus)){
            baseViewHolder.setTextColor(R.id.tv_check_status, Color.parseColor("#E0C488"));
        }else if(StringUtils.equals("1",checkStatus)){
            baseViewHolder.setTextColor(R.id.tv_check_status, Color.parseColor("#E0C488"));
        }else if(StringUtils.equals("2",checkStatus)){
            baseViewHolder.setTextColor(R.id.tv_check_status, Color.parseColor("#EB4F47"));
        }else if(StringUtils.equals("3",checkStatus)){
            baseViewHolder.setTextColor(R.id.tv_check_status, Color.parseColor("#999999"));
        }
    }
}
