package com.lingyan.banquet.ui.data.controller;

import android.view.View;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.LayoutPkRankBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.DataHomeActivity;
import com.lingyan.banquet.ui.data.PkListActivity;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.ui.data.bean.NetPkData;
import com.lingyan.banquet.ui.data.bean.PersonBean;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class DataPkController {
    private LayoutPkRankBinding mBinding;
    private int mTabType = 1;
    private String[] mStrings = {"实际收入", "完成合同总额", "均单", "签定数", "锁台数", "意向数", "商机数"};

    public DataPkController(LayoutPkRankBinding binding, DataHomeActivity dataHomeActivity) {
        mBinding = binding;

        initUI();

        for (int i = 0; i < mStrings.length; i++) {
            TabLayout.Tab tab = binding.tabLayout.newTab();
            tab.setText(mStrings[i]);
            mBinding.tabLayout.addTab(tab);
        }
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mTabType = (position + 1);
                refresh(dataHomeActivity.getConditionFilter());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        binding.llLookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConditionFilter filter = dataHomeActivity.getConditionFilter();
                String json = GsonUtils.toJson(filter);
                PkListActivity.start(json);

            }
        });
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

    public void refresh(ConditionFilter condition) {
        String json = GsonUtils.toJson(condition);
        JsonObject jo = (JsonObject) JsonParser.parseString(json);
        jo.addProperty("order", 5);
        jo.addProperty("tab_type", mTabType);
        OkGo.<NetPkData>post(HttpURLs.screenData1)
                .upJson(jo.toString())
                .execute(new JsonCallback<NetPkData>() {
                    @Override
                    public void onSuccess(Response<NetPkData> response) {
                        NetPkData body = response.body();
                        NetPkData.DataDTO data = body.getData();
                        if (data == null) {
                            return;
                        }
                        initUI();
                        List<NetPkData.DataDTO.DataInfoDTO> data1 = data.getData1();
                        List<NetPkData.DataDTO.DataInfoDTO> data2 = data.getData2();
                        List<NetPkData.DataDTO.DataInfoDTO> data3 = data.getData3();
                        List<NetPkData.DataDTO.DataInfoDTO> data4 = data.getData4();
                        List<NetPkData.DataDTO.DataInfoDTO> income = data.getIncome();
                        List<NetPkData.DataDTO.DataInfoDTO> final_amount = data.getFinal_amount();
                        List<NetPkData.DataDTO.DataInfoDTO> avgAmount = data.getAvg_amount();

                        List<NetPkData.DataDTO.DataInfoDTO> list;
                        if (mTabType == 1) {
                            list = income;
                        } else if (mTabType == 2) {
                            list = final_amount;
                        } else if (mTabType == 3) {
                            list = avgAmount;
                        } else if (mTabType == 4) {
                            list = data1;
                        } else if (mTabType == 5) {
                            list = data2;
                        } else if (mTabType == 6) {
                            list = data3;
                        } else {
                            list = data4;
                        }

                        NetPkData.DataDTO.DataInfoDTO dto1 = CollectionUtils.find(list, new CollectionUtils.Predicate<NetPkData.DataDTO.DataInfoDTO>() {
                            @Override
                            public boolean evaluate(NetPkData.DataDTO.DataInfoDTO item) {
                                return item.getSort() == 1;
                            }
                        });
                        NetPkData.DataDTO.DataInfoDTO dto2 = CollectionUtils.find(list, new CollectionUtils.Predicate<NetPkData.DataDTO.DataInfoDTO>() {
                            @Override
                            public boolean evaluate(NetPkData.DataDTO.DataInfoDTO item) {
                                return item.getSort() == 2;
                            }
                        });
                        NetPkData.DataDTO.DataInfoDTO dto3 = CollectionUtils.find(list, new CollectionUtils.Predicate<NetPkData.DataDTO.DataInfoDTO>() {
                            @Override
                            public boolean evaluate(NetPkData.DataDTO.DataInfoDTO item) {
                                return item.getSort() == 3;
                            }
                        });

                        if (dto1 != null) {
                            mBinding.tvName1.setText(dto1.getUser_name());
                            mBinding.tvCount1.setText(dto1.getCount());
                            String avatar = dto1.getAvatar();
                            if (!StringUtils.isEmpty(avatar)) {
                                MyImageUtils.displayUseImageServer(mBinding.civAvatar1, avatar);
                            }
                        }
                        if (dto2 != null) {
                            mBinding.tvName2.setText(dto2.getUser_name());
                            mBinding.tvCount2.setText(dto2.getCount());
                            String avatar = dto2.getAvatar();
                            if (!StringUtils.isEmpty(avatar)) {
                                MyImageUtils.displayUseImageServer(mBinding.civAvatar2, avatar);
                            }
                        }
                        if (dto3 != null) {
                            mBinding.tvName3.setText(dto3.getUser_name());
                            mBinding.tvCount3.setText(dto3.getCount());
                            String avatar = dto3.getAvatar();
                            if (!StringUtils.isEmpty(avatar)) {
                                MyImageUtils.displayUseImageServer(mBinding.civAvatar3, avatar);
                            }
                        }

                    }
                });
    }
}
