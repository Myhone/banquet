package com.lingyan.banquet.ui.data.july;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityJulySiegeBinding;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.Constant;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.utils.StatusBarUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.Callback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * Created by _hxb on 2021/1/24.
 */

public class JulySiegeActivity extends BaseActivity implements OnRefreshListener {

    private ActivityJulySiegeBinding mBinding;
    private ConditionFilter mConditionFilter;
    //    private DataBasicController mBasicController;
    //    private DataCompleteController mCompleteController;
//    private DataNewAddController mNewAddController;
    //    private DataAnalyzeController mAnalyzeController;
//    private DataTargetController mTargetController;
//    private DataSuccessController mSuccessController;
    //private DataConvertController mConvertController;
    private DataPkController mPkCountry, mPkPersonalQg, mPkDepartmentGs, mPkPersonalGs;
    private String mAllDepartID;
    //??????
    private PkDataBean.DataBean.DataChildBean dataQg, dataGs, dataKingSignedQg, dataKingSignedGs;

    public static void start() {
        ActivityUtils.startActivity(JulySiegeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityJulySiegeBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        StatusBarUtil.setTranslucentForImageView(this, 0, mBinding.clBar);

        mBinding.tvGroupTime.setOnClickListener(v -> {
            /*int[] ints = new int[2];
            mBinding.rlConditionContainer.getLocationOnScreen(ints);
            GroupTimeSelectPop pop = new GroupTimeSelectPop(getActivity());
            pop.setData(mConditionFilter);
            pop.setHeight(ScreenUtils.getScreenHeight() - ints[1] - mBinding.rlConditionContainer.getMeasuredHeight());
            pop.showAsDropDown(mBinding.rlConditionContainer);*/
            JulyDataFilterActivity.start(getActivity(), 1, GsonUtils.toJson(mConditionFilter));
        });

        mBinding.flDataPkKingSignedQg.tvTitle.setText(Constant.Str.PK_KING_SIGNED_QG);
        mBinding.tvTitle.setText(Constant.Str.PK_KING_SIGNED_GS);
        mBinding.flDataPkKingSignedQg.llKingSigned.setOnClickListener(v -> {
            //??????????????????
            JulyPkListActivity.start(dataKingSignedQg, null, Constant.Str.PK_KING_SIGNED_QG);
        });

        mBinding.rlKingSignedGs.setOnClickListener(v -> {
            //??????????????????
            JulyPkListActivity.start(dataKingSignedGs, null, Constant.Str.PK_KING_SIGNED_GS);
        });

        mBinding.refreshLayout.setOnRefreshListener(this);
        mConditionFilter = new ConditionFilter();
        mConditionFilter.banquet_type = BanquetCelebrationType.TYPE_BANQUET;
        mConditionFilter.dept_id = new ArrayList<>();
        mConditionFilter.user_id = new ArrayList<>();
        UserInfoManager userInfoManager = UserInfoManager.getInstance();
        String isAdmin = userInfoManager.get(UserInfoManager.IS_ADMIN);
        mAllDepartID = userInfoManager.get(UserInfoManager.ALL_DEPART_ID);
        if (mAllDepartID == null) {
            mAllDepartID = "0";
        }
        if (StringUtils.equals(isAdmin, "1")) {
            mConditionFilter.dept_id.add(mAllDepartID);
            mConditionFilter.time_type = Constant.Filter.MONTH;
            mBinding.tvGroupTime.setText("??????");
        } else {
            mConditionFilter.user_id.add(userInfoManager.get(UserInfoManager.ID));
            mConditionFilter.time_type = Constant.Filter.MONTH;
            mBinding.tvGroupTime.setText("??????");
        }

//        mBinding.tvBanquetType.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                List<String> list = new ArrayList<>();
//                list.add("??????");
//                list.add("??????");
//                PickerListDialog dialog = new PickerListDialog(getActivity());
//                dialog.items(list);
//                dialog.itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
//                    @Override
//                    public void onItemSelected(int position, String text, PickerListDialog dialog) {
//                        mConditionFilter.banquet_type = position + 1;
//                        mBinding.tvBanquetType.setText(text);
//                        dialog.dismiss();
//                        onRefresh(mBinding.refreshLayout);
//                    }
//                });
//                dialog.show();
//            }
//        });

//        mBasicController = new DataBasicController(mBinding.llDataBasicRoot, JulySiegeActivity.this);
//        mCompleteController = new DataCompleteController(mBinding.llDataCompleteRoot, DataHomeActivity.this);
//        mNewAddController = new DataNewAddController(mBinding.llDataNewAddRoot, JulySiegeActivity.this);
        mPkCountry = new DataPkController(mBinding.flDataPkCountry, JulySiegeActivity.this);
        mPkPersonalQg = new DataPkController(mBinding.flDataPkPersonalQg, JulySiegeActivity.this);
        mPkDepartmentGs = new DataPkController(mBinding.flDataPkDepartmentGs, JulySiegeActivity.this);
        mPkPersonalGs = new DataPkController(mBinding.flDataPkPersonalGs, JulySiegeActivity.this);
//        mAnalyzeController = new DataAnalyzeController(mBinding.flDataAnalyzeRoot, JulySiegeActivity.this);
//        mTargetController = new DataTargetController(mBinding.flDataTargetRoot, JulySiegeActivity.this);
//        mSuccessController = new DataSuccessController(mBinding.flDataSuccessRoot, JulySiegeActivity.this);
//        mConvertController = new DataConvertController(mBinding.llDataConvertRoot, JulySiegeActivity.this);

        onRefresh(mBinding.refreshLayout);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        long currentTimeMills = System.currentTimeMillis() / 1000;
        if (1625068800 < currentTimeMills && currentTimeMills < 1625155200) {
            mBinding.tvTitleBarTitle.setImageResource(R.mipmap.ic_title_july);
        } else {
            mBinding.tvTitleBarTitle.setImageResource(R.mipmap.ic_title_july_other);
        }

        //????????????
        getTodayStar();
        //????????????tab
        getPkItem();
//        mBasicController.refresh(mConditionFilter, refreshLayout);
//        mCompleteController.refresh(mConditionFilter);
//        mNewAddController.refresh(mConditionFilter);
//        mAnalyzeController.refresh(mConditionFilter);
//        mTargetController.refresh(mConditionFilter);
//        mSuccessController.refresh(mConditionFilter);
//        mConvertController.refresh(mConditionFilter);
    }

    private void getTodayStar() {
        OkGo.<TodayStarBean>post(HttpURLs.todayStar)
                .tag(this)
                .execute(new JsonCallback<TodayStarBean>() {
                    @Override
                    public void onSuccess(Response<TodayStarBean> response) {
                        TodayStarBean body = response.body();
                        if (body == null || body.getData() == null) {
                            return;
                        }

                        MyImageUtils.display(mBinding.ivAvatar, body.getData().getAvatar(), R.mipmap.ic_default_image, R.mipmap.ic_default_image);
                        mBinding.tvName.setText(body.getData().getTitle());
                        mBinding.tvContent.setText(body.getData().getContent());
                    }
                });
    }

    private void getPkItem() {
        OkGo.<PkItemBean>post(HttpURLs.pkItem)
                .tag(this)
                .execute(new JsonCallback<PkItemBean>() {
                    @Override
                    public void onSuccess(Response<PkItemBean> response) {
                        PkItemBean body = response.body();
                        if (body == null) {
                            return;
                        }

                        //??????????????????
                        getPkData(50, body);//??????
                        getPkData(51, body);//??????
                        getPkData(52, body);//??????
                        getPkData(53, body);//?????????
                    }
                });
    }

    //??????????????????
    private void getPkData(int order, PkItemBean tabList) {
        String json = GsonUtils.toJson(mConditionFilter);
        JsonObject jo = (JsonObject) JsonParser.parseString(json);
        jo.addProperty(Constant.Parameter.ORDER, order);
        OkGo.<PkDataBean>post(HttpURLs.screen2Data1)
                .upJson(jo.toString())
                .tag(this)
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
                        if (order == 50) {
                            mPkPersonalQg.refresh(tabList, dataQg, Constant.Str.PK_PERSONAL_QG);
                            mPkPersonalGs.refresh(tabList, dataGs, Constant.Str.PK_PERSONAL_GS);
                        } else if (order == 51) {
                            mPkDepartmentGs.refresh(tabList, dataGs, Constant.Str.PK_DEPARTMENT_GS);
                        } else if (order == 52) {
                            mPkCountry.refresh(tabList, dataQg, Constant.Str.PK_COUNTRY_QG);
                        } else if (order == 53) {
                            //?????????
                            dataKingSignedQg = dataQg;
                            dataKingSignedGs = dataGs;
                        }

                        mBinding.refreshLayout.finishRefresh();
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
                        if (element.isJsonObject()) {//??????????????????object
                            // ???JsonElement???????????????JsonObject
                            JsonObject JsonObject = element.getAsJsonObject();
                            JsonElement jsonElement = JsonObject.get("data");
                            if (jsonElement.isJsonObject()) {
                                JsonObject JsonObject1 = jsonElement.getAsJsonObject();
                                JsonElement jsonElementQg = JsonObject1.get("qg");
                                if (jsonElementQg.isJsonObject()) {
                                    dataQg = gson.fromJson(jsonElementQg, PkDataBean.DataBean.DataChildBean.class);
                                } else if (jsonElementQg.isJsonArray()) {
//                                    Type type = new TypeToken<List<PkDataBean.DataBean.DataChildBean>>() {
//                                    }.getType();
                                    // ???JsonElement???????????????JsonArray
//                                List<PkDataBean.DataBean.DataChildBean> list = gson.fromJson(jsonElement, type);
//                                    dataQg = gson.fromJson(jsonElementQg, type);
                                }

                                JsonElement jsonElementGs = JsonObject1.get("gs");
                                if (jsonElementGs.isJsonObject()) {
                                    dataGs = gson.fromJson(jsonElementGs, PkDataBean.DataBean.DataChildBean.class);
                                } else if (jsonElementGs.isJsonArray()) {
//                                    Type type = new TypeToken<List<PkDataBean.DataBean.DataChildBean>>() {
//                                    }.getType();
                                    // ???JsonElement???????????????JsonArray
//                                List<PkDataBean.DataBean.DataChildBean> list = gson.fromJson(jsonElement, type);
//                                    dataGs = gson.fromJson(jsonElementGs, type);
                                }

                            }
                        }
                        return dataBean;
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case 1: {
                String json = data.getStringExtra(Constant.Parameter.JSON);
                ConditionFilter filter = GsonUtils.fromJson(json, ConditionFilter.class);
                filter.banquet_type = mConditionFilter.banquet_type;
                mConditionFilter = filter;
                String text = "";
//                text += "/";
                if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.TODAY)) {
                    text = "??????";
                } else if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.MONTH)) {
                    text = "??????";
                } else if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.PERIOD_ZERO)) {
                    text = "?????????";
                } else if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.PERIOD_ONE)) {
                    text = "????????????";
                } else if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.PERIOD_TWO)) {
                    text = "????????????";
                } else if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.PERIOD_THREE)) {
                    text = "????????????";
                } else {
                    mConditionFilter.time_type = Constant.Filter.MONTH;
                    text = "??????";
                }
                mBinding.tvGroupTime.setText(text);
                onRefresh(mBinding.refreshLayout);
                break;
            }
        }
    }

    public String getTitleDesc() {
        UserInfoManager userInfoManager = UserInfoManager.getInstance();
        String allDepartId = userInfoManager.get(UserInfoManager.ALL_DEPART_ID);
        String text;
        if (ObjectUtils.isNotEmpty(mConditionFilter.user_id)
                && mConditionFilter.user_id.size() == 1
                && mConditionFilter.user_id.get(0).equals(UserInfoManager.getInstance().get(UserInfoManager.ID))
                && ObjectUtils.isEmpty(mConditionFilter.dept_id)
        ) {
            text = "??????";
        } else if (ObjectUtils.isNotEmpty(mConditionFilter.dept_id) && ObjectUtils.isEmpty(mConditionFilter.user_id)
                && mConditionFilter.dept_id.size() == 1
                && mConditionFilter.dept_id.get(0).equals(allDepartId)
        ) {
            text = "?????????";
        } else if (ObjectUtils.isNotEmpty(mConditionFilter.dept_id) || ObjectUtils.isNotEmpty(mConditionFilter.user_id)) {
            text = "??????";

            if (ObjectUtils.isNotEmpty(mConditionFilter.title_list)) {
                text = "";
                int size = Math.min(2, mConditionFilter.title_list.size());
                for (int i = 0; i < size; i++) {
                    text += (mConditionFilter.title_list.get(i) + ",");
                }
                text = text.substring(0, text.length() - 1);
            }
        } else {
            mConditionFilter.dept_id = new ArrayList<>();
            mConditionFilter.dept_id.add(allDepartId);
            text = "?????????";
        }


        return text;
    }

    public ConditionFilter getConditionFilter() {
        return mConditionFilter;
    }
}
