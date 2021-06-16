package com.lingyan.banquet.ui.order;

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
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.lingyan.banquet.App;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivitySearchOrderBinding;
import com.lingyan.banquet.databinding.LayoutRefreshRecyclerViewBinding;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.order.adapter.BanquetOrderAdapter;
import com.lingyan.banquet.ui.order.bean.NetBanquetOrderList;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/25.
 */

public class SearchOrderActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private ActivitySearchOrderBinding mBinding;
    private List<NetBanquetOrderList.DataDTO.ListDTO> mRecData;
    private BanquetOrderAdapter mAdapter;
    private String mUrl;
    private int mCurPage;
    private int mType;
    private Runnable mRunnable;

    public static void start(String url, int type) {
        Intent intent = new Intent(App.sApp, SearchOrderActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("type", type);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySearchOrderBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mType = intent.getIntExtra("type", BanquetCelebrationType.TYPE_BANQUET);
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
        mBinding.etKey.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ThreadUtils.getMainHandler().removeCallbacks(mRunnable);
                mRunnable = new Runnable() {
                    @Override
                    public void run() {
                        requestNet(1);
                    }
                };
                ThreadUtils.getMainHandler().postDelayed(mRunnable, 300);

            }
        });
        mBinding.ivDeleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etKey.setText("");
            }
        });

        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
        String text = mBinding.etKey.getText().toString().trim();
        if (StringUtils.isEmpty(text)) {
            return;
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("page", page);
        jsonObject.addProperty("type", mType);

        boolean match = RegexUtils.isMatch("^\\d+$", text);
        if (match) {
            jsonObject.addProperty("mobile", text);
        } else {
            jsonObject.addProperty("real_name", text);
        }
        OkGo.<NetBanquetOrderList>post(mUrl)
                .upJson(jsonObject.toString())
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


}
