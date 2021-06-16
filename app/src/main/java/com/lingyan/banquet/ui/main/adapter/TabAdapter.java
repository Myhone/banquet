package com.lingyan.banquet.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.main.bean.TabBean;
import com.lingyan.banquet.utils.MyImageUtils;

import java.util.List;

/**
 * Created by _hxb on 2021/1/3.
 */

public class TabAdapter extends BaseQuickAdapter<TabBean , BaseViewHolder> {
    public TabAdapter(@Nullable List<TabBean> data) {
        super(R.layout.item_main_home_tab,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TabBean item) {
        helper.setText(R.id.tv_text,item.getName());
        MyImageUtils.display(helper.getView(R.id.iv_image),item.getImageUrl());
    }
}
