package com.lingyan.banquet.ui.main;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.FragmentMainMessageBinding;
import com.lingyan.banquet.databinding.FragmentMainMineBinding;
import com.lingyan.banquet.event.LoginEvent;
import com.lingyan.banquet.event.LogoutEvent;
import com.lingyan.banquet.event.ReadMessageEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.main.adapter.MessageAdapter;
import com.lingyan.banquet.ui.main.bean.NetMessageList;
import com.lingyan.banquet.ui.message.MessageDetailActivity;
import com.lingyan.banquet.views.AdapterLoadingView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.umeng.message.entity.UMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/1/1.
 */

public class MessageFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private List<NetMessageList.DataDTO> mRecData;
    private FragmentMainMessageBinding mBinding;
    private MessageAdapter mAdapter;
    private int mCurPage;
    private AdapterLoadingView mLoadingView;

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMainMessageBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        mRecData = new ArrayList<>();
        mAdapter = new MessageAdapter(mRecData);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = ConvertUtils.dp2px(15);
                outRect.right = ConvertUtils.dp2px(15);
                outRect.top = ConvertUtils.dp2px(6);
                outRect.bottom = ConvertUtils.dp2px(6);
            }
        });
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.refreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this, mBinding.recyclerView);
        mLoadingView = new AdapterLoadingView(getContext());
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onRefresh(mBinding.refreshLayout);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!ActivityUtils.isActivityExistsInStack(MessageDetailActivity.class)) {
            onRefresh(mBinding.refreshLayout);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(LoginEvent event) {
        onRefresh(mBinding.refreshLayout);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receivedMessage(UMessage message) {
        onRefresh(mBinding.refreshLayout);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logout(LogoutEvent event) {
        mRecData.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void read(ReadMessageEvent event) {
        String id = event.getId();
        for (NetMessageList.DataDTO dto : mRecData) {
            if (StringUtils.equals(id, dto.getId())) {
                dto.setIs_read("1");
            }
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        NetMessageList.DataDTO dto = mRecData.get(position);
        MessageDetailActivity.start(dto.getId());
    }

    private void refresh(int page) {
        if (mBinding == null) {
            return;
        }
        OkGo.<NetMessageList>post(HttpURLs.messageQueryList)
                .params("page", page)
                .params("limit", 10)
                .execute(new JsonCallback<NetMessageList>() {
                    @Override
                    public void onStart(Request<NetMessageList, ? extends Request> request) {
                        super.onStart(request);
                        mAdapter.setEmptyView(mLoadingView.getLoadingView());
                    }

                    @Override
                    public void onSuccess(Response<NetMessageList> response) {
                        mAdapter.setEmptyView(mLoadingView.getEmptyView(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                refresh(1);
                            }
                        }));

                        if (!UserInfoManager.getInstance().isLogin()) {
                            mRecData.clear();
                            mAdapter.setNewData(mRecData);
                            mAdapter.loadMoreEnd();
                            return;
                        }

                        NetMessageList body = response.body();
                        List<NetMessageList.DataDTO> list = body.getData();
                        if (ObjectUtils.isEmpty(list)) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        if (page == 1) {
                            mRecData.clear();
                            mAdapter.setNewData(mRecData);
                        }
                        mAdapter.addData(list);
                        if (list.size() < 10) {
                            mAdapter.loadMoreEnd();
                        } else {
                            mAdapter.loadMoreComplete();
                        }

                        mCurPage = page;

                    }

                    @Override
                    public void onError(Response<NetMessageList> response) {
                        super.onError(response);
                        mAdapter.loadMoreFail();
                        mAdapter.setEmptyView(mLoadingView.getErrorView(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                refresh(1);
                            }
                        }));
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
