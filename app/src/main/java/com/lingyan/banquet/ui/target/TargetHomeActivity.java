package com.lingyan.banquet.ui.target;

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
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityTargetHomeBinding;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.target.adapter.TargetAdapter;
import com.lingyan.banquet.ui.target.bean.NetDepartList;
import com.lingyan.banquet.ui.target.bean.NetReqTargetCondition;
import com.lingyan.banquet.ui.target.bean.NetTargetList;
import com.lingyan.banquet.ui.target.bean.NetTargetTabList;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/1/29.
 */

public class TargetHomeActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private List<NetTargetList.DataDTO> mRecData;
    private TargetAdapter mAdapter;
    private List<NetTargetTabList.DataDTO> mTabList;
    private NetReqTargetCondition mCondition;
    private int mCurPage;
    private String b_type, currentTableTitle;

    public static void start() {
        ActivityUtils.startActivity(TargetHomeActivity.class);
    }

    private ActivityTargetHomeBinding mBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTargetHomeBinding.inflate(getLayoutInflater());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("目标管理");
        setContentView(mBinding.getRoot());

        mRecData = new ArrayList<>();
        mAdapter = new TargetAdapter(mRecData);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ConvertUtils.dp2px(5);
                outRect.bottom = ConvertUtils.dp2px(10);
            }
        });
        mAdapter.setOnItemClickListener(this);
        mBinding.refreshLayout.setOnRefreshListener(this);
        mCondition = new NetReqTargetCondition();
        mBinding.tvType.setText("员工");
        mBinding.tvDeptId.setText(" -- ");
        mBinding.tvYearType.setText("本月");
        mBinding.tvBType.setText("宴会");
        mCondition.type = "3";//2-部门 3-员工
        mCondition.dept_id = null;
        mCondition.year_type = "1";//1.本月 2.本季度 3.本年
        mCondition.b_type = BanquetCelebrationType.TYPE_BANQUET + ""; //1-宴会 2-庆典


        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                NetTargetTabList.DataDTO dto = mTabList.get(tab.getPosition());
                mCondition.a_type = dto.getA_type();
                currentTableTitle = tab.getText().toString();
                onRefresh(mBinding.refreshLayout);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mBinding.tvBType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickerListDialog(getContext())
                        .items("宴会", "庆典")
                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                            @Override
                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                dialog.dismiss();
                                mBinding.tvBType.setText(text);
                                if (position == 0) {
                                    mCondition.b_type = BanquetCelebrationType.TYPE_BANQUET + "";
                                } else if (position == 1) {
                                    mCondition.b_type = BanquetCelebrationType.TYPE_CELEBRATION + "";
                                }
                                onRefresh(mBinding.refreshLayout);
                            }
                        })
                        .show();
            }
        });
        mBinding.tvYearType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickerListDialog(getContext())
                        .items("本月", "本季度", "本年")
                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                            @Override
                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                dialog.dismiss();
                                mBinding.tvYearType.setText(text);
                                if (position == 0) {
                                    mCondition.year_type = "1";
                                } else if (position == 1) {
                                    mCondition.year_type = "2";
                                } else if (position == 2) {
                                    mCondition.year_type = "3";
                                }
                                onRefresh(mBinding.refreshLayout);
                            }
                        })
                        .show();
            }
        });
        mBinding.tvDeptId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<NetDepartList>post(HttpURLs.queryDeptList)
                        .params("type", "list")
                        .execute(new JsonCallback<NetDepartList>() {
                            @Override
                            public void onSuccess(Response<NetDepartList> response) {
                                NetDepartList body = response.body();
                                List<NetDepartList.DataDTO> list = body.getData();
                                if (ObjectUtils.isEmpty(list)) {
                                    return;
                                }

                                List<String> strings = new ArrayList<>();
                                for (NetDepartList.DataDTO dataDTO : list) {
                                    strings.add(dataDTO.getLabel());
                                }

                                new PickerListDialog(getContext())
                                        .items(strings)
                                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                                            @Override
                                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                                dialog.dismiss();

                                                mCondition.type = "2";
                                                mBinding.tvType.setText("部门");

                                                mBinding.tvDeptId.setText(text);
                                                NetDepartList.DataDTO dto = list.get(position);
                                                mCondition.dept_id = dto.getId();
                                                onRefresh(mBinding.refreshLayout);
                                            }
                                        })
                                        .show();


                            }
                        });
            }
        });

        mBinding.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickerListDialog(getContext())
                        .items("部门", "员工")
                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                            @Override
                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                dialog.dismiss();
                                mBinding.tvType.setText(text);
                                if (position == 0 && !StringUtils.equals(mCondition.type, "2")) {
                                    mCondition.type = "2";
                                    mCondition.dept_id = null;
                                    mBinding.tvDeptId.setText("全公司");
                                } else if (position == 1 && !StringUtils.equals(mCondition.type, "3")) {
                                    mCondition.type = "3";
                                    mCondition.dept_id = null;
                                    mBinding.tvDeptId.setText(" -- ");
                                }
                                onRefresh(mBinding.refreshLayout);
                            }
                        })
                        .show();
            }
        });

        mAdapter.setOnLoadMoreListener(this, mBinding.recyclerView);

        onRefresh(mBinding.refreshLayout);
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        NetTargetList.DataDTO dto = mRecData.get(i);
        String name = dto.getName();
        String deptName = dto.getDept_name();
        String avatarName = dto.getAvatar_name();
        String b_type = dto.getB_type();
        String rate = dto.getRate();
        String user_number = dto.getUser_number();
        if (ObjectUtils.isEmpty(deptName)) {
            deptName = name;
        }
        TargetDetailActivity.start(currentTableTitle, dto.getType(), dto.getId(), name, avatarName, deptName, b_type, rate, user_number);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (ObjectUtils.isNotEmpty(mTabList)) {
            getData(1);
        } else {
            getTabList();
        }
    }

    private void getData(int page) {
        mCondition.page = page;
        String json = GsonUtils.toJson(mCondition);
        OkGo.<NetTargetList>post(HttpURLs.achievementIndex)
                .upJson(json)
                .execute(new JsonCallback<NetTargetList>() {
                    @Override
                    public void onSuccess(Response<NetTargetList> response) {
                        NetTargetList body = response.body();
                        List<NetTargetList.DataDTO> list = body.getData();

                        if (page == 1) {
                            mRecData.clear();
                            mAdapter.setNewData(mRecData);
                        }

                        if (ObjectUtils.isEmpty(list)) {
                            mAdapter.loadMoreEnd();
                            return;
                        }

                        mAdapter.addData(list);
//                        if (list.size() < 10) {
//                            mAdapter.loadMoreEnd();
//                        } else {
//                            mAdapter.loadMoreComplete();
//                        }
                        mAdapter.loadMoreEnd();
                        mCurPage = page;
                    }

                    @Override
                    public void onError(Response<NetTargetList> response) {
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

    private void getTabList() {
        OkGo.<NetTargetTabList>post(HttpURLs.achievementTabList)
                .execute(new JsonCallback<NetTargetTabList>() {
                    @Override
                    public void onSuccess(Response<NetTargetTabList> response) {
                        if (ObjectUtils.isNotEmpty(mTabList)) {
                            mBinding.refreshLayout.finishRefresh();
                            return;
                        }
                        NetTargetTabList body = response.body();
                        mTabList = body.getData();
                        if (ObjectUtils.isNotEmpty(mTabList)) {
                            mBinding.tabLayout.removeAllTabs();
                            for (NetTargetTabList.DataDTO dataDTO : mTabList) {
                                TabLayout.Tab tab = mBinding.tabLayout.newTab();
                                tab.setText(dataDTO.getTitle());
                                mBinding.tabLayout.addTab(tab);
                            }
                        }
                        getData(1);
                    }

                    @Override
                    public void onError(Response<NetTargetTabList> response) {
                        super.onError(response);
                        mBinding.refreshLayout.finishRefresh();
                    }
                });
    }

    @Override
    public void onLoadMoreRequested() {
        int page = mCurPage;
        page++;
        getData(page);
    }
}
