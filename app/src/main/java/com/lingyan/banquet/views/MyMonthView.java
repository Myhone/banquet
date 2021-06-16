package com.lingyan.banquet.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.haibin.calendarview.LunarCalendar;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.ItemMonthViewDayBinding;
import com.lingyan.banquet.databinding.ViewMonthViewBinding;

import java.util.Calendar;

/**
 * Created by _hxb on 2021/1/9.
 */

public class MyMonthView extends FrameLayout {
    private Calendar mCalendar;
    private Calendar mTodayCanlerdar;
    private Calendar mStartCalendar;

    private ViewMonthViewBinding mBinding;
    private int mItemWidth;
    private int mItemHeight;
    private LayoutInflater mInflater;
    private int mYear;
    private int mMonth;
    private OnDayClick mOnDayClick;

    public static interface OnDayClick {
        void onDayClick(Calendar c);
    }

    public void setOnDayClick(OnDayClick onDayClick) {
        mOnDayClick = onDayClick;
    }


    public MyMonthView(Context context) {
        super(context);
        init();
    }

    public MyMonthView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mInflater = LayoutInflater.from(getContext());
        mBinding = ViewMonthViewBinding.inflate(mInflater);
        addView(mBinding.getRoot());

        mItemWidth = ScreenUtils.getScreenWidth() / 7;
        mItemHeight = mItemWidth;
        mTodayCanlerdar = Calendar.getInstance();

//        for (int i = 0; i < 37; i++) {
//            ItemMonthViewDayBinding dayBinding = ItemMonthViewDayBinding.inflate(mInflater);
//            mBinding.flBoxLayout.addView(dayBinding.getRoot());
//            ViewGroup.LayoutParams params = dayBinding.getRoot().getLayoutParams();
//            params.width = mItemWidth;
//            params.height = mItemHeight;
//            dayBinding.getRoot().setLayoutParams(params);
//        }

//        setCalendar(Calendar.getInstance());
    }

    public void setCalendar(Calendar calendar) {
        mCalendar = calendar;
        add();
    }

    private void add() {
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);

        mBinding.tvYearMonth.setText(String.format("%d年%d月", mYear, mMonth + 1));

        mStartCalendar = Calendar.getInstance();
        mStartCalendar.set(mYear, mMonth, 1);


        int childCount = mBinding.flBoxLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = mBinding.flBoxLayout.getChildAt(i);
            child.setOnClickListener(null);
            TextView tvDay = child.findViewById(R.id.tv_day);
            TextView tvLunar = child.findViewById(R.id.tv_lunar);
            tvDay.setText("");
            tvLunar.setText("");
        }

        int startDayOfWeek = mStartCalendar.get(Calendar.DAY_OF_WEEK);
        int endDay = mCalendar.getActualMaximum(Calendar.DATE);
        int newChildCount = endDay + startDayOfWeek - 1;
        if (newChildCount < childCount) {
            for (int i = 0; i < (childCount - newChildCount); i++) {
                mBinding.flBoxLayout.removeViewAt(0);
            }
        } else if (newChildCount > childCount) {
            for (int i = 0; i < (newChildCount - childCount); i++) {
                ItemMonthViewDayBinding dayBinding = ItemMonthViewDayBinding.inflate(mInflater);
                mBinding.flBoxLayout.addView(dayBinding.getRoot());
                ViewGroup.LayoutParams params = dayBinding.getRoot().getLayoutParams();
                params.width = mItemWidth;
                params.height = mItemHeight;
                dayBinding.getRoot().setLayoutParams(params);
            }
        }

//        LogUtils.i("year " + mYear, "month " + (mMonth + 1), "startDayOfWeek " + startDayOfWeek, "endDay " + endDay, "newChildCount " + newChildCount);

        for (int i = 0; i < newChildCount; i++) {
            View child = mBinding.flBoxLayout.getChildAt(i);
            TextView tvDay = (TextView) child.getTag(R.id.tv_day);
            TextView tvLunar = (TextView) child.getTag(R.id.tv_lunar);
            if (tvDay == null) {
                tvDay = child.findViewById(R.id.tv_day);
                tvLunar = child.findViewById(R.id.tv_lunar);
                child.setTag(R.id.tv_day, tvDay);
                child.setTag(R.id.tv_lunar, tvLunar);
            }

            if (i < startDayOfWeek - 1) {
                tvDay.setText("");
                tvLunar.setText("");
            } else {
                int day = i - (startDayOfWeek - 1) + 1;
                tvDay.setText(String.format("%d", day));
                tvLunar.setText(LunarCalendar.getLunarText(mYear, mMonth + 1, day));
                int todayYear = mTodayCanlerdar.get(Calendar.YEAR);
                int todayMonth = mTodayCanlerdar.get(Calendar.MONTH);
                int todayDay = mTodayCanlerdar.get(Calendar.DATE);
                if (mYear == todayYear && mMonth == todayMonth && todayDay == day) {
                    //今天
                    tvDay.setTextColor(getContext().getResources().getColor(R.color.gold));
                    tvLunar.setTextColor(getContext().getResources().getColor(R.color.gold));
                    tvLunar.setText("今日");
                } else {
                    tvDay.setTextColor(getContext().getResources().getColor(R.color.black2));
                    tvLunar.setTextColor(getContext().getResources().getColor(R.color.black2));
                }

                child.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar c = Calendar.getInstance();
                        c.set(mYear, mMonth, day);

                        if (mOnDayClick != null) {
                            mOnDayClick.onDayClick(c);
                        }
//                        ToastUtils.showShort(mYear + "年" + (mMonth + 1) + "月" + day + "日");
                    }
                });

            }
        }
    }


}
