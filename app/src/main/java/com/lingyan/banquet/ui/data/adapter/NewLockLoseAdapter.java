package com.lingyan.banquet.ui.data.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.data.bean.NetAddData;
import com.lingyan.banquet.ui.data.bean.NetLockLoseData;

import java.util.List;

/**
 * Created by _hxb on 2021/2/28.
 */

public class NewLockLoseAdapter extends BaseQuickAdapter<NetLockLoseData.DataDTO.ListDTO, BaseViewHolder> {
    public NewLockLoseAdapter(@Nullable List<NetLockLoseData.DataDTO.ListDTO> data) {
        super(R.layout.item_add_new_data, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, NetLockLoseData.DataDTO.ListDTO dto) {
            holder
                    .setText(R.id.tv_title,dto.getNiche_name())
                    .setText(R.id.tv_hall_name,dto.getHall_str())
                    .setText(R.id.tv_time,dto.getDate_str())
                    .setText(R.id.tv_customer_name,dto.getReal_name())
                    .setText(R.id.tv_intent_man,dto.getIntent_man_name());
    }
}
