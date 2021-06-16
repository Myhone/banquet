package com.lingyan.banquet.ui.finance.adapter;

import android.graphics.Color;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.finance.bean.NetDepositList;

import java.util.List;

/**
 * Created by _hxb on 2021/1/8.
 */

public class FrontBackMoneyAdapter extends BaseQuickAdapter<NetDepositList.DataDTO.ListDTO, BaseViewHolder> {
    public FrontBackMoneyAdapter(@Nullable List<NetDepositList.DataDTO.ListDTO> data) {
        super(R.layout.item_front_back_money, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, NetDepositList.DataDTO.ListDTO item) {
        String financeConfirmed = item.getFinance_confirmed();
        //状态  1-待确认  2-已确认  3-已退款
        String financeConfirmedDesc = "";
        String color = "#999999";
        if (StringUtils.equals("1", financeConfirmed)) {
            financeConfirmedDesc = "待确认";
            color = "#FF0000";
        } else if (StringUtils.equals("2", financeConfirmed)) {
            financeConfirmedDesc = "已确认";
            color = "#E3C18B";
        } else if (StringUtils.equals("3", financeConfirmed)) {
            financeConfirmedDesc = "已退款";
            color = "#999999";
        } else if (StringUtils.equals("4", financeConfirmed)) {
            financeConfirmedDesc = "已驳回";
            color = "#999999";
        }
        holder
                .setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_finance_confirmed, financeConfirmedDesc)
                .setText(R.id.tv_hall_list, item.getHall_list())
                .setText(R.id.tv_date_list, item.getDate_list())
                .setText(R.id.tv_money, "¥" + item.getMoney())
                .setText(R.id.tv_pay_user, item.getPay_user())
                .setText(R.id.tv_pay_time, item.getPay_time())
                .setTextColor(R.id.tv_finance_confirmed, Color.parseColor(color))
        ;

    }
}
