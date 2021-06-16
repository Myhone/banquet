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
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.databinding.LayoutDataCompleteBinding;
import com.lingyan.banquet.databinding.LayoutDataNewAddBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.DataHomeActivity;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.ui.data.bean.NetDataComplete;
import com.lingyan.banquet.ui.data.bean.NetDataNewAdd;
import com.lingyan.banquet.utils.BanquetChartHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.FactoryConfigurationError;

/**
 * Created by _hxb on 2021/2/20.
 */

public class DataNewAddController {


    private LayoutDataNewAddBinding mBinding;


    private int[] mColors = new int[]{
            Color.parseColor("#696969"),
            Color.parseColor("#D75E32"),
            Color.parseColor("#5E84D4"),
            Color.parseColor("#C7A876"),
    };

    public DataNewAddController(LayoutDataNewAddBinding binding, DataHomeActivity dataHomeActivity) {
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
        jo.addProperty("order", 3);
        OkGo.<NetDataNewAdd>post(HttpURLs.screenData1)
                .upJson(jo.toString())
                .execute(new JsonCallback<NetDataNewAdd>() {
                    @Override
                    public void onSuccess(Response<NetDataNewAdd> response) {
                        NetDataNewAdd body = response.body();
                        List<NetDataNewAdd.DataDTO> list = body.getData();
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
                                int i = (int) value;
                                return String.valueOf(i);
                            }
                        });

                        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

                        ArrayList<Entry> values1 = new ArrayList<Entry>();
                        ArrayList<Entry> values2 = new ArrayList<Entry>();
                        ArrayList<Entry> values3 = new ArrayList<Entry>();
                        ArrayList<Entry> values4 = new ArrayList<Entry>();
                        int index = 0;
                        float maxFloat = 0;
                        for (NetDataNewAdd.DataDTO dto : list) {
                            NetDataNewAdd.DataDTO.AddInfoDTO addInfo = dto.getAdd_info();
                            float f1 = getFloat(addInfo.getData1());
                            float f2 = getFloat(addInfo.getData2());
                            float f3 = getFloat(addInfo.getData3());
                            float f4 = getFloat(addInfo.getData4());
                            values1.add(new Entry(index, f1));
                            values2.add(new Entry(index, f2));
                            values3.add(new Entry(index, f3));
                            values4.add(new Entry(index, f4));
                            maxFloat = Math.max(f1, maxFloat);
                            maxFloat = Math.max(f2, maxFloat);
                            maxFloat = Math.max(f3, maxFloat);
                            maxFloat = Math.max(f4, maxFloat);

                            index++;
                        }

                        index--;
                        xAxis.setAxisMaximum(Math.max(6,index));
                        axisLeft.setAxisMaximum(Math.max(6,maxFloat));

                        LineDataSet d1 = getLineDataSet(values1, mColors[0], "新增商机");
                        LineDataSet d2 = getLineDataSet(values2, mColors[1], "新增意向");
                        LineDataSet d3 = getLineDataSet(values3, mColors[2], "新增锁台");
                        LineDataSet d4 = getLineDataSet(values4, mColors[3], "新增签订");
                        dataSets.add(d1);
                        dataSets.add(d2);
                        dataSets.add(d3);
                        dataSets.add(d4);

                        mBinding.tv1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dataSets.size() == 4) {
                                    dataSets.clear();
                                    dataSets.add(d1);
                                } else {
                                    dataSets.clear();
                                    dataSets.add(d1);
                                    dataSets.add(d2);
                                    dataSets.add(d3);
                                    dataSets.add(d4);
                                }
                                LineData lineData = new LineData(dataSets);
                                mBinding.lineChart.setData(lineData);
                                mBinding.lineChart.invalidate();
                            }
                        });
                        mBinding.tv2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dataSets.size() == 4) {
                                    dataSets.clear();
                                    dataSets.add(d2);
                                } else {
                                    dataSets.clear();
                                    dataSets.add(d1);
                                    dataSets.add(d2);
                                    dataSets.add(d3);
                                    dataSets.add(d4);
                                }
                                LineData lineData = new LineData(dataSets);
                                mBinding.lineChart.setData(lineData);
                                mBinding.lineChart.invalidate();
                            }
                        });
                        mBinding.tv3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dataSets.size() == 4) {
                                    dataSets.clear();
                                    dataSets.add(d3);
                                } else {
                                    dataSets.clear();
                                    dataSets.add(d1);
                                    dataSets.add(d2);
                                    dataSets.add(d3);
                                    dataSets.add(d4);
                                }
                                LineData lineData = new LineData(dataSets);
                                mBinding.lineChart.setData(lineData);
                                mBinding.lineChart.invalidate();
                            }
                        });
                        mBinding.tv4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (dataSets.size() == 4) {
                                    dataSets.clear();
                                    dataSets.add(d4);
                                } else {
                                    dataSets.clear();
                                    dataSets.add(d1);
                                    dataSets.add(d2);
                                    dataSets.add(d3);
                                    dataSets.add(d4);
                                }
                                LineData lineData = new LineData(dataSets);
                                mBinding.lineChart.setData(lineData);
                                mBinding.lineChart.invalidate();
                            }
                        });

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
