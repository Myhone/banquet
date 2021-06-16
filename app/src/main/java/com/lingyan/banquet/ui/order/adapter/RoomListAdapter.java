package com.lingyan.banquet.ui.order.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetType;
import com.lingyan.banquet.ui.order.bean.NetOrderDetail;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * Created by _hxb on 2021/1/3.
 */

public class RoomListAdapter extends BaseQuickAdapter<NetOrderDetail.DataDTO.NumberListDTO, BaseViewHolder> {

    private int type;//宴会/庆典

    public RoomListAdapter(@Nullable List<NetOrderDetail.DataDTO.NumberListDTO> data, int type) {
        super(R.layout.item_order_detail_room, data);
        this.type = type;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, NetOrderDetail.DataDTO.NumberListDTO item) {

        helper
                .setText(R.id.tv_number_info_count, item.getSort())
                .setText(R.id.tv_number_info_date, item.getDate_name())
                .setText(R.id.tv_number_list, item.getHall_name())
                .setText(R.id.tv_table_number, item.getTable_number())
                .setText(R.id.tv_prepare_number, item.getPrepare_number());

        //是否显示修改包间按钮
        if (item.getIs_modify_hall() == 1) {
            helper.getView(R.id.tv_change_room).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.tv_change_room).setVisibility(View.INVISIBLE);
        }

        //宴会显示桌数，庆典不显示
        if (type == BanquetCelebrationType.TYPE_BANQUET) {
            helper.getView(R.id.ll_table).setVisibility(View.VISIBLE);
        } else {
            helper.getView(R.id.ll_table).setVisibility(View.GONE);
        }
        //修改包间添加点击事件
        helper.addOnClickListener(R.id.tv_change_room);
    }
}
