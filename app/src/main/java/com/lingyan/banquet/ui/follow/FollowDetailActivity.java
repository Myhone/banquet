package com.lingyan.banquet.ui.follow;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.lingyan.banquet.App;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityFollowDetailBinding;
import com.lingyan.banquet.event.ApplyRecordEvent;
import com.lingyan.banquet.event.FollowRefreshEvent;
import com.lingyan.banquet.event.OrderDetailRefreshEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.follow.adapter.FollowListAdapter;
import com.lingyan.banquet.ui.follow.bean.FollowList;
import com.lingyan.banquet.ui.order.adapter.BanquetOrderAdapter;
import com.lingyan.banquet.ui.order.bean.NetBanquetOrderList;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyq on 2021/6/9.
 */
public class FollowDetailActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private ActivityFollowDetailBinding mBinding;
    private FollowListAdapter mAdapter;
    private List<FollowList.DataBean> mRecData;
    private String banquet_id;
    private int mCurPage;
    private String name;

    public static void start(String banquet_id, String name) {
        Intent intent = new Intent(App.sApp, FollowDetailActivity.class);
        intent.putExtra("banquet_id", banquet_id);
        intent.putExtra("name", name);
        ActivityUtils.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mBinding = ActivityFollowDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("跟进详情");
        mBinding.llTitleBarRoot.tvTitleBarRight.setText("增加跟进");
        mBinding.llTitleBarRoot.tvTitleBarRight.setTextColor(getResources().getColor(R.color.gold));
        Intent intent = getIntent();
        banquet_id = intent.getStringExtra("banquet_id");
        name = intent.getStringExtra("name");
        mBinding.refreshLayout.setOnRefreshListener(this);

        mBinding.rvFollow.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvFollow.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(10);
            }
        });
        mRecData = new ArrayList<>();
        mAdapter = new FollowListAdapter(mRecData);
        mBinding.rvFollow.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mBinding.refreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mBinding.rvFollow);
        requestNet(mCurPage);

        mBinding.llTitleBarRoot.tvTitleBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFollowActivity.start(banquet_id, name);
            }
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurPage = 1;
        requestNet(mCurPage);
    }


    private void requestNet(int page) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("banquet_id", banquet_id);
        jsonObject.addProperty("limit", 10);
        jsonObject.addProperty("page", page);
        OkGo.<FollowList>post(HttpURLs.followList)
                .upJson(jsonObject.toString())
                .execute(new JsonCallback<FollowList>() {
                    @Override
                    public void onSuccess(Response<FollowList> response) {
                        FollowList body = response.body();
                        if (page == 1) {
                            mRecData.clear();
                            mAdapter.setNewData(mRecData);
                        }

                        if (body == null) {
                            mAdapter.loadMoreEnd();
                            return;
                        }

                        List<FollowList.DataBean> list = body.getData();
                        if (ObjectUtils.isNotEmpty(list)) {
                            mAdapter.addData(list);
                            mCurPage = page;
                            if (list.size() >= 10) {
                                mAdapter.loadMoreComplete();
                            } else {
                                mAdapter.loadMoreEnd();
                            }
                        } else {
                            mAdapter.loadMoreEnd();
                        }


                    }

                    @Override
                    public void onError(Response<FollowList> response) {
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void change(FollowRefreshEvent event) {
        onRefresh(mBinding.refreshLayout);
        EventBus.getDefault().post(new OrderDetailRefreshEvent());
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

    }

    @Override
    public void onLoadMoreRequested() {
        int page = mCurPage;
        page++;
        requestNet(page);
    }
}
