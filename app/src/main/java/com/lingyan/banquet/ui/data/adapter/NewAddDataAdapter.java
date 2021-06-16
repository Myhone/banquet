package com.lingyan.banquet.ui.data.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.data.bean.NetAddData;

import java.util.List;

/**
 * Created by _hxb on 2021/2/28.
 */

public class NewAddDataAdapter extends BaseQuickAdapter<NetAddData.DataDTO, BaseViewHolder> {
    public NewAddDataAdapter(@Nullable List<NetAddData.DataDTO> data) {
        super(R.layout.item_add_new_data, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, NetAddData.DataDTO dto) {
            holder
                    .setText(R.id.tv_title,dto.getNiche_name())
                    .setText(R.id.tv_hall_name,dto.getHall_list())
                    .setText(R.id.tv_time,dto.getDate_list())
                    .setText(R.id.tv_customer_name,dto.getReal_name())
                    .setText(R.id.tv_intent_man,dto.getIntent_man_name());
    }
}
