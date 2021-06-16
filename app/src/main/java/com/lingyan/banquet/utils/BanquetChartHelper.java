package com.lingyan.banquet.utils;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/11.
 */

public class BanquetChartHelper {
    
    public static void init(LineChart lineChart){
       lineChart.setTouchEnabled(false);
       lineChart.getLegend().setEnabled(false);
       lineChart.getDescription().setEnabled(false);
       lineChart.getAxisRight().setEnabled(false);
       lineChart.setDrawBorders(true);
       lineChart.setDrawGridBackground(false);
       lineChart.setBorderColor(Color.parseColor("#E5E5E5"));
       lineChart.setBorderWidth(1);

        YAxis axisLeft =lineChart.getAxisLeft();
        axisLeft.setAxisMinimum(0);
        axisLeft.setGridColor(Color.parseColor("#E5E5E5"));
        axisLeft.setGridLineWidth(1);
        axisLeft.setAxisLineColor(Color.parseColor("#E5E5E5"));
        axisLeft.setAxisLineWidth(1);
        axisLeft.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if (value == 0) {
                    return "(万) 0";
                }
                return super.getAxisLabel(value, axis);
            }
        });

        XAxis xAxis =lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGridColor(Color.parseColor("#E5E5E5"));
        xAxis.setGridLineWidth(1);
        xAxis.setAxisLineColor(Color.parseColor("#E5E5E5"));
        xAxis.setAxisLineWidth(1);
        xAxis.setAxisMinimum(0);


        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            entries.add(new Entry(i, 0));
        }
        LineDataSet d = new LineDataSet(entries, "");
        d.setDrawCircleHole(false);
        d.setDrawCircles(false);
        d.setValueTextSize(0);
        d.setColor(Color.parseColor("#E5E5E5"));
        LineData lineData = new LineData(d);
       lineChart.setData(lineData);
    }


}
