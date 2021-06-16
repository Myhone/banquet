package com.lingyan.banquet.ui.order;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.LayoutRefreshRecyclerViewBinding;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.order.adapter.BanquetOrderAdapter;
import com.lingyan.banquet.ui.order.bean.NetBanquetOrderList;
import com.lingyan.banquet.ui.order.bean.OrderFilterCondition;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/1/3.
 */

public class OrderListFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private LayoutRefreshRecyclerViewBinding mBinding;
    private List<NetBanquetOrderList.DataDTO.ListDTO> mRecData;
    private BanquetOrderAdapter mAdapter;
    private String mUrl;
    private int mCurPage;
    private int mType;
    private OrderFilterCondition mCondition;

    public static OrderListFragment newInstance(String url, int type) {
        OrderListFragment f = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putInt("type", type);
        f.setArguments(bundle);
        return f;
    }

    public static OrderListFragment newInstance(String url, String json) {
        OrderListFragment f = new OrderListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        bundle.putString("json", json);
        f.setArguments(bundle);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = LayoutRefreshRecyclerViewBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle arguments = getArguments();
        mUrl = arguments.getString("url");
        mType = arguments.getInt("type", BanquetCelebrationType.TYPE_BANQUET);
        String json = arguments.getString("json");
        if (json != null) {
            mCondition = GsonUtils.fromJson(json, OrderFilterCondition.class);
            mType=mCondition.type;
        } else {
            mCondition = new OrderFilterCondition();
            mCondition.type = mType;
        }


        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(10);
            }
        });
        mRecData = new ArrayList<>();
        mAdapter = new BanquetOrderAdapter(mRecData);
        mBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        mBinding.refreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mBinding.recyclerView);
        requestNet(mCurPage);
        mBinding.tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //筛选
                Intent i = new Intent(getContext(), FilterOrderActivity.class);
                i.putExtra("json", GsonUtils.toJson(mCondition));
                startActivityForResult(i, 1);
            }
        });
        mBinding.llSearchContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchOrderActivity.start(mUrl, mType);
            }
        });

    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        NetBanquetOrderList.DataDTO.ListDTO dto = mRecData.get(position);
        String id = dto.getId();
        String banquetId = dto.getBanquet_id();
        if (StringUtils.equals(mUrl, HttpURLs.banquetOrderList)) {
            //宴会订单
            OrderDetailActivity.start(HttpURLs.banquetOrderInfo, id, mType);
        } else if (StringUtils.equals(mUrl, HttpURLs.banquetOrderNumberList)) {
            //宴会场次
            OrderDetailActivity.start(HttpURLs.banquetOrderNumberInfo, id, mType);
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurPage = 1;
        requestNet(mCurPage);
    }

    private void requestNet(int page) {
        mCondition.page = page;
        OkGo.<NetBanquetOrderList>post(mUrl)
                .upJson(GsonUtils.toJson(mCondition))
                .tag(getThisFragment())
                .execute(new JsonCallback<NetBanquetOrderList>() {
                    @Override
                    public void onSuccess(Response<NetBanquetOrderList> response) {
                        NetBanquetOrderList body = response.body();
                        if (page == 1) {
                            mRecData.clear();
                            mAdapter.setNewData(mRecData);
                        }

                        if (body == null) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        NetBanquetOrderList.DataDTO data = body.getData();
                        if (data == null) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        List<NetBanquetOrderList.DataDTO.ListDTO> list = data.getList();
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
                    public void onError(Response<NetBanquetOrderList> response) {
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
    public void onLoadMoreRequested() {
        int page = mCurPage;
        page++;
        requestNet(page);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 1 || data == null || resultCode != Activity.RESULT_OK) {
            return;
        }
        String json = data.getStringExtra("json");
        OrderFilterCondition result = GsonUtils.fromJson(json, OrderFilterCondition.class);
        result.type = mType;
        result.limit = 10;

        mCondition = result;
        requestNet(1);
    }
}
