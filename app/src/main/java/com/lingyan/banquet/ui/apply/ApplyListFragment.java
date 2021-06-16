package com.lingyan.banquet.ui.apply;

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
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.FragmentApplyListBinding;
import com.lingyan.banquet.event.ApplyRecordEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.apply.adapter.ApplyAdapter;
import com.lingyan.banquet.ui.apply.bean.NetExamineIndex;
import com.lingyan.banquet.utils.MyGsonUtils;
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
 * Created by _hxb on 2021/1/3.
 */

public class ApplyListFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private FragmentApplyListBinding mBinding;
    private List<NetExamineIndex.DataDTO.ListDTO> mRecData;
    private ApplyAdapter mAdapter;
    private String mBy;
    private int mCurPage;

    private JsonObject mJsonObject;


    public static ApplyListFragment newInstance(String by) {
        ApplyListFragment f = new ApplyListFragment();
        Bundle bundle = new Bundle();
        bundle.putString("by", by);
        f.setArguments(bundle);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentApplyListBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();
        mBy = arguments.getString("by");
        mJsonObject = new JsonObject();
        mJsonObject.addProperty("by", mBy);
        mBinding.llSearchContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchApplyActivity.start(mBy);
            }
        });
        mBinding.tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FilterApplyActivity.class);
                intent.putExtra("json", mJsonObject.toString());
                startActivityForResult(intent, 1);
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
        onRefresh(mBinding.refreshLayout);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void change(ApplyRecordEvent event) {
        onRefresh(mBinding.refreshLayout);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        NetExamineIndex.DataDTO.ListDTO dto = mRecData.get(position);
        ApplyDetailActivity.start(dto.getE_id(), mBy);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            String json = data.getStringExtra("json");
            if (json == null) {
                return;
            }
            LogUtils.i(json);
            JsonObject jo = (JsonObject) JsonParser.parseString(json);
            MyGsonUtils.assignAndRemove(jo, mJsonObject, "create_user_name"
                    , "create_user_id"
                    , "date_start"
                    , "date_end"
                    , "type"
                    , "check_status"
                    , "e_type"
                    , "status"
            );
            LogUtils.i(mJsonObject);
            refresh(1);
        }
    }


    private void refresh(int page) {
        mJsonObject.addProperty("page", page);
        JsonObject jo = makeNetJo();
        OkGo.<NetExamineIndex>post(HttpURLs.examineIndex)
                .upJson(jo.toString())
                .tag(getThisFragment())
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

    private JsonObject makeNetJo() {
        JsonObject jo = new JsonObject();

        MyGsonUtils.assignAndRemove(mJsonObject, jo, "by", "title", "status", "type", "check_status", "e_type", "page");

        String createUserId = MyGsonUtils.getString(mJsonObject, "create_user_id");
        if (createUserId != null) {
            jo.addProperty("create_user", createUserId);
        }

        String dateStart = MyGsonUtils.getString(mJsonObject, "date_start");
        String dateEnd = MyGsonUtils.getString(mJsonObject, "date_end");
        String date = "";
        if (dateStart != null) {
            date += dateStart;
        }
        date += "-";
        if (dateEnd != null) {
            date += dateEnd;
        }
        if (!StringUtils.isEmpty(date) && !StringUtils.equals(date, "-")) {
            jo.addProperty("date", date);
        }
        jo.addProperty("limit", 10);
        return jo;
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
