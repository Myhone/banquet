package com.lingyan.banquet.ui.banquet.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.FragmentBanquetStep6Binding;
import com.lingyan.banquet.event.SaveReserveSuccessEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep6;
import com.lingyan.banquet.ui.banquet.session.EndSessionFragment;
import com.lingyan.banquet.views.dialog.PayWayDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 结算
 * Created by _hxb on 2020/12/13.
 */

public class BanquetStep6Fragment extends BaseBanquetStepFragment {
    private NetRestoreStep6.DataDTO mData;

    public static BanquetStep6Fragment newInstance() {
        BanquetStep6Fragment fragment = new BanquetStep6Fragment();
        return fragment;
    }

    private FragmentManager mFragmentManager;
    private List<EndSessionFragment> mFragmentList;
    private FragmentBanquetStep6Binding mBinding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentBanquetStep6Binding.inflate(inflater);
        return mBinding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //支付方式
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
        //财务确认
        mBinding.tvFinanceConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String finalAmount = mBinding.tvFinalAmount.getText().toString().trim();
                String receiveDeposit = mBinding.tvReceiveDeposit.getText().toString().trim();
                String balance = mBinding.tvBalance.getText().toString().trim();
                String payUser = mBinding.etPayUser.getText().toString().trim();


                if (StringUtils.isTrimEmpty(finalAmount)) {
                    ToastUtils.showShort("请输入最终金额");
                    return;
                }
                if (StringUtils.isTrimEmpty(receiveDeposit)) {
                    ToastUtils.showShort("请输入实收定金");
                    return;
                }
                if (StringUtils.isTrimEmpty(balance)) {
                    ToastUtils.showShort("请输入未结款项");
                    return;
                }

                if (StringUtils.isTrimEmpty(payUser)) {
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
                mData.setFinal_amount(finalAmount);
                mData.setReceive_deposit(receiveDeposit);
                mData.setBalance(balance);
                mData.setPay_user(payUser);
                mData.setRemarks_6(mBinding.etRemarks.getText().toString());

                mData.setStep("6");
//                mData.setStatus(null);
                String json = GsonUtils.toJson(mData);
                LogUtils.i(json);
                //保存
                OkGo.<NetBaseResp>post(HttpURLs.saveBanquetStep6)
                        .upJson(json)
                        .tag(getThisFragment())
                        .execute(new JsonCallback<NetBaseResp>() {
                            @Override
                            public void onSuccess(Response<NetBaseResp> response) {
                                NetBaseResp body = response.body();
                                String msg = body.getMsg();
                                int code = body.getCode();
                                if (code == 200) {
                                    ToastUtils.showShort("提交成功");
                                    setMaxStep(6);
                                    EventBus.getDefault().post(new SaveReserveSuccessEvent(getBanquetId()));
                                    getStepActivity().changeStep(6);
                                    restoreDataFromNet();
                                } else {
                                    ToastUtils.showShort(msg);
                                }
                            }
                        });
            }
        });
        //完成
        mBinding.tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("该预定已完成");
            }
        });



        mFragmentList = new ArrayList<>();
        mFragmentManager = getChildFragmentManager();

        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                for (int i = 0; i < mFragmentList.size(); i++) {
                    EndSessionFragment fragment = mFragmentList.get(i);
                    if (i == position) {
                        transaction.show(fragment);
                    } else {
                        transaction.hide(fragment);
                    }
                }
                transaction.commitAllowingStateLoss();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        restoreDataFromNet();
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

    private void restoreDataFromNet() {
        OkGo.<NetRestoreStep6>post(HttpURLs.banquetGetInfo)
                .params("id", getBanquetId())
                .params("step", 6)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetRestoreStep6>() {
                    @Override
                    public void onSuccess(Response<NetRestoreStep6> response) {
                        NetRestoreStep6 body = response.body();
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
        if (ObjectUtils.isEmpty(mData)) {
            return;
        }
        mBinding.tabLayout.removeAllTabs();
        if (ObjectUtils.isNotEmpty(mFragmentList)) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            for (EndSessionFragment fragment : mFragmentList) {
                transaction.remove(fragment);
            }
            transaction.commitAllowingStateLoss();
        }


        mBinding.etRemarks.setText(mData.getRemarks_6());
        List<NetRestoreStep6.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
        if (ObjectUtils.isEmpty(list)) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            NetRestoreStep6.DataDTO.BanquetNumDTO dto = list.get(i);
            EndSessionFragment fragment = add();
            fragment.setData(dto);
        }


        //最终金额
        mBinding.tvFinalAmount.setText(mData.getFinal_amount());
        //实收定金
        mBinding.tvReceiveDeposit.setText(mData.getReceive_deposit());
        //未结款项
        mBinding.tvBalance.setText(mData.getBalance());
        setMoney();

        mBinding.tvPayType.setText(mData.getPay_name());
        mBinding.tvCode.setText(mData.getCode());
        String payTime = mData.getPay_time();
        if (StringUtils.isTrimEmpty(payTime)) {
            payTime = TimeUtils.getNowString();
        }
        mBinding.tvPayTime.setText(payTime);
        String contractorCustomer = mData.getPay_user();
        if(StringUtils.isEmpty(contractorCustomer)){
            mData.setPay_user(mData.getReal_name());
        }

        mBinding.etPayUser.setText(mData.getPay_user());


        String status = mData.getStatus();
        String step = mData.getStep();
        String financeConfirmed = mData.getFinance_confirmed3();
        mBinding.llBottomStep1.setVisibility(View.GONE);
        mBinding.llBottomStep2.setVisibility(View.GONE);
        mBinding.llBottomStep3.setVisibility(View.GONE);

        if (StringUtils.equals(financeConfirmed, "0")||StringUtils.isEmpty(financeConfirmed)) {
            //准备新建锁台
            mBinding.tvTopBarDes.setText("需要财务确认");
            mBinding.llBottomStep1.setVisibility(View.VISIBLE);
        } else if (StringUtils.equals(financeConfirmed, "1")) {
            mBinding.tvTopBarDes.setText("待财务确认");
            mBinding.llBottomStep2.setVisibility(View.VISIBLE);
        } else if (StringUtils.equals(financeConfirmed, "2")) {
            mBinding.tvTopBarDes.setText("财务已确认");
            mBinding.llBottomStep3.setVisibility(View.VISIBLE);
        }else  if (StringUtils.equals(financeConfirmed, "3")){
            mBinding.tvTopBarDes.setText("已退款");
        }

        if (!StringUtils.isEmpty(mData.getPay_name())) {
            mBinding.tvPayType.setText(mData.getPay_name());
        } else {
            mBinding.tvPayType.setText("现金支付");
            mData.setPay_type("4");
        }
    }

    public void setMoney() {
        List<NetRestoreStep6.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
        if (ObjectUtils.isEmpty(list)) {
            return;
        }
        BigDecimal all = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            NetRestoreStep6.DataDTO.BanquetNumDTO dto = list.get(i);
            String sessionAmount = dto.getSession_amount();
            try {
                if (StringUtils.isEmpty(sessionAmount)) {
                    sessionAmount = "0";
                }
                BigDecimal sessionBigDecimal = new BigDecimal(sessionAmount);
                all = sessionBigDecimal.add(all);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //最终金额
        mBinding.tvFinalAmount.setText(all.toPlainString());
        //定金
        String deposit = mBinding.tvReceiveDeposit.getText().toString();
        try {
            //未结款项
            BigDecimal subtract = all.subtract(new BigDecimal(deposit));
            mBinding.tvBalance.setText(subtract.toPlainString());
        } catch (Exception e) {
            e.getStackTrace();
        }
    }


    private EndSessionFragment add() {
        int tabCount = mBinding.tabLayout.getTabCount();
        tabCount++;
        TabLayout.Tab tab = mBinding.tabLayout.newTab();
        tab.setText("第" + tabCount + "场");
        mBinding.tabLayout.addTab(tab);

        EndSessionFragment fragment = EndSessionFragment.newInstance();
        mFragmentList.add(fragment);

        FragmentTransaction transaction = mFragmentManager.beginTransaction();

        transaction.add(R.id.fl_session_container, fragment);
        if (tabCount == 1) {
            //显示第一场
            transaction.show(fragment);
        } else {
            transaction.hide(fragment);
        }

        transaction.commitAllowingStateLoss();


        return fragment;

    }
}
