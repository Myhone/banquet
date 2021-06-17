package com.lingyan.banquet.ui.data.july;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.LayoutDataBasicBinding;
import com.lingyan.banquet.databinding.LayoutDataBasicJulyBinding;
import com.lingyan.banquet.databinding.LayoutDataChannelBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.DataHomeActivity;
import com.lingyan.banquet.ui.data.NewAddDataListActivity;
import com.lingyan.banquet.ui.data.NewLockLoseListActivity;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.ui.data.bean.NetDataBasic;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class DataBasicController {

    private LayoutDataBasicJulyBinding mBinding;
    private JulySiegeActivity mActivity;

    public DataBasicController(LayoutDataBasicJulyBinding binding, JulySiegeActivity dataHomeActivity) {
        mBinding = binding;
        mActivity = dataHomeActivity;

        binding.tvIncome.setText("0");
        binding.tvDeposit.setText("0");
        binding.tvBalance.setText("0");
        binding.tvFinalAmount.setText("0");
        binding.tvTableNumber.setText("0");
        binding.tvOrderNumber.setText("0");

        binding.tvUndoIncome.setText("0");
        binding.tvUndoFinalAmount.setText("0");
        binding.tvUndoTableNumber.setText("0");
        binding.tvUndoOrderNumber.setText("0");

        binding.tvNewData1.setText("0");
        binding.tvNewData2.setText("0");
        binding.tvNewData3.setText("0");
        binding.tvNewData4.setText("0");
        binding.tvNewData5.setText("0");
        binding.tvNewData6.setText("0");


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConditionFilter filter = dataHomeActivity.getConditionFilter();
                String json = GsonUtils.toJson(filter);
                switch (v.getId()) {
                    case R.id.tv_new_data1:
                    case R.id.tv_new_data1_title: {
                        NewAddDataListActivity.start(json, 1);
                        break;
                    }
                    case R.id.tv_new_data2:
                    case R.id.tv_new_data2_title: {
                        NewAddDataListActivity.start(json, 2);
                        break;
                    }
                    case R.id.tv_new_data3:
                    case R.id.tv_new_data3_title: {
                        NewAddDataListActivity.start(json, 3);
                        break;
                    }
                    case R.id.tv_new_data4:
                    case R.id.tv_new_data4_title: {
                        NewAddDataListActivity.start(json, 4);
                        break;
                    }
                    case R.id.tv_new_data5:
                    case R.id.tv_new_data5_title: {
                        NewLockLoseListActivity.start(json, 1);
                        break;
                    }
                    case R.id.tv_new_data6:
                    case R.id.tv_new_data6_title: {
                        NewLockLoseListActivity.start(json, 2);
                        break;
                    }
                }
            }
        };
        binding.tvNewData1.setOnClickListener(listener);
        binding.tvNewData2.setOnClickListener(listener);
        binding.tvNewData3.setOnClickListener(listener);
        binding.tvNewData4.setOnClickListener(listener);
        binding.tvNewData5.setOnClickListener(listener);
        binding.tvNewData6.setOnClickListener(listener);

        binding.tvNewData1Title.setOnClickListener(listener);
        binding.tvNewData2Title.setOnClickListener(listener);
        binding.tvNewData3Title.setOnClickListener(listener);
        binding.tvNewData4Title.setOnClickListener(listener);
        binding.tvNewData5Title.setOnClickListener(listener);
        binding.tvNewData6Title.setOnClickListener(listener);


    }

    public void refresh(ConditionFilter condition, RefreshLayout layout) {
        String json = GsonUtils.toJson(condition);
        JsonObject jo = (JsonObject) JsonParser.parseString(json);
        jo.addProperty("order", 1);

        OkGo.<NetDataBasic>post(HttpURLs.screen2Data1)
                .upJson(jo.toString())
                .execute(new JsonCallback<NetDataBasic>() {
                    @Override
                    public void onSuccess(Response<NetDataBasic> response) {
                        NetDataBasic body = response.body();
                        if (body == null) {
                            return;
                        }
                        NetDataBasic.DataBean data = body.getData();
                        if (data == null) {
                            return;
                        }
                        NetDataBasic.CompleteData completeData = data.getCompleteData();
                        NetDataBasic.ExpectData expectData = data.getExpectData();
                        NetDataBasic.IncompleteData incompleteData = data.getIncompleteData();
                        NetDataBasic.IncreaseData increaseData = data.getIncreaseData();
                        List<NetDataBasic.CustomerTypeData> customerTypeData = data.getCustomerTypeData();

                        if (completeData != null) {
                            mBinding.tvIncome.setText(completeData.income);
                            mBinding.tvDeposit.setText(completeData.deposit);
                            mBinding.tvBalance.setText(completeData.balance);
                            mBinding.tvFinalAmount.setText(completeData.final_amount);
                            mBinding.tvTableNumber.setText(completeData.table_number);
                            mBinding.tvOrderNumber.setText(completeData.order_number);

                        }

                        if (expectData != null) {
                            mBinding.tvUndoIncome.setText(expectData.income);
                            mBinding.tvUndoFinalAmount.setText(expectData.final_amount);
                            mBinding.tvUndoTableNumber.setText(expectData.table_number + " (" + expectData.prepare_number + ")");
                            mBinding.tvUndoOrderNumber.setText(expectData.order_number);
                        }

                        if (increaseData != null) {
                            mBinding.tvNewData1.setText(increaseData.data1);
                            mBinding.tvNewData2.setText(increaseData.data2);
                            mBinding.tvNewData3.setText(increaseData.data3);
                            mBinding.tvNewData4.setText(increaseData.data4);
                            mBinding.tvNewData5.setText(increaseData.data5);
                            mBinding.tvNewData6.setText(increaseData.data6);
                            mBinding.tvNewData7.setText(increaseData.follow_all);
                            mBinding.tvNewData8.setText(increaseData.follow_banquet);
                        }
                        mBinding.flexBoxChannel.removeAllViews();
                        if (ObjectUtils.isNotEmpty(customerTypeData)) {
                            for (NetDataBasic.CustomerTypeData bean : customerTypeData) {
                                LayoutDataChannelBinding channelBinding = LayoutDataChannelBinding.inflate(mActivity.getLayoutInflater());
                                channelBinding.tvChannelTitle.setText(bean.name);
                                channelBinding.tvChannelCount.setText(bean.value);
                                FrameLayout root = channelBinding.getRoot();
                                mBinding.flexBoxChannel.addView(root);
                                ViewGroup.LayoutParams params = root.getLayoutParams();
                                params.width = (int) ((ScreenUtils.getAppScreenWidth() * 1f - ConvertUtils.dp2px(20) * 2) / 3);
                                root.setLayoutParams(params);
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        if (layout != null) {
                            layout.finishRefresh();
                        }
                    }
                });
    }
}
