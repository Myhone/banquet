package com.lingyan.banquet.ui.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivitySelectGroupBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.adapter.SelectGroupAdapter;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.ui.data.bean.DepartBean;
import com.lingyan.banquet.ui.data.bean.NetDepartList;
import com.lingyan.banquet.ui.data.bean.PersonBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/1/31.
 */

public class SelectGroupActivity extends BaseActivity implements OnRefreshListener {


    private ActivitySelectGroupBinding mBinding;
    private List<MultiItemEntity> mRecData;
    private SelectGroupAdapter mAdapter;
    private List<String> mIdList;
    private ConditionFilter mConditionFilter;


    public static void start(AppCompatActivity activity, int code, String json) {
        Intent intent = new Intent(activity, SelectGroupActivity.class);
        intent.putExtra("json", json);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySelectGroupBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        LogUtils.i("json", json);
        mConditionFilter = GsonUtils.fromJson(json, ConditionFilter.class);


        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("请选择");
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecData = new ArrayList<>();
        mAdapter = new SelectGroupAdapter(mRecData);
        mBinding.recyclerView.setAdapter(mAdapter);
        mBinding.refreshLayout.setOnRefreshListener(this);
        mIdList = new ArrayList<>();
        mIdList.add("0");
        onRefresh(mBinding.refreshLayout);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                MultiItemEntity multiItemEntity = mRecData.get(i);
                if (multiItemEntity instanceof DepartBean) {
                    DepartBean departBean = (DepartBean) multiItemEntity;
                    switch (view.getId()) {
                        case R.id.iv_depart_children: {
                            mIdList.add(departBean.getId());
                            onRefresh(mBinding.refreshLayout);
                            break;
                        }
                        case R.id.iv_selected:
                            departBean.setSelected(!departBean.isSelected());
                            mAdapter.notifyDataSetChanged();
                            break;

                    }
                } else if (multiItemEntity instanceof PersonBean) {
                    PersonBean personBean = (PersonBean) multiItemEntity;
                    switch (view.getId()) {
                        case R.id.iv_selected:
                            personBean.setSelected(!personBean.isSelected());
                            mAdapter.notifyDataSetChanged();
                            break;
                    }
                }
            }
        });
        mBinding.tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBinding.llTitleBarRoot.ivTitleBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ObjectUtils.isEmpty(mRecData)) {
                    return;
                }
                ArrayList<String> selectedDepartList = new ArrayList<>();
                ArrayList<String> selectedPersonList = new ArrayList<>();
                ArrayList<String> titleList = new ArrayList<>();

                for (MultiItemEntity entity : mRecData) {
                    if (entity instanceof DepartBean) {
                        DepartBean departBean = (DepartBean) entity;
                        if (departBean.isSelected()) {
                            selectedDepartList.add(departBean.getId());
                            titleList.add(departBean.getName());
                        }
                    } else if (entity instanceof PersonBean) {
                        PersonBean personBean = (PersonBean) entity;
                        if (personBean.isSelected()) {
                            selectedPersonList.add(personBean.getId());
                            titleList.add(personBean.getRealname());
                        }
                    }
                }


                ConditionFilter conditionFilter = new ConditionFilter();
                conditionFilter.dept_id = selectedDepartList;
                conditionFilter.user_id = selectedPersonList;
                conditionFilter.title_list = titleList;
                Intent intent = new Intent();
                intent.putExtra("json", GsonUtils.toJson(conditionFilter));
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        if (ObjectUtils.isEmpty(mIdList)) {
            refreshLayout.finishRefresh();
            return;
        }
        String id = mIdList.get(mIdList.size() - 1);
        OkGo.<NetDepartList>post(HttpURLs.queryDeptUserList + id)
                .execute(new JsonCallback<NetDepartList>() {
                    @Override
                    public void onSuccess(Response<NetDepartList> response) {
                        NetDepartList body = response.body();
                        NetDepartList.DataDTO data = body.getData();
                        if (data == null) {
                            return;
                        }
                        List<DepartBean> deptList = data.getDept_list();
                        List<PersonBean> userList = data.getUser_list();
                        mRecData.clear();
                        if (ObjectUtils.isNotEmpty(deptList)) {
//                            for (DepartBean bean : deptList) {
//                                if(mSelectedDepartSet.contains(bean.getId())){
//                                    bean.setSelected(true);
//                                }
//                            }

                            if (ObjectUtils.isNotEmpty(mConditionFilter) && ObjectUtils.isNotEmpty(mConditionFilter.dept_id)) {
                                for (DepartBean bean : deptList) {
                                    if (mConditionFilter.dept_id.contains(bean.getId())) {
                                        bean.setSelected(true);
                                    }
                                }
                            }

                            mRecData.addAll(deptList);
                        }
                        if (ObjectUtils.isNotEmpty(userList)) {
//                            for (PersonBean personBean : userList) {
//                                if(mSelectedPersonSet.contains(personBean.getId())){
//                                    personBean.setSelected(true);
//                                }
//                            }
                            if (ObjectUtils.isNotEmpty(mConditionFilter) && ObjectUtils.isNotEmpty(mConditionFilter.user_id)) {
                                for (PersonBean personBean : userList) {
                                    if (mConditionFilter.user_id.contains(personBean.getId())) {
                                        personBean.setSelected(true);
                                    }
                                }
                            }
                            mRecData.addAll(userList);
                        }

                        mAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        refreshLayout.finishRefresh();
                    }
                });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (ObjectUtils.isEmpty(mIdList) || mIdList.size() == 1) {
            finish();
            return;
        }
        mIdList.remove(mIdList.size() - 1);
        onRefresh(mBinding.refreshLayout);

    }
}
