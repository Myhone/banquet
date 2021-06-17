package com.lingyan.banquet.ui.data.july;

import android.view.View;

import com.blankj.utilcode.util.CollectionUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.LayoutPkRankJulyBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.PkListActivity;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */
public class DataPkController {
    private LayoutPkRankJulyBinding mBinding;
    //全国0/本公司1
    private int mType = 0;
    private String mTabType = "";
    private PkDataBean.DataBean data;
    //个人详细数据
    private List<PkDataBean.DataBean.DataChildBean.PersonBean> data1, data2, data3, data4, income, continuation;

    public DataPkController(LayoutPkRankJulyBinding binding, JulySiegeActivity dataHomeActivity) {
        mBinding = binding;

        initUI();

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTabType = (String) tab.getTag();
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

        binding.tvBanquetType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<>();
                list.add("全国");
                list.add("本公司");
                PickerListDialog dialog = new PickerListDialog(dataHomeActivity);
                dialog.items(list);
                dialog.itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                    @Override
                    public void onItemSelected(int position, String text, PickerListDialog dialog) {
                        mType = position;
                        binding.tvBanquetType.setText(text);
                        dialog.dismiss();
                        setData(0, 1);
                    }
                });
                dialog.show();
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

    public void refresh(ConditionFilter condition, HashMap<String, String> tabList, int order) {
        mBinding.tabLayout.removeAllTabs();
        for (String key : tabList.keySet()) {
            TabLayout.Tab tab = mBinding.tabLayout.newTab();
            tab.setTag(key);
            tab.setText(tabList.get(key));
            mBinding.tabLayout.addTab(tab);
        }

        if (order == 50) {
            mBinding.tvTitle.setText("个人PK榜");
            mBinding.tvBanquetType.setVisibility(View.VISIBLE);
        } else if (order == 51) {
            mBinding.tvTitle.setText("部门PK榜");
            mBinding.tvBanquetType.setVisibility(View.VISIBLE);
        } else if (order == 52) {
            mBinding.tvTitle.setText("全国PK榜");
            mBinding.tvBanquetType.setVisibility(View.INVISIBLE);
        } else if (order == 53) {
            mBinding.tvTitle.setText("连单王");
            mBinding.tvBanquetType.setVisibility(View.VISIBLE);
        } else {
            mBinding.tvTitle.setText("PK榜");
            mBinding.tvBanquetType.setVisibility(View.VISIBLE);
        }

        String json = GsonUtils.toJson(condition);
        JsonObject jo = (JsonObject) JsonParser.parseString(json);
        jo.addProperty("order", order);
        OkGo.<PkDataBean>post(HttpURLs.screen2Data1)
                .upJson(jo.toString())
                .execute(new JsonCallback<PkDataBean>() {
                    @Override
                    public void onSuccess(Response<PkDataBean> response) {
                        PkDataBean body = response.body();
                        if (body != null) {
                            data = body.getData();
                            if (data == null) {
                                return;
                            }
                            initUI();

                            setData(body.getCode(), order);
                        }
                    }

                    @Override
                    public void onError(Response<PkDataBean> response) {
                        super.onError(response);
                    }
                });
    }

    private void setData(int code, int order) {
        if (data == null) return;
        if (mType == 1) {
            //公司
            PkDataBean.DataBean.DataChildBean dataGs = data.getGs();
            if (dataGs == null) {
                initUI();
                return;
            }
            data1 = dataGs.getData1();
            data2 = dataGs.getData2();
            data3 = dataGs.getData3();
            data4 = dataGs.getData4();
            income = dataGs.getIncome();
            continuation = dataGs.getContinuation();
        } else {
            //全国
            PkDataBean.DataBean.DataChildBean dataQg = data.getQg();
            if (dataQg == null) {
                initUI();
                return;
            }
            data1 = dataQg.getData1();
            data2 = dataQg.getData2();
            data3 = dataQg.getData3();
            data4 = dataQg.getData4();
            income = dataQg.getIncome();
            continuation = dataQg.getContinuation();
        }

        List<PkDataBean.DataBean.DataChildBean.PersonBean> list = new ArrayList<>();
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
}
