package com.lingyan.banquet.ui.apply;

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
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.lingyan.banquet.App;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivitySearchApplyBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.apply.adapter.ApplyAdapter;
import com.lingyan.banquet.ui.apply.bean.NetExamineIndex;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/16.
 */

public class SearchApplyActivity extends BaseActivity implements OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private ActivitySearchApplyBinding mBinding;
    private JsonObject mJsonObject;
    private Runnable mSearchRun;
    private ApplyAdapter mAdapter;
    private ArrayList<NetExamineIndex.DataDTO.ListDTO> mRecData;
    private int mCurPage;
    private String mBy;

    public static void start(String by) {
        Intent intent = new Intent(App.sApp, SearchApplyActivity.class);
        intent.putExtra("by", by);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySearchApplyBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBy = getIntent().getStringExtra("by");
        mJsonObject = new JsonObject();
        mJsonObject.addProperty("by", mBy);
        mBinding.ivDeleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etKey.setText("");
            }
        });
        mBinding.ivDeleteSearch.setVisibility(View.INVISIBLE);
        mBinding.etKey.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (StringUtils.isEmpty(text)) {
                    mBinding.ivDeleteSearch.setVisibility(View.INVISIBLE);
                } else {
                    mBinding.ivDeleteSearch.setVisibility(View.VISIBLE);
                }
                search();
            }
        });
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(10);
            }
        });
        mRecData = new ArrayList<>();
        mAdapter = new ApplyAdapter(mRecData);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.refreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this, mBinding.recyclerView);

    }

    public void search() {
        String title = mBinding.etKey.getText().toString();
        if (StringUtils.isTrimEmpty(title)) {
            mJsonObject.remove("title");
        } else {
            mJsonObject.addProperty("title", title);
        }
        ThreadUtils.getMainHandler().removeCallbacks(mSearchRun);
        mSearchRun = new Runnable() {
            @Override
            public void run() {
                OkGo.getInstance().cancelTag(getActivity());
                refresh(1);
            }
        };
        ThreadUtils.getMainHandler().postDelayed(mSearchRun, 500);
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

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
        NetExamineIndex.DataDTO.ListDTO dto = mRecData.get(position);
        ApplyDetailActivity.start(dto.getE_id(), mBy);
    }

    private void refresh(int page) {
        mJsonObject.addProperty("page", page);
        OkGo.<NetExamineIndex>post(HttpURLs.examineIndex)
                .upJson(mJsonObject.toString())
                .execute(new JsonCallback<NetExamineIndex>() {
                    @Override
                    public void onSuccess(Response<NetExamineIndex> response) {
                        NetExamineIndex body = response.body();
                        NetExamineIndex.DataDTO data = body.getData();
                        if (page == 1) {
                            mRecData.clear();
                            mAdapter.setNewData(mRecData);
                        }

                        if (data == null) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        List<NetExamineIndex.DataDTO.ListDTO> list = data.getList();
                        if (ObjectUtils.isEmpty(list)) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        mCurPage = page;
                        mAdapter.addData(list);
                        if (list.size() < 10) {
                            mAdapter.loadMoreEnd();
                        } else {
                            mAdapter.loadMoreComplete();
                        }

                    }

                    @Override
                    public void onError(Response<NetExamineIndex> response) {
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
}
