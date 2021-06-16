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
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.FragmentBanquetStep5Binding;
import com.lingyan.banquet.event.SaveReserveSuccessEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep5;
import com.lingyan.banquet.ui.banquet.session.ExecSessionFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 执行
 * Created by _hxb on 2020/12/13.
 */

public class BanquetStep5Fragment extends BaseBanquetStepFragment {
    private FragmentManager mFragmentManager;
    private List<ExecSessionFragment> mFragmentList;
    private FragmentBanquetStep5Binding mBinding;
    private NetRestoreStep5.DataDTO mData;

    public static BanquetStep5Fragment newInstance() {
        BanquetStep5Fragment fragment = new BanquetStep5Fragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentBanquetStep5Binding.inflate(inflater);
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
                                StringUtils.equals(mData.getStatus(), "5") ||
                                StringUtils.equals(mData.getStatus(), "6")

                ) {
                    if (!StringUtils.equals(mData.getIs_status(), "0")) {
                        ToastUtils.showShort("当前状态不可操作");
                    } else {
                        setMaxStep(5 + 1);
                        getStepActivity().changeStep(6);
                    }
                    return;
                }


                List<NetRestoreStep5.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
                if (ObjectUtils.isEmpty(list)) {
                    ToastUtils.showShort("当前状态不可操作");
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    NetRestoreStep5.DataDTO.BanquetNumDTO dto = list.get(i);
                    String confirmWine = dto.getConfirm_wine();
                    String platformType = dto.getPlatform_type();
                    String confirmPlatrorm = dto.getConfirm_platrorm();
                    if (StringUtils.isTrimEmpty(confirmWine)) {
                        ToastUtils.showShort(String.format("请输入第%d场的确认酒水", (i + 1)));
                        return;
                    }
                    if (StringUtils.isTrimEmpty(platformType) || StringUtils.equals(platformType, "0")) {
                        ToastUtils.showShort(String.format("请选择第%d场的礼台类型", (i + 1)));
                        return;
                    }
                    if (StringUtils.isTrimEmpty(confirmPlatrorm)) {
                        ToastUtils.showShort(String.format("请输入第%d场的确定台型", (i + 1)));
                        return;
                    }

                }

                mData.setStep("5");
//                mData.setStatus(null);
                mData.setRemarks_5(mBinding.etRemarks.getText().toString());
                //保存
                OkGo.<NetBaseResp>post(HttpURLs.saveBanquetStep5)
                        .upJson(GsonUtils.toJson(mData))
                        .tag(getThisFragment())
                        .execute(new JsonCallback<NetBaseResp>() {
                            @Override
                            public void onSuccess(Response<NetBaseResp> response) {
                                NetBaseResp body = response.body();
                                String msg = body.getMsg();
                                int code = body.getCode();
                                if (code == 200) {
                                    setMaxStep(5 + 1);
                                    EventBus.getDefault().post(new SaveReserveSuccessEvent(getBanquetId()));
                                    getStepActivity().changeStep(6);
                                } else {
                                    ToastUtils.showShort(msg);
                                }
                            }
                        });
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
                    ExecSessionFragment fragment = mFragmentList.get(i);
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
        OkGo.<NetRestoreStep5>post(HttpURLs.banquetGetInfo)
                .params("id", getBanquetId())
                .params("step", 5)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetRestoreStep5>() {
                    @Override
                    public void onSuccess(Response<NetRestoreStep5> response) {
                        NetRestoreStep5 body = response.body();
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
        mBinding.etRemarks.setText(mData.getRemarks_5());
        List<NetRestoreStep5.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
        if (ObjectUtils.isEmpty(list)) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            NetRestoreStep5.DataDTO.BanquetNumDTO dto = list.get(i);
            ExecSessionFragment fragment = add();
            fragment.setData(dto);
        }


    }


    private ExecSessionFragment add() {
        int tabCount = mBinding.tabLayout.getTabCount();
        tabCount++;
        TabLayout.Tab tab = mBinding.tabLayout.newTab();
        tab.setText("第" + tabCount + "场");
        mBinding.tabLayout.addTab(tab);

        ExecSessionFragment fragment = ExecSessionFragment.newInstance();
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
