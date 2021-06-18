package com.lingyan.banquet.ui.data.july;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.android.material.tabs.TabLayout;
import com.lingyan.banquet.App;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityJulyPkListBinding;
import com.lingyan.banquet.databinding.LayoutPkRankJulyBinding;
import com.lingyan.banquet.utils.MyImageUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by _hxb on 2021/2/28.
 */

public class JulyPkListActivity extends BaseActivity {

    private ActivityJulyPkListBinding mBinding;
    private LayoutPkRankJulyBinding mHeadBinding;
    private String mTabType = "";
    private List<PkDataBean.DataBean.DataChildBean.PersonBean> list;
    private PkDataBean.DataBean.DataChildBean dataList;
    private List<PkDataBean.DataBean.DataChildBean.PersonBean> data1, data2, data3, data4, income, continuation;
    private HashMap<String, String> tabList;
    private JulyPkListAdapter mAdapter;
    private String title;

    public static void start(PkDataBean.DataBean.DataChildBean dataList, HashMap<String, String> tabList, String title) {
        Intent intent = new Intent(App.sApp, JulyPkListActivity.class);
        intent.putExtra("dataList", dataList);
        intent.putExtra("tabList", tabList);
        intent.putExtra("title", title);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityJulyPkListBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        dataList = getIntent().getParcelableExtra("dataList");
        tabList = (HashMap<String, String>) getIntent().getSerializableExtra("tabList");
        title = getIntent().getStringExtra("title");
        mHeadBinding = LayoutPkRankJulyBinding.inflate(getLayoutInflater());
        mHeadBinding.llLookList.setVisibility(View.GONE);
        mHeadBinding.llPkTitle.setVisibility(View.GONE);
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText(title);

        initUI();

        list = new ArrayList<>();
        mAdapter = new JulyPkListAdapter(list);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(mHeadBinding.getRoot());

        mHeadBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabType = (String) tab.getTag();
                refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (String key : tabList.keySet()) {
            TabLayout.Tab tab = mHeadBinding.tabLayout.newTab();
            tab.setTag(key);
            tab.setText(tabList.get(key));
            mHeadBinding.tabLayout.addTab(tab);
        }
    }

    private void initUI() {
        mHeadBinding.civAvatar1.setImageResource(R.mipmap.ic_default_image);
        mHeadBinding.civAvatar2.setImageResource(R.mipmap.ic_default_image);
        mHeadBinding.civAvatar3.setImageResource(R.mipmap.ic_default_image);
        mHeadBinding.tvName1.setText("");
        mHeadBinding.tvName2.setText("");
        mHeadBinding.tvName3.setText("");
        mHeadBinding.tvCount1.setText("");
        mHeadBinding.tvCount2.setText("");
        mHeadBinding.tvCount3.setText("");
    }

    private void refresh() {
        if (dataList == null) {
            initUI();
            return;
        }
        data1 = dataList.getData1();
        data2 = dataList.getData2();
        data3 = dataList.getData3();
        data4 = dataList.getData4();
        income = dataList.getIncome();
        continuation = dataList.getContinuation();

        if ("连单王".equals(title)){
            mHeadBinding.tabLayout.setVisibility(View.GONE);
            mTabType = "continuation";
        }

        if ("data1".equals(mTabType)) {
            list = data1;
        } else if ("data2".equals(mTabType)) {
            list = data2;
        } else if ("data3".equals(mTabType)) {
            list = data3;
        } else if ("data4".equals(mTabType)) {
            list = data4;
        } else if ("income".equals(mTabType)) {
            list = income;
        } else if ("continuation".equals(mTabType)) {
            list = continuation;
        }
        mAdapter.setNewData(list);
        PkDataBean.DataBean.DataChildBean.PersonBean dto1 = CollectionUtils.find(list, new CollectionUtils.Predicate<PkDataBean.DataBean.DataChildBean.PersonBean>() {
            @Override
            public boolean evaluate(PkDataBean.DataBean.DataChildBean.PersonBean item) {
                return item.getSort() == 1;
            }
        });
        PkDataBean.DataBean.DataChildBean.PersonBean dto2 = CollectionUtils.find(list, new CollectionUtils.Predicate<PkDataBean.DataBean.DataChildBean.PersonBean>() {
            @Override
            public boolean evaluate(PkDataBean.DataBean.DataChildBean.PersonBean item) {
                return item.getSort() == 2;
            }
        });
        PkDataBean.DataBean.DataChildBean.PersonBean dto3 = CollectionUtils.find(list, new CollectionUtils.Predicate<PkDataBean.DataBean.DataChildBean.PersonBean>() {
            @Override
            public boolean evaluate(PkDataBean.DataBean.DataChildBean.PersonBean item) {
                return item.getSort() == 3;
            }
        });

        if (dto1 != null) {
            mHeadBinding.tvName1.setText(dto1.getUser_name());
            mHeadBinding.tvCount1.setText(dto1.getCount());
            String avatar = dto1.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mHeadBinding.civAvatar1, avatar);
            }
        }
        if (dto2 != null) {
            mHeadBinding.tvName2.setText(dto2.getUser_name());
            mHeadBinding.tvCount2.setText(dto2.getCount());
            String avatar = dto2.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mHeadBinding.civAvatar2, avatar);
            }
        }
        if (dto3 != null) {
            mHeadBinding.tvName3.setText(dto3.getUser_name());
            mHeadBinding.tvCount3.setText(dto3.getCount());
            String avatar = dto3.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mHeadBinding.civAvatar3, avatar);
            }
        }

    }
}
