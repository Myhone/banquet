package com.lingyan.banquet.ui.data.july;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.android.material.tabs.TabLayout;
import com.lingyan.banquet.App;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityJulyPkListBinding;
import com.lingyan.banquet.databinding.LayoutPkRankJulyBinding;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.views.dialog.PickerListDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/28.
 */

public class JulyPkListActivity extends BaseActivity {

    private ActivityJulyPkListBinding mBinding;
    private LayoutPkRankJulyBinding mHeadBinding;
    private String mTabType = "";
    private List<PkDataBean.DataBean.DataChildBean.PersonBean> list;
    private PkDataBean.DataBean.DataChildBean data;
    private PkItemBean tabList;
    private JulyPkListAdapter mAdapter;
    private String title;
    //全国0/本公司1
//    private int mType = 0;

    public static void start(PkDataBean.DataBean.DataChildBean data, PkItemBean tabList, String title) {
        Intent intent = new Intent(App.sApp, JulyPkListActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("tabList", tabList);
        intent.putExtra("title", title);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityJulyPkListBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        data = getIntent().getParcelableExtra("data");
        tabList = getIntent().getParcelableExtra("tabList");
        title = getIntent().getStringExtra("title");
        mHeadBinding = LayoutPkRankJulyBinding.inflate(getLayoutInflater());
        mHeadBinding.llLookList.setVisibility(View.GONE);
        mHeadBinding.llPkTitle.setVisibility(View.GONE);
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText(title);

        list = new ArrayList<>();
        mAdapter = new JulyPkListAdapter(list);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(mHeadBinding.getRoot());
        initUI();

        if ("连单王".equals(title)) {
            mHeadBinding.llTopThree.setVisibility(View.GONE);
            mHeadBinding.llKingSigned.setVisibility(View.VISIBLE);
            mTabType = "continuation";
            refresh();
        }

        if ("酒店连单王".equals(title)) {
            mHeadBinding.llTopThree.setVisibility(View.GONE);
            mTabType = "continuation";
            refresh();
        }

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

        if (tabList != null)
            for (PkItemBean.DataBean dataBean : tabList.getData()) {
                TabLayout.Tab tab = mHeadBinding.tabLayout.newTab();
                tab.setTag(dataBean.getKey());
                tab.setText(dataBean.getTitle());
                mHeadBinding.tabLayout.addTab(tab);
            }

//        mBinding.llTitleBarRoot.tvBanquetType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<String> list = new ArrayList<>();
//                list.add("全国");
//                list.add("本公司");
//                PickerListDialog dialog = new PickerListDialog(JulyPkListActivity.this);
//                dialog.items(list);
//                dialog.itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
//                    @Override
//                    public void onItemSelected(int position, String text, PickerListDialog dialog) {
//                        mType = position;
//                        mBinding.llTitleBarRoot.tvBanquetType.setText(text);
//                        dialog.dismiss();
//                        refresh();
//                    }
//                });
//                dialog.show();
//            }
//        });
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
        mAdapter.setNewData(list);
    }

    private void refresh() {
        if (data == null) {
            initUI();
            return;
        }

        if ("data1".equals(mTabType)) {
            list = data.getData1();
        } else if ("data2".equals(mTabType)) {
            list = data.getData2();
        } else if ("data3".equals(mTabType)) {
            list = data.getData3();
        } else if ("data4".equals(mTabType)) {
            list = data.getData4();
        } else if ("income".equals(mTabType)) {
            list = data.getIncome();
        } else if ("income_rate".equals(mTabType)) {
            list = data.getIncome_rate();
        } else if ("data1_rate".equals(mTabType)) {
            list = data.getData1_rate();
        } else if ("data2_rate".equals(mTabType)) {
            list = data.getData2_rate();
        } else if ("data3_rate".equals(mTabType)) {
            list = data.getData3_rate();
        } else if ("continuation".equals(mTabType)) {
            list = data.getContinuation();
        }

        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
            initUI();
            return;
        }
        mAdapter.setNewData(list);
//        PkDataBean.DataBean.DataChildBean.PersonBean dto1 = CollectionUtils.find(list, item -> item.getSort() == 1);
//        PkDataBean.DataBean.DataChildBean.PersonBean dto2 = CollectionUtils.find(list, item -> item.getSort() == 2);
//        PkDataBean.DataBean.DataChildBean.PersonBean dto3 = CollectionUtils.find(list, item -> item.getSort() == 3);
        PkDataBean.DataBean.DataChildBean.PersonBean dto1 = list.get(0);
        PkDataBean.DataBean.DataChildBean.PersonBean dto2 = null;
        PkDataBean.DataBean.DataChildBean.PersonBean dto3 = null;
        if (list.size() > 1) dto2 = list.get(1);
        if (list.size() > 2) dto3 = list.get(2);

        if (dto1 != null) {
            mHeadBinding.tvName1.setText(dto1.getUser_name());
            mHeadBinding.tvCount1.setText(dto1.getCount());
            String avatar = dto1.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mHeadBinding.civAvatar1, avatar);
                mHeadBinding.tvAvatar1.setText("");
            } else {
                mHeadBinding.civAvatar1.setImageResource(R.color.gold);
                mHeadBinding.tvAvatar1.setText(dto1.getAvatar_name());
            }
        }
        if (dto2 != null) {
            mHeadBinding.tvName2.setText(dto2.getUser_name());
            mHeadBinding.tvCount2.setText(dto2.getCount());
            String avatar = dto2.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mHeadBinding.civAvatar2, avatar);
                mHeadBinding.tvAvatar2.setText("");
            } else {
                mHeadBinding.civAvatar2.setImageResource(R.color.gold);
                mHeadBinding.tvAvatar2.setText(dto2.getAvatar_name());
            }
        }
        if (dto3 != null) {
            mHeadBinding.tvName3.setText(dto3.getUser_name());
            mHeadBinding.tvCount3.setText(dto3.getCount());
            String avatar = dto3.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mHeadBinding.civAvatar3, avatar);
                mHeadBinding.tvAvatar3.setText("");
            } else {
                mHeadBinding.civAvatar3.setImageResource(R.color.gold);
                mHeadBinding.tvAvatar3.setText(dto3.getAvatar_name());
            }
        }

    }
}
