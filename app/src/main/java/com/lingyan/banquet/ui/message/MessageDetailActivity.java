package com.lingyan.banquet.ui.message;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lingyan.banquet.App;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityMessageDetailBinding;
import com.lingyan.banquet.event.ReadMessageEvent;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.BanquetStepManagerActivity;
import com.lingyan.banquet.ui.celebration.CelStepManagerActivity;
import com.lingyan.banquet.ui.finance.FinanceManageActivity;
import com.lingyan.banquet.ui.main.bean.NetMessageDetail;
import com.lingyan.banquet.ui.order.OrderDetailActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by _hxb on 2021/1/11.
 */

public class MessageDetailActivity extends BaseActivity {

    private ActivityMessageDetailBinding mBinding;

    public static void start(String id) {
        Intent intent = new Intent(App.sApp, MessageDetailActivity.class);
        intent.putExtra("id", id);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        mBinding = ActivityMessageDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.llTitleBarRoot.setBackgroundColor(getResources().getColor(R.color.black2));
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("消息详情");
        mBinding.llTitleBarRoot.tvTitleBarTitle.setTextColor(Color.WHITE);
        mBinding.llTitleBarRoot.ivTitleBarLeft.setImageResource(R.mipmap.ic_back);
        mBinding.tvTitle.setText("");
        mBinding.tvContent.setText("");
        mBinding.tvCreateTime.setText("");
        mBinding.tvBottomAction.setVisibility(View.INVISIBLE);
        OkGo.<NetMessageDetail>post(HttpURLs.messageQueryInfo)
                .params("id", id)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new JsonCallback<NetMessageDetail>() {
                    @Override
                    public void onSuccess(Response<NetMessageDetail> response) {
                        NetMessageDetail body = response.body();
                        NetMessageDetail.DataDTO data = body.getData();
                        if (data == null) {
                            return;
                        }
                        EventBus.getDefault().post(new ReadMessageEvent(id));
                        String title = data.getTitle();
                        String createTime = data.getCreate_time();
                        String content = data.getContent();
                        String banquetId = data.getBanquet_id();
                        String banquetType = data.getBanquet_type();

                        mBinding.tvTitle.setText(title);
                        mBinding.tvCreateTime.setText(createTime);
                        mBinding.tvContent.setText(content);
                        if (title == null) {
                            title = "";
                        }
                        if (title.contains("财务") && !title.contains("已确认收款")) {
                            mBinding.tvBottomAction.setVisibility(View.VISIBLE);
                            mBinding.tvBottomAction.setText("查看财务详情");
                            String finalTitle = title;
                            mBinding.tvBottomAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (finalTitle.contains("尾款")) {
                                        FinanceManageActivity.start(2);
                                    } else {
                                        FinanceManageActivity.start(1);
                                    }
                                }
                            });

                        } else if (banquetId != null && banquetType != null) {
                            mBinding.tvBottomAction.setVisibility(View.VISIBLE);
                            mBinding.tvBottomAction.setText("查看订单详情");
                            mBinding.tvBottomAction.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    OrderDetailActivity.start(HttpURLs.banquetOrderInfo, banquetId, Integer.parseInt(banquetType));
                                }
                            });
                        }
                    }
                });
    }
}
