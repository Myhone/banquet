package com.lingyan.banquet.ui.data.july;

import android.view.View;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.LayoutPkRankJulyBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */
public class DataPkController {
    private LayoutPkRankJulyBinding mBinding;
    //全国0/本公司1
    private int mType = 0;
    private String mTabType = "";
    private PkItemBean tabList;
    //榜单
    private PkDataBean.DataBean.DataChildBean data;

    public DataPkController(LayoutPkRankJulyBinding binding, JulySiegeActivity dataHomeActivity) {
        mBinding = binding;

        initUI();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabType = (String) tab.getTag();
                setData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        binding.llLookList.setOnClickListener(v -> {
            //查看榜单详情
            JulyPkListActivity.start(data, tabList, mBinding.tvTitle.getText().toString());
        });

//        binding.tvBanquetType.setOnClickListener(v -> {
//            List<String> list = new ArrayList<>();
//            list.add("全国");
//            list.add("本公司");
//            PickerListDialog dialog = new PickerListDialog(dataHomeActivity);
//            dialog.items(list);
//            dialog.itemSelectedCallBack((position, text, dialog1) -> {
//                mType = position;
//                binding.tvBanquetType.setText(text);
//                dialog1.dismiss();
//                setData();
//            });
//            dialog.show();
//        });
    }

    private void initUI() {
        mBinding.civAvatar1.setImageResource(R.mipmap.ic_default_image);
        mBinding.civAvatar2.setImageResource(R.mipmap.ic_default_image);
        mBinding.civAvatar3.setImageResource(R.mipmap.ic_default_image);
        mBinding.tvName1.setText("");
        mBinding.tvName2.setText("");
        mBinding.tvName3.setText("");
        mBinding.tvCount1.setText("");
        mBinding.tvCount2.setText("");
        mBinding.tvCount3.setText("");

    }

    public void refresh(PkItemBean tabList, PkDataBean.DataBean.DataChildBean data, String title) {
        this.data = data;
        mBinding.tvTitle.setText(title);
        mBinding.tabLayout.removeAllTabs();
        this.tabList = tabList;
        for (PkItemBean.DataBean dataBean : tabList.getData()) {
            TabLayout.Tab tab = mBinding.tabLayout.newTab();
            tab.setTag(dataBean.getKey());
            tab.setText(dataBean.getTitle());
            mBinding.tabLayout.addTab(tab);
        }

        setData();
    }

    private void setData() {
        if (data == null) {
            initUI();
            return;
        }

        List<PkDataBean.DataBean.DataChildBean.PersonBean> list = new ArrayList<>();
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
            initUI();
            return;
        }
        PkDataBean.DataBean.DataChildBean.PersonBean dto1 = CollectionUtils.find(list, item -> item.getSort() == 1);
        PkDataBean.DataBean.DataChildBean.PersonBean dto2 = CollectionUtils.find(list, item -> item.getSort() == 2);
        PkDataBean.DataBean.DataChildBean.PersonBean dto3 = CollectionUtils.find(list, item -> item.getSort() == 3);

        if (dto1 != null) {
            mBinding.tvName1.setText(dto1.getLong_user_name());
            mBinding.tvCount1.setText(dto1.getLong_count());
            String avatar = dto1.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mBinding.civAvatar1, avatar);
            }
        }
        if (dto2 != null) {
            mBinding.tvName2.setText(dto2.getLong_user_name());
            mBinding.tvCount2.setText(dto2.getLong_count());
            String avatar = dto2.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mBinding.civAvatar2, avatar);
            }
        }
        if (dto3 != null) {
            mBinding.tvName3.setText(dto3.getLong_user_name());
            mBinding.tvCount3.setText(dto3.getLong_count());
            String avatar = dto3.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mBinding.civAvatar3, avatar);
            }
        }

    }
}
