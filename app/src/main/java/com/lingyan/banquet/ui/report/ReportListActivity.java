package com.lingyan.banquet.ui.report;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityReportListBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.report.adapter.ReportRecordAdapter;
import com.lingyan.banquet.ui.report.bean.NetReportList;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/1/14.
 */

public class ReportListActivity extends BaseActivity implements OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private ActivityReportListBinding mBinding;
    private List<NetReportList.DataDTO> mRecData;
    private ReportRecordAdapter mAdapter;
    private int mCurPage;

    public static void start() {
        ActivityUtils.startActivity(ReportListActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityReportListBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("反馈记录");
        mRecData = new ArrayList<>();
        mAdapter = new ReportRecordAdapter(mRecData);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = ConvertUtils.dp2px(15);
                outRect.right = ConvertUtils.dp2px(15);
                outRect.top = ConvertUtils.dp2px(15);
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                ReportDetailActivity.start(mRecData.get(i).getId());
            }
        });
        mBinding.refreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mBinding.recyclerView);
        onRefresh(mBinding.refreshLayout);
    }

    private void refresh(int page) {
        OkGo.<NetReportList>post(HttpURLs.feedBack)
                .params("page", page)
                .params("limit", 10)
                .execute(new JsonCallback<NetReportList>() {
                    @Override
                    public void onSuccess(Response<NetReportList> response) {
                        NetReportList body = response.body();
                        List<NetReportList.DataDTO> list = body.getData();
                        if (ObjectUtils.isEmpty(list)) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        if (page == 1) {
                            mRecData.clear();
                            mAdapter.setNewData(mRecData);
                        }
                        mRecData.addAll(list);
                        if (list.size() < 10) {
                            mAdapter.loadMoreEnd();
                        } else {
                            mAdapter.loadMoreComplete();
                        }
                        mCurPage = page;

                    }

                    @Override
                    public void onError(Response<NetReportList> response) {
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
