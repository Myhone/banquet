package com.lingyan.banquet.ui.banquet.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.FragmentBanquetStep4Binding;
import com.lingyan.banquet.event.SaveReserveSuccessEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.banquet.bean.NetMarketing;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep2;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep4;
import com.lingyan.banquet.ui.banquet.bean.NetSinglePersonList;
import com.lingyan.banquet.ui.banquet.session.SignSessionFragment;
import com.lingyan.banquet.views.dialog.PayWayDialog;
import com.lingyan.banquet.views.dialog.PersonPickerDialog;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 签订
 * Created by _hxb on 2020/12/13.
 */

public class BanquetStep4Fragment extends BaseBanquetStepFragment {

    private FragmentBanquetStep4Binding mBinding;
    private FragmentManager mFragmentManager;
    private List<SignSessionFragment> mFragmentList;
    private NetRestoreStep4.DataDTO mData;


    public static BanquetStep4Fragment newInstance() {
        BanquetStep4Fragment fragment = new BanquetStep4Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentBanquetStep4Binding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //保存
        mBinding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        mData == null ||
                                !StringUtils.equals(mData.getIs_status(), "0") ||
                                StringUtils.equals(mData.getStatus(), "6")

                ) {
                    ToastUtils.showShort("当前状态不可操作");
                    return;
                }
                setMaxStep(5);
                getStepActivity().changeStep(5);
            }
        });
        //财务确认
        mBinding.tvFinanceConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        mData == null ||
                                !StringUtils.equals(mData.getIs_status(), "0") ||
                                StringUtils.equals(mData.getStatus(), "6")

                ) {
                    ToastUtils.showShort("当前状态不可操作");
                    return;
                }
                String payType = mData.getPay_type();
                if (StringUtils.isEmpty(payType)) {
                    ToastUtils.showShort("请选择支付方式");
                    return;
                }
                List<NetRestoreStep4.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
                if (ObjectUtils.isEmpty(list)) {
                    ToastUtils.showShort("当前状态不可操作");
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    NetRestoreStep4.DataDTO.BanquetNumDTO dto = list.get(i);
                    String segmentType = dto.getSegment_type();
                    String date = dto.getDate();
                    List<String> hallId = dto.getHall_id();
                    if (StringUtils.isTrimEmpty(date)) {
                        ToastUtils.showShort(String.format("请选择第%d场的场次时间", (i + 1)));
                        return;
                    }
                    if (StringUtils.isTrimEmpty(segmentType)) {
                        ToastUtils.showShort(String.format("请选择第%d场的用餐时间", (i + 1)));
                        return;
                    }
                    if (ObjectUtils.isEmpty(hallId)) {
                        ToastUtils.showShort(String.format("请选择第%d场的宴会厅", (i + 1)));
                        return;
                    }
                }
                String customerName = mBinding.etContractorCustomer.getText().toString().trim();
                if (StringUtils.isTrimEmpty(customerName)) {
                    ToastUtils.showShort("请输入签约客户");
                    return;
                }

//                if (customerName.matches(".*\\d+.*")) {
//                    ToastUtils.showShort("客户名称不能包含数字");
//                    return;
//                }
                String contractorUser = mBinding.tvContractorUser.getText().toString().trim();
                if (StringUtils.isEmpty(contractorUser)) {
                    ToastUtils.showShort("请选择签约人");
                    return;
                }
                if (StringUtils.isTrimEmpty(mBinding.etSignMoney.getText().toString())) {
                    ToastUtils.showShort("请输入签订定金");
                    return;
                }
                if (StringUtils.isTrimEmpty(mBinding.etBudgetMoney.getText().toString())) {
                    ToastUtils.showShort("请输入预算总额");
                    return;
                }


                mData.setStep("4");
//                mData.setStatus(null);
                mData.setPreferential_fee(mBinding.etPreferentialFee.getText().toString().trim());
                mData.setBudget_money(mBinding.etBudgetMoney.getText().toString().trim());
                mData.setSign_money(mBinding.etSignMoney.getText().toString().trim());
                mData.setContractor_customer(mBinding.etContractorCustomer.getText().toString().trim());
                mData.setRemarks_4(mBinding.etRemarks.getText().toString().trim());
                //保存
                OkGo.<NetBaseResp>post(HttpURLs.saveBanquetStep4)
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
                                    EventBus.getDefault().post(new SaveReserveSuccessEvent(getBanquetId()));

                                } else {
                                    ToastUtils.showShort(msg);
                                }
                            }
                        });
            }
        });
        mBinding.tvPayType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payType = mData.getPay_type();
                int payWay = 0;
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


        mBinding.tvMarketingType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //优惠活动
                OkGo.<NetMarketing>post(HttpURLs.listMarketing)
//                        .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                        .execute(new JsonCallback<NetMarketing>() {
                            @Override
                            public void onSuccess(Response<NetMarketing> response) {

                                NetMarketing body = response.body();
                                List<NetMarketing.DataDTO> list = body.getData();
                                if (ObjectUtils.isEmpty(list)) {
                                    return;
                                }

                                List<String> strings = new ArrayList<>();
                                for (NetMarketing.DataDTO dataDTO : list) {
                                    strings.add(dataDTO.getTitle());
                                }
                                new PickerListDialog(getContext())
                                        .items(strings)
                                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                                            @Override
                                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                                dialog.dismiss();
                                                NetMarketing.DataDTO dto = list.get(position);

                                                String title = dto.getTitle();
                                                String id = dto.getId();

                                                mBinding.tvMarketingType.setText(title);
                                                mData.setMarketing_type(id);
                                                mData.setMarketing_type_name(title);
                                            }
                                        })
                                        .show();
                            }
                        });
            }
        });
        mBinding.tvContractorUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //签约人
                showPersonList();
            }
        });


        mFragmentList = new ArrayList<>();
        mFragmentManager = getChildFragmentManager();
        mBinding.tvAddSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData == null) {
                    return;
                }
                List<NetRestoreStep4.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
                SignSessionFragment fragment = add();
                NetRestoreStep4.DataDTO.BanquetNumDTO dto = new NetRestoreStep4.DataDTO.BanquetNumDTO();
                fragment.setData(dto);
                list.add(dto);
                ToastUtils.showShort("长按左边场次可删除");
            }
        });
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                for (int i = 0; i < mFragmentList.size(); i++) {
                    SignSessionFragment fragment = mFragmentList.get(i);
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

    //签约人
    private void showPersonList() {
        PersonPickerDialog dialog = new PersonPickerDialog(getActivity());
        dialog.setOnPersonClickListener(new PersonPickerDialog.OnPersonClickListener() {
            @Override
            public void onPersonClick(String name, String id, PersonPickerDialog dialog) {
                mBinding.tvContractorUser.setText(name);
                mData.setContractor_user(id);
                mData.setContractor_user_name(name);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void restoreDataFromNet() {
        OkGo.<NetRestoreStep4>post(HttpURLs.banquetGetInfo)
                .params("id", getBanquetId())
                .params("step", 4)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetRestoreStep4>() {
                    @Override
                    public void onSuccess(Response<NetRestoreStep4> response) {
                        NetRestoreStep4 body = response.body();
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
            for (Fragment fragment : mFragmentList) {
                transaction.remove(fragment);
            }
            transaction.commitAllowingStateLoss();
            mFragmentList.clear();
        }


        mBinding.etRemarks.setText(mData.getRemarks_4());
        List<NetRestoreStep4.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
        if (list.size() == 0) {
            SignSessionFragment fragment = add();
            NetRestoreStep4.DataDTO.BanquetNumDTO dto = new NetRestoreStep4.DataDTO.BanquetNumDTO();
            fragment.setData(dto);
            list.add(dto);
        } else {
            for (int i = 0; i < list.size(); i++) {
                NetRestoreStep4.DataDTO.BanquetNumDTO dto = list.get(i);
                SignSessionFragment fragment = add();
                fragment.setData(dto);
            }
        }
        String status = mData.getStatus();
        String step = mData.getStep();
        String financeConfirmed = mData.getFinance_confirmed2();
        mBinding.llBottomStep1.setVisibility(View.GONE);
        mBinding.llBottomStep2.setVisibility(View.GONE);
        mBinding.llBottomStep3.setVisibility(View.GONE);
        //在预定里面
        if (StringUtils.equals(financeConfirmed, "0") || StringUtils.isEmpty(financeConfirmed)) {
            //准备新建锁台
            mBinding.tvTopBarDes.setText("需要财务确认");
            mBinding.llBottomStep1.setVisibility(View.VISIBLE);

        } else if (StringUtils.equals(financeConfirmed, "1")) {
            mBinding.tvTopBarDes.setText("待财务确认");
            mBinding.llBottomStep2.setVisibility(View.VISIBLE);
        } else if (StringUtils.equals(financeConfirmed, "2")) {
            mBinding.tvTopBarDes.setText("财务已确认");
            mBinding.llBottomStep3.setVisibility(View.VISIBLE);

        } else if (StringUtils.equals(financeConfirmed, "3")) {
            mBinding.tvTopBarDes.setText("已退款");
        }


        String marketingTypeName = mData.getMarketing_type_name();
        if (StringUtils.isTrimEmpty(marketingTypeName)) {
            marketingTypeName = "不使用活动";
        }
        mBinding.tvMarketingType.setText(marketingTypeName);

        mBinding.etPreferentialFee.setText(mData.getPreferential_fee());
        mBinding.etBudgetMoney.setText(mData.getBudget_money());
        mBinding.etSignMoney.setText(mData.getSign_money());

        mBinding.tvPayType.setText(mData.getPay_name());
        mBinding.tvCode.setText(mData.getCode());
        String payTime = mData.getPay_time();
        if (StringUtils.isTrimEmpty(payTime)) {
            payTime = TimeUtils.getNowString();
        }
        mBinding.tvPayTime.setText(payTime);

        String contractorUserName = mData.getContractor_user_name();
        if (StringUtils.isEmpty(contractorUserName)) {
            mData.setContractor_user(mData.getIntent_man_id());
            mData.setContractor_user_name(mData.getIntent_man_name());
        }


        String contractorCustomer = mData.getContractor_customer();
        if(StringUtils.isEmpty(contractorCustomer)){
            mData.setContractor_customer(mData.getReal_name());
        }

        mBinding.tvContractorUser.setText(mData.getContractor_user_name());
        mBinding.etContractorCustomer.setText(mData.getContractor_customer());
        mBinding.tvContractNo.setText(mData.getContract_no());
        mBinding.etRemarks.setText(mData.getRemarks_4());


    }


    private SignSessionFragment add() {
        int tabCount = mBinding.tabLayout.getTabCount();
        tabCount++;
        TabLayout.Tab tab = mBinding.tabLayout.newTab();
        tab.setText("第" + tabCount + "场");
        mBinding.tabLayout.addTab(tab);

        SignSessionFragment fragment = SignSessionFragment.newInstance();
        mFragmentList.add(fragment);

        mFragmentManager.beginTransaction().add(R.id.fl_session_container, fragment).commitAllowingStateLoss();

        tab.select();

        tab.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int nowCount = mBinding.tabLayout.getTabCount();
                if (nowCount <= 1) {
                    return false;
                }
                new MaterialDialog.Builder(getContext())
                        .title("提示")
                        .content("确定删除该场次?")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                int position = tab.getPosition();
                                List<NetRestoreStep4.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
                                list.remove(position);
                                mBinding.tabLayout.removeTab(tab);
                                mFragmentList.remove(fragment);
                                mFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();

                                for (int i = 0; i < nowCount - 1; i++) {
                                    mBinding.tabLayout.getTabAt(i).setText("第" + (i + 1) + "场");
                                }
                            }
                        })
                        .show();

                return true;
            }
        });

        return fragment;

    }
}
