package com.lingyan.banquet.ui.finance;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.google.gson.JsonObject;
import com.lingyan.banquet.App;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivitySearchApplyBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.apply.ApplyDetailActivity;
import com.lingyan.banquet.ui.apply.adapter.ApplyAdapter;
import com.lingyan.banquet.ui.apply.bean.NetExamineIndex;
import com.lingyan.banquet.ui.finance.adapter.FrontBackMoneyAdapter;
import com.lingyan.banquet.ui.finance.adapter.SearchFinanceAdapter;
import com.lingyan.banquet.ui.finance.bean.NetDepositList;
import com.lingyan.banquet.ui.finance.bean.NetSearchHall;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/16.
 */

public class SearchFinanceListActivity extends BaseActivity implements OnRefreshListener, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private ActivitySearchApplyBinding mBinding;
    private JsonObject mJsonObject;
    private Runnable mSearchRun;
    private SearchFinanceAdapter mAdapter;
    private ArrayList<MultiItemEntity> mRecData;
    private int mCurPage;
    private int mTab;
    private String mId;

    public static void start(int tab) {
        Intent intent = new Intent(App.sApp, SearchFinanceListActivity.class);
        intent.putExtra("tab", tab);
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
        mTab = getIntent().getIntExtra("tab", 1);
        mJsonObject = new JsonObject();
        mJsonObject.addProperty("tab", mTab);
        mBinding.ivDeleteSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etKey.setText("");
            }
        });
        mBinding.ivDeleteSearch.setVisibility(View.INVISIBLE);
        mBinding.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
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
        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {


            private Paint mPaint = new Paint();

            {
                mPaint.setColor(Color.parseColor("#FFF7F7F7"));
                mPaint.setAntiAlias(true);
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
                int itemViewType = holder.getItemViewType();
                if (itemViewType == 1) {
                    outRect.bottom = ConvertUtils.dp2px(1);
                } else if (itemViewType == 2) {
                    outRect.bottom = ConvertUtils.dp2px(10);
                }

            }

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                int childCount = parent.getChildCount();

                for (int i = 0; i < childCount; i++) {
                    View view = parent.getChildAt(i);
                    RecyclerView.ViewHolder holder = parent.getChildViewHolder(view);
                    int itemViewType = holder.getItemViewType();
                    int dividerHeight = 0;
                    if (itemViewType == 1) {
                        dividerHeight = ConvertUtils.dp2px(1);
                    } else if (itemViewType == 2) {
                        dividerHeight = ConvertUtils.dp2px(10);
                    }
                    if (dividerHeight == 0) {
                        return;
                    }
                    float dividerTop = view.getBottom();
                    float dividerLeft = parent.getPaddingLeft();
                    float dividerBottom = view.getBottom() + dividerHeight;
                    float dividerRight = parent.getWidth() - parent.getPaddingRight();

                    c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
                }
            }
        });
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecData = new ArrayList<>();
        mAdapter = new SearchFinanceAdapter(mRecData);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.refreshLayout.setOnRefreshListener(this);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnLoadMoreListener(this, mBinding.recyclerView);

    }

    public void search() {
        String title = mBinding.etKey.getText().toString();
        ThreadUtils.getMainHandler().removeCallbacks(mSearchRun);
        mSearchRun = new Runnable() {
            @Override
            public void run() {
                OkGo.getInstance().cancelTag(getActivity());
                OkGo.<NetSearchHall>post(HttpURLs.searchHall)
                        .params("name", title)
                        .execute(new JsonCallback<NetSearchHall>() {
                            @Override
                            public void onSuccess(Response<NetSearchHall> response) {
                                NetSearchHall body = response.body();
                                List<NetSearchHall.DataDTO> list = body.getData();
                                if (ObjectUtils.isEmpty(list)) {
                                    return;
                                }
                                mRecData.clear();
                                mRecData.addAll(list);
                                mAdapter.notifyDataSetChanged();
                                mAdapter.loadMoreEnd();
                            }
                        });
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
        MultiItemEntity multiItemEntity = mRecData.get(position);
        if (multiItemEntity instanceof NetDepositList.DataDTO.ListDTO) {
            NetDepositList.DataDTO.ListDTO dto = (NetDepositList.DataDTO.ListDTO) multiItemEntity;
            FinanceOrderDetailActivity.start(dto.getId());
        } else if (multiItemEntity instanceof NetSearchHall.DataDTO) {
            NetSearchHall.DataDTO dto = (NetSearchHall.DataDTO) multiItemEntity;
            mId = dto.getId();
            refresh(1);
        }
    }

    private void refresh(int page) {
        OkGo.<NetDepositList>post(HttpURLs.depositList)
                .params("tab", mTab)
                .params("page", page)
                .params("hall_id", mId)
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
                        List<NetDepositList.DataDTO.ListDTO> list = data.getList();
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
                    public void onError(Response<NetDepositList> response) {
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
