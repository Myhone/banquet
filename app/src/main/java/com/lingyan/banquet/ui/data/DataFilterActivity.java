package com.lingyan.banquet.ui.data;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityDataFilterBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetChildHall;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.utils.GroupViewUtils;
import com.lingyan.banquet.views.dialog.TwoLineTabSelectDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class DataFilterActivity extends BaseActivity {

    private ConditionFilter mConditionFilter;
    private ActivityDataFilterBinding mBinding;
    private GroupViewUtils mGroupViewUtils;


    public static void start(AppCompatActivity activity, int code, String json) {
        Intent intent = new Intent(activity, DataFilterActivity.class);
        intent.putExtra("json", json);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityDataFilterBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("筛选");
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        LogUtils.i("json", json);
        mConditionFilter = GsonUtils.fromJson(json, ConditionFilter.class);
        mBinding.tvSelectGroup.setText(getTitleDesc());
        mBinding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConditionFilter conditionFilter = new ConditionFilter();
                conditionFilter.banquet_type = mConditionFilter.banquet_type;
                mConditionFilter = conditionFilter;
                mGroupViewUtils.reset();
                mBinding.tvStartTime.setText("");
                mBinding.tvEndTime.setText("");
                mBinding.tvSelectGroup.setText(getTitleDesc());
                mBinding.tvHallId.setText("");
            }
        });
        mBinding.ivDeleteHall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mConditionFilter.hall_id=null;
                mConditionFilter.hall_id_name=null;
                mBinding.tvHallId.setText("");
            }
        });

        mBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(mConditionFilter.start_time) && StringUtils.isEmpty(mConditionFilter.end_time)) {
                    ToastUtils.showShort("请选择结束时间");
                    return;
                }
                if (StringUtils.isEmpty(mConditionFilter.start_time) && !StringUtils.isEmpty(mConditionFilter.end_time)) {
                    ToastUtils.showShort("请选择开始时间");
                    return;
                }
                String j = GsonUtils.toJson(mConditionFilter);
                Intent intent = new Intent();
                intent.putExtra("json", j);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        mBinding.llTimePickerContainer.setVisibility(View.GONE);
        mBinding.tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始时间
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy-MM-dd");
                        mBinding.tvStartTime.setText(string);
                        mConditionFilter.time_type = null;
                        mConditionFilter.start_time = string;
                    }
                })
                        .setTitleText("请选择开始时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build()
                        .show();
            }
        });
        mBinding.tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //结束时间
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy-MM-dd");
                        mBinding.tvEndTime.setText(string);
                        mConditionFilter.time_type = null;
                        mConditionFilter.end_time = string;
                    }
                })
                        .setTitleText("请选择开始时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build()
                        .show();
            }
        });
        //宴会厅
        mBinding.tvHallId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<NetBanquetChildHall>post(HttpURLs.listBanquetHall)
                        .params("s_type", 0)
                        .params("type", mConditionFilter.banquet_type)
                        .execute(new JsonCallback<NetBanquetChildHall>() {
                            @Override
                            public void onSuccess(Response<NetBanquetChildHall> response) {
                                NetBanquetChildHall body = response.body();
                                if (body == null) {
                                    return;
                                }
                                List<NetBanquetChildHall.DataDTO> list = body.getData();
                                TwoLineTabSelectDialog dialog = new TwoLineTabSelectDialog(getContext());
                                dialog.setData(list, Collections.singletonList(mConditionFilter.hall_id));
                                dialog.setOnHallSelectListener(new TwoLineTabSelectDialog.OnHallSelectListener() {
                                    @Override
                                    public void OnHallSelect(String id, String name, TwoLineTabSelectDialog dialog) {
                                        mConditionFilter.hall_id = id;
                                        mConditionFilter.hall_id_name = name;
                                        dialog.dismiss();
                                        mBinding.tvHallId.setText(name);
                                    }
                                });
                                dialog.show();
                            }
                        });
            }
        });

        mGroupViewUtils = new GroupViewUtils();
        mGroupViewUtils.add("range", mBinding.tvMine, "mine");
        mGroupViewUtils.add("range", mBinding.tvSelectGroup, "depart", false);
        mBinding.tvMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean select = mGroupViewUtils.select("range", "mine");
                if (select) {
                    if (ObjectUtils.isEmpty(mConditionFilter.user_id)) {
                        mConditionFilter.user_id = new ArrayList<>();
                    }
                    mConditionFilter.user_id.clear();
                    mConditionFilter.user_id.add(UserInfoManager.getInstance().get(UserInfoManager.ID));

                } else {
                    mConditionFilter.user_id = null;
                }
                mConditionFilter.dept_id = null;
            }
        });
        mBinding.tvSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择部门
                boolean select = mGroupViewUtils.select("range", "depart");
                if (!select) {
                    mGroupViewUtils.select("range", "depart");
                }
                SelectGroupActivity.start(getActivity(), 1, GsonUtils.toJson(mConditionFilter));

            }
        });


        mGroupViewUtils.add("time_type", mBinding.tvYesterday, "yesterday", false);
        mGroupViewUtils.add("time_type", mBinding.tvToday, "today", false);
        mGroupViewUtils.add("time_type", mBinding.tvWeek, "week", false);
        mGroupViewUtils.add("time_type", mBinding.tvMonth, "month", false);
        mGroupViewUtils.add("time_type", mBinding.tvYear, "year", false);
        mGroupViewUtils.add("time_type", mBinding.tvCustomTime, "custom", false);

        mBinding.tvYesterday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean select = mGroupViewUtils.select("time_type", "yesterday");
                mBinding.llTimePickerContainer.setVisibility(View.GONE);
                if (select) {
                    mConditionFilter.time_type = "yesterday";
                } else {
                    mConditionFilter.time_type = null;
                }
                mConditionFilter.start_time = null;
                mConditionFilter.end_time = null;

            }
        });
        mBinding.tvWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean select = mGroupViewUtils.select("time_type", "week");
                mBinding.llTimePickerContainer.setVisibility(View.GONE);
                if (select) {
                    mConditionFilter.time_type = "week";
                } else {
                    mConditionFilter.time_type = null;
                }
                mConditionFilter.start_time = null;
                mConditionFilter.end_time = null;

            }
        });
        mBinding.tvToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean select = mGroupViewUtils.select("time_type", "today");
                mBinding.llTimePickerContainer.setVisibility(View.GONE);
                if (select) {
                    mConditionFilter.time_type = "today";
                } else {
                    mConditionFilter.time_type = null;
                }
                mConditionFilter.start_time = null;
                mConditionFilter.end_time = null;

            }
        });
        mBinding.tvMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean select = mGroupViewUtils.select("time_type", "month");
                mBinding.llTimePickerContainer.setVisibility(View.GONE);
                if (select) {
                    mConditionFilter.time_type = "month";
                } else {
                    mConditionFilter.time_type = null;
                }

                mConditionFilter.start_time = null;
                mConditionFilter.end_time = null;
            }
        });
        mBinding.tvYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean select = mGroupViewUtils.select("time_type", "year");
                mBinding.llTimePickerContainer.setVisibility(View.GONE);
                if (select) {
                    mConditionFilter.time_type = "year";
                } else {
                    mConditionFilter.time_type = null;
                }

                mConditionFilter.start_time = null;
                mConditionFilter.end_time = null;
            }
        });
        mBinding.tvCustomTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean select = mGroupViewUtils.select("time_type", "custom");
                if (select) {
                    mBinding.llTimePickerContainer.setVisibility(View.VISIBLE);
                } else {
                    mBinding.llTimePickerContainer.setVisibility(View.GONE);
                }
                mConditionFilter.time_type = null;
                mConditionFilter.start_time = null;
                mConditionFilter.end_time = null;
                mBinding.tvStartTime.setText("");
                mBinding.tvEndTime.setText("");
            }
        });

        if (StringUtils.equals(mConditionFilter.time_type, "yesterday")) {
            mGroupViewUtils.select("time_type", "yesterday");
        } else if (StringUtils.equals(mConditionFilter.time_type, "today")) {
            mGroupViewUtils.select("time_type", "today");
        } else if (StringUtils.equals(mConditionFilter.time_type, "week")) {
            mGroupViewUtils.select("time_type", "week");
        } else if (StringUtils.equals(mConditionFilter.time_type, "month")) {
            mGroupViewUtils.select("time_type", "month");
        } else if (StringUtils.equals(mConditionFilter.time_type, "year")) {
            mGroupViewUtils.select("time_type", "year");
        } else if (!StringUtils.isEmpty(mConditionFilter.start_time) || !StringUtils.isEmpty(mConditionFilter.end_time)) {
            mGroupViewUtils.select("time_type", "custom");
            mBinding.llTimePickerContainer.setVisibility(View.VISIBLE);
            mBinding.tvStartTime.setText(mConditionFilter.start_time);
            mBinding.tvEndTime.setText(mConditionFilter.end_time);
        }

        if (ObjectUtils.isNotEmpty(mConditionFilter.user_id)
                && mConditionFilter.user_id.size() == 1
                && mConditionFilter.user_id.get(0).equals(UserInfoManager.getInstance().get(UserInfoManager.ID))
                && ObjectUtils.isEmpty(mConditionFilter.dept_id)
        ) {
            mGroupViewUtils.select("range", "mine");
        } else if (ObjectUtils.isNotEmpty(mConditionFilter.dept_id) || ObjectUtils.isNotEmpty(mConditionFilter.user_id)) {
            mGroupViewUtils.select("range", "depart");
        }

        if(ObjectUtils.isNotEmpty(mConditionFilter.hall_id)){
            mBinding.tvHallId.setText(mConditionFilter.hall_id_name);
        }else {
            mBinding.tvHallId.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        switch (requestCode) {
            case 1: {
                String json = data.getStringExtra("json");
                ConditionFilter filter = GsonUtils.fromJson(json, ConditionFilter.class);
                mConditionFilter.user_id = filter.user_id;
                mConditionFilter.dept_id = filter.dept_id;
                mConditionFilter.title_list = filter.title_list;
                mBinding.tvSelectGroup.setText(getTitleDesc());
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
            text = "我的";
        } else if (ObjectUtils.isNotEmpty(mConditionFilter.dept_id) && ObjectUtils.isEmpty(mConditionFilter.user_id)
                && mConditionFilter.dept_id.size() == 1
                && mConditionFilter.dept_id.get(0).equals(allDepartId)
        ) {
            text = "全公司";
        } else if (ObjectUtils.isNotEmpty(mConditionFilter.dept_id) || ObjectUtils.isNotEmpty(mConditionFilter.user_id)) {
            text = "部门";

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
            text = "全公司";
        }


        return text;
    }


}
