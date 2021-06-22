package com.lingyan.banquet.ui.data.july;

import android.content.Context;

import com.blankj.utilcode.util.StringUtils;
import com.google.android.material.tabs.TabLayout;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.LayoutPkRankJulyBinding;
import com.lingyan.banquet.global.Constant;
import com.lingyan.banquet.utils.MyImageUtils;

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
    private Context mContext;

    public DataPkController(LayoutPkRankJulyBinding binding, JulySiegeActivity dataHomeActivity) {
        mBinding = binding;
        mContext = dataHomeActivity;

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
        if (Constant.Str.PK_COUNTRY_QG.equals(title)
                || Constant.Str.PK_PERSONAL_QG.equals(title)
                || Constant.Str.PK_KING_SIGNED_QG.equals(title)) {
            mBinding.tvTitle.setTextColor(mContext.getResources().getColor(R.color.red_df3a32));
        } else {
            mBinding.tvTitle.setTextColor(mContext.getResources().getColor(R.color.black_33));
        }
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
        if (Constant.RankingTab.DATA1.equals(mTabType)) {
            list = data.getData1();
        } else if (Constant.RankingTab.DATA2.equals(mTabType)) {
            list = data.getData2();
        } else if (Constant.RankingTab.DATA3.equals(mTabType)) {
            list = data.getData3();
        } else if (Constant.RankingTab.DATA4.equals(mTabType)) {
            list = data.getData4();
        } else if (Constant.RankingTab.INCOME.equals(mTabType)) {
            list = data.getIncome();
        } else if (Constant.RankingTab.INCOME_RATE.equals(mTabType)) {
            list = data.getIncome_rate();
        } else if (Constant.RankingTab.DATA1_RATE.equals(mTabType)) {
            list = data.getData1_rate();
        } else if (Constant.RankingTab.DATA2_RATE.equals(mTabType)) {
            list = data.getData2_rate();
        } else if (Constant.RankingTab.DATA3_RATE.equals(mTabType)) {
            list = data.getData3_rate();
        } else if (Constant.RankingTab.CONTINUATION.equals(mTabType)) {
            list = data.getContinuation();
        }

        if (list == null || list.size() == 0) {
            initUI();
            return;
        }
//        PkDataBean.DataBean.DataChildBean.PersonBean dto1 = CollectionUtils.find(list, item -> item.getSort() == 1);
//        PkDataBean.DataBean.DataChildBean.PersonBean dto2 = CollectionUtils.find(list, item -> item.getSort() == 2);
//        PkDataBean.DataBean.DataChildBean.PersonBean dto3 = CollectionUtils.find(list, item -> item.getSort() == 3);
        PkDataBean.DataBean.DataChildBean.PersonBean dto1 = list.get(0);
        PkDataBean.DataBean.DataChildBean.PersonBean dto2 = null;
        PkDataBean.DataBean.DataChildBean.PersonBean dto3 = null;
        if (list.size() > 1) dto2 = list.get(1);
        if (list.size() > 2) dto3 = list.get(2);

        if (dto1 != null) {
            mBinding.tvName1.setText(dto1.getUser_name());
            mBinding.tvCount1.setText(dto1.getCount());
            String avatar = dto1.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mBinding.civAvatar1, avatar);
                mBinding.tvAvatar1.setText("");
            } else {
                mBinding.civAvatar1.setImageResource(R.color.gold);
                mBinding.tvAvatar1.setText(dto1.getAvatar_name());
            }
        }
        if (dto2 != null) {
            mBinding.tvName2.setText(dto2.getUser_name());
            mBinding.tvCount2.setText(dto2.getCount());
            String avatar = dto2.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mBinding.civAvatar2, avatar);
                mBinding.tvAvatar2.setText("");
            } else {
                mBinding.civAvatar2.setImageResource(R.color.gold);
                mBinding.tvAvatar2.setText(dto2.getAvatar_name());
            }
        }
        if (dto3 != null) {
            mBinding.tvName3.setText(dto3.getUser_name());
            mBinding.tvCount3.setText(dto3.getCount());
            String avatar = dto3.getAvatar();
            if (!StringUtils.isEmpty(avatar)) {
                MyImageUtils.displayUseImageServer(mBinding.civAvatar3, avatar);
                mBinding.tvAvatar3.setText("");
            } else {
                mBinding.civAvatar3.setImageResource(R.color.gold);
                mBinding.tvAvatar3.setText(dto3.getAvatar_name());
            }
        }

    }
}
