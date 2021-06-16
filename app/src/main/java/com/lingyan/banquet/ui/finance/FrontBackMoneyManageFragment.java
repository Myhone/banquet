package com.lingyan.banquet.ui.finance;

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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.FragmentFrontMoneyManageBinding;
import com.lingyan.banquet.event.DepositStatusEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.finance.adapter.FrontBackMoneyAdapter;
import com.lingyan.banquet.ui.finance.bean.FinanceFilterCondition;
import com.lingyan.banquet.ui.finance.bean.NetDepositList;
import com.lingyan.banquet.ui.order.bean.OrderFilterCondition;
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
 * 定金管理
 * Created by _hxb on 2021/1/6.
 */

public class FrontBackMoneyManageFragment extends BaseFragment implements OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private FragmentFrontMoneyManageBinding mBinding;
    private FrontBackMoneyAdapter mAdapter;
    private List<NetDepositList.DataDTO.ListDTO> mRecData;
    private int mTab;
    private int mCurPage;
    private FinanceFilterCondition mCondition;

    //1-定金管理  2-尾款管理
    public static FrontBackMoneyManageFragment newInstance(int tab) {
        FrontBackMoneyManageFragment fragment = new FrontBackMoneyManageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("tab", tab);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentFrontMoneyManageBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        Bundle arguments = getArguments();
        mTab = arguments.getInt("tab");
        mBinding.llTab1Container.setVisibility(View.GONE);
        mBinding.llTab2Container.setVisibility(View.GONE);
        if (mTab == 1) {
            mBinding.llTab1Container.setVisibility(View.VISIBLE);

        } else {
            mBinding.llTab2Container.setVisibility(View.VISIBLE);
        }
        mCondition = new FinanceFilterCondition();
        mCondition.tab = mTab;

        mBinding.llSearchContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFinanceListActivity.start(mTab);
            }
        });

        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(10);
            }
        });
        mRecData = new ArrayList<>();
        mAdapter = new FrontBackMoneyAdapter(mRecData);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.refreshLayout.setOnRefreshListener(this);
        mAdapter.setOnLoadMoreListener(this, mBinding.recyclerView);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                NetDepositList.DataDTO.ListDTO listDTO = mRecData.get(i);
                FinanceOrderDetailActivity.start(listDTO.getId());
            }
        });
        mBinding.tvFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FilterFinanceActivity.class);
                intent.putExtra("json", GsonUtils.toJson(mCondition));
                startActivityForResult(intent, 1);
            }
        });
        onRefresh(mBinding.refreshLayout);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(DepositStatusEvent event){
        onRefresh(mBinding.refreshLayout);
    }
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refresh(1);
    }

    private void refresh(int page) {
        mCondition.page = page;
        OkGo.<NetDepositList>post(HttpURLs.depositList)
                .upJson(GsonUtils.toJson(mCondition))
                .execute(new JsonCallback<NetDepositList>() {
                    @Override
                    public void onSuccess(Response<NetDepositList> response) {
                        NetDepositList body = response.body();
                        NetDepositList.DataDTO data = body.getData();
                        if (page == 1) {
                            mRecData.clear();
                            mAdapter.setNewData(mRecData);
                        }
                        if (data == null) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        NetDepositList.DataDTO.HeaderInfoDTO header_info = data.getHeader_info();
                        if (header_info == null) {
                            mAdapter.loadMoreEnd();
                            return;
                        }
                        if (mTab == 1) {
                            mBinding.tvReceiveMoney.setText("¥ " + header_info.getReceive_money());
                            mBinding.tvRetiredMoney.setText("¥ " + header_info.getRetired_money());
                        } else {

                            mBinding.tvTab2ReceiveMoney.setText("¥ " + header_info.getReceive_money());
                            mBinding.tvToalMoney.setText("¥ " + header_info.getToal_money());
                            mBinding.tvBalanceMoney.setText("¥ " + header_info.getBalance_money());
                            mBinding.tvNotReceive.setText("¥ " + header_info.getNot_receive());
                        }


                        List<NetDepositList.DataDTO.ListDTO> list = data.getList();
                        if (ObjectUtils.isEmpty(list)) {
                            mAdapter.loadMoreEnd();
                        } else {
                            mAdapter.addData(list);
                            mAdapter.loadMoreComplete();
                            mCurPage = page;
                        }

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
        refresh(page);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 1 || data == null || resultCode != Activity.RESULT_OK) {
            return;
        }
        String json = data.getStringExtra("json");
        mCondition = GsonUtils.fromJson(json, FinanceFilterCondition.class);
        mCondition.tab = mTab;
        mCondition.limit = 10;
        refresh(1);
    }
}
