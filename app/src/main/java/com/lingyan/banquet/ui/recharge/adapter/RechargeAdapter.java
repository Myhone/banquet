package com.lingyan.banquet.ui.recharge.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.recharge.bean.NetRecharge;

import java.util.List;

/**
 * Created by _hxb on 2021/1/11.
 */

public class RechargeAdapter extends BaseQuickAdapter<NetRecharge.DataDTO.ListDTO, BaseViewHolder> {
    public RechargeAdapter(@Nullable List<NetRecharge.DataDTO.ListDTO> data) {
        super(R.layout.item_recharge, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, NetRecharge.DataDTO.ListDTO dto) {
        baseViewHolder
                .setText(R.id.tv_money, dto.getMoney())
                .setText(R.id.tv_expire_time, "充值时长：" + dto.getExpire_time())
                .setText(R.id.tv_create_time, dto.getCreate_time())
                .setText(R.id.tv_remarks, "备注：" + dto.getRemarks());
    }
}
