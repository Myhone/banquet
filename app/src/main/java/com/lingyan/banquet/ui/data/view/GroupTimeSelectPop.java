package com.lingyan.banquet.ui.data.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TimePicker;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ViewUtils;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.PopGroupTimeSelectBinding;
import com.lingyan.banquet.ui.data.SelectGroupActivity;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.utils.GroupViewUtils;

import java.util.Date;

/**
 * Created by _hxb on 2021/1/31.
 */

public class GroupTimeSelectPop extends PopupWindow {

    private final PopGroupTimeSelectBinding mBinding;
    private final GroupViewUtils mGroupViewUtils;
    private ConditionFilter mConditionFilter;

    public GroupTimeSelectPop(Context context) {
        super();
        LayoutInflater inflater = LayoutInflater.from(context);
        mBinding = PopGroupTimeSelectBinding.inflate(inflater);
        setContentView(mBinding.getRoot());


        setWidth(ScreenUtils.getAppScreenWidth());
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
        setFocusable(true);
        setAnimationStyle(R.style.view_top_slide_anim);
        mBinding.llTimePickerContainer.setVisibility(View.GONE);
        mBinding.tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始时间
                new TimePickerBuilder(context, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy-MM-dd");
                        mBinding.tvStartTime.setText(string);
                    }
                })
                        .setTitleText("请选择开始时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build()
                        .show();
            }
        });
        mBinding.tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始时间
                new TimePickerBuilder(context, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy-MM-dd");
                        mBinding.tvEndTime.setText(string);
                    }
                })
                        .setTitleText("请选择开始时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build()
                        .show();
            }
        });


        mGroupViewUtils = new GroupViewUtils();
        mGroupViewUtils.add("range", mBinding.tvMine, "mine");
        mGroupViewUtils.add("range", mBinding.tvSelectGroup, "depart", false);
        mBinding.tvSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupViewUtils.select("range", "depart");
//                SelectGroupActivity.start();
            }
        });


        mGroupViewUtils.add("time_type", mBinding.tvToday, "today", false);
        mGroupViewUtils.add("time_type", mBinding.tvMonth, "month", false);
        mGroupViewUtils.add("time_type", mBinding.tvYear, "year", false);
        mGroupViewUtils.add("time_type", mBinding.tvCustomTime, "custom", false);

        mBinding.tvToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupViewUtils.select("time_type", "today");
                mBinding.llTimePickerContainer.setVisibility(View.GONE);
            }
        });
        mBinding.tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupViewUtils.select("time_type", "month");
                mBinding.llTimePickerContainer.setVisibility(View.GONE);
            }
        });
        mBinding.tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupViewUtils.select("time_type", "year");
                mBinding.llTimePickerContainer.setVisibility(View.GONE);
            }
        });
        mBinding.tvCustomTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupViewUtils.select("time_type", "custom");
                mBinding.llTimePickerContainer.setVisibility(View.VISIBLE);
            }
        });

    }

    public void setData(ConditionFilter conditionFilter) {
        mConditionFilter = conditionFilter;
    }
}
