package com.lingyan.banquet.ui.recharge;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityRechargeBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.apply.bean.NetExamineIndex;
import com.lingyan.banquet.ui.recharge.adapter.RechargeAdapter;
import com.lingyan.banquet.ui.recharge.bean.NetRecharge;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/1/11.
 */

public class RechargeActivity extends BaseActivity implements OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private ActivityRechargeBinding mBinding;
    private List<NetRecharge.DataDTO.ListDTO> mRecData;
    private RechargeAdapter mAdapter;
    private int mCurPage;

    public static void start() {
        ActivityUtils.startActivity(RechargeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityRechargeBinding.inflate(getLayoutInflater());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("充值");
        mBinding.llTitleBarRoot.tvTitleBarTitle.setTextColor(Color.WHITE);
        mBinding.llTitleBarRoot.ivTitleBarLeft.setImageResource(R.mipmap.ic_back);
        setContentView(mBinding.getRoot());
        View.OnClickListener telListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof TextView) {
                    TextView tv = (TextView) v;
                    PhoneUtils.dial(tv.getText().toString());
                }
            }
        };
        ;
        mBinding.tvKefu.setOnClickListener(telListener);
        mBinding.tvXiashou.setOnClickListener(telListener);

        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecData = new ArrayList<>();
        mAdapter = new RechargeAdapter(mRecData);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.refreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mBinding.recyclerView);
        onRefresh(mBinding.refreshLayout);
    }

    private void refresh(int page) {
        OkGo.<NetRecharge>post(HttpURLs.userRecharge)
                .params("page", page)
                .execute(new JsonCallback<NetRecharge>() {
                    @Override
                    public void onSuccess(Response<NetRecharge> response) {
                        NetRecharge body = response.body();
                        NetRecharge.DataDTO data = body.getData();
                        if (data == null) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        NetRecharge.DataDTO.TelDTO tel = data.getTel();
                        if (tel != null) {
                            mBinding.tvKefu.setText(tel.getKefu());
                            mBinding.tvXiashou.setText(tel.getXiashou());
                        }

                        List<NetRecharge.DataDTO.ListDTO> list = data.getList();
                        if (ObjectUtils.isEmpty(list)) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        if (page == 1) {
                            mRecData.clear();
                            mAdapter.setNewData(mRecData);
                        }
                        mCurPage = page;
                        mAdapter.addData(list);
                        mAdapter.loadMoreComplete();
                    }

                    @Override
                    public void onError(Response<NetRecharge> response) {
                        super.onError(response);
                        mAdapter.loadMoreFail();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mBinding.refreshLayout.finishRefresh();
                    }
                });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh(1);
    }

    @Override
    public void onLoadMoreRequested() {
        int page = mCurPage;
        page++;
        refresh(page);
    }
}
