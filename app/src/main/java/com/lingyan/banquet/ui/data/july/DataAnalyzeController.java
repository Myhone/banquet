package com.lingyan.banquet.ui.data.july;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.databinding.LayoutDataAnalyzeBinding;
import com.lingyan.banquet.databinding.LayoutDataAnalyzeDescBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.DataHomeActivity;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.ui.data.bean.NetDataAnalyze;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/21.
 */

public class DataAnalyzeController {

    private LayoutDataAnalyzeBinding mBinding;
    private String[] mTopTabs = {"获客渠道", "宴会类型", "宴会厅"};
    private String[] mBottomTabs = {"商机数", "签订数", "合同金额"};
    private int mTopTabIndex = 0;
    private int mBottomTabIndex = 0;
    private String[] mColors = {
            "#A57C5B",
            "#DCC592",
            "#E99643",
            "#669995",
            "#666666",

    };
    private JulySiegeActivity mActivity;

    public DataAnalyzeController(LayoutDataAnalyzeBinding binding, JulySiegeActivity dataHomeActivity) {
        mBinding = binding;
        mActivity = dataHomeActivity;
        for (int i = 0; i < mTopTabs.length; i++) {
            TabLayout.Tab tab = mBinding.tabLayoutTop.newTab();
            tab.setText(mTopTabs[i]);
            mBinding.tabLayoutTop.addTab(tab);

        }

        for (int i = 0; i < mBottomTabs.length; i++) {
            TabLayout.Tab tab = mBinding.tabLayoutBottom.newTab();
            tab.setText(mBottomTabs[i]);
            mBinding.tabLayoutBottom.addTab(tab);
        }

        mBinding.tabLayoutTop.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTopTabIndex = tab.getPosition();
                int oldBottomIndex  = mBottomTabIndex;
                mBinding.tabLayoutBottom.removeAllTabs();
                if (mTopTabIndex == 0) {
                    for (int i = 0; i < mBottomTabs.length; i++) {
                        TabLayout.Tab newTab = mBinding.tabLayoutBottom.newTab();
                        newTab.setText(mBottomTabs[i]);
                        mBinding.tabLayoutBottom.addTab(newTab);
                    }
                } else {
                    for (int i = 1; i < mBottomTabs.length; i++) {
                        TabLayout.Tab newTab = mBinding.tabLayoutBottom.newTab();
                        newTab.setText(mBottomTabs[i]);
                        mBinding.tabLayoutBottom.addTab(newTab);
                    }
                    if (oldBottomIndex >= 2) {
                        oldBottomIndex = 1;
                    }
                }
                TabLayout.Tab tabAt = mBinding.tabLayoutBottom.getTabAt(0);
                if (tabAt != null) {
                    LogUtils.i("mBottomTabIndex",mBottomTabIndex);
                    tabAt.select();
                }


//                ConditionFilter conditionFilter = dataHomeActivity.getConditionFilter();
//                refresh(conditionFilter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mBinding.tabLayoutBottom.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mBottomTabIndex = tab.getPosition();
                ConditionFilter conditionFilter = dataHomeActivity.getConditionFilter();
                refresh(conditionFilter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        //  是否显示中间的洞
        mBinding.pieChart.setDrawHoleEnabled(true);
        mBinding.pieChart.setHoleRadius(40f);//设置中间洞的大小
        mBinding.pieChart.setTransparentCircleRadius(0f);
        mBinding.pieChart.getLegend().setEnabled(false);
        mBinding.pieChart.getDescription().setEnabled(false);
////设置每份所占数量
//        List<PieEntry> yvals = new ArrayList<>();
//        yvals.add(new PieEntry(1, "本科"));
//        yvals.add(new PieEntry(1, "硕士"));
//        yvals.add(new PieEntry(1, "博士"));
//        yvals.add(new PieEntry(4, "大专"));
//        yvals.add(new PieEntry(1, "其他"));
//// 设置每份的颜色
//        List<Integer> colors = new ArrayList<>();
//        colors.add(Color.parseColor("#6785f2"));
//        colors.add(Color.parseColor("#675cf2"));
//        colors.add(Color.parseColor("#496cef"));
//        colors.add(Color.parseColor("#aa63fa"));
//        colors.add(Color.parseColor("#f5a658"));
//        PieDataSet dataset = new PieDataSet(yvals, "");
//        //填充每个区域的颜色
//        dataset.setColors(colors);
//        dataset.setDrawValues(false);
//        PieData pieData = new PieData(dataset);
//        mBinding.pieChart.setData(pieData);
    }


    public void refresh(ConditionFilter condition) {
        String json = GsonUtils.toJson(condition);
        JsonObject jo = (JsonObject) JsonParser.parseString(json);
        jo.addProperty("order", 4);

        OkGo.<NetDataAnalyze>post(HttpURLs.screen2Data1)
                .upJson(jo.toString())
                .execute(new JsonCallback<NetDataAnalyze>() {
                    @Override
                    public void onSuccess(Response<NetDataAnalyze> response) {
                        NetDataAnalyze body = response.body();
                        NetDataAnalyze.DataDTO data = body.getData();
                        if (data == null) {
                            return;
                        }
                        NetDataAnalyze.DataListDTO data1 = data.getData1();
                        NetDataAnalyze.DataListDTO data2 = data.getData2();
                        NetDataAnalyze.DataListDTO data3 = data.getData3();
                        if (data1 == null || data2 == null || data3 == null) {
                            return;
                        }
                        List<NetDataAnalyze.ArrDTO> data1HkArr = data1.getHk_arr();
                        List<NetDataAnalyze.ArrDTO> data1HtArr = data1.getHt_arr();
                        List<NetDataAnalyze.ArrDTO> data1HsArr = data1.getHs_arr();

                        List<NetDataAnalyze.ArrDTO> data2HkArr = data2.getHk_arr();
                        List<NetDataAnalyze.ArrDTO> data2HtArr = data2.getHt_arr();

                        List<NetDataAnalyze.ArrDTO> data3HkArr = data3.getHk_arr();
                        List<NetDataAnalyze.ArrDTO> data3HtArr = data3.getHt_arr();


                        List<PieEntry> yvals = new ArrayList<>();
                        List<Integer> colors = new ArrayList<>();

                        mBinding.llDescContainer.removeAllViews();
                        if (mTopTabIndex == 0 && mBottomTabIndex == 0) {
                            if (ObjectUtils.isNotEmpty(data1HsArr)) {
                                for (int i = 0; i < data1HsArr.size(); i++) {
                                    NetDataAnalyze.ArrDTO dto = data1HsArr.get(i);
                                    addData(yvals, colors, i, dto.getCount(), dto.getCustomer_type_name(), dto.getProp() + "(" + dto.getCount() + "单)");
                                }
                            }
                        } else if (mTopTabIndex == 0 && mBottomTabIndex == 1) {
                            if (ObjectUtils.isNotEmpty(data1HkArr)) {
                                for (int i = 0; i < data1HkArr.size(); i++) {
                                    NetDataAnalyze.ArrDTO dto = data1HkArr.get(i);
                                    addData(yvals, colors, i, dto.getCount(), dto.getCustomer_type_name(), dto.getProp() + "(" + dto.getCount() + "单)");
                                }
                            }
                        } else if (mTopTabIndex == 0 && mBottomTabIndex == 2) {
                            if (ObjectUtils.isNotEmpty(data1HtArr)) {
                                for (int i = 0; i < data1HtArr.size(); i++) {
                                    NetDataAnalyze.ArrDTO dto = data1HtArr.get(i);
                                    addData(yvals, colors, i, dto.getB_budget_money(), dto.getCustomer_type_name(), dto.getProp() + "(" + dto.getB_budget_money() + "元)");
                                }
                            }
                        } else if (mTopTabIndex == 1 && mBottomTabIndex == 0) {
                            if (ObjectUtils.isNotEmpty(data2HkArr)) {
                                for (int i = 0; i < data2HkArr.size(); i++) {
                                    NetDataAnalyze.ArrDTO dto = data2HkArr.get(i);
                                    addData(yvals, colors, i, dto.getCount(), dto.getNiche_name(), dto.getProp() + "(" + dto.getCount() + "单)");
                                }
                            }
                        } else if (mTopTabIndex == 1 && mBottomTabIndex == 1) {
                            if (ObjectUtils.isNotEmpty(data2HtArr)) {
                                for (int i = 0; i < data2HtArr.size(); i++) {
                                    NetDataAnalyze.ArrDTO dto = data2HtArr.get(i);
                                    addData(yvals, colors, i, dto.getB_budget_money(), dto.getNiche_name(), dto.getProp() + "(" + dto.getB_budget_money() + "元)");
                                }
                            }
                        } else if (mTopTabIndex == 2 && mBottomTabIndex == 0) {
                            if (ObjectUtils.isNotEmpty(data3HkArr)) {
                                for (int i = 0; i < data3HkArr.size(); i++) {
                                    NetDataAnalyze.ArrDTO dto = data3HkArr.get(i);
                                    addData(yvals, colors, i, dto.getCount(), dto.getHall_name(), dto.getProp() + "(" + dto.getCount() + "单)");
                                }
                            }
                        } else if (mTopTabIndex == 2 && mBottomTabIndex == 1) {
                            if (ObjectUtils.isNotEmpty(data3HtArr)) {
                                for (int i = 0; i < data3HtArr.size(); i++) {
                                    NetDataAnalyze.ArrDTO dto = data3HtArr.get(i);
                                    addData(yvals, colors, i, dto.getB_budget_money(), dto.getHall_name(), dto.getProp() + "(" + dto.getB_budget_money() + "元)");
                                }
                            }
                        }

                        PieDataSet dataset = new PieDataSet(yvals, "");
                        //填充每个区域的颜色
                        dataset.setColors(colors);
                        dataset.setDrawValues(false);
                        PieData pieData = new PieData(dataset);
                        mBinding.pieChart.setData(pieData);
                        mBinding.pieChart.invalidate();
                    }
                });
    }


    private void addData(List<PieEntry> yvals, List<Integer> colors, int i, String count, String name, String desc) {
        yvals.add(new PieEntry(getFloat(count), ""));
        colors.add(Color.parseColor(mColors[i % mColors.length]));

        LayoutDataAnalyzeDescBinding descBinding = LayoutDataAnalyzeDescBinding.inflate(mActivity.getLayoutInflater());
        descBinding.civColor.setImageDrawable(new ColorDrawable(Color.parseColor(mColors[i % mColors.length])));
        descBinding.tvText.setText(name);
        descBinding.tvPercent.setText(desc);
        mBinding.llDescContainer.addView(descBinding.getRoot());
    }

    private float getFloat(String str) {
        float f = 0;
        try {
            f = Float.parseFloat(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

}
