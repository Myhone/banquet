package com.lingyan.banquet.ui.celebration.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.databinding.FragmentCelStep3Binding;
import com.lingyan.banquet.event.ApplyUnlockOrderEvent;
import com.lingyan.banquet.event.SaveReserveSuccessEvent;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.celebration.bean.NetCelRestoreStep3;
import com.lingyan.banquet.views.dialog.InputUnlockReasonDialog;
import com.lingyan.banquet.views.dialog.PayWayDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 锁台
 * Created by _hxb on 2020/12/13.
 */

public class CelStep3Fragment extends BaseCelStepFragment {

    private FragmentCelStep3Binding mBinding;
    private NetCelRestoreStep3.DataDTO mData;

    public static CelStep3Fragment newInstance() {
        CelStep3Fragment fragment = new CelStep3Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCelStep3Binding.inflate(inflater);
        mBinding.tvPayTime.setText(TimeUtils.getNowString());
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        mBinding.tvPayType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payType = mData.getPay_type();
                int payWay = 4;
                if (ObjectUtils.isNotEmpty(payType)) {
                    payWay = Integer.valueOf(payType.trim());
                }

                PayWayDialog payWayDialog = new PayWayDialog(getActivity());
                payWayDialog.setOnPayWayClickListener(new PayWayDialog.OnPayWayClickListener() {
                    @Override
                    public void onPayWayClick(int payWay, String name, PayWayDialog dialog) {
                        mBinding.tvPayType.setText(name);
                        mData.setPay_type(payWay + "");
                        mData.setPay_name(name);
                        dialog.dismiss();
                    }
                });
                payWayDialog.setPayWay(payWay);
                payWayDialog.show();
            }
        });

        mBinding.llUnlockContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mData ==null
                        ||!StringUtils.equals("0",mData.getIs_status())
                        ||!StringUtils.equals("3",mData.getStatus())

                ){
                    ToastUtils.showShort("当前状态不允许操作！");
                    return;
                }

                InputUnlockReasonDialog dialog = new InputUnlockReasonDialog(getActivity());
                dialog.setType(4);
                dialog.setId(getCelId());
                dialog.show();
            }
        });
        //保存
        mBinding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        mData == null||
                                StringUtils.equals(mData.getFinance_confirmed(), "1") ||
                                !StringUtils.equals(mData.getIs_status(), "0")||
                                StringUtils.equals(mData.getStatus(), "6")

                ) {
                    ToastUtils.showShort("当前状态不可操作");
                    return;
                }
                setMaxStep(4);
                getStepActivity().changeStep(4);
            }
        });
        //财务确认
        mBinding.tvFinanceConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        mData == null||
                                StringUtils.equals(mData.getFinance_confirmed(), "1") ||
                                !StringUtils.equals(mData.getIs_status(), "0")||
                                StringUtils.equals(mData.getStatus(), "6")

                ) {
                    ToastUtils.showShort("当前状态不可操作");
                    return;
                }
                String payUser = mBinding.etPayUser.getText().toString().trim();
                String money = mBinding.etMoney.getText().toString().trim();
                if(StringUtils.isTrimEmpty(money)){
                    ToastUtils.showShort("请输入锁台定金");
                    return;
                }
                if(StringUtils.isTrimEmpty(payUser)){
                    ToastUtils.showShort("请输入付款人");
                    return;
                }
//                if(payUser.matches(".*\\d+.*") ){
//                    ToastUtils.showShort("客户名称不能包含数字");
//                    return;
//                }
                String payType = mData.getPay_type();
                if(StringUtils.isEmpty(payType)){
                    ToastUtils.showShort("请选择支付方式");
                    return;
                }

                mData.setStep("3");
//                mData.setStatus(null);
                mData.setMoney(money);
                mData.setPay_user(payUser);
                mData.setRemarks_3(mBinding.etRemarks.getText().toString().trim());
                //保存
                OkGo.<NetBaseResp>post(HttpURLs.saveBanquetStep3)
                        .upJson(GsonUtils.toJson(mData))
                        .tag(getThisFragment())
                        .execute(new JsonCallback<NetBaseResp>() {
                            @Override
                            public void onSuccess(Response<NetBaseResp> response) {
                                NetBaseResp body = response.body();
                                String msg = body.getMsg();
                                int code = body.getCode();
                                if (code == 200) {
                                    restoreDataFromNet();
                                    EventBus.getDefault().post(new SaveReserveSuccessEvent(getCelId()));
                                } else {
                                    ToastUtils.showShort(msg);
                                }
                            }
                        });
            }
        });

        restoreDataFromNet();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void unlock(ApplyUnlockOrderEvent event){
        restoreDataFromNet();
    }
    private void restoreDataFromNet() {
        OkGo.<NetCelRestoreStep3>post(HttpURLs.banquetGetInfo)
                .params("id", getCelId())
                .params("step", 3)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetCelRestoreStep3>() {
                    @Override
                    public void onSuccess(Response<NetCelRestoreStep3> response) {
                        NetCelRestoreStep3 body = response.body();
                        mData = body.getData();
                        String step = mData.getStep();
                        if (ObjectUtils.isNotEmpty(step)) {
                            setMaxStep(Integer.valueOf(step));
                        }
                        refreshUI();
                    }
                });
    }

    private void refreshUI() {
        if (mData == null) {
            return;
        }
        String status = mData.getStatus();
        String step = mData.getStep();
        String financeConfirmed = mData.getFinance_confirmed1();
        mBinding.llBottomStep1.setVisibility(View.GONE);
        mBinding.llBottomStep2.setVisibility(View.GONE);
        mBinding.llBottomStep3.setVisibility(View.GONE);

        //在预定里面
        if (StringUtils.equals(financeConfirmed, "0")|| StringUtils.isEmpty(financeConfirmed)) {
            //准备新建锁台
            mBinding.tvTopBarDes.setText("需要财务确认");
            mBinding.llBottomStep1.setVisibility(View.VISIBLE);
        } else if (StringUtils.equals(financeConfirmed, "1")) {
            mBinding.tvTopBarDes.setText("待财务确认");
            mBinding.llBottomStep2.setVisibility(View.VISIBLE);
        } else if (StringUtils.equals(financeConfirmed, "2")) {
            mBinding.tvTopBarDes.setText("财务已确认");
            mBinding.llBottomStep3.setVisibility(View.VISIBLE);
            mBinding.llUnlockContainer.setVisibility(View.VISIBLE);
        }else  if (StringUtils.equals(financeConfirmed, "3")){
            mBinding.tvTopBarDes.setText("已退款");
        }


        mBinding.etMoney.setText(mData.getMoney());
        if (!StringUtils.isEmpty(mData.getPay_name())) {
            mBinding.tvPayType.setText(mData.getPay_name());
        } else {
            mBinding.tvPayType.setText("现金支付");
            mData.setPay_type("4");
        }        mBinding.tvCode.setText(mData.getCode());

        String time = mData.getPay_time();
        if (StringUtils.isTrimEmpty(time)) {
            time = TimeUtils.getNowString();
        }
        mBinding.tvPayTime.setText(time);

        String payUser = mData.getPay_user();
        if(StringUtils.isEmpty(payUser)){
            mData.setPay_user(mData.getReal_name());
        }
        mBinding.etPayUser.setText(mData.getPay_user());

        mBinding.etRemarks.setText(mData.getRemarks_3());


    }
    @Override
    public boolean canLoseOrder() {
        if (mData == null) {
            return false;
        }
        if (StringUtils.equals(mData.getStatus(), "6")
                || StringUtils.equals(mData.getIs_lost(), "1")
                || StringUtils.equals(mData.getFinance_confirmed(), "1")
                || !StringUtils.equals(mData.getIs_status(), "0")
        ) {
            return false;
        }


        return true;
    }
}
