package com.lingyan.banquet.ui.data.july;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityJulyDataFilterBinding;
import com.lingyan.banquet.global.Constant;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetChildHall;
import com.lingyan.banquet.ui.data.SelectGroupActivity;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.utils.GroupViewUtils;
import com.lingyan.banquet.views.dialog.TwoLineTabSelectDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class JulyDataFilterActivity extends BaseActivity {

    private ConditionFilter mConditionFilter;
    private ActivityJulyDataFilterBinding mBinding;
    private GroupViewUtils mGroupViewUtils;


    public static void start(AppCompatActivity activity, int code, String json) {
        Intent intent = new Intent(activity, JulyDataFilterActivity.class);
        intent.putExtra(Constant.Parameter.JSON, json);
        activity.startActivityForResult(intent, code);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityJulyDataFilterBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("筛选");
        Intent intent = getIntent();
        String json = intent.getStringExtra(Constant.Parameter.JSON);
        LogUtils.i(Constant.Parameter.JSON, json);
        mConditionFilter = GsonUtils.fromJson(json, ConditionFilter.class);
        mBinding.tvSelectGroup.setText(getTitleDesc());
        mBinding.tvReset.setOnClickListener(v -> {
            ConditionFilter conditionFilter = new ConditionFilter();
            conditionFilter.banquet_type = mConditionFilter.banquet_type;
            mConditionFilter = conditionFilter;
            mGroupViewUtils.reset();
            mBinding.tvStartTime.setText("");
            mBinding.tvEndTime.setText("");
            mBinding.tvSelectGroup.setText(getTitleDesc());
            mBinding.tvHallId.setText("");
        });
        mBinding.ivDeleteHall.setOnClickListener(v -> {
            mConditionFilter.hall_id = null;
            mConditionFilter.hall_id_name = null;
            mBinding.tvHallId.setText("");
        });

        mBinding.tvConfirm.setOnClickListener(v -> {
            if (!StringUtils.isEmpty(mConditionFilter.start_time) && StringUtils.isEmpty(mConditionFilter.end_time)) {
                ToastUtils.showShort("请选择结束时间");
                return;
            }
            if (StringUtils.isEmpty(mConditionFilter.start_time) && !StringUtils.isEmpty(mConditionFilter.end_time)) {
                ToastUtils.showShort("请选择开始时间");
                return;
            }
            String j = GsonUtils.toJson(mConditionFilter);
            Intent intent1 = new Intent();
            intent1.putExtra(Constant.Parameter.JSON, j);
            setResult(RESULT_OK, intent1);
            finish();
        });
        mBinding.llTimePickerContainer.setVisibility(View.GONE);
        mBinding.tvStartTime.setOnClickListener(v -> {
            //开始时间
            new TimePickerBuilder(getActivity(), (date, view) -> {
                String string = TimeUtils.date2String(date, "yyyy-MM-dd");
                mBinding.tvStartTime.setText(string);
                mConditionFilter.time_type = null;
                mConditionFilter.start_time = string;
            })
                    .setTitleText("请选择开始时间")
                    .setItemVisibleCount(12)
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .build()
                    .show();
        });
        mBinding.tvEndTime.setOnClickListener(v -> {
            //结束时间
            new TimePickerBuilder(getActivity(), (date, view) -> {
                String string = TimeUtils.date2String(date, "yyyy-MM-dd");
                mBinding.tvEndTime.setText(string);
                mConditionFilter.time_type = null;
                mConditionFilter.end_time = string;
            })
                    .setTitleText("请选择开始时间")
                    .setItemVisibleCount(12)
                    .setType(new boolean[]{true, true, true, false, false, false})
                    .build()
                    .show();
        });
        //宴会厅
        mBinding.tvHallId.setOnClickListener(v -> OkGo.<NetBanquetChildHall>post(HttpURLs.listBanquetHall)
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
                        dialog.setOnHallSelectListener((id, name, dialog1) -> {
                            mConditionFilter.hall_id = id;
                            mConditionFilter.hall_id_name = name;
                            dialog1.dismiss();
                            mBinding.tvHallId.setText(name);
                        });
                        dialog.show();
                    }
                }));

        mGroupViewUtils = new GroupViewUtils();
        mGroupViewUtils.add(Constant.Parameter.RANGE, mBinding.tvMine, "mine");
        mGroupViewUtils.add(Constant.Parameter.RANGE, mBinding.tvSelectGroup, "depart", false);
        mBinding.tvMine.setOnClickListener(v -> {
            boolean select = mGroupViewUtils.select(Constant.Parameter.RANGE, "mine");
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
        });
        mBinding.tvSelectGroup.setOnClickListener(v -> {
            //选择部门
            boolean select = mGroupViewUtils.select(Constant.Parameter.RANGE, "depart");
            if (!select) {
                mGroupViewUtils.select(Constant.Parameter.RANGE, "depart");
            }
            SelectGroupActivity.start(getActivity(), 1, GsonUtils.toJson(mConditionFilter));

        });

        mGroupViewUtils.add(Constant.Parameter.TIME_TYPE, mBinding.tvToday, Constant.Filter.TODAY, false);
        mGroupViewUtils.add(Constant.Parameter.TIME_TYPE, mBinding.tvMonth, Constant.Filter.MONTH, false);
        mGroupViewUtils.add(Constant.Parameter.TIME_TYPE, mBinding.tvFirstDay, Constant.Filter.PERIOD_ZERO, false);
        mGroupViewUtils.add(Constant.Parameter.TIME_TYPE, mBinding.tvStageOne, Constant.Filter.PERIOD_ONE, false);
        mGroupViewUtils.add(Constant.Parameter.TIME_TYPE, mBinding.tvStageTwo, Constant.Filter.PERIOD_TWO, false);
        mGroupViewUtils.add(Constant.Parameter.TIME_TYPE, mBinding.tvStageThree, Constant.Filter.PERIOD_THREE, false);

        mBinding.tvToday.setOnClickListener(v -> {
            boolean select = mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.TODAY);
            mBinding.llTimePickerContainer.setVisibility(View.GONE);
            if (select) {
                mConditionFilter.time_type = Constant.Filter.TODAY;
            } else {
                mConditionFilter.time_type = null;
            }
            mConditionFilter.start_time = null;
            mConditionFilter.end_time = null;

        });
        mBinding.tvMonth.setOnClickListener(v -> {
            boolean select = mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.MONTH);
            mBinding.llTimePickerContainer.setVisibility(View.GONE);
            if (select) {
                mConditionFilter.time_type = Constant.Filter.MONTH;
            } else {
                mConditionFilter.time_type = null;
            }

            mConditionFilter.start_time = null;
            mConditionFilter.end_time = null;
        });
        mBinding.tvFirstDay.setOnClickListener(v -> {
            boolean select = mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.PERIOD_ZERO);
            mBinding.llTimePickerContainer.setVisibility(View.GONE);
            if (select) {
                mConditionFilter.time_type = Constant.Filter.PERIOD_ZERO;
            } else {
                mConditionFilter.time_type = null;
            }

            mConditionFilter.start_time = null;
            mConditionFilter.end_time = null;
        });
        mBinding.tvStageOne.setOnClickListener(v -> {
            boolean select = mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.PERIOD_ONE);
            mBinding.llTimePickerContainer.setVisibility(View.GONE);
            if (select) {
                mConditionFilter.time_type = Constant.Filter.PERIOD_ONE;
            } else {
                mConditionFilter.time_type = null;
            }

            mConditionFilter.start_time = null;
            mConditionFilter.end_time = null;
        });


        mBinding.tvStageTwo.setOnClickListener(v -> {
            boolean select = mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.PERIOD_TWO);
            mBinding.llTimePickerContainer.setVisibility(View.GONE);
            if (select) {
                mConditionFilter.time_type = Constant.Filter.PERIOD_TWO;
            } else {
                mConditionFilter.time_type = null;
            }

            mConditionFilter.start_time = null;
            mConditionFilter.end_time = null;
        });


        mBinding.tvStageThree.setOnClickListener(v -> {
            boolean select = mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.PERIOD_THREE);
            mBinding.llTimePickerContainer.setVisibility(View.GONE);
            if (select) {
                mConditionFilter.time_type = Constant.Filter.PERIOD_THREE;
            } else {
                mConditionFilter.time_type = null;
            }

            mConditionFilter.start_time = null;
            mConditionFilter.end_time = null;
        });

        if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.TODAY)) {
            mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.TODAY);
        } else if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.MONTH)) {
            mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.MONTH);
        } else if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.PERIOD_ZERO)) {
            mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.PERIOD_ZERO);
        } else if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.PERIOD_ONE)) {
            mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.PERIOD_ONE);
        } else if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.PERIOD_TWO)) {
            mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.PERIOD_TWO);
        } else if (StringUtils.equals(mConditionFilter.time_type, Constant.Filter.PERIOD_THREE)) {
            mGroupViewUtils.select(Constant.Parameter.TIME_TYPE, Constant.Filter.PERIOD_THREE);
        }

        if (ObjectUtils.isNotEmpty(mConditionFilter.user_id)
                && mConditionFilter.user_id.size() == 1
                && mConditionFilter.user_id.get(0).equals(UserInfoManager.getInstance().get(UserInfoManager.ID))
                && ObjectUtils.isEmpty(mConditionFilter.dept_id)
        ) {
            mGroupViewUtils.select(Constant.Parameter.RANGE, "mine");
        } else if (ObjectUtils.isNotEmpty(mConditionFilter.dept_id) || ObjectUtils.isNotEmpty(mConditionFilter.user_id)) {
            mGroupViewUtils.select(Constant.Parameter.RANGE, "depart");
        }

        if (ObjectUtils.isNotEmpty(mConditionFilter.hall_id)) {
            mBinding.tvHallId.setText(mConditionFilter.hall_id_name);
        } else {
            mBinding.tvHallId.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == 1) {
            String json = data.getStringExtra(Constant.Parameter.JSON);
            ConditionFilter filter = GsonUtils.fromJson(json, ConditionFilter.class);
            mConditionFilter.user_id = filter.user_id;
            mConditionFilter.dept_id = filter.dept_id;
            mConditionFilter.title_list = filter.title_list;
            mBinding.tvSelectGroup.setText(getTitleDesc());
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
