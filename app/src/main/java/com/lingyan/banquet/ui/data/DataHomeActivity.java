package com.lingyan.banquet.ui.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityDataHomeBinding;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.ui.data.controller.DataAnalyzeController;
import com.lingyan.banquet.ui.data.controller.DataBasicController;
import com.lingyan.banquet.ui.data.controller.DataCompleteController;
import com.lingyan.banquet.ui.data.controller.DataConvertController;
import com.lingyan.banquet.ui.data.controller.DataNewAddController;
import com.lingyan.banquet.ui.data.controller.DataPkController;
import com.lingyan.banquet.ui.data.controller.DataSuccessController;
import com.lingyan.banquet.ui.data.controller.DataTargetController;
import com.lingyan.banquet.ui.data.view.GroupTimeSelectPop;
import com.lingyan.banquet.ui.main.MineFragment;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/1/24.
 */

public class DataHomeActivity extends BaseActivity implements OnRefreshListener {

    private ActivityDataHomeBinding mBinding;
    private ConditionFilter mConditionFilter;
    private DataBasicController mBasicController;
//    private DataCompleteController mCompleteController;
    private DataNewAddController mNewAddController;
    private DataPkController mPkController;
    private DataAnalyzeController mAnalyzeController;
    private DataTargetController mTargetController;
    private DataSuccessController mSuccessController;
    private String mAllDepartID;
    private DataConvertController mConvertController;

    public static void start() {
        ActivityUtils.startActivity(DataHomeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDataHomeBinding.inflate(getLayoutInflater());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("数据大屏");
        setContentView(mBinding.getRoot());
        mBinding.tvGroupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int[] ints = new int[2];
                mBinding.rlConditionContainer.getLocationOnScreen(ints);
                GroupTimeSelectPop pop = new GroupTimeSelectPop(getActivity());
                pop.setData(mConditionFilter);
                pop.setHeight(ScreenUtils.getScreenHeight() - ints[1] - mBinding.rlConditionContainer.getMeasuredHeight());
                pop.showAsDropDown(mBinding.rlConditionContainer);*/
                DataFilterActivity.start(getActivity(), 1, GsonUtils.toJson(mConditionFilter));
            }
        });

        mBinding.refreshLayout.setOnRefreshListener(this);
        mConditionFilter = new ConditionFilter();
        mConditionFilter.banquet_type = BanquetCelebrationType.TYPE_BANQUET;
        mConditionFilter.dept_id = new ArrayList<>();
        mConditionFilter.user_id = new ArrayList<>();
        UserInfoManager userInfoManager = UserInfoManager.getInstance();
        String isAdmin = userInfoManager.get(UserInfoManager.IS_ADMIN);
        mAllDepartID = userInfoManager.get(UserInfoManager.ALL_DEPART_ID);
        if (mAllDepartID == null) {
            mAllDepartID = "0";
        }
        if (StringUtils.equals(isAdmin, "1")) {
            mConditionFilter.dept_id.add(mAllDepartID);
            mConditionFilter.time_type = "today";
            mBinding.tvGroupTime.setText("全公司/今日");
        } else {
            mConditionFilter.user_id.add(userInfoManager.get(UserInfoManager.ID));
            mConditionFilter.time_type = "today";
            mBinding.tvGroupTime.setText("我的/今日");
        }


        mBinding.tvBanquetType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<>();
                list.add("宴会");
                list.add("庆典");
                PickerListDialog dialog = new PickerListDialog(getActivity());
                dialog.items(list);
                dialog.itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                    @Override
                    public void onItemSelected(int position, String text, PickerListDialog dialog) {
                        mConditionFilter.banquet_type = position + 1;
                        mBinding.tvBanquetType.setText(text);
                        dialog.dismiss();
                        onRefresh(mBinding.refreshLayout);
                    }
                });
                dialog.show();
            }
        });

        mBasicController = new DataBasicController(mBinding.llDataBasicRoot, DataHomeActivity.this);
//        mCompleteController = new DataCompleteController(mBinding.llDataCompleteRoot, DataHomeActivity.this);
        mNewAddController = new DataNewAddController(mBinding.llDataNewAddRoot, DataHomeActivity.this);
        mPkController = new DataPkController(mBinding.flDataPkRoot, DataHomeActivity.this);
        mAnalyzeController = new DataAnalyzeController(mBinding.flDataAnalyzeRoot, DataHomeActivity.this);
        mTargetController = new DataTargetController(mBinding.flDataTargetRoot, DataHomeActivity.this);
        mSuccessController = new DataSuccessController(mBinding.flDataSuccessRoot, DataHomeActivity.this);
        mConvertController = new DataConvertController(mBinding.llDataConvertRoot, DataHomeActivity.this);

        onRefresh(mBinding.refreshLayout);
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mBasicController.refresh(mConditionFilter, refreshLayout);
//        mCompleteController.refresh(mConditionFilter);
        mNewAddController.refresh(mConditionFilter);
        mPkController.refresh(mConditionFilter);
        mAnalyzeController.refresh(mConditionFilter);
        mTargetController.refresh(mConditionFilter);
        mSuccessController.refresh(mConditionFilter);
        mConvertController.refresh(mConditionFilter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case 1: {
                String json = data.getStringExtra("json");
                ConditionFilter filter = GsonUtils.fromJson(json, ConditionFilter.class);
                filter.banquet_type=mConditionFilter.banquet_type;
                mConditionFilter=filter;
                String text = getTitleDesc();
                text += "/";
                if (StringUtils.equals(mConditionFilter.time_type, "yesterday")) {
                    text += "昨日";
                } else if (StringUtils.equals(mConditionFilter.time_type, "today")) {
                    text += "今日";
                } else if (StringUtils.equals(mConditionFilter.time_type, "week")) {
                    text += "本周";
                } else if (StringUtils.equals(mConditionFilter.time_type, "month")) {
                    text += "本月";
                } else if (StringUtils.equals(mConditionFilter.time_type, "year")) {
                    text += "本年";
                } else if (!StringUtils.isEmpty(mConditionFilter.start_time) || !StringUtils.isEmpty(mConditionFilter.end_time)) {
                    text += "自定义时间";
                } else {
                    mConditionFilter.time_type = "month";
                    text += "本月";
                }
                mBinding.tvGroupTime.setText(text);
                onRefresh(mBinding.refreshLayout);
                break;
            }
        }
    }

    public String getTitleDesc() {
        UserInfoManager userInfoManager = UserInfoManager.getInstance();
        String allDepartId = userInfoManager.get(UserInfoManager.ALL_DEPART_ID);
        String text;
        if (ObjectUtils.isNotEmpty(mConditionFilter.user_id)
                && mConditionFilter.user_id.size() == 1
                && mConditionFilter.user_id.get(0).equals(UserInfoManager.getInstance().get(UserInfoManager.ID))
                && ObjectUtils.isEmpty(mConditionFilter.dept_id)
        ) {
            text = "我的";
        } else if (ObjectUtils.isNotEmpty(mConditionFilter.dept_id) && ObjectUtils.isEmpty(mConditionFilter.user_id)
                && mConditionFilter.dept_id.size() == 1
                && mConditionFilter.dept_id.get(0).equals(allDepartId)
        ) {
            text = "全公司";
        } else if (ObjectUtils.isNotEmpty(mConditionFilter.dept_id) || ObjectUtils.isNotEmpty(mConditionFilter.user_id)) {
            text = "部门";

            if (ObjectUtils.isNotEmpty(mConditionFilter.title_list)) {
                text="";
                int size = Math.min(2, mConditionFilter.title_list.size());
                for (int i = 0; i < size; i++) {
                    text += (mConditionFilter.title_list.get(i) + ",");
                }
                text = text.substring(0, text.length() - 1);
            }
        } else {
            mConditionFilter.dept_id = new ArrayList<>();
            mConditionFilter.dept_id.add(allDepartId);
            text = "全公司";
        }


        return text;
    }

    public ConditionFilter getConditionFilter() {
        return mConditionFilter;
    }
}
