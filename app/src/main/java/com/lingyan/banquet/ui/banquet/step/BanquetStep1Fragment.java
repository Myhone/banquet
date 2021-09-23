package com.lingyan.banquet.ui.banquet.step;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;

import com.lingyan.banquet.databinding.FragmentBanquetStep1Binding;
import com.lingyan.banquet.event.SaveReserveSuccessEvent;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetType;
import com.lingyan.banquet.ui.banquet.bean.NetCustomerChannel;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep1;
import com.lingyan.banquet.ui.banquet.bean.NetSinglePersonList;
import com.lingyan.banquet.ui.banquet.bean.NetStep;
import com.lingyan.banquet.views.MyMonthView;
import com.lingyan.banquet.views.dialog.PersonPickerDialog;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lingyan.banquet.views.dialog.SelectDayDialog;
import com.lingyan.banquet.views.dialog.TwoLineTabSelectDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.lingyan.banquet.global.BanquetCelebrationType.TYPE_BANQUET;

/**
 * 商机
 * Created by _hxb on 2020/12/13.
 */

public class BanquetStep1Fragment extends BaseBanquetStepFragment {

    private FragmentBanquetStep1Binding mBinding;
    private List<NetBanquetType.DataDTO> mBanquetCelebrationTypeList;


    private List<NetCustomerChannel.DataDTO> mCustomerList;


    private NetRestoreStep1.DataDTO mData;

    public static BanquetStep1Fragment newInstance() {
        BanquetStep1Fragment fragment = new BanquetStep1Fragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentBanquetStep1Binding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.tvLabelTime.setText("商机时间");
        mBinding.tvLabelSort.setText("宴会类型");
        mBinding.tvSelectSort.setHint("请选择宴会类型");

        mBinding.tvTime.setText(TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM-dd")));
        mBinding.tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDayDialog dialog = new SelectDayDialog(getActivity());
                dialog.setOnDayClick(new MyMonthView.OnDayClick() {
                    @Override
                    public void onDayClick(Calendar c) {
                        int compare = Calendar.getInstance().compareTo(c);
                        if (compare > 0) {
                            ToastUtils.showShort("选择日期必须大于今天");
                            return;
                        }
                        String string = TimeUtils.date2String(c.getTime(), "yyyy-MM-dd");
                        mBinding.tvTime.setText(string);
                        dialog.dismiss();
                        mData.setDate(string);
                    }
                });
                dialog.show();
            }
        });

        mBinding.tvSelectSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //选择宴会类型
                OkGo.<NetBanquetType>post(HttpURLs.listBanquetType)
                        .params("type", "tree")
                        .tag(getThisFragment())
                        .execute(new JsonCallback<NetBanquetType>() {
                            @Override
                            public void onSuccess(Response<NetBanquetType> response) {
                                NetBanquetType body = response.body();
                                mBanquetCelebrationTypeList = body.getData();
                                if (ObjectUtils.isEmpty(mBanquetCelebrationTypeList)) {
                                    return;
                                }
                                showSelectBanquetCelebrationTypeDialog();
                            }
                        });

            }
        });

        mBinding.tvNicheSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //介绍人
                showPersonList(1);

            }
        });
        mBinding.tvIntentMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跟单人
                showPersonList(2);
            }
        });
        mBinding.tvChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获客渠道

                if (ObjectUtils.isEmpty(mCustomerList)) {
                    OkGo.<NetCustomerChannel>post(HttpURLs.customerChannel)
                            .tag(getThisFragment())
                            .execute(new JsonCallback<NetCustomerChannel>() {
                                @Override
                                public void onSuccess(Response<NetCustomerChannel> response) {
                                    NetCustomerChannel body = response.body();
                                    mCustomerList = body.getData();
                                    showCustomerListDialog();
                                }
                            });
                } else {
                    showCustomerListDialog();
                }

            }
        });


        mBinding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mData == null ||
                        !StringUtils.equals(mData.getFinance_confirmed(), "0") ||
                        !StringUtils.equals(mData.getIs_status(), "0")

                ) {
                    ToastUtils.showShort("当前状态不可操作");
                    return;
                }


                String customerName = mBinding.etCutomerName.getText().toString().trim();
                String customerPhone = mBinding.etCutomerPhone.getText().toString().trim();
                if (StringUtils.isTrimEmpty(customerName)) {
                    ToastUtils.showShort("请输入客户名称");
                    return;
                }

//                if(customerName.matches(".*\\d+.*") ){
//                    ToastUtils.showShort("客户名称不能包含数字");
//                    return;
//                }
                if (!RegexUtils.isMobileSimple(customerPhone)) {
                    ToastUtils.showShort("请输入正确的联系电话");
                    return;
                }

                NetRestoreStep1.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
                if (StringUtils.isEmpty(linkmen.getSex())) {
                    ToastUtils.showShort("请选择客户性别");
                    return;
                }
                String remark = mBinding.etRemarks.getText().toString().trim();
                String customerRemarks = mBinding.etCustomerRemarks.getText().toString().trim();
                linkmen.setReal_name(customerName);
                linkmen.setMobile(customerPhone);
                mData.setRemarks_1(remark);
                mData.setCustomer_remarks(customerRemarks);
                mData.setType(TYPE_BANQUET + "");
                //保存
                OkGo.<NetStep>post(HttpURLs.saveBanquetStep1)
                        .upJson(GsonUtils.toJson(mData))
                        .tag(getThisFragment())
                        .execute(new JsonCallback<NetStep>() {
                            @Override
                            public void onSuccess(Response<NetStep> response) {
                                NetStep body = response.body();
                                String msg = body.getMsg();
                                int code = body.getCode();
                                if (code == 200 && body.getData() != null) {
                                    NetStep.DataDTO data = body.getData();
                                    String id = data.getId();
                                    getStepActivity().setId(id);
                                    getStepActivity().setMaxStep(2);
                                    EventBus.getDefault().post(new SaveReserveSuccessEvent(getBanquetId()));
                                    getStepActivity().changeStep(2);
                                } else {
                                    ToastUtils.showShort(msg);
                                }
                            }
                        });


            }
        });
        mBinding.tvSelectSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData == null) {
                    return;
                }
                new PickerListDialog(getContext())
                        .items("男", "女")
                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                            @Override
                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                dialog.dismiss();
                                NetRestoreStep1.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
                                linkmen.setSex((position + 1) + "");
                                mBinding.tvSelectSex.setText(text);
                            }
                        })
                        .show();
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

    public void restoreDataFromNet() {
        OkGo.<NetRestoreStep1>post(HttpURLs.banquetGetInfo)
                .params("id", getBanquetId())
                .params("step", 1)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetRestoreStep1>() {
                    @Override
                    public void onSuccess(Response<NetRestoreStep1> response) {
                        NetRestoreStep1 body = response.body();
                        mData = body.getData();
                        String step = mData.getStep();
                        if (ObjectUtils.isNotEmpty(step)) {
                            setMaxStep(Integer.valueOf(step));
                        }
                        if (StringUtils.isTrimEmpty(mData.getIntent_man_id())) {
                            mData.setIntent_man_id(UserInfoManager.getInstance().get(UserInfoManager.ID));
                            mData.setIntent_man_name(UserInfoManager.getInstance().get(UserInfoManager.REAL_NAME));
                        }
                        if (StringUtils.isTrimEmpty(mData.getNiche_source_id())) {
                            mData.setNiche_source_id(UserInfoManager.getInstance().get(UserInfoManager.ID));
                            mData.setNiche_source_name(UserInfoManager.getInstance().get(UserInfoManager.REAL_NAME));
                        }
                        if (StringUtils.isTrimEmpty(mData.getLinkmen().getSex()))
                            mData.getLinkmen().setSex("1");
                        refreshUI();


                    }
                });
    }

    public void refreshUI() {
        NetRestoreStep1.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
        mBinding.tvTime.setText(mData.getDate());
        mBinding.tvSelectSort.setText(mData.getNiche_name());
        mBinding.tvIntentMan.setText(mData.getIntent_man_name());
        mBinding.tvNicheSource.setText(mData.getNiche_source_name());
        mBinding.etCutomerName.setText(linkmen.getReal_name());
        mBinding.etCutomerPhone.setText(linkmen.getMobile());
        mBinding.etCustomerRemarks.setText(mData.getCustomer_remarks());
        mBinding.tvChannel.setText(mData.getCustomer_type_name());
        mBinding.etRemarks.setText(mData.getRemarks_1());
        String sex = linkmen.getSex();
        if (StringUtils.equals("1", sex)) {
            mBinding.tvSelectSex.setText("男");
        } else if (StringUtils.equals("2", sex)) {
            mBinding.tvSelectSex.setText("女");
        }

    }

    //介绍人，跟单人
    private void showPersonList(int type) {
        PersonPickerDialog dialog = new PersonPickerDialog(getActivity());
        dialog.setOnPersonClickListener(new PersonPickerDialog.OnPersonClickListener() {
            @Override
            public void onPersonClick(String name, String id, PersonPickerDialog dialog) {
                if (type == 1) {
                    mBinding.tvNicheSource.setText(name);
                    mData.setNiche_source_id(id);
                    mData.setNiche_source_name(name);
                } else {
                    mData.setIntent_man_id(id);
                    mData.setIntent_man_name(name);
                    mBinding.tvIntentMan.setText(name);
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //宴会类型
    private void showSelectBanquetCelebrationTypeDialog() {
        if (ObjectUtils.isEmpty(mBanquetCelebrationTypeList)) {
            return;
        }

        String nicheType = mData.getNiche_type();
        ArrayList<String> existList = new ArrayList();
        if (nicheType != null) {
            existList.add(nicheType);
        }
        TwoLineTabSelectDialog dialog = new TwoLineTabSelectDialog(getContext());
        dialog.setData2(mBanquetCelebrationTypeList, existList);
        dialog.setTitle("宴会类型");
        dialog.setOnHallSelectListener(new TwoLineTabSelectDialog.OnHallSelectListener() {
            @Override
            public void OnHallSelect(String id, String name, TwoLineTabSelectDialog dialog) {
                mBinding.tvSelectSort.setText(name);
                mData.setNiche_type(id);
                mData.setNiche_name(name);
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    //获客渠道
    private void showCustomerListDialog() {
        if (ObjectUtils.isEmpty(mCustomerList)) {
            return;
        }
        List<String> strings = new ArrayList<>();
        for (NetCustomerChannel.DataDTO dataDTO : mCustomerList) {
            strings.add(dataDTO.getName());
        }

        PickerListDialog dialog = new PickerListDialog(getActivity());
        dialog.items(strings);
        dialog.itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
            @Override
            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                NetCustomerChannel.DataDTO dataDTO = mCustomerList.get(position);
                mBinding.tvChannel.setText(dataDTO.getName());
                mData.setCustomer_type(dataDTO.getId());
                mData.setCustomer_type_name(dataDTO.getName());
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}
