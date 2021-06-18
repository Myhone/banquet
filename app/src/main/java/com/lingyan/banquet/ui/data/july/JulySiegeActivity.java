package com.lingyan.banquet.ui.data.july;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityJulySiegeBinding;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.utils.StatusBarUtil;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/1/24.
 */

public class JulySiegeActivity extends BaseActivity implements OnRefreshListener {

    private ActivityJulySiegeBinding mBinding;
    private ConditionFilter mConditionFilter;
    private DataBasicController mBasicController;
    //    private DataCompleteController mCompleteController;
    private DataNewAddController mNewAddController;
    private DataPkController mPkController, mPkDepartmentController, mPkCountryController, mPkManyController;
    private DataAnalyzeController mAnalyzeController;
    private DataTargetController mTargetController;
    private DataSuccessController mSuccessController;
    private String mAllDepartID;
    private DataConvertController mConvertController;

    public static void start() {
        ActivityUtils.startActivity(JulySiegeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityJulySiegeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        StatusBarUtil.setTranslucentForImageView(this, 0, mBinding.clBar);

        mBinding.tvGroupTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*int[] ints = new int[2];
                mBinding.rlConditionContainer.getLocationOnScreen(ints);
                GroupTimeSelectPop pop = new GroupTimeSelectPop(getActivity());
                pop.setData(mConditionFilter);
                pop.setHeight(ScreenUtils.getScreenHeight() - ints[1] - mBinding.rlConditionContainer.getMeasuredHeight());
                pop.showAsDropDown(mBinding.rlConditionContainer);*/
                JulyDataFilterActivity.start(getActivity(), 1, GsonUtils.toJson(mConditionFilter));
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

        mBasicController = new DataBasicController(mBinding.llDataBasicRoot, JulySiegeActivity.this);
//        mCompleteController = new DataCompleteController(mBinding.llDataCompleteRoot, DataHomeActivity.this);
        mNewAddController = new DataNewAddController(mBinding.llDataNewAddRoot, JulySiegeActivity.this);
        mPkController = new DataPkController(mBinding.flDataPkRoot, JulySiegeActivity.this);
        mPkDepartmentController = new DataPkController(mBinding.flDataPkRootDepartment, JulySiegeActivity.this);
        mPkCountryController = new DataPkController(mBinding.flDataPkRootCountry, JulySiegeActivity.this);
        mPkManyController = new DataPkController(mBinding.flDataPkRootMany, JulySiegeActivity.this);
        mAnalyzeController = new DataAnalyzeController(mBinding.flDataAnalyzeRoot, JulySiegeActivity.this);
        mTargetController = new DataTargetController(mBinding.flDataTargetRoot, JulySiegeActivity.this);
//        mSuccessController = new DataSuccessController(mBinding.flDataSuccessRoot, JulySiegeActivity.this);
//        mConvertController = new DataConvertController(mBinding.llDataConvertRoot, JulySiegeActivity.this);

        onRefresh(mBinding.refreshLayout);
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        //获取榜单tab
        getPkItem();

        mBasicController.refresh(mConditionFilter, refreshLayout);
//        mCompleteController.refresh(mConditionFilter);
        mNewAddController.refresh(mConditionFilter);
        mAnalyzeController.refresh(mConditionFilter);
        mTargetController.refresh(mConditionFilter);
//        mSuccessController.refresh(mConditionFilter);
//        mConvertController.refresh(mConditionFilter);
    }

    private void getPkItem() {
        OkGo.<PkItemBean>post(HttpURLs.pkItem)
                .tag(this)
                .execute(new JsonCallback<PkItemBean>() {
                    @Override
                    public void onSuccess(Response<PkItemBean> response) {
                        PkItemBean body = response.body();
                        if (body == null) {
                            return;
                        }

                        mPkController.refresh(mConditionFilter, body, 50);
                        mPkDepartmentController.refresh(mConditionFilter, body, 51);
                        mPkCountryController.refresh(mConditionFilter, body, 52);
                        mPkManyController.refresh(mConditionFilter, body, 53);
                    }
                });
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
                filter.banquet_type = mConditionFilter.banquet_type;
                mConditionFilter = filter;
                String text = getTitleDesc();
                text += "/";
                if (StringUtils.equals(mConditionFilter.time_type, "today")) {
                    text += "今日";
                } else if (StringUtils.equals(mConditionFilter.time_type, "month")) {
                    text += "本月";
                } else if (StringUtils.equals(mConditionFilter.time_type, "period0")) {
                    text += "首爆日";
                } else if (StringUtils.equals(mConditionFilter.time_type, "period1")) {
                    text += "第一阶段";
                } else if (StringUtils.equals(mConditionFilter.time_type, "period2")) {
                    text += "第二阶段";
                } else if (StringUtils.equals(mConditionFilter.time_type, "period3")) {
                    text += "第三阶段";
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
                text = "";
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
