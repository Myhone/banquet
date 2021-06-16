package com.lingyan.banquet.ui.finance;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.ActivityUtils;
import com.lingyan.banquet.App;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityFinanceManageBinding;

/**
 * 财务管理
 * Created by _hxb on 2021/1/6.
 */

public class FinanceManageActivity extends BaseActivity {

    private ActivityFinanceManageBinding mBinding;

    public static void start() {
        start(0);
    }

    public static void start(int tab) {
        Intent intent = new Intent(App.sApp,FinanceManageActivity.class);
        intent.putExtra("tab",tab);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFinanceManageBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Intent intent = getIntent();
        int tab = intent.getIntExtra("tab", 0);

        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("财务管理");
        FinanceSummaryFragment financeSummaryFragment = FinanceSummaryFragment.newInstance();
        FrontBackMoneyManageFragment frontMoneyManageFragment = FrontBackMoneyManageFragment.newInstance(1);
        FrontBackMoneyManageFragment backMoneyManageFragment = FrontBackMoneyManageFragment.newInstance(2);
        mBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return financeSummaryFragment;
                }
                if (position == 1) {
                    return frontMoneyManageFragment;
                }
                if (position == 2) {
                    return backMoneyManageFragment;
                }


                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "财务汇总";
                }
                if (position == 1) {
                    return "定金管理";
                }
                if (position == 2) {
                    return "尾款管理";
                }

                return super.getPageTitle(position);
            }
        });
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        mBinding.viewPager.setCurrentItem(tab);
    }
}
