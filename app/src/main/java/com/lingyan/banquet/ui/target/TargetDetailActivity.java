package com.lingyan.banquet.ui.target;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lingyan.banquet.App;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityTargetDetailBinding;
import com.lingyan.banquet.global.Constant;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.target.bean.NetReqTargetDetailCondition;
import com.lingyan.banquet.ui.target.bean.NetTargetDetail;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by _hxb on 2021/1/31.
 */

public class TargetDetailActivity extends BaseActivity {

    private ActivityTargetDetailBinding mBinding;

    private NetReqTargetDetailCondition mCondition;
    private String mType, b_type, rate, user_number, target_type;
    private String mId;
    private String mPersonName;
    private String mAvatarName;
    private String mDepartName;
    private NetTargetDetail.DataDTO mData;

    /**
     * @param type 2-部门 3-员工
     * @param id
     */
    public static void start(String tableTitle, String type, String id, String personName, String avatarName, String departName, String b_type, String rate, String user_number, String target_type) {
        Intent intent = new Intent(App.sApp, TargetDetailActivity.class);
        intent.putExtra(Constant.Parameter.TABLE_TITLE, tableTitle);
        intent.putExtra("type", type);
        intent.putExtra("id", id);
        intent.putExtra("personName", personName);
        intent.putExtra("avatarName", avatarName);
        intent.putExtra("departName", departName);
        intent.putExtra("b_type", b_type);
        intent.putExtra(Constant.Parameter.RATE, rate);
        intent.putExtra(Constant.Parameter.USER_NUMBER, user_number);
        intent.putExtra(Constant.Parameter.TARGET_TYPE, target_type);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTargetDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Intent intent = getIntent();
        String tableTitle = intent.getStringExtra(Constant.Parameter.TABLE_TITLE);
        mType = intent.getStringExtra("type");
        mId = intent.getStringExtra("id");
        mPersonName = intent.getStringExtra("personName");
        mAvatarName = intent.getStringExtra("avatarName");
        mDepartName = intent.getStringExtra("departName");
        b_type = intent.getStringExtra("b_type");
        rate = intent.getStringExtra(Constant.Parameter.RATE);
        user_number = intent.getStringExtra(Constant.Parameter.USER_NUMBER);
        target_type = intent.getStringExtra(Constant.Parameter.TARGET_TYPE);

        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("目标详情");
        mCondition = new NetReqTargetDetailCondition();
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                NetTargetTabList.DataDTO dto = mTabList.get(tab.getPosition());
//                mCondition.target_type = dto.getA_type();
                getData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mBinding.tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始时间
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy");
                        mCondition.year = string;
                        mBinding.tvYear.setText(mCondition.year);
                        mBinding.tvYearTitle.setText(mCondition.year);
                        getData();

                    }
                })
                        .setTitleText("请选择开始时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, false, false, false, false, false})
                        .build()
                        .show();
            }
        });

        initUI();

        mCondition.year = Calendar.getInstance().get(Calendar.YEAR) + "";
        mCondition.obj_id = mId;
        mCondition.type = mType;
        mCondition.b_type = b_type;
        mCondition.target_type = target_type;

        mBinding.tvYear.setText(mCondition.year);
        mBinding.tvYearTitle.setText(mCondition.year);

        mBinding.tabLayout.removeAllTabs();
        TabLayout.Tab tab = mBinding.tabLayout.newTab();
        tab.setText(tableTitle);
        mBinding.tabLayout.addTab(tab);
//        OkGo.<NetTargetTabList>post(HttpURLs.achievementTabList)
////                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
//                .execute(new JsonCallback<NetTargetTabList>() {
//                    @Override
//                    public void onSuccess(Response<NetTargetTabList> response) {
//                        if (ObjectUtils.isNotEmpty(mTabList)) {
//                            return;
//                        }
//                        NetTargetTabList body = response.body();
//                        mTabList = body.getData();
//                        if (ObjectUtils.isNotEmpty(mTabList)) {
//                            mBinding.tabLayout.removeAllTabs();
//                            for (NetTargetTabList.DataDTO dataDTO : mTabList) {
//                                TabLayout.Tab tab = mBinding.tabLayout.newTab();
//                                tab.setText(dataDTO.getTitle());
//                                mBinding.tabLayout.addTab(tab);
//                            }
//                        }
//
//                    }
//                });

    }

    private void getData() {
        String json = GsonUtils.toJson(mCondition);

        OkGo.<NetTargetDetail>post(HttpURLs.achievementInfo)
                .upJson(json)
                .execute(new JsonCallback<NetTargetDetail>() {
                    @Override
                    public void onSuccess(Response<NetTargetDetail> response) {
                        NetTargetDetail body = response.body();
                        mData = body.getData();
                        initTarget();
                        enableTarget(false);
                        KeyboardUtils.hideSoftInput(getActivity());
                        if (mData != null) {
                            String deptName = mData.getDept_name();
                            String avatarName = mData.getAvatar_name();
                            String name = mData.getName();

//                            if (StringUtils.equals(mType, "2")) {
//                                //2-部门 3-员工
//                                mBinding.tvDepartmentName.setText(name);
//                            } else {
//                                mBinding.tvUserName.setText(name);
//                                mBinding.tvUserDepartName.setText(deptName);
//                                mBinding.wordAvatar.setWord(avatarName);
//                            }

                            String isShow = mData.getIs_show();
                            if (StringUtils.equals(isShow, "1")) {
                                //显示
                                mBinding.llBottomActionContainer.setVisibility(View.GONE);
                                mBinding.llSetTarget.setVisibility(View.VISIBLE);

                                KeyboardUtils.registerSoftInputChangedListener(getActivity(), new KeyboardUtils.OnSoftInputChangedListener() {
                                    @Override
                                    public void onSoftInputChanged(int height) {
                                        if (height > 0) {
                                            mBinding.llBottomActionContainer.setVisibility(View.GONE);
                                            mBinding.llSetTarget.setVisibility(View.GONE);
                                        } else {
                                            ThreadUtils.getMainHandler().post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mBinding.llBottomActionContainer.setVisibility(View.VISIBLE);
                                                    mBinding.llSetTarget.setVisibility(View.GONE);
                                                }
                                            });
                                        }
                                    }
                                });

                            } else {
                                mBinding.llBottomActionContainer.setVisibility(View.GONE);
                                mBinding.llSetTarget.setVisibility(View.GONE);
                            }

                            mBinding.tvJanuary.setText(mData.getJanuary());
                            mBinding.tvFebruary.setText(mData.getFebruary());
                            mBinding.tvMarch.setText(mData.getMarch());
                            mBinding.tvApril.setText(mData.getApril());
                            mBinding.tvMay.setText(mData.getMay());
                            mBinding.tvJune.setText(mData.getJune());
                            mBinding.tvJuly.setText(mData.getJuly());
                            mBinding.tvAugust.setText(mData.getAugust());
                            mBinding.tvSeptember.setText(mData.getSeptember());
                            mBinding.tvOctober.setText(mData.getOctober());
                            mBinding.tvNovember.setText(mData.getNovember());
                            mBinding.tvDecember.setText(mData.getDecember());

                            mBinding.tvYeartarget.setText(mData.getYeartarget());
                            mBinding.tvFirstTarget.setText(mData.getFirst_target());
                            mBinding.tvSecondTarget.setText(mData.getSecond_target());
                            mBinding.tvThirdTarget.setText(mData.getThird_target());
                            mBinding.tvFourthTarget.setText(mData.getFourth_target());
                        }

                        mBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String url = "";
                                NetTargetDetail.DataDTO dto = mData;
                                if (Float.parseFloat(rate) >= 0 && Integer.parseInt(user_number) != 0) {
                                    url = HttpURLs.achievementUpdate;
                                    dto.setId(dto.getAchievement_id());
                                } else {
                                    if (Integer.parseInt(user_number) == 0) {
                                        url = HttpURLs.achievementSave;
                                        dto = new NetTargetDetail.DataDTO();

                                        if (StringUtils.equals(mType, "2")) {
                                            //2-部门 3-员工
                                            dto.setDept_id(mId);
                                        } else {
                                            dto.setUser_id(mId);
                                        }
                                    }

                                }

                                dto.setType(mType);
                                dto.setTarget_type(target_type);
                                dto.setYear(mBinding.tvYear.getText().toString().trim());

                                dto.setJanuary(mBinding.tvJanuary.getText().toString().trim());
                                dto.setFebruary(mBinding.tvFebruary.getText().toString().trim());
                                dto.setMarch(mBinding.tvMarch.getText().toString().trim());
                                dto.setApril(mBinding.tvApril.getText().toString().trim());
                                dto.setMay(mBinding.tvMay.getText().toString().trim());
                                dto.setJune(mBinding.tvJune.getText().toString().trim());
                                dto.setJuly(mBinding.tvJuly.getText().toString().trim());
                                dto.setAugust(mBinding.tvAugust.getText().toString().trim());
                                dto.setSeptember(mBinding.tvSeptember.getText().toString().trim());
                                dto.setOctober(mBinding.tvOctober.getText().toString().trim());
                                dto.setNovember(mBinding.tvNovember.getText().toString().trim());
                                dto.setDecember(mBinding.tvDecember.getText().toString().trim());

                                dto.setYeartarget(mBinding.tvYeartarget.getText().toString().trim());
                                dto.setFirst_target(mBinding.tvFirstTarget.getText().toString().trim());
                                dto.setSecond_target(mBinding.tvSecondTarget.getText().toString().trim());
                                dto.setThird_target(mBinding.tvThirdTarget.getText().toString().trim());
                                dto.setFourth_target(mBinding.tvFourthTarget.getText().toString().trim());
                                dto.setB_type(b_type);
                                String toJson = GsonUtils.toJson(dto);


                                OkGo.<NetBaseResp>post(url)
                                        .upJson(toJson)
                                        .execute(new JsonCallback<NetBaseResp>() {
                                            @Override
                                            public void onSuccess(Response<NetBaseResp> response) {
                                                NetBaseResp resp = response.body();
                                                if (resp.getCode() == 200) {
                                                    getData();
                                                }
                                                ToastUtils.showShort(resp.getMsg());


                                            }
                                        });
                            }
                        });


                    }
                });

    }

    private void initUI() {
        mBinding.tvDepartmentName.setVisibility(View.GONE);
        mBinding.llPersonInfoContainer.setVisibility(View.GONE);
        if (StringUtils.equals(mType, "2")) {
            //2-部门 3-员工
            mBinding.tvDepartmentName.setVisibility(View.VISIBLE);
            mBinding.tvDepartmentName.setText(mDepartName);
        } else {
            mBinding.llPersonInfoContainer.setVisibility(View.VISIBLE);
            mBinding.tvUserName.setText(mPersonName);
            mBinding.tvUserDepartName.setText(mDepartName);
            mBinding.wordAvatar.setWord(mAvatarName);
        }

        mBinding.llBottomActionContainer.setVisibility(View.GONE);
        mBinding.llSetTarget.setVisibility(View.GONE);

        initTarget();
        enableTarget(false);


        mBinding.llSetTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.llSetTarget.setVisibility(View.GONE);
                mBinding.llBottomActionContainer.setVisibility(View.VISIBLE);
                enableTarget(true);
            }
        });
        mBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.llSetTarget.setVisibility(View.VISIBLE);
                mBinding.llBottomActionContainer.setVisibility(View.GONE);
                enableTarget(false);
                KeyboardUtils.hideSoftInput(getActivity());
            }
        });


    }

    private void initTarget() {
        mBinding.tvJanuary.setText("");
        mBinding.tvFebruary.setText("");
        mBinding.tvMarch.setText("");
        mBinding.tvApril.setText("");
        mBinding.tvMay.setText("");
        mBinding.tvJune.setText("");
        mBinding.tvJuly.setText("");
        mBinding.tvAugust.setText("");
        mBinding.tvSeptember.setText("");
        mBinding.tvOctober.setText("");
        mBinding.tvNovember.setText("");
        mBinding.tvDecember.setText("");

        mBinding.tvYeartarget.setText("");
        mBinding.tvFirstTarget.setText("");
        mBinding.tvSecondTarget.setText("");
        mBinding.tvThirdTarget.setText("");
        mBinding.tvFourthTarget.setText("");
    }

    public void enableTarget(boolean enable) {
        mBinding.tvJanuary.setEnabled(enable);
        mBinding.tvFebruary.setEnabled(enable);
        mBinding.tvMarch.setEnabled(enable);
        mBinding.tvApril.setEnabled(enable);
        mBinding.tvMay.setEnabled(enable);
        mBinding.tvJune.setEnabled(enable);
        mBinding.tvJuly.setEnabled(enable);
        mBinding.tvAugust.setEnabled(enable);
        mBinding.tvSeptember.setEnabled(enable);
        mBinding.tvOctober.setEnabled(enable);
        mBinding.tvNovember.setEnabled(enable);
        mBinding.tvDecember.setEnabled(enable);

        mBinding.tvYeartarget.setEnabled(enable);
        mBinding.tvFirstTarget.setEnabled(enable);
        mBinding.tvSecondTarget.setEnabled(enable);
        mBinding.tvThirdTarget.setEnabled(enable);
        mBinding.tvFourthTarget.setEnabled(enable);
    }

    @Override
    protected void onDestroy() {
        KeyboardUtils.unregisterSoftInputChangedListener(getWindow());
        super.onDestroy();
    }
}
