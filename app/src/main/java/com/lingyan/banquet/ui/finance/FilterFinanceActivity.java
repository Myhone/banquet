package com.lingyan.banquet.ui.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityFilterFinanceBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetChildHall;
import com.lingyan.banquet.ui.finance.bean.FinanceFilterCondition;
import com.lingyan.banquet.ui.order.bean.OrderFilterCondition;
import com.lingyan.banquet.utils.GroupViewUtils;
import com.lingyan.banquet.views.dialog.PersonPickerDialog;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lingyan.banquet.views.dialog.TwoLineTabSelectDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by _hxb on 2021/2/25.
 */

public class FilterFinanceActivity extends BaseActivity {


    private ActivityFilterFinanceBinding mBinding;
    private FinanceFilterCondition mCondition;
    private GroupViewUtils mGroupViewUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFilterFinanceBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("筛选");
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        mCondition = GsonUtils.fromJson(json, FinanceFilterCondition.class);
        mBinding.tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy/MM/dd");
                        mBinding.tvStartTime.setText(string);
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
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy/MM/dd");
                        mBinding.tvEndTime.setText(string);
                    }
                })
                        .setTitleText("请选择结束时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build()
                        .show();
            }
        });

        mBinding.tvBanquetStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickerListDialog(getActivity())
                        .items("商机", "意向", "锁台", "签订", "执行", "结算")
                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                            @Override
                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                mCondition.banquet_status = (position + 1) + "";
                                dialog.dismiss();
                                mBinding.tvBanquetStatus.setText(text);
                            }
                        })
                        .show();
            }
        });
        //宴会厅
        mBinding.tvHallId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<NetBanquetChildHall>post(HttpURLs.listBanquetHall)
                        .params("s_type", 0)
                        .execute(new JsonCallback<NetBanquetChildHall>() {
                            @Override
                            public void onSuccess(Response<NetBanquetChildHall> response) {
                                NetBanquetChildHall body = response.body();
                                if (body == null) {
                                    return;
                                }
                                List<NetBanquetChildHall.DataDTO> list = body.getData();
                                TwoLineTabSelectDialog dialog = new TwoLineTabSelectDialog(getContext());
                                dialog.setData(list, null);
                                dialog.setOnHallSelectListener(new TwoLineTabSelectDialog.OnHallSelectListener() {
                                    @Override
                                    public void OnHallSelect(String id, String name, TwoLineTabSelectDialog dialog) {
                                        mCondition.hall_id = id;
                                        mCondition.hall_id_name = name;
                                        dialog.dismiss();
                                        mBinding.tvHallId.setText(name);
                                    }
                                });
                                dialog.show();
                            }
                        });
            }
        });
        //跟单人
        mBinding.tvIntentManId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonPickerDialog dialog = new PersonPickerDialog(getActivity());
                dialog.setOnPersonClickListener(new PersonPickerDialog.OnPersonClickListener() {
                    @Override
                    public void onPersonClick(String name, String id, PersonPickerDialog dialog) {

                        mCondition.intent_man_id = id;
                        mCondition.intent_man_id_name = name;
                        dialog.dismiss();
                        mBinding.tvIntentManId.setText(name);
                    }
                });
                dialog.show();
            }
        });
        //收款人
        mBinding.tvPayee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonPickerDialog dialog = new PersonPickerDialog(getActivity());
                dialog.setOnPersonClickListener(new PersonPickerDialog.OnPersonClickListener() {
                    @Override
                    public void onPersonClick(String name, String id, PersonPickerDialog dialog) {

                        mCondition.payee = id;
                        mCondition.payee_name = name;
                        dialog.dismiss();
                        mBinding.tvPayee.setText(name);
                    }
                });
                dialog.show();
            }
        });
        //退款人
        mBinding.tvRefundUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonPickerDialog dialog = new PersonPickerDialog(getActivity());
                dialog.setOnPersonClickListener(new PersonPickerDialog.OnPersonClickListener() {
                    @Override
                    public void onPersonClick(String name, String id, PersonPickerDialog dialog) {

                        mCondition.refund_user = id;
                        mCondition.refund_user_name = name;
                        dialog.dismiss();
                        mBinding.tvRefundUser.setText(name);
                    }
                });
                dialog.show();
            }
        });

        mGroupViewUtils = new GroupViewUtils();
        mGroupViewUtils.add("time_type", mBinding.tvTimeType1, "1");
        mGroupViewUtils.add("time_type", mBinding.tvTimeType2, "2");
        mGroupViewUtils.add("time_type", mBinding.tvTimeType3, "3");
        mGroupViewUtils.add("time_type", mBinding.tvTimeType4, "4");

        mGroupViewUtils.add("status", mBinding.tvStatus1, "1");
        mGroupViewUtils.add("status", mBinding.tvStatus2, "2");
        mGroupViewUtils.add("status", mBinding.tvStatus3, "3");

        mGroupViewUtils.add("type", mBinding.tvType1, "1");
        mGroupViewUtils.add("type", mBinding.tvType2, "2");

        mGroupViewUtils.add("b_type", mBinding.tvBType1, "1");
        mGroupViewUtils.add("b_type", mBinding.tvBType2, "2");

        mBinding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCondition = new FinanceFilterCondition();
                refreshUI();
            }
        });
        mBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> selectedValueMap = mGroupViewUtils.getSelectedValueMap();
                mCondition.time_type= selectedValueMap.get("time_type");
                mCondition.status= selectedValueMap.get("status");
                mCondition.type= selectedValueMap.get("type");
                mCondition.b_type= selectedValueMap.get("b_type");

                String startTime = mBinding.tvStartTime.getText().toString().trim();
                String endTime = mBinding.tvEndTime.getText().toString().trim();
                String date = startTime+"-"+endTime;
                if (StringUtils.equals(date, "-")) {
                    date=null;
                }
                mCondition.date=date;

                String tojson = GsonUtils.toJson(mCondition);
                LogUtils.i(tojson);
                Intent intent1 = new Intent();
                intent1.putExtra("json", tojson);
                setResult(RESULT_OK, intent1);
                finish();

            }
        });

        refreshUI();

    }

    private void refreshUI() {
        //1-定金管理  2-尾款管理
        if (mCondition.tab == 1) {
            mBinding.tvStatus3.setVisibility(View.VISIBLE);
            mBinding.tvBType1.setVisibility(View.VISIBLE);
            mBinding.tvBType2.setVisibility(View.VISIBLE);
        } else if (mCondition.tab == 2) {
            mBinding.tvStatus3.setVisibility(View.GONE);
            mBinding.tvBType1.setVisibility(View.GONE);
            mBinding.tvBType2.setVisibility(View.GONE);
        }
        String statusName = "";
        String[] statusArr = {"商机", "意向", "锁台", "签订", "执行", "结算"};
        try {
            if (!StringUtils.isEmpty(mCondition.status)) {
                int i = Integer.parseInt(mCondition.status);
                statusName = statusArr[i - 1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBinding.tvBanquetStatus.setText(statusName);

        mGroupViewUtils.select("time_type", mCondition.time_type);
        mGroupViewUtils.select("status", mCondition.status);
        mGroupViewUtils.select("type", mCondition.type);
        mGroupViewUtils.select("b_type", mCondition.b_type);

        mBinding.tvHallId.setText(mCondition.hall_id_name);
        mBinding.tvIntentManId.setText(mCondition.intent_man_id_name);
        mBinding.tvPayee.setText(mCondition.payee_name);
        mBinding.tvRefundUser.setText(mCondition.refund_user_name);


        String startTime = "";
        String endTime = "";
        try {
            if (mCondition.date != null) {
                String[] split = mCondition.date.split("-");
                startTime = split[0];
                endTime = split[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBinding.tvStartTime.setText(startTime);
        mBinding.tvEndTime.setText(endTime);
    }

}
