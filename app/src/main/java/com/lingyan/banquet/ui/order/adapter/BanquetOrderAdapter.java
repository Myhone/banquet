package com.lingyan.banquet.ui.order.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.order.bean.NetBanquetOrderList;

import java.util.List;

/**
 * Created by _hxb on 2021/1/3.
 */

public class BanquetOrderAdapter extends BaseQuickAdapter<NetBanquetOrderList.DataDTO.ListDTO, BaseViewHolder> {
    public BanquetOrderAdapter(@Nullable List<NetBanquetOrderList.DataDTO.ListDTO> data) {
        super(R.layout.item_banquet_order,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NetBanquetOrderList.DataDTO.ListDTO item) {

        helper
                .setText(R.id.tv_title,item.getNiche_name())
                .setText(R.id.tv_step,item.getStatus_name())
                .setText(R.id.tv_customer_name,item.getReal_name())
                .setText(R.id.tv_hall_name,item.getHall_list())
                .setText(R.id.tv_time,item.getDate_list())
                .setText(R.id.tv_intent_man,item.getIntent_man_name())//跟单人
                .setText(R.id.tv_niche_source,item.getNiche_source_name())//介绍人

                ;
    }
}
