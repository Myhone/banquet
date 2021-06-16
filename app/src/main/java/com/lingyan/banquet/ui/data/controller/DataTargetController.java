package com.lingyan.banquet.ui.data.controller;

import android.view.View;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.databinding.LayoutTargetAnalyzeBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.DataHomeActivity;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.ui.data.bean.NetDataTarget;
import com.lingyan.banquet.ui.target.TargetHomeActivity;
import com.lingyan.banquet.ui.target.bean.NetTargetTabList;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * Created by _hxb on 2021/2/21.
 */

public class DataTargetController {
    private LayoutTargetAnalyzeBinding mBinding;
    private DataHomeActivity mActivity;
    private List<NetTargetTabList.DataDTO> mTabList;
    private NetTargetTabList.DataDTO mDto;

    public DataTargetController(LayoutTargetAnalyzeBinding binding, DataHomeActivity activity) {
        mBinding = binding;
        mActivity = activity;
        mBinding.tvLookDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TargetHomeActivity.start();
            }
        });
        mBinding.tabLayoutTop.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mDto = mTabList.get(position);
                refresh(activity.getConditionFilter());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mBinding.tvCompleteBig.setText("");
        mBinding.tvCount.setText("");
        mBinding.tvComplete.setText("");
        mBinding.tvExceptComplete.setText("");

        getTabList();
    }

    private void getTabList() {
        OkGo.<NetTargetTabList>post(HttpURLs.achievementTabList)
                .execute(new JsonCallback<NetTargetTabList>() {
                    @Override
                    public void onSuccess(Response<NetTargetTabList> response) {
                        if (ObjectUtils.isNotEmpty(mTabList)) {
                            return;
                        }
                        NetTargetTabList body = response.body();
                        mTabList = body.getData();
                        if (ObjectUtils.isNotEmpty(mTabList)) {
                            mBinding.tabLayoutTop.removeAllTabs();
                            for (NetTargetTabList.DataDTO dataDTO : mTabList) {
                                TabLayout.Tab tab = mBinding.tabLayoutTop.newTab();
                                tab.setText(dataDTO.getTitle());
                                mBinding.tabLayoutTop.addTab(tab);
                            }
                        }
                    }
                });
    }

    public void refresh(ConditionFilter condition) {
        if (mDto == null) {
            getTabList();
            return;
        }

        String json = GsonUtils.toJson(condition);
        JsonObject jo = (JsonObject) JsonParser.parseString(json);
        jo.addProperty("order", 7);
        jo.addProperty("target_type", mDto.getA_type());

        OkGo.<NetDataTarget>post(HttpURLs.screenData1)
                .upJson(jo.toString())
                .execute(new JsonCallback<NetDataTarget>() {
                    @Override
                    public void onSuccess(Response<NetDataTarget> response) {
                        NetDataTarget body = response.body();
                        NetDataTarget.DataDTO data = body.getData();
                        if (data == null) {
                            return;
                        }
                        String userNumber = data.getUser_number();
                        String overNumber = data.getOver_number();
                        mBinding.tvCount.setText(overNumber + "/" + userNumber);
                        String rateNum = data.getRate_num();
                        String sxNum = data.getSx_num();
                        if (!rateNum.contains("%")) {
                            rateNum += "%";
                        }
                        if (!sxNum.contains("%")) {
                            sxNum += "%";
                        }
                        mBinding.tvCompleteBig.setText(rateNum.substring(0, rateNum.length() - 1));
                        //已经完成
                        mBinding.tvComplete.setText(rateNum);
                        //预计完成
                        mBinding.tvExceptComplete.setText(sxNum);

                        mBinding.cbv.setProgressNum(getProgress(rateNum), getProgress(sxNum), 1000);
                    }
                });

    }

    private float getProgress(String s) {
        float p = 0;
        try {
            String progress = s.substring(0, s.length() - 1);
            p = Float.parseFloat(progress);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (p > 100) {
            p = 100;
        }
        return p;

    }

}
