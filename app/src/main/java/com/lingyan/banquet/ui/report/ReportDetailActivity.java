package com.lingyan.banquet.ui.report;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.lingyan.banquet.App;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityReportDetailBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.report.bean.NetReportDetail;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * Created by _hxb on 2021/1/14.
 */

public class ReportDetailActivity extends BaseActivity {

    private ActivityReportDetailBinding mBinding;

    public static void start(String id) {
        Intent intent = new Intent(App.sApp, ReportDetailActivity.class);
        intent.putExtra("id", id);

        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        mBinding = ActivityReportDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("反馈记录详情");
        mBinding.tvContent.setText("");
        mBinding.imageLayout.clear();
        mBinding.tvCreateTime.setText("");
        mBinding.tvReply.setText("");
        mBinding.tvReplyTime.setText("");
        mBinding.imageLayout.setPreview(true);

        OkGo.<NetReportDetail>post(HttpURLs.feedInfo)
                .params("id", id)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new JsonCallback<NetReportDetail>() {
                    @Override
                    public void onSuccess(Response<NetReportDetail> response) {
                        NetReportDetail body = response.body();
                        NetReportDetail.DataDTO data = body.getData();
                        if (data == null) {
                            return;
                        }
                        mBinding.tvContent.setText(data.getContent());
                        mBinding.imageLayout.clear();
                        List<String> imgUrl = data.getImg_url();
                        if (ObjectUtils.isNotEmpty(imgUrl)) {
                            for (String s : imgUrl) {
                                mBinding.imageLayout.add(HttpURLs.IMAGE_BASE + s);
                            }
                        }
                        mBinding.tvCreateTime.setText(data.getCreate_time());
                        mBinding.tvReply.setText(data.getReply());
                        mBinding.tvReplyTime.setText(data.getReply_time());

                    }
                });
    }
}
