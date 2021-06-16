package com.lingyan.banquet.ui.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.lingyan.banquet.App;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityFilterOrderBinding;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetChildHall;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetType;
import com.lingyan.banquet.ui.banquet.bean.NetCustomerChannel;
import com.lingyan.banquet.ui.banquet.bean.NetMealList;
import com.lingyan.banquet.ui.order.bean.OrderFilterCondition;
import com.lingyan.banquet.utils.GroupViewUtils;
import com.lingyan.banquet.views.dialog.PersonPickerDialog;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lingyan.banquet.views.dialog.TwoLineTabSelectDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by _hxb on 2021/1/3.
 */

public class FilterOrderActivity extends BaseActivity {

    private ActivityFilterOrderBinding mBinding;
    private int mType;
    private OrderFilterCondition mCondition;
    private GroupViewUtils mGroupViewUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFilterOrderBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("筛选");
        Intent intent = getIntent();
        String json = intent.getStringExtra("json");
        mCondition = GsonUtils.fromJson(json, OrderFilterCondition.class);
        mType = mCondition.type;
        if (mType == BanquetCelebrationType.TYPE_BANQUET) {
            mBinding.tvBanCelTitle.setText("宴会时间");
            mBinding.llColorSystemContainer.setVisibility(View.GONE);
        } else {
            mBinding.tvBanCelTitle.setText("庆典时间");
            mBinding.llColorSystemContainer.setVisibility(View.VISIBLE);
        }
        mGroupViewUtils = new GroupViewUtils();
        mGroupViewUtils.add("is_yd_qd", mBinding.tvIsYdQd1, "1");
        mGroupViewUtils.add("is_yd_qd", mBinding.tvIsYdQd2, "2");

        mBinding.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickerListDialog(getActivity())
                        .items("商机", "意向", "锁台", "签订", "执行", "结算", "待跟进", "待结算", "待处理")
                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                            @Override
                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                mCondition.status = (position + 1) + "";
                                dialog.dismiss();
                                mBinding.tvStatus.setText(text);
                            }
                        })
                        .show();
            }
        });
        mBinding.tvIsLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickerListDialog(getActivity())
                        .items("否", "是")
                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                            @Override
                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                mCondition.is_lost = position + "";
                                dialog.dismiss();
                                mBinding.tvIsLost.setText(text);
                            }
                        })
                        .show();
            }
        });

        mBinding.tvNicheType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择宴会类型
                OkGo.<NetBanquetType>post(HttpURLs.listBanquetType)
                        .params("type", "tree")
                        .execute(new JsonCallback<NetBanquetType>() {
                            @Override
                            public void onSuccess(Response<NetBanquetType> response) {
                                NetBanquetType body = response.body();
                                List<NetBanquetType.DataDTO> list = body.getData();
                                if (ObjectUtils.isEmpty(list)) {
                                    return;
                                }

                                TwoLineTabSelectDialog dialog = new TwoLineTabSelectDialog(getContext());
                                dialog.setData2(list, null);
                                dialog.setTitle("宴会类型");
                                dialog.setOnHallSelectListener(new TwoLineTabSelectDialog.OnHallSelectListener() {
                                    @Override
                                    public void OnHallSelect(String id, String name, TwoLineTabSelectDialog dialog) {
                                        mBinding.tvNicheType.setText(name);
                                        mCondition.niche_type = id;
                                        mCondition.niche_type_name = name;
                                        dialog.dismiss();
                                        mBinding.tvNicheType.setText(name);
                                    }
                                });
                                dialog.show();

                            }
                        });
            }
        });
        //宴会厅
        mBinding.tvHallId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<NetBanquetChildHall>post(HttpURLs.listBanquetHall)
                        .params("s_type", 0)
                        .params("type", mType)
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
        //获客渠道
        mBinding.tvCustomerType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<NetCustomerChannel>post(HttpURLs.customerChannel)
//                        .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                        .execute(new JsonCallback<NetCustomerChannel>() {
                            @Override
                            public void onSuccess(Response<NetCustomerChannel> response) {
                                NetCustomerChannel body = response.body();
                                List<NetCustomerChannel.DataDTO> list = body.getData();
                                if (ObjectUtils.isEmpty(list)) {
                                    return;
                                }

                                List<String> strings = new ArrayList<>();
                                for (NetCustomerChannel.DataDTO dataDTO : list) {
                                    strings.add(dataDTO.getName());
                                }
                                PickerListDialog dialog = new PickerListDialog(getActivity());
                                dialog.items(strings);
                                dialog.itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                                    @Override
                                    public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                        NetCustomerChannel.DataDTO dataDTO = list.get(position);

                                        mCondition.customer_type = dataDTO.getId();
                                        mCondition.customer_type_name = dataDTO.getName();
                                        dialog.dismiss();
                                        mBinding.tvCustomerType.setText(dataDTO.getName());
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
        //套餐
        mBinding.tvMealId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                if (mCondition.type == BanquetCelebrationType.TYPE_BANQUET) {
                    url = HttpURLs.listMeal;
                } else {
                    url = HttpURLs.listCelebration;
                }

                OkGo.<NetMealList>post(url)
//                        .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                        .execute(new JsonCallback<NetMealList>() {
                            @Override
                            public void onSuccess(Response<NetMealList> response) {
                                NetMealList body = response.body();
                                List<NetMealList.DataDTO> list = body.getData();
                                if (ObjectUtils.isEmpty(list)) {
                                    return;
                                }

                                List<String> strings = new ArrayList<>();
                                for (NetMealList.DataDTO dataDTO : list) {
                                    strings.add(dataDTO.getName());
                                }
                                PickerListDialog dialog = new PickerListDialog(getActivity());
                                dialog.items(strings);
                                dialog.itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                                    @Override
                                    public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                        NetMealList.DataDTO dataDTO = list.get(position);
                                        mCondition.meal_id = dataDTO.getId();
                                        mCondition.meal_id_name = dataDTO.getName();
                                        mBinding.tvMealId.setText(mCondition.meal_id_name);
                                        dialog.dismiss();
                                    }
                                });
                                dialog.show();

                            }
                        });
            }
        });
        mBinding.tvDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开始时间
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy/MM/dd");
                        mBinding.tvDateStart.setText(string);
                    }
                })
                        .setTitleText("请选择开始时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build()
                        .show();
            }
        });
        mBinding.tvDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy/MM/dd");
                        mBinding.tvDateEnd.setText(string);
                    }
                })
                        .setTitleText("请选择结束时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build()
                        .show();
            }
        });
        mBinding.tvNumberDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy/MM/dd");
                        mBinding.tvNumberDateStart.setText(string);
                    }
                })
                        .setTitleText("请选择开始时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build()
                        .show();
            }
        });
        mBinding.tvNumberDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        String string = TimeUtils.date2String(date, "yyyy/MM/dd");
                        mBinding.tvNumberDateEnd.setText(string);
                    }
                })
                        .setTitleText("请选择结束时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, false, false, false})
                        .build()
                        .show();
            }
        });

        mBinding.tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCondition = new OrderFilterCondition();
                refreshUI();
            }
        });
        mBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCondition.real_name = mBinding.etRealName.getText().toString().trim();
                mCondition.mobile = mBinding.etMobile.getText().toString().trim();
                mCondition.color_system = mBinding.etColorSystem.getText().toString().trim();
                float rating = mBinding.rbIntentionality.getRating();
                if (rating > 0) {
                    mCondition.Intentionality = rating + "";
                } else {
                    mCondition.Intentionality = null;
                }

                mCondition.is_yd_qd = mGroupViewUtils.getSelectedValueMap().get("is_yd_qd");
                mCondition.table_number = mBinding.etTableNumberStart.getText().toString() + "-"
                        + mBinding.etTableNumberEnd.getText().toString().trim();
                mCondition.budget_money = mBinding.etBudgetMoneyStart.getText().toString() + "-"
                        + mBinding.etBudgetMoneyEnd.getText().toString().trim();

                mCondition.final_amount = mBinding.etFinalAmountStart.getText().toString() + "-"
                        + mBinding.etFinalAmountEnd.getText().toString().trim();

                mCondition.date = mBinding.tvDateStart.getText().toString() + "-"
                        + mBinding.tvDateEnd.getText().toString().trim();
                mCondition.number_date = mBinding.tvNumberDateStart.getText().toString() + "-"
                        + mBinding.tvNumberDateEnd.getText().toString().trim();

                if (StringUtils.isEmpty(mCondition.table_number) || StringUtils.equals(mCondition.table_number, "-")) {
                    mCondition.table_number = null;
                }
                if (StringUtils.isEmpty(mCondition.budget_money) || StringUtils.equals(mCondition.budget_money, "-")) {
                    mCondition.budget_money = null;
                }
                if (StringUtils.isEmpty(mCondition.final_amount) || StringUtils.equals(mCondition.final_amount, "-")) {
                    mCondition.final_amount = null;
                }
                if (StringUtils.isEmpty(mCondition.date) || StringUtils.equals(mCondition.date, "-")) {
                    mCondition.date = null;
                }
                if (StringUtils.isEmpty(mCondition.number_date) || StringUtils.equals(mCondition.number_date, "-")) {
                    mCondition.number_date = null;
                }


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
        mBinding.etRealName.setText(mCondition.real_name);
        mBinding.etMobile.setText(mCondition.mobile);

        String statusName = "";
        if (!StringUtils.isEmpty(mCondition.status)) {
            if ("1".equals(mCondition.status)) {
                statusName = "商机";
            } else if ("2".equals(mCondition.status)) {
                statusName = "意向";
            } else if ("11".equals(mCondition.status)) {
                statusName = "待跟进";
            } else if ("12".equals(mCondition.status)) {
                statusName = "待结算";
            } else if ("13".equals(mCondition.status)) {
                statusName = "待处理";
            }
        }
        mBinding.tvStatus.setText(statusName);

        String isLostStr = "";
        if (StringUtils.equals(mCondition.is_lost, "0")) {
            isLostStr = "否";
        } else if (StringUtils.equals(mCondition.is_lost, "1")) {
            isLostStr = "是";
        }
        mBinding.tvIsLost.setText(isLostStr);

        mBinding.tvNicheType.setText(mCondition.niche_type_name);
        mBinding.tvHallId.setText(mCondition.hall_id_name);
        mBinding.tvCustomerType.setText(mCondition.customer_type_name);
        mBinding.tvIntentManId.setText(mCondition.intent_man_id_name);
        mBinding.tvMealId.setText(mCondition.meal_id_name);

        float rb = 0;
        try {
            rb = Float.parseFloat(mCondition.Intentionality);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBinding.rbIntentionality.setRating(rb);


        String isYdQd = mCondition.is_yd_qd;
        mGroupViewUtils.select("is_yd_qd", isYdQd);

        String tableStart = "";
        String tableEnd = "";
        try {
            if (mCondition.table_number != null) {
                String[] split = mCondition.table_number.split("-");
                tableStart = split[0];
                tableEnd = split[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBinding.etTableNumberStart.setText(tableStart);
        mBinding.etTableNumberEnd.setText(tableEnd);


        //et_budget_money_start
        String budgetStart = "";
        String budgetEnd = "";
        try {
            if (mCondition.budget_money != null) {
                String[] split = mCondition.budget_money.split("-");
                budgetStart = split[0];
                budgetEnd = split[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBinding.etBudgetMoneyStart.setText(budgetStart);
        mBinding.etBudgetMoneyEnd.setText(budgetEnd);

        //et_final_amount_start
        String finalAmountStart = "";
        String finalAmountEnd = "";
        try {
            if (mCondition.final_amount != null) {
                String[] split = mCondition.final_amount.split("-");
                finalAmountStart = split[0];
                finalAmountEnd = split[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBinding.etFinalAmountStart.setText(finalAmountStart);
        mBinding.etFinalAmountEnd.setText(finalAmountEnd);

        String dateStart = "";
        String dateEnd = "";
        try {
            if (mCondition.date != null) {
                String[] split = mCondition.date.split("-");
                dateStart = split[0];
                dateEnd = split[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBinding.tvDateStart.setText(dateStart);
        mBinding.tvDateEnd.setText(dateEnd);

        //tv_number_date_start
        String numberDateStart = "";
        String numberDateEnd = "";
        try {
            if (mCondition.number_date != null) {
                String[] split = mCondition.number_date.split("-");
                numberDateStart = split[0];
                numberDateEnd = split[1];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mBinding.tvNumberDateStart.setText(numberDateStart);
        mBinding.tvNumberDateEnd.setText(numberDateEnd);

        mBinding.etColorSystem.setText(mCondition.color_system);

    }


}
