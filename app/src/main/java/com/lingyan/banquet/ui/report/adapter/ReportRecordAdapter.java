package com.lingyan.banquet.ui.report.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.report.bean.NetReportList;

import java.util.List;

/**
 * Created by _hxb on 2021/1/14.
 */

public class ReportRecordAdapter extends BaseQuickAdapter<NetReportList.DataDTO, BaseViewHolder> {
    public ReportRecordAdapter(@Nullable List<NetReportList.DataDTO> data) {
        super(R.layout.item_report_record, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, NetReportList.DataDTO item) {
        String reply = item.getReply();
        if(StringUtils.isTrimEmpty(reply)){
            reply="暂无回复";
        }
        baseViewHolder.setText(R.id.tv_content, item.getContent())
                .setText(R.id.tv_reply, reply)
                .setText(R.id.tv_create_time, item.getCreate_time());
    }
}
