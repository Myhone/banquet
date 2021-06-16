package com.lingyan.banquet.ui.data;

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
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.App;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityNewAddDataListBinding;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.BanquetStepManagerActivity;
import com.lingyan.banquet.ui.celebration.CelStepManagerActivity;
import com.lingyan.banquet.ui.data.adapter.NewAddDataAdapter;
import com.lingyan.banquet.ui.data.bean.NetAddData;
import com.lingyan.banquet.ui.order.OrderDetailActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/28.
 */

public class NewAddDataListActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private ActivityNewAddDataListBinding mBinding;
    private List<NetAddData.DataDTO> mRecData;
    private NewAddDataAdapter mAdapter;
    private String mJson;

    private int mStatus;
    private int mCurPage;

    public static void start(String json, int status) {
        Intent intent = new Intent(App.sApp, NewAddDataListActivity.class);
        intent.putExtra("json", json);
        intent.putExtra("status", status);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mJson = intent.getStringExtra("json");
        mStatus = intent.getIntExtra("status", 1);
        mBinding = ActivityNewAddDataListBinding.inflate(getLayoutInflater());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("详情");
        setContentView(mBinding.getRoot());

        mRecData = new ArrayList<>();
        mAdapter = new NewAddDataAdapter(mRecData);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(10);
            }
        });
        mBinding.recyclerView.setAdapter(mAdapter);

        mBinding.refreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this,mBinding.recyclerView);
        onRefresh(mBinding.refreshLayout);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mCurPage = 1;
        requestNet(mCurPage);
    }

    private void requestNet(int page) {
        JsonObject jo = (JsonObject) JsonParser.parseString(mJson);
        jo.addProperty("page", page);
        jo.addProperty("status", mStatus);
        OkGo.<NetAddData>post(HttpURLs.screenAddList)
                .upJson(jo.toString())
                .execute(new JsonCallback<NetAddData>() {
                    @Override
                    public void onSuccess(Response<NetAddData> response) {
                        NetAddData body = response.body();
                        if (page == 1) {
                            mRecData.clear();
                            mAdapter.setNewData(mRecData);
                        }

                        if (body == null) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        List<NetAddData.DataDTO> list = body.getData();
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
                    public void onError(Response<NetAddData> response) {
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
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {

        NetAddData.DataDTO dto = mRecData.get(i);
        String type = dto.getType();
        String id = dto.getId();
//        if(StringUtils.equals(type, BanquetCelebrationType.TYPE_BANQUET+"")){
//            BanquetStepManagerActivity.start(id,0);
//        }else {
//            CelStepManagerActivity.start(id,0);
//        }
        try {
            int typeInt = Integer.parseInt(type);
            OrderDetailActivity.start(HttpURLs.banquetOrderInfo,id, typeInt);
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
