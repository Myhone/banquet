package com.lingyan.banquet.ui.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.App;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityPkListBinding;
import com.lingyan.banquet.databinding.LayoutPkRankBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.adapter.PkListAdapter;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.ui.data.bean.NetPkData;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/28.
 */

public class PkListActivity extends BaseActivity {

    private ActivityPkListBinding mBinding;
    private ConditionFilter mConditionFilter;
    private LayoutPkRankBinding mHeadBinding;
    private int mTabType = 1;
    private String[] mStrings = {"实际收入", "完成合同总额", "均单", "签定数", "锁台数", "意向数", "商机数"};
    private List<NetPkData.DataDTO.DataInfoDTO> mRecData;
    private PkListAdapter mAdapter;
    private String mJson;

    public static void start(String json) {
        Intent intent = new Intent(App.sApp, PkListActivity.class);
        intent.putExtra("json", json);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPkListBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mJson = getIntent().getStringExtra("json");
        mConditionFilter = GsonUtils.fromJson(mJson, ConditionFilter.class);
        mHeadBinding = LayoutPkRankBinding.inflate(getLayoutInflater());
        mHeadBinding.llLookList.setVisibility(View.GONE);
        mHeadBinding.llPkTitle.setVisibility(View.GONE);
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("PK榜");
        initUI();
        for (int i = 0; i < mStrings.length; i++) {
            TabLayout.Tab tab = mHeadBinding.tabLayout.newTab();
            tab.setText(mStrings[i]);
            mHeadBinding.tabLayout.addTab(tab);
        }
        mHeadBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                mTabType = (position + 1);
                refresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mRecData = new ArrayList<>();
        mAdapter = new PkListAdapter(mRecData);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.addHeaderView(mHeadBinding.getRoot());
        refresh();

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

        JsonObject jo = (JsonObject) JsonParser.parseString(mJson);
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
                        NetPkData.DataDTO.DataInfoDTO dto1 = null;
                        if (ObjectUtils.isNotEmpty(list) && list.size() >= 1) {
                            dto1 = list.get(0);
                        }
                        NetPkData.DataDTO.DataInfoDTO dto2 = null;
                        if (ObjectUtils.isNotEmpty(list) && list.size() >= 2) {
                            dto2 = list.get(1);
                        }
                        NetPkData.DataDTO.DataInfoDTO dto3 = null;
                        if (ObjectUtils.isNotEmpty(list) && list.size() >= 3) {
                            dto3 = list.get(2);
                        }

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

                        mRecData.clear();

                        if(list.size()>3){
                            List<NetPkData.DataDTO.DataInfoDTO> subList = list.subList(3, list.size());
                            mRecData.addAll(subList);
                        }
                        mAdapter.setNewData(mRecData);

                    }
                });
    }
}
