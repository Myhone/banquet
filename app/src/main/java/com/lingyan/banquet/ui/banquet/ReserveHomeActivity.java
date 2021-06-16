package com.lingyan.banquet.ui.banquet;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityReserveHomeBinding;
import com.lingyan.banquet.views.MyMonthView;
import com.lingyan.banquet.views.dialog.SelectDayDialog;

import java.util.Calendar;

/**
 * 预定的首页
 * Created by _hxb on 2021/1/10.
 */

public class ReserveHomeActivity extends BaseActivity {


    private ActivityReserveHomeBinding mBinding;
    private Calendar mCalendar;
    private RestaurantListFragment mLunchFragment;
    private RestaurantListFragment mDinnerFragment;

    public static void start() {
        ActivityUtils.startActivity(ReserveHomeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityReserveHomeBinding.inflate(getLayoutInflater());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setTextColor(Color.WHITE);
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("宴会预定");
        mBinding.llTitleBarRoot.ivTitleBarLeft.setImageResource(R.mipmap.ic_back);

        setContentView(mBinding.getRoot());

        mBinding.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BanquetStepManagerActivity.start(null,0);
            }
        });

        mLunchFragment = RestaurantListFragment.newInstance();
        mDinnerFragment = RestaurantListFragment.newInstance();


        mBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return mLunchFragment;
                }

                return mDinnerFragment;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "午餐";
                }
                if (position == 1) {
                    return "晚餐";
                }

                return super.getPageTitle(position);
            }
        });

        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);


        mCalendar = Calendar.getInstance();
        mBinding.llBeforeDayContaienr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar.add(Calendar.DATE, -1);
                setDateUI();
            }
        });
        mBinding.llAfterDayContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar.add(Calendar.DATE, 1);
                setDateUI();
            }
        });
        mBinding.llDateContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDayDialog dialog = new SelectDayDialog(getActivity());
                dialog.setOnDayClick(new MyMonthView.OnDayClick() {
                    @Override
                    public void onDayClick(Calendar c) {
                        mCalendar=c;
                        setDateUI();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        setDateUI();
    }

    private void setDateUI() {

        int year = mCalendar.get(Calendar.YEAR);
        int month = mCalendar.get(Calendar.MONTH) + 1;
        int day = mCalendar.get(Calendar.DATE);
        int week = mCalendar.get(Calendar.DAY_OF_WEEK);
        String weekStr = null;
        switch (week) {
            case 1:
                weekStr = "周日";
                break;
            case 2:
                weekStr = "周一";
                break;
            case 3:
                weekStr = "周二";
                break;
            case 4:
                weekStr = "周三";
                break;
            case 5:
                weekStr = "周四";
                break;
            case 6:
                weekStr = "周五";
                break;
            case 7:
                weekStr = "周六";
                break;
        }

        mBinding.tvDate.setText(month + "月" + day + "日 " + weekStr);

        String monthStr = month < 10 ? "0" + month : month + "";
        String dayStr = day < 10 ? "0" + day : day + "";

        String date = year + "-" + monthStr + "-" + dayStr;
        mLunchFragment.setData(date, "1");
        mDinnerFragment.setData(date, "2");
    }
}
