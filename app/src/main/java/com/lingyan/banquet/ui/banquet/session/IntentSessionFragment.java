package com.lingyan.banquet.ui.banquet.session;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.FragmentSessionBinding;
import com.lingyan.banquet.databinding.LayoutAddRoomBinding;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.BanquetStepManagerActivity;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetChildHall;
import com.lingyan.banquet.ui.banquet.bean.NetMealList;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep2;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep4;
import com.lingyan.banquet.views.MyMonthView;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lingyan.banquet.views.dialog.TwoLineTabSelectDialog;
import com.lingyan.banquet.views.dialog.SelectDayDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 意向里面的场次
 * Created by _hxb on 2021/1/10.
 */

public class IntentSessionFragment extends BaseFragment {

    private FragmentSessionBinding mBinding;

    private NetRestoreStep2.DataDTO.BanquetNumDTO mDTO;
    private List<NetMealList.DataDTO> mMealList;
    private List<NetBanquetChildHall.DataDTO> mHallList;
    private List<String> mSelectedHall;

    public static IntentSessionFragment newInstance() {
        IntentSessionFragment fragment = new IntentSessionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentSessionBinding.inflate(inflater);

        mBinding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDTO == null) {
                    return;
                }
                SelectDayDialog dayDialog = new SelectDayDialog(getActivity());
                dayDialog.setOnDayClick(new MyMonthView.OnDayClick() {
                    @Override
                    public void onDayClick(Calendar c) {
                        int compare = Calendar.getInstance().compareTo(c);
                        if (compare > 0) {
                            ToastUtils.showShort("选择日期必须大于今天");
                            return;
                        }

                        dayDialog.dismiss();
                        String time = TimeUtils.date2String(c.getTime(), "yyyy-MM-dd");
                        String date = mDTO.getDate();
                        if (StringUtils.equals(time, date)) {
                            return;
                        }
                        mBinding.tvDate.setText(time);
                        mDTO.setDate(time);
                        mBinding.tvHallName.setText("");
                        mBinding.tvHallName.setTag(R.id.tv_hall_name, null);
                        mBinding.llRoomContainer.removeAllViews();
                        mDTO.getHall_info().clear();
                        mDTO.getHall_id().clear();
                    }
                });
                dayDialog.show();
            }
        });
        mBinding.tvSegmentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDTO == null) {
                    return;
                }
                new PickerListDialog(getActivity())
                        .items("午餐", "晚餐")
                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                            @Override
                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                dialog.dismiss();
                                String type = position == 0 ? "1" : "2";
                                String segmentType = mDTO.getSegment_type();
                                if (StringUtils.equals(type, segmentType)) {
                                    return;
                                }
                                mDTO.setSegment_type(type);
                                mDTO.setSegment_name(position == 0 ? "午餐" : "晚餐");
                                mBinding.tvSegmentType.setText(position == 0 ? "午餐" : "晚餐");

                                mBinding.tvHallName.setText("");
                                mBinding.tvHallName.setTag(R.id.tv_hall_name, null);
                                mBinding.llRoomContainer.removeAllViews();
                                mDTO.getHall_info().clear();
                                mDTO.getHall_id().clear();
                            }
                        })
                        .show();
            }
        });
        mBinding.tvMealName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDTO == null) {
                    return;
                }
                if (ObjectUtils.isEmpty(mMealList)) {
                    OkGo.<NetMealList>post(HttpURLs.listMeal)
                            .tag(getThisFragment())
//                            .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                            .execute(new JsonCallback<NetMealList>() {
                                @Override
                                public void onSuccess(Response<NetMealList> response) {

                                    NetMealList body = response.body();
                                    mMealList = body.getData();
                                    showMealDialog();
                                }
                            });
                } else {
                    showMealDialog();
                }

            }
        });
        mBinding.tvHallName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHallList();
            }
        });
        return mBinding.getRoot();
    }

    private void showSelectHallDialog() {
        if (ObjectUtils.isEmpty(mHallList)) {
            return;
        }
        //已存在的
        List<String> existIdList = new ArrayList<>();
        List<String> existNameList = new ArrayList<>();
        List<NetRestoreStep2.DataDTO.BanquetNumDTO.HallInfoDTO> hallInfo = mDTO.getHall_info();
        if (ObjectUtils.isNotEmpty(hallInfo)) {
            for (NetRestoreStep2.DataDTO.BanquetNumDTO.HallInfoDTO dto : hallInfo) {
                existIdList.add(dto.getId());
                existNameList.add(dto.getName());
            }
        }

        TwoLineTabSelectDialog dialog = new TwoLineTabSelectDialog(getContext());
        dialog.setSingleMode(false);
        boolean hasData = dialog.setData(mHallList, existIdList);
        dialog.setOnMultipleSelectListener(new TwoLineTabSelectDialog.OnMultipleSelectListener() {
            @Override
            public void OnMultipleSelect(List<String> idList, List<String> nameList, TwoLineTabSelectDialog dialog) {
                if (ObjectUtils.isEmpty(idList)) {
                    ToastUtils.showShort("至少选择一个");
                    return;
                }
                dialog.dismiss();
                List<NetRestoreStep2.DataDTO.BanquetNumDTO.HallInfoDTO> hallInfoList = new ArrayList<>();

                for (int i = 0; i < idList.size(); i++) {
                    String id = idList.get(i);
                    String name = nameList.get(i);
                    NetRestoreStep2.DataDTO.BanquetNumDTO.HallInfoDTO hallInfoDTO = new NetRestoreStep2.DataDTO.BanquetNumDTO.HallInfoDTO();
                    hallInfoDTO.setId(id);
                    hallInfoDTO.setName(name);
                    hallInfoList.add(hallInfoDTO);
                }

                List<String> hallId = mDTO.getHall_id();
                List<NetRestoreStep2.DataDTO.BanquetNumDTO.HallInfoDTO> hallInfo = mDTO.getHall_info();
                hallId.clear();
                hallInfo.clear();
                hallId.addAll(idList);
                hallInfo.addAll(hallInfoList);
                changeUI();
            }
        });

        if (hasData) {
            dialog.show();
        } else {
            ToastUtils.showShort("暂无可选的宴会厅");
        }


    }

    private void showMealDialog() {
        if (ObjectUtils.isEmpty(mMealList)) {
            return;
        }
        List<String> strings = new ArrayList<>();
        for (NetMealList.DataDTO dataDTO : mMealList) {
            strings.add(dataDTO.getName());
        }
        new PickerListDialog(getActivity())
                .title("选择套餐")
                .items(strings)
                .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                    @Override
                    public void onItemSelected(int position, String text, PickerListDialog dialog) {
                        dialog.dismiss();
                        NetMealList.DataDTO dataDTO = mMealList.get(position);
                        mBinding.tvMealName.setText(dataDTO.getName());
                        mDTO.setMeal_id(dataDTO.getId());
                        mDTO.setMeal_name(dataDTO.getName());
                    }
                })
                .show();
    }


    private LayoutAddRoomBinding addHall() {
        LayoutAddRoomBinding roomBinding = LayoutAddRoomBinding.inflate(getLayoutInflater());
        mBinding.llRoomContainer.addView(roomBinding.getRoot());
        roomBinding.tvHallName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHallList();
            }
        });
        return roomBinding;
    }

    //获取包间列表
    private void getHallList() {
        if (ObjectUtils.isEmpty(mDTO)) {
            return;
        }
        if (StringUtils.isTrimEmpty(mDTO.getDate())) {
            ToastUtils.showShort("请选择场次时间");
            return;
        }
        if (StringUtils.isTrimEmpty(mDTO.getSegment_type())) {
            ToastUtils.showShort("请选择用餐时间");
            return;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("s_type", 0);
        jsonObject.addProperty("type", BanquetCelebrationType.TYPE_BANQUET);
        jsonObject.addProperty("date", mDTO.getDate());
        jsonObject.addProperty("segment_type", mDTO.getSegment_type());

        if (ObjectUtils.isNotEmpty(mSelectedHall)) {
            JsonArray jsonArray = new JsonArray();
            for (String s : mSelectedHall) {
                jsonArray.add(s);
            }
            jsonObject.add("hall_ids", jsonArray);
        }

        OkGo.<NetBanquetChildHall>post(HttpURLs.listBanquetHall)
                .upJson(jsonObject.toString())
                .tag(getThisFragment())
                .execute(new JsonCallback<NetBanquetChildHall>() {
                    @Override
                    public void onSuccess(Response<NetBanquetChildHall> response) {
                        NetBanquetChildHall body = response.body();
                        if (body == null) {
                            return;
                        }
                        mHallList = body.getData();
                        showSelectHallDialog();
                    }
                });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        changeUI();
        //计划桌数
        mBinding.etTableNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString();
                mDTO.setTable_number(string);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setData(NetRestoreStep2.DataDTO.BanquetNumDTO dto) {
        mDTO = dto;
        if (mDTO == null) {
            mDTO = new NetRestoreStep2.DataDTO.BanquetNumDTO();
        }
        mSelectedHall = mDTO.getHall_id();
        if (isResumed()) {
            changeUI();
        }
    }


    private void changeUI() {
        if (mDTO != null) {
            List<NetRestoreStep2.DataDTO.BanquetNumDTO.HallInfoDTO> hallInfoDTOList = mDTO.getHall_info();
            List<String> hallIdList = mDTO.getHall_id();
            mBinding.llRoomContainer.removeAllViews();
            if (ObjectUtils.isNotEmpty(hallInfoDTOList)) {
                for (int i = 0; i < hallInfoDTOList.size(); i++) {
                    NetRestoreStep2.DataDTO.BanquetNumDTO.HallInfoDTO hallInfoDTO = hallInfoDTOList.get(i);
                    if (i == 0) {
                        mBinding.tvHallName.setText(hallInfoDTO.getName());
                        mBinding.tvHallName.setTag(R.id.tv_hall_name, hallInfoDTO);
                    } else {
                        LayoutAddRoomBinding roomBinding = addHall();
                        roomBinding.tvHallName.setTag(R.id.tv_hall_name, hallInfoDTO);
                        roomBinding.tvHallName.setText(hallInfoDTO.getName());


                    }
                }
            }
            mBinding.etTableNumber.setText(mDTO.getTable_number());
            mBinding.tvMealName.setText(mDTO.getMeal_name());
            mBinding.tvDate.setText(mDTO.getDate());
            mBinding.tvSegmentType.setText(mDTO.getSegment_name());
        }


    }


}
