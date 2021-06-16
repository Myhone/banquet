package com.lingyan.banquet.ui.follow.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.ui.follow.bean.FollowList;
import com.lingyan.banquet.ui.order.bean.NetBanquetOrderList;
import com.lingyan.banquet.views.ImageLayout;

import java.util.List;

/**
 * Created by _hxb on 2021/1/3.
 */

public class FollowListAdapter extends BaseQuickAdapter<FollowList.DataBean, BaseViewHolder> {
    public FollowListAdapter(@Nullable List<FollowList.DataBean> data) {
        super(R.layout.item_follow_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FollowList.DataBean item) {

        helper
                .setText(R.id.tv_follow_type, item.getType_name())
                .setText(R.id.tv_add_who, item.getCreate_user_name())
                .setText(R.id.tv_add_time, item.getCreate_time())
                .setText(R.id.tv_order_status, item.getBanquet_status_name())
                .setText(R.id.tv_evaluation, item.getContent())
                .setText(R.id.tv_notice_time, item.getNotice_time())
                .setText(R.id.tv_notice_content, item.getNotice_content());

        if (item.getIs_notice() == 1) {
            helper.getView(R.id.ll_notice_time).setVisibility(View.VISIBLE);
            helper.getView(R.id.ll_notice_content).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.ll_notice_time).setVisibility(View.GONE);
            helper.getView(R.id.ll_notice_content).setVisibility(View.GONE);
        }
        ImageLayout imageLayout = helper.getView(R.id.image_layout);
        imageLayout.setPreview(true);
        imageLayout.clear();
        List<String> imgUrl = item.getImg_url();
        if (ObjectUtils.isNotEmpty(imgUrl)) {
            for (String s : imgUrl) {
                imageLayout.add(HttpURLs.IMAGE_BASE + s);
            }
        }
    }
}
