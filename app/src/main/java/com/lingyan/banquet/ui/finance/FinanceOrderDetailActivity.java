package com.lingyan.banquet.ui.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.App;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityFinanceOrderDetailBinding;
import com.lingyan.banquet.event.DepositStatusEvent;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.banquet.BanquetStepManagerActivity;
import com.lingyan.banquet.ui.celebration.CelStepManagerActivity;
import com.lingyan.banquet.ui.finance.bean.NetDepositDetail;
import com.lingyan.banquet.ui.main.MineFragment;
import com.lingyan.banquet.ui.order.OrderDetailActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by _hxb on 2021/1/3.
 */

public class FinanceOrderDetailActivity extends BaseActivity implements OnRefreshListener {


    private ActivityFinanceOrderDetailBinding mBinding;
    private String mId;


    public static void start(String id) {
        Intent intent = new Intent(App.sApp, FinanceOrderDetailActivity.class);
        intent.putExtra("id", id);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityFinanceOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("订单详情");
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mBinding.refreshLayout.setOnRefreshListener(this);
        initUI();
        onRefresh(mBinding.refreshLayout);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {

        OkGo.<NetDepositDetail>post(HttpURLs.depositInfo)
                .params("id", mId)
                .execute(new JsonCallback<NetDepositDetail>() {
                    @Override
                    public void onSuccess(Response<NetDepositDetail> response) {
                        NetDepositDetail body = response.body();
                        if (body == null) {
                            return;
                        }
                        NetDepositDetail.DataDTO data = body.getData();
                        if (data == null) {
                            return;
                        }

                        String financeConfirmed = data.getFinance_confirmed();
                        String step = data.getStep();
                        //状态  1-待确认  2-已确认  3-已退款
                        String financeConfirmedDesc = "";
                        if (StringUtils.equals("1", financeConfirmed)) {
                            financeConfirmedDesc = "待确认";
                        } else if (StringUtils.equals("2", financeConfirmed)) {
                            financeConfirmedDesc = "已确认";
                        } else if (StringUtils.equals("3", financeConfirmed)) {
                            financeConfirmedDesc = "已退款";
                        } else if (StringUtils.equals("4", financeConfirmed)) {
                            financeConfirmedDesc = "已驳回";
                        }
                        mBinding.tvFinanceConfirmed.setText(financeConfirmedDesc);
                        mBinding.tvTitle.setText(data.getTitle());
                        mBinding.tvMoney.setText("¥" + data.getMoney());
                        mBinding.tvId.setText(data.getBanquet_id());
                        mBinding.tvPayTime.setText(data.getPay_time());
                        mBinding.tvCode.setText(data.getCode());
                        mBinding.tvPayType.setText(data.getPay_type_name());
                        mBinding.tvPayUser.setText(data.getPay_user());
                        mBinding.tvPayee.setText(data.getPayee());
                        mBinding.tvPayeeTime.setText(data.getPayee_time());

                        if (StringUtils.equals("4", financeConfirmed)) {
                            mBinding.tvActionUserTitle.setText("驳回人");
                            mBinding.tvActionTimeTitle.setText("驳回时间");
                        } else {
                            mBinding.tvActionUserTitle.setText("收款人");
                            mBinding.tvActionTimeTitle.setText("收款时间");
                        }

                        String isReceivedMoney = "";
                        if (StringUtils.equals("2", financeConfirmed)) {
                            isReceivedMoney = "是";
                        } else {
                            isReceivedMoney = "否";
                        }
                        mBinding.tvIsReceiveMoney.setText(isReceivedMoney);

                        mBinding.tvRefundRealname.setText(data.getRefund_realname());
                        mBinding.tvRefundTime.setText(data.getRefund_time());
                        String isRefund = "";
                        if (StringUtils.equals("3", financeConfirmed)) {
                            isRefund = "是";
                        } else {
                            isRefund = "否";
                        }
                        mBinding.tvIsRefund.setText(isRefund);

                        mBinding.tvStatus.setText(data.getStatus());
                        mBinding.tvCustomer.setText(data.getCustomer());
                        mBinding.tvDate.setText(data.getDate());
                        mBinding.tvLookQd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String banquetId = data.getBanquet_id();
                                String banquetType = data.getBanquet_type();
                                int type = BanquetCelebrationType.TYPE_BANQUET;
                                try {
                                    type = Integer.parseInt(banquetType);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                OrderDetailActivity.start(HttpURLs.banquetOrderInfo, banquetId, type);

                            }
                        });


                        String actionStatus = "";
                        if (data.getType() == 1 || data.getType() == 2) {
                            actionStatus = "定金";
                        } else {
                            actionStatus = "尾款";
                        }
                        String finalActionStatus = actionStatus;
                        //状态  1-待确认  2-已确认  3-已退款
                        String actionStr = "";
                        mBinding.llActionReceiveRejectContainer.setVisibility(View.GONE);
                        mBinding.tvActionRefund.setVisibility(View.GONE);
                        if (StringUtils.equals("1", financeConfirmed)) {
                            actionStr = "收" + actionStatus;
                            mBinding.llActionReceiveRejectContainer.setVisibility(View.VISIBLE);
                            mBinding.tvActionReceive.setText(actionStr);
                            mBinding.tvActionReceive.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    new MaterialDialog.Builder(getActivity())
                                            .title("提示")
                                            .content(String.format("确认收到该%s?", finalActionStatus))
                                            .positiveText("确认")
                                            .negativeText("点错了")
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    OkGo.<NetBaseResp>post(HttpURLs.depositConfirm)
                                                            .params("id", data.getId())
                                                            .execute(new JsonCallback<NetBaseResp>() {
                                                                @Override
                                                                public void onSuccess(Response<NetBaseResp> response) {
                                                                    NetBaseResp body = response.body();
                                                                    String msg = body.getMsg();
                                                                    int code = body.getCode();
                                                                    if (code == 200) {
                                                                        EventBus.getDefault().post(new DepositStatusEvent(data.getId()));
                                                                        onRefresh(mBinding.refreshLayout);
                                                                    } else {
                                                                        ToastUtils.showShort(msg);
                                                                    }
                                                                }
                                                            });
                                                }
                                            })
                                            .show();


                                }
                            });
                            mBinding.tvActionReject.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    new MaterialDialog.Builder(getActivity())
                                            .title("提示")
                                            .content(String.format("确认驳回该订单?"))
                                            .positiveText("确认")
                                            .negativeText("点错了")
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    OkGo.<NetBaseResp>post(HttpURLs.depositReject)
                                                            .params("id", data.getId())
                                                            .execute(new JsonCallback<NetBaseResp>() {
                                                                @Override
                                                                public void onSuccess(Response<NetBaseResp> response) {
                                                                    NetBaseResp body = response.body();
                                                                    String msg = body.getMsg();
                                                                    int code = body.getCode();
                                                                    if (code == 200) {
                                                                        EventBus.getDefault().post(new DepositStatusEvent(data.getId()));
                                                                        onRefresh(mBinding.refreshLayout);
                                                                    } else {
                                                                        ToastUtils.showShort(msg);
                                                                    }
                                                                }
                                                            });
                                                }
                                            })
                                            .show();


                                }
                            });

                        } else if (StringUtils.equals("2", financeConfirmed) && StringUtils.equals("1", data.getIs_display())) {
                            actionStr = "退" + actionStatus;
                            mBinding.tvActionRefund.setText(actionStr);
                            mBinding.tvActionRefund.setVisibility(View.VISIBLE);
                            mBinding.tvActionRefund.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    new MaterialDialog.Builder(getActivity())
                                            .title("提示")
                                            .content(String.format("确认要退该%s?", finalActionStatus))
                                            .positiveText("确认")
                                            .negativeText("点错了")
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                    OkGo.<NetBaseResp>post(HttpURLs.reFundConfirm)
                                                            .params("id", data.getId())
                                                            .execute(new JsonCallback<NetBaseResp>() {
                                                                @Override
                                                                public void onSuccess(Response<NetBaseResp> response) {
                                                                    NetBaseResp body = response.body();
                                                                    String msg = body.getMsg();
                                                                    int code = body.getCode();
                                                                    if (code == 200) {
                                                                        EventBus.getDefault().post(new DepositStatusEvent(data.getId()));
                                                                        onRefresh(mBinding.refreshLayout);
                                                                    } else {
                                                                        ToastUtils.showShort(msg);
                                                                    }
                                                                }
                                                            });
                                                }
                                            })
                                            .show();
                                }
                            });
                        }


                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mBinding.refreshLayout.finishRefresh();
                    }
                });
    }

    private void initUI() {
        mBinding.tvFinanceConfirmed.setText("");
        mBinding.tvTitle.setText("");
        mBinding.tvMoney.setText("");
        mBinding.tvId.setText("");
        mBinding.tvPayTime.setText("");
        mBinding.tvCode.setText("");
        mBinding.tvPayType.setText("");
        mBinding.tvPayUser.setText("");
        mBinding.tvPayee.setText("");
        mBinding.tvPayeeTime.setText("");
        mBinding.tvIsReceiveMoney.setText("");

        mBinding.tvRefundRealname.setText("");
        mBinding.tvRefundTime.setText("");
        mBinding.tvIsRefund.setText("");

        mBinding.tvStatus.setText("");
        mBinding.tvCustomer.setText("");
        mBinding.tvDate.setText("");
        mBinding.llActionReceiveRejectContainer.setVisibility(View.GONE);
        mBinding.tvActionRefund.setVisibility(View.GONE);
    }
}
