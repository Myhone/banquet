package com.lingyan.banquet.ui.data.controller;

import android.graphics.Color;
import android.view.View;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.databinding.LayoutDataConvertBinding;
import com.lingyan.banquet.databinding.LayoutDataNewAddBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.DataHomeActivity;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.ui.data.bean.NetDataConvert;
import com.lingyan.banquet.ui.data.bean.NetDataNewAdd;
import com.lingyan.banquet.utils.BanquetChartHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class DataConvertController {


    private LayoutDataConvertBinding mBinding;


    private int[] mColors = new int[]{
            Color.parseColor("#696969"),
            Color.parseColor("#D75E32"),
            Color.parseColor("#5E84D4"),

    };

    public DataConvertController(LayoutDataConvertBinding binding, DataHomeActivity dataHomeActivity) {
        mBinding = binding;
        BanquetChartHelper.init(mBinding.lineChart);
        YAxis axisLeft = mBinding.lineChart.getAxisLeft();
        axisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return super.getAxisLabel(value, axis);
            }
        });

    }

    public void refresh(ConditionFilter condition) {
        String json = GsonUtils.toJson(condition);
        JsonObject jo = (JsonObject) JsonParser.parseString(json);
        jo.addProperty("order", 8);
        OkGo.<NetDataConvert>post(HttpURLs.screenData1)
                .upJson(jo.toString())
                .execute(new JsonCallback<NetDataConvert>() {
                    @Override
                    public void onSuccess(Response<NetDataConvert> response) {
                        NetDataConvert body = response.body();
                        List<NetDataConvert.DataDTO> list = body.getData();

                        XAxis xAxis = mBinding.lineChart.getXAxis();
                        xAxis.setLabelCount(6);
                        xAxis.setGranularity(1f);
                        xAxis.setAxisMinimum(0);
                        xAxis.setLabelRotationAngle(30);



                        YAxis axisLeft = mBinding.lineChart.getAxisLeft();
                        axisLeft.setGranularity(1);
                        axisLeft.setAxisMinimum(0);
                        axisLeft.setLabelCount(6);

                        //x轴
                        xAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getAxisLabel(float value, AxisBase axis) {
                                if(ObjectUtils.isEmpty(list)){
                                    return String.valueOf(value);
                                }

                                //日期
                                int index = (int) value;
                                if (index >= 0 && index < list.size()) {
                                    return list.get((int) value).getDate();
                                }
                                return "";
                            }

                        });
                        //y轴
                        axisLeft.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getAxisLabel(float value, AxisBase axis) {
                                int i = (int) value;
                                return String.valueOf(i);
                            }
                        });

                        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

                        if (ObjectUtils.isNotEmpty(list)) {

                            ArrayList<Entry> values1 = new ArrayList<Entry>();
                            ArrayList<Entry> values2 = new ArrayList<Entry>();
                            ArrayList<Entry> values3 = new ArrayList<Entry>();

                            int index = 0;
                            float maxFloat = 0;
                            for (NetDataConvert.DataDTO dto : list) {
                                NetDataConvert.DataDTO.DatacrDTO datacr = dto.getDatacr();
                                float f1 = getFloat(datacr.getSj_cr());
                                float f2 = getFloat(datacr.getYx_cr());
                                float f3 = getFloat(datacr.getSt_cr());

                                values1.add(new Entry(index, f1));
                                values2.add(new Entry(index, f2));
                                values3.add(new Entry(index, f3));

                                maxFloat = Math.max(f1, maxFloat);
                                maxFloat = Math.max(f2, maxFloat);
                                maxFloat = Math.max(f3, maxFloat);


                                index++;
                            }

                            index--;
                            xAxis.setAxisMaximum(Math.max(6, index));
                            axisLeft.setAxisMaximum(Math.max(6, maxFloat));

                            LineDataSet d1 = getLineDataSet(values1, mColors[0], "商机转化率");
                            LineDataSet d2 = getLineDataSet(values2, mColors[1], "意向转化率");
                            LineDataSet d3 = getLineDataSet(values3, mColors[2], "锁台转化率");
                            dataSets.add(d1);
                            dataSets.add(d2);
                            dataSets.add(d3);


                            mBinding.tv1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (dataSets.size() == 3) {
                                        dataSets.clear();
                                        dataSets.add(d1);
                                    } else {
                                        dataSets.clear();
                                        dataSets.add(d1);
                                        dataSets.add(d2);
                                        dataSets.add(d3);
                                    }
                                    LineData lineData = new LineData(dataSets);
                                    mBinding.lineChart.setData(lineData);
                                    mBinding.lineChart.invalidate();
                                }
                            });
                            mBinding.tv2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (dataSets.size() == 3) {
                                        dataSets.clear();
                                        dataSets.add(d2);
                                    } else {
                                        dataSets.clear();
                                        dataSets.add(d1);
                                        dataSets.add(d2);
                                        dataSets.add(d3);

                                    }
                                    LineData lineData = new LineData(dataSets);
                                    mBinding.lineChart.setData(lineData);
                                    mBinding.lineChart.invalidate();
                                }
                            });
                            mBinding.tv3.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if (dataSets.size() == 3) {
                                        dataSets.clear();
                                        dataSets.add(d3);
                                    } else {
                                        dataSets.clear();
                                        dataSets.add(d1);
                                        dataSets.add(d2);
                                        dataSets.add(d3);

                                    }
                                    LineData lineData = new LineData(dataSets);
                                    mBinding.lineChart.setData(lineData);
                                    mBinding.lineChart.invalidate();
                                }
                            });
                        }else {
                            xAxis.setAxisMaximum(6);
                            axisLeft.setAxisMaximum(6);
//                            ArrayList<Entry> arrayList = new ArrayList<>();
//                            for(int i=0;i<10;i++){
//                                arrayList.add(new Entry(i,0));
//                            }
//
//                            LineDataSet d1 = getLineDataSet(arrayList, mColors[0], "");
//                            dataSets.add(d1);
                        }
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

    private LineDataSet getLineDataSet(ArrayList<Entry> arrayList, int color, String label) {
        LineDataSet d1 = new LineDataSet(arrayList, label);
        d1.setDrawCircleHole(false);
        d1.setDrawCircles(false);
        d1.setValueTextSize(0);
        d1.setColor(color);
        d1.setMode(LineDataSet.Mode.LINEAR);
        d1.setLineWidth(2);
        return d1;
    }

}
