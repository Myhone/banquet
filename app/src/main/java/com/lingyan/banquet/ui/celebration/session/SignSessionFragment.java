package com.lingyan.banquet.ui.celebration.session;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
import com.lingyan.banquet.databinding.FragmentCelSignSessionBinding;
import com.lingyan.banquet.databinding.LayoutAddRoomBinding;
import com.lingyan.banquet.event.DeleteImageEvent;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.net.DialogJsonCallBack;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetUploadImage;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetChildHall;
import com.lingyan.banquet.ui.banquet.bean.NetMealList;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep4;
import com.lingyan.banquet.ui.celebration.bean.NetCelRestoreStep2;
import com.lingyan.banquet.ui.celebration.bean.NetCelRestoreStep4;
import com.lingyan.banquet.ui.common.PicListActivity;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.utils.PicSelectUtils;
import com.lingyan.banquet.views.MyMonthView;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lingyan.banquet.views.dialog.SelectDayDialog;
import com.lingyan.banquet.views.dialog.TwoLineTabSelectDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 签订里面的场次
 * Created by _hxb on 2021/1/10.
 */

public class SignSessionFragment extends BaseFragment {
    private List<NetBanquetChildHall.DataDTO> mHallList;
    private FragmentCelSignSessionBinding mBinding;
    private NetCelRestoreStep4.DataDTO.BanquetNumDTO mDTO;
    private List<NetMealList.DataDTO> mMealList;
    private List<String> mSelectedHall;

    public static SignSessionFragment newInstance() {
        SignSessionFragment fragment = new SignSessionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCelSignSessionBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBinding.tvHallName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHallList();
            }
        });

        mBinding.tvSegmentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        //场次时间
        mBinding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        mBinding.tvLookDetailePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = mDTO.getDetaile_pic();
                ArrayList<String> imageList = new ArrayList<>();
                for (String s : list) {
                    imageList.add(HttpURLs.IMAGE_BASE + s);
                }
                PicListActivity.start(imageList, getThisFragment().hashCode() + 1);
            }
        });
        mBinding.tvUploadDetailePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        if (mDTO != null) {
            changeUI();
        }
        EventBus.getDefault().register(this);

        //场次金额
        mBinding.etSessionAmount.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mDTO == null) {
                    return;
                }
                mDTO.setSession_amount(s.toString().trim());
            }
        });

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void deleteImage(DeleteImageEvent event) {
        int code = event.getCode();
        String image = MyImageUtils.getRelativePath(event.getImage());
        if (code == getThisFragment().hashCode() + 1) {
            mDTO.getDetaile_pic().remove(image);
        }
    }

    private void uploadImage() {
        PicSelectUtils.start(new PicSelectUtils.CallBack() {
            @Override
            public void before(Matisse matisse, int code) {
                matisse.choose(MimeType.ofImage())
                        .countable(false)
                        .maxSelectable(Integer.MAX_VALUE)
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(false, "com.lingyan.banquet.fileprovider"))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.5f)
                        .imageEngine(new GlideEngine())
                        .forResult(code);
            }

            @Override
            public void onSuccess(List<String> list) {
                if (ObjectUtils.isNotEmpty(list)) {
                    for (String s : list) {
                        OkGo.<NetUploadImage>post(HttpURLs.upload)
                                .params("type", 2)
                                .params("header", new File(s))
                                .tag(getThisFragment())
                                .execute(new DialogJsonCallBack<NetUploadImage>() {
                                    @Override
                                    public void onSuccess(Response<NetUploadImage> response) {
                                        NetUploadImage body = response.body();
                                        NetUploadImage.DataDTO data = body.getData();
                                        if (body.getCode() != 200 || data == null || data.getUrl() == null) {
                                            return;
                                        }
                                        String dir = data.getDir();
                                        mDTO.getDetaile_pic().add(dir);
                                    }
                                });
                    }
                }
            }
        });
    }

    public void setData(NetCelRestoreStep4.DataDTO.BanquetNumDTO dto) {
        mDTO = dto;
        if (mDTO == null) {
            mDTO = new NetCelRestoreStep4.DataDTO.BanquetNumDTO();
        }
        mSelectedHall = mDTO.getHall_id();
        if (isResumed()) {
            changeUI();
        }
    }

    private void changeUI() {
        List<NetCelRestoreStep4.DataDTO.BanquetNumDTO.HallInfoDTO> hallInfoDTOList = mDTO.getHall_info();
        List<String> hallIdList = mDTO.getHall_id();
        mBinding.llRoomContainer.removeAllViews();
        if (ObjectUtils.isNotEmpty(hallInfoDTOList)) {
            for (int i = 0; i < hallInfoDTOList.size(); i++) {
                NetCelRestoreStep4.DataDTO.BanquetNumDTO.HallInfoDTO hallInfoDTO = hallInfoDTOList.get(i);
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

        mBinding.tvDate.setText(mDTO.getDate());
        mBinding.tvSegmentType.setText(mDTO.getSegment_name());
        mBinding.etSessionAmount.setText(mDTO.getSession_amount());
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
        jsonObject.addProperty("type", BanquetCelebrationType.TYPE_CELEBRATION);
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


    private void showSelectHallDialog() {
        if (ObjectUtils.isEmpty(mHallList)) {
            return;
        }
        //已存在的
        List<String> existIdList = new ArrayList<>();
        List<String> existNameList = new ArrayList<>();
        List<NetCelRestoreStep4.DataDTO.BanquetNumDTO.HallInfoDTO> hallInfo = mDTO.getHall_info();
        if (ObjectUtils.isNotEmpty(hallInfo)) {
            for (NetCelRestoreStep4.DataDTO.BanquetNumDTO.HallInfoDTO dto : hallInfo) {
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
                List<NetCelRestoreStep4.DataDTO.BanquetNumDTO.HallInfoDTO> hallInfoList = new ArrayList<>();

                for (int i = 0; i < idList.size(); i++) {
                    String id = idList.get(i);
                    String name = nameList.get(i);
                    NetCelRestoreStep4.DataDTO.BanquetNumDTO.HallInfoDTO hallInfoDTO = new NetCelRestoreStep4.DataDTO.BanquetNumDTO.HallInfoDTO();
                    hallInfoDTO.setId(id);
                    hallInfoDTO.setName(name);
                    hallInfoList.add(hallInfoDTO);
                }

                List<String> hallId = mDTO.getHall_id();
                List<NetCelRestoreStep4.DataDTO.BanquetNumDTO.HallInfoDTO> hallInfo = mDTO.getHall_info();
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
}
