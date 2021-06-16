package com.lingyan.banquet.ui.data.controller;

import android.graphics.Color;
import android.view.View;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.databinding.LayoutDataBasicBinding;
import com.lingyan.banquet.databinding.LayoutDataCompleteBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.DataHomeActivity;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.ui.data.bean.NetDataBasic;
import com.lingyan.banquet.ui.data.bean.NetDataComplete;
import com.lingyan.banquet.utils.BanquetChartHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class DataCompleteController {

    private int mTabType;
    private LayoutDataCompleteBinding mBinding;


    private int[] mColors = new int[]{
            Color.parseColor("#696969"),
            Color.parseColor("#C7A876"),
    };

    public DataCompleteController(LayoutDataCompleteBinding binding, DataHomeActivity dataHomeActivity) {
        mBinding = binding;
        mTabType = 1;
        binding.viewTabType1Line.setVisibility(View.VISIBLE);
        binding.viewTabType2Line.setVisibility(View.INVISIBLE);
        binding.llTab1Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabType == 1) {
                    return;
                }
                mTabType = 1;
                binding.viewTabType1Line.setVisibility(View.VISIBLE);
                binding.viewTabType2Line.setVisibility(View.INVISIBLE);
                ConditionFilter conditionFilter = dataHomeActivity.getConditionFilter();
                refresh(conditionFilter);
            }
        });
        binding.llTab2Container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabType == 2) {
                    return;
                }
                mTabType = 2;
                binding.viewTabType1Line.setVisibility(View.INVISIBLE);
                binding.viewTabType2Line.setVisibility(View.VISIBLE);
                ConditionFilter conditionFilter = dataHomeActivity.getConditionFilter();
                refresh(conditionFilter);
            }
        });
        BanquetChartHelper.init(mBinding.lineChart);


    }

    public void refresh(ConditionFilter condition) {
        String json = GsonUtils.toJson(condition);
        JsonObject jo = (JsonObject) JsonParser.parseString(json);
        jo.addProperty("order", 2);
        jo.addProperty("tab_type ", mTabType);
        OkGo.<NetDataComplete>post(HttpURLs.screenData1)
                .upJson(jo.toString())
                .execute(new JsonCallback<NetDataComplete>() {
                    @Override
                    public void onSuccess(Response<NetDataComplete> response) {
                        NetDataComplete body = response.body();
                        List<NetDataComplete.DataDTO> list = body.getData();
                        if (ObjectUtils.isEmpty(list)) {
                            return;
                        }

                        XAxis xAxis = mBinding.lineChart.getXAxis();
                        xAxis.setLabelCount(6);
                        xAxis.setGranularity(1f);
                        xAxis.setAxisMinimum(0);
                        xAxis.setLabelRotationAngle(30);

                        //x轴
                        xAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getAxisLabel(float value, AxisBase axis) {
                                //日期
                                int index = (int) value;
                                if (index >= 0 && index < list.size()) {
                                    return list.get((int) value).getDate();
                                }
                                return "";
                            }
                        });

                        YAxis axisLeft = mBinding.lineChart.getAxisLeft();
                        axisLeft.setGranularity(1);
                        axisLeft.setAxisMinimum(0);
                        axisLeft.setLabelCount(6);

                        //y轴
                        axisLeft.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getAxisLabel(float value, AxisBase axis) {
                                BigDecimal bigDecimal = new BigDecimal(value);
                                bigDecimal = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
                                int i = bigDecimal.intValue();
                                return String.valueOf(i);
                            }
                        });


                        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

                        ArrayList<Entry> values1 = new ArrayList<Entry>();
                        ArrayList<Entry> values2 = new ArrayList<Entry>();
                        int index = 0;
                        float maxFloat = 0;
                        for (NetDataComplete.DataDTO dto : list) {
                            float f1 = getFloat(dto.getTable_number());
                            float f2 = getFloat(dto.getZhi_count());
                            maxFloat = Math.max(f1, maxFloat);
                            maxFloat = Math.max(f2, maxFloat);

                            values1.add(new Entry(index, f1));
                            values2.add(new Entry(index, f2));
                            index++;
                        }

                        index--;
                        xAxis.setAxisMaximum(Math.max(6,index));
                        axisLeft.setAxisMaximum(Math.max(6,maxFloat));


                        LineDataSet d1 = new LineDataSet(values1, "桌数");
                        d1.setDrawCircleHole(false);
                        d1.setDrawCircles(false);
                        d1.setValueTextSize(0);
                        int color = mColors[0];
                        d1.setColor(color);
                        d1.setMode(LineDataSet.Mode.LINEAR);
                        d1.setLineWidth(2);
                        dataSets.add(d1);

                        LineDataSet d2 = new LineDataSet(values2, "执行单数");
                        d2.setDrawCircleHole(false);
                        d2.setDrawCircles(false);
                        d2.setValueTextSize(0);
                        color = mColors[1];
                        d2.setColor(color);
                        d2.setMode(LineDataSet.Mode.LINEAR);
                        d2.setLineWidth(2);
                        dataSets.add(d2);

                        LineData lineData = new LineData(dataSets);
                        mBinding.lineChart.setData(lineData);
                        mBinding.lineChart.invalidate();

                    }
                });
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
