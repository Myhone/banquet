package com.lingyan.banquet.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.JsonObject;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityBanquetOrderListBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.ui.order.bean.OrderFilterCondition;

import static com.lingyan.banquet.global.BanquetCelebrationType.TYPE_BANQUET;


/**
 * Created by _hxb on 2021/1/3.
 */

public class OrderListActivity extends BaseActivity {

    private ActivityBanquetOrderListBinding mBinding;
    private int mType;
    private OrderListFragment mFragment1;
    private OrderListFragment mFragment2;


    public static void start(int type) {
        Intent intent = new Intent(Utils.getApp(), OrderListActivity.class);
        intent.putExtra("type", type);
        ActivityUtils.startActivity(intent);
    }

    /**
     *
     * @param tab 0-1
     * @param json1 订单的筛选信息
     * @param json2 场次的筛选信息
     */
    public static void start(int tab,String json1 ,String json2) {
        Intent intent = new Intent(Utils.getApp(), OrderListActivity.class);
        intent.putExtra("json1", json1);
        intent.putExtra("json2", json2);
        intent.putExtra("tab", tab);
        ActivityUtils.startActivity(intent);
        LogUtils.i("json1",json1);
        LogUtils.i("json2",json2);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String json1 = intent.getStringExtra("json1");
        String json2 = intent.getStringExtra("json2");
        if (json1 != null) {
            OrderFilterCondition condition = GsonUtils.fromJson(json1, OrderFilterCondition.class);
            mType = condition.type;
        } else {
            mType = intent.getIntExtra("type", TYPE_BANQUET);
        }
        mBinding = ActivityBanquetOrderListBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        if (mType == TYPE_BANQUET) {
            mBinding.llTitleBarRoot.tvTitleBarTitle.setText("宴会订单");
        } else {
            mBinding.llTitleBarRoot.tvTitleBarTitle.setText("庆典订单");
        }

        if(json1!=null){
            mFragment1 = OrderListFragment.newInstance(HttpURLs.banquetOrderList, json1);
            mFragment2 = OrderListFragment.newInstance(HttpURLs.banquetOrderNumberList, json2);
        }else {
            mFragment1 = OrderListFragment.newInstance(HttpURLs.banquetOrderList, mType);
            mFragment2 = OrderListFragment.newInstance(HttpURLs.banquetOrderNumberList, mType);
        }



        mBinding.viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return mFragment1;
                }
                if (position == 1) {
                    return mFragment2;
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
                if (mType == TYPE_BANQUET) {
                    if (position == 0) {
                        return "宴会订单";
                    }
                    if (position == 1) {
                        return "宴会场次";
                    }
                } else {
                    if (position == 0) {
                        return "庆典订单";
                    }
                    if (position == 1) {
                        return "庆典场次";
                    }
                }
                return super.getPageTitle(position);
            }
        });
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
        int tab = intent.getIntExtra("tab", 0);
        mBinding.viewPager.setCurrentItem(tab);

    }
}
