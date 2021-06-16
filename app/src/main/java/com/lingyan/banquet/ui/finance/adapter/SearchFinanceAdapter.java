package com.lingyan.banquet.ui.finance.adapter;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.finance.bean.NetDepositList;
import com.lingyan.banquet.ui.finance.bean.NetSearchHall;

import java.util.List;

/**
 * Created by _hxb on 2021/2/16.
 */

public class SearchFinanceAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public SearchFinanceAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(1, R.layout.layout_text_view);
        addItemType(2, R.layout.item_front_back_money);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, MultiItemEntity multiItemEntity) {
        int itemViewType = holder.getItemViewType();
        switch (itemViewType) {
            case 1: {
                NetSearchHall.DataDTO dto = (NetSearchHall.DataDTO) multiItemEntity;
                holder.setText(R.id.tv_text, dto.getName());
                break;
            }
            case 2: {
                NetDepositList.DataDTO.ListDTO item = (NetDepositList.DataDTO.ListDTO) multiItemEntity;
                String financeConfirmed = item.getFinance_confirmed();
                //状态  1-待确认  2-已确认  3-已退款
                String financeConfirmedDesc = "";
                if (StringUtils.equals("1", financeConfirmed)) {
                    financeConfirmedDesc = "待确认";
                } else if (StringUtils.equals("2", financeConfirmed)) {
                    financeConfirmedDesc = "已确认";
                } else if (StringUtils.equals("3", financeConfirmed)) {
                    financeConfirmedDesc = "已退款";
                }
                holder
                        .setText(R.id.tv_title, item.getTitle())
                        .setText(R.id.tv_finance_confirmed, financeConfirmedDesc)
                        .setText(R.id.tv_hall_list, item.getHall_list())
                        .setText(R.id.tv_date_list, item.getDate_list())
                        .setText(R.id.tv_money, "¥" + item.getMoney())
                ;
                break;
            }
        }
    }
}
