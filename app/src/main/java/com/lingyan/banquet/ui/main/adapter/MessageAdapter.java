package com.lingyan.banquet.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.main.bean.NetMessageList;

import java.util.List;

/**
 * Created by _hxb on 2021/1/10.
 */

public class MessageAdapter extends BaseQuickAdapter<NetMessageList.DataDTO, BaseViewHolder> {
    public MessageAdapter(@Nullable List<NetMessageList.DataDTO> data) {
        super(R.layout.item_message, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NetMessageList.DataDTO item) {
        helper.setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_time, item.getTime())
                .setText(R.id.tv_content, item.getContent())
                .setVisible(R.id.view_read, StringUtils.equals("0", item.getIs_read()));
    }
}
