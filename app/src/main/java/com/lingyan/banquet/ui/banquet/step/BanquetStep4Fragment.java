package com.lingyan.banquet.ui.banquet.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.amap.api.location.DPoint;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.FragmentBanquetStep4Binding;
import com.lingyan.banquet.event.OrderDetailRefreshEvent;
import com.lingyan.banquet.event.RefreshTotalPriceEvent;
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
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * ??????
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
        EventBus.getDefault().register(this);
        //??????
        mBinding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        mData == null ||
                                !StringUtils.equals(mData.getIs_status(), "0") ||
                                StringUtils.equals(mData.getStatus(), "6")

                ) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }
                setMaxStep(5);
                getStepActivity().changeStep(5);
            }
        });
        //????????????
        mBinding.tvFinanceConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        mData == null ||
                                !StringUtils.equals(mData.getIs_status(), "0") ||
                                StringUtils.equals(mData.getStatus(), "6")

                ) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }
//                String payType = mData.getPay_type();
//                if (StringUtils.isEmpty(payType)) {
//                    ToastUtils.showShort("?????????????????????");
//                    return;
//                }
                List<NetRestoreStep4.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
                if (ObjectUtils.isEmpty(list)) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    NetRestoreStep4.DataDTO.BanquetNumDTO dto = list.get(i);
                    String segmentType = dto.getSegment_type();
                    String date = dto.getDate();
                    List<String> hallId = dto.getHall_id();
                    if (StringUtils.isTrimEmpty(date)) {
                        ToastUtils.showShort(String.format("????????????%d??????????????????", (i + 1)));
                        return;
                    }
                    if (StringUtils.isTrimEmpty(segmentType)) {
                        ToastUtils.showShort(String.format("????????????%d??????????????????", (i + 1)));
                        return;
                    }
                    if (ObjectUtils.isEmpty(hallId)) {
                        ToastUtils.showShort(String.format("????????????%d???????????????", (i + 1)));
                        return;
                    }
                }
                String customerName = mBinding.etContractorCustomer.getText().toString().trim();
                if (StringUtils.isTrimEmpty(customerName)) {
                    ToastUtils.showShort("?????????????????????");
                    return;
                }

//                if (customerName.matches(".*\\d+.*")) {
//                    ToastUtils.showShort("??????????????????????????????");
//                    return;
//                }
                String contractorUser = mBinding.tvContractorUser.getText().toString().trim();
                if (StringUtils.isEmpty(contractorUser)) {
                    ToastUtils.showShort("??????????????????");
                    return;
                }
//                if (StringUtils.isTrimEmpty(mBinding.etSignMoney.getText().toString())) {
//                    ToastUtils.showShort("?????????????????????");
//                    return;
//                }
                if (StringUtils.isTrimEmpty(mBinding.etBudgetMoney.getText().toString())) {
                    ToastUtils.showShort("?????????????????????");
                    return;
                }


                mData.setStep("4");
//                mData.setStatus(null);
                mData.setPreferential_fee(mBinding.etPreferentialFee.getText().toString().trim());
                mData.setBudget_money(mBinding.etBudgetMoney.getText().toString().trim());
                mData.setSign_money(mBinding.etSignMoney.getText().toString().trim());
                mData.setContractor_customer(mBinding.etContractorCustomer.getText().toString().trim());
                mData.setRemarks_4(mBinding.etRemarks.getText().toString().trim());
                if ("0".equals(mData.getIs_pay())){
                    mData.setPay_type("");
                    mData.setSign_money("");
                }
                //??????
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


        mBinding.tvMarketingType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????
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
                //?????????
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
                ToastUtils.showShort("???????????????????????????");

                clearTotalPrice();
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

        mBinding.switchMoney.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mBinding.llMoney.setVisibility(View.VISIBLE);
                if (mData != null) mData.setIs_pay("1");
            } else {
                mBinding.llMoney.setVisibility(View.GONE);
                if (mData != null) mData.setIs_pay("0");
            }
        });

        mBinding.etBudgetMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTotalPrice();
            }
        });

        mBinding.etBudgetMoney.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    resetTotalPrice();
                }
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

    //?????????
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
        BigDecimal perMoneyAll = new BigDecimal("0");//????????????
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
                BigDecimal tables = new BigDecimal(StringUtils.isTrimEmpty(dto.getTable_number()) ? "0" : dto.getTable_number());//??????
                BigDecimal price = new BigDecimal(StringUtils.isTrimEmpty(dto.getPrice()) ? "0" : dto.getPrice());//??????
                perMoneyAll = perMoneyAll.add(tables.multiply(price));
            }
            mBinding.etBudgetMoney.setText(perMoneyAll.toPlainString());
        }
        String status = mData.getStatus();
        String step = mData.getStep();
        String financeConfirmed = mData.getFinance_confirmed2();
        mBinding.llBottomStep1.setVisibility(View.GONE);
        mBinding.llBottomStep2.setVisibility(View.GONE);
        mBinding.llBottomStep3.setVisibility(View.GONE);
        //???????????????
        if (StringUtils.equals(financeConfirmed, "0") || StringUtils.isEmpty(financeConfirmed)) {
            //??????????????????
            mBinding.tvTopBarDes.setText("??????????????????");
            mBinding.llBottomStep1.setVisibility(View.VISIBLE);

        } else if (StringUtils.equals(financeConfirmed, "1")) {
            mBinding.tvTopBarDes.setText("???????????????");
            mBinding.llBottomStep2.setVisibility(View.VISIBLE);
        } else if (StringUtils.equals(financeConfirmed, "2")) {
            mBinding.tvTopBarDes.setText("???????????????");
            mBinding.llBottomStep3.setVisibility(View.VISIBLE);

        } else if (StringUtils.equals(financeConfirmed, "3")) {
            mBinding.tvTopBarDes.setText("?????????");
        }

        String marketingTypeName = mData.getMarketing_type_name();
        if (StringUtils.isTrimEmpty(marketingTypeName)) {
            marketingTypeName = "???????????????";
        }
        mBinding.tvMarketingType.setText(marketingTypeName);

        mBinding.etPreferentialFee.setText(mData.getPreferential_fee());
        mBinding.etSignMoney.setText(mData.getSign_money());

        if ("1".equals(mData.getIs_pay())) {
            mBinding.llMoney.setVisibility(View.VISIBLE);
            mBinding.switchMoney.setChecked(true);
        } else {
            mBinding.llMoney.setVisibility(View.GONE);
            mBinding.switchMoney.setChecked(false);
        }
        if (!StringUtils.isEmpty(mData.getPay_name())) {
            mBinding.tvPayType.setText(mData.getPay_name());
        } else {
            mBinding.tvPayType.setText("????????????");
            mData.setPay_type("4");
        }
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
        if (StringUtils.isEmpty(contractorCustomer)) {
            mData.setContractor_customer(mData.getReal_name());
        }

        mBinding.tvContractorUser.setText(mData.getContractor_user_name());
        mBinding.etContractorCustomer.setText(mData.getContractor_customer());
        mBinding.tvContractNo.setText(mData.getContract_no());
        mBinding.etRemarks.setText(mData.getRemarks_4());


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshPrice(RefreshTotalPriceEvent event) {
        clearTotalPrice();
    }

    //??????????????????
    private void clearTotalPrice() {
        mBinding.etBudgetMoney.setText("");
    }

    //????????????????????????
    private void resetTotalPrice() {
        if (mData == null) return;
        List<NetRestoreStep4.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
        BigDecimal perMoneyAll = new BigDecimal("0");//????????????
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                NetRestoreStep4.DataDTO.BanquetNumDTO dto = list.get(i);
                BigDecimal tables = new BigDecimal(StringUtils.isTrimEmpty(dto.getTable_number()) ? "0" : dto.getTable_number());//??????
                BigDecimal price = new BigDecimal(StringUtils.isTrimEmpty(dto.getPrice()) ? "0" : dto.getPrice());//??????
                perMoneyAll = perMoneyAll.add(tables.multiply(price));
            }
            mBinding.etBudgetMoney.setText(perMoneyAll.toPlainString());
        }
    }

    private SignSessionFragment add() {
        int tabCount = mBinding.tabLayout.getTabCount();
        tabCount++;
        TabLayout.Tab tab = mBinding.tabLayout.newTab();
        tab.setText("???" + tabCount + "???");
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
                        .title("??????")
                        .content("??????????????????????")
                        .positiveText("??????")
                        .negativeText("??????")
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
                                    mBinding.tabLayout.getTabAt(i).setText("???" + (i + 1) + "???");
                                }

                                refreshUI();

                                clearTotalPrice();
                            }
                        })
                        .show();

                return true;
            }
        });

        return fragment;

    }
}
