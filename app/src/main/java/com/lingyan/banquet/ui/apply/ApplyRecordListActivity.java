package com.lingyan.banquet.ui.apply;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.ActivityUtils;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityApplyListBinding;
import com.lingyan.banquet.global.ApplyRecordType;

/**
 * Created by _hxb on 2021/1/3.
 */

public class ApplyRecordListActivity extends BaseActivity {

    private ActivityApplyListBinding mBinding;

    public static void start() {
        ActivityUtils.startActivity(ApplyRecordListActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityApplyListBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("审批记录");
        ApplyListFragment fragment1 = ApplyListFragment.newInstance(ApplyRecordType.TYPE_SEND);
        ApplyListFragment fragment2 = ApplyListFragment.newInstance(ApplyRecordType.TYPE_RECEIVED);
        mBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return fragment1;
                }
                if (position == 1) {
                    return fragment2;
                }


                return null;
            }

            @Override
            public int getCount() {
                return 2;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                if (position == 0) {
                    return "我发起的";
                }
                if (position == 1) {
                    return "我收到的";
                }

                return super.getPageTitle(position);
            }
        });
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);

    }
}
