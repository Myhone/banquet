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
    //公司榜单
    private PkDataBean.DataBean.DataChildBean dataGs;
    //全国榜单
    private PkDataBean.DataBean.DataChildBean dataQg;

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

        binding.llLookList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看榜单详情
                JulyPkListActivity.start(dataGs, dataQg, tabList, mBinding.tvTitle.getText().toString());
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
                        setData();
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

    public void refresh(ConditionFilter condition, PkItemBean tabList, int order) {
        mBinding.tabLayout.removeAllTabs();
        this.tabList = tabList;
        for (PkItemBean.DataBean dataBean: tabList.getData()) {
            TabLayout.Tab tab = mBinding.tabLayout.newTab();
            tab.setTag(dataBean.getKey());
            tab.setText(dataBean.getTitle());
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
            mTabType = "continuation";
            mBinding.tvBanquetType.setVisibility(View.VISIBLE);
            mBinding.tabLayout.setVisibility(View.GONE);
        } else {
            mBinding.tvTitle.setText("PK榜");
            mBinding.tvBanquetType.setVisibility(View.VISIBLE);
        }

        String json = GsonUtils.toJson(condition);
        JsonObject jo = (JsonObject) JsonParser.parseString(json);
        jo.addProperty("order", order);
        OkGo.<PkDataBean>post(HttpURLs.screen2Data1)
                .upJson(jo.toString())
                .execute(new Callback<PkDataBean>() {
                    @Override
                    public void onStart(Request<PkDataBean, ? extends Request> request) {
                    }

                    @Override
                    public void onSuccess(Response<PkDataBean> response) {
                    }

                    @Override
                    public void onCacheSuccess(Response<PkDataBean> response) {
                    }

                    @Override
                    public void onError(Response<PkDataBean> response) {
                    }

                    @Override
                    public void onFinish() {
                        initUI();
                        setData();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                    }

                    @Override
                    public PkDataBean convertResponse(okhttp3.Response response) throws Throwable {
                        Gson gson = new Gson();
                        PkDataBean dataBean = new PkDataBean();
                        JsonParser parser = new JsonParser();
                        JsonElement element = parser.parse(response.body().string());
                        if (element.isJsonObject()) {//假设最外层是object
                            // 把JsonElement对象转换成JsonObject
                            JsonObject JsonObject = element.getAsJsonObject();
                            JsonElement jsonElement = JsonObject.get("data");
                            if (jsonElement.isJsonObject()) {
                                JsonObject JsonObject1 = jsonElement.getAsJsonObject();
                                JsonElement jsonElementQg = JsonObject1.get("qg");
                                if (jsonElementQg.isJsonObject()) {
                                    dataQg = gson.fromJson(jsonElementQg, PkDataBean.DataBean.DataChildBean.class);
                                } else if (jsonElementQg.isJsonArray()) {
                                    Type type = new TypeToken<List<PkDataBean.DataBean.DataChildBean>>() {
                                    }.getType();
                                    // 把JsonElement对象转换成JsonArray
//                                List<PkDataBean.DataBean.DataChildBean> list = gson.fromJson(jsonElement, type);
                                    dataQg = gson.fromJson(jsonElementQg, type);
                                }
                                JsonElement jsonElementGs = JsonObject1.get("gs");
                                if (jsonElementGs.isJsonObject()) {
                                    dataGs = gson.fromJson(jsonElementGs, PkDataBean.DataBean.DataChildBean.class);
                                } else if (jsonElementGs.isJsonArray()) {
                                    Type type = new TypeToken<List<PkDataBean.DataBean.DataChildBean>>() {
                                    }.getType();
                                    // 把JsonElement对象转换成JsonArray
//                                List<PkDataBean.DataBean.DataChildBean> list = gson.fromJson(jsonElement, type);
                                    dataGs = gson.fromJson(jsonElementGs, type);
                                }
                            }
                        }
                        return dataBean;
                    }
                });
    }

    private void setData() {
        //当前榜单(查看完整榜单使用)
        PkDataBean.DataBean.DataChildBean dataCurrent;

        if (mType == 1) {
            dataCurrent = dataGs;//公司
        } else {
            dataCurrent = dataQg;//全国
        }

        if (dataCurrent == null) {
            initUI();
            return;
        }

        List<PkDataBean.DataBean.DataChildBean.PersonBean> list = new ArrayList<>();
        if ("data1".equals(mTabType)) {
            list = dataCurrent.getData1();
        } else if ("data2".equals(mTabType)) {
            list = dataCurrent.getData2();
        } else if ("data3".equals(mTabType)) {
            list = dataCurrent.getData3();
        } else if ("data4".equals(mTabType)) {
            list = dataCurrent.getData4();
        } else if ("income".equals(mTabType)) {
            list = dataCurrent.getIncome();
        } else if ("income_rate".equals(mTabType)) {
            list = dataCurrent.getIncome_rate();
        } else if ("data1_rate".equals(mTabType)) {
            list = dataCurrent.getData1_rate();
        } else if ("data2_rate".equals(mTabType)) {
            list = dataCurrent.getData2_rate();
        } else if ("data3_rate".equals(mTabType)) {
            list = dataCurrent.getData3_rate();
        } else if ("continuation".equals(mTabType)) {
            list = dataCurrent.getContinuation();
        }

        if (list == null || list.size() == 0) {
            initUI();
            return;
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
