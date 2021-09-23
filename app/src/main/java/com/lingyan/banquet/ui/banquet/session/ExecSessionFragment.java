package com.lingyan.banquet.ui.banquet.session;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.FragmentExecSessionBinding;
import com.lingyan.banquet.event.DeleteImageEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.net.DialogJsonCallBack;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetUploadImage;
import com.lingyan.banquet.ui.banquet.bean.NetMealList;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep5;
import com.lingyan.banquet.ui.common.PicListActivity;

import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.utils.PicSelectUtils;
import com.lingyan.banquet.views.MyMonthView;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lingyan.banquet.views.dialog.SelectDayDialog;
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
 * 执行里面的场次
 * Created by _hxb on 2021/1/10.
 */

public class ExecSessionFragment extends BaseFragment {

    private FragmentExecSessionBinding mBinding;
    private NetRestoreStep5.DataDTO.BanquetNumDTO mDTO;

    public static ExecSessionFragment newInstance() {
        ExecSessionFragment fragment = new ExecSessionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentExecSessionBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //用餐时间
        mBinding.tvSegmentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PickerListDialog(getActivity())
                        .items("午餐", "晚餐")
                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                            @Override
                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                dialog.dismiss();
                                mDTO.setSegment_type(position == 0 ? "1" : "2");
                                mDTO.setSegment_name(position == 0 ? "午餐" : "晚餐");
                                mBinding.tvSegmentType.setText(position == 0 ? "午餐" : "晚餐");
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
                        String time = TimeUtils.date2String(c.getTime(), "yyyy-MM-dd");
                        mBinding.tvDate.setText(time);
                        mDTO.setDate(time);
                        dayDialog.dismiss();
                    }
                });
                dayDialog.show();
            }
        });
        //确定桌数
        mBinding.etTableNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mDTO == null) {
                    return;
                }
                mDTO.setTable_number(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mBinding.etPrepareNumber.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mDTO == null) {
                    return;
                }
                String string = s.toString();
                mDTO.setPrepare_number(string);
            }
        });
        //确定套餐
        mBinding.tvMealName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<NetMealList>post(HttpURLs.listMeal)
//                        .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                        .tag(getThisFragment())
                        .execute(new JsonCallback<NetMealList>() {
                            @Override
                            public void onSuccess(Response<NetMealList> response) {
                                NetMealList body = response.body();
                                List<NetMealList.DataDTO> list = body.getData();
                                if (ObjectUtils.isEmpty(list)) {
                                    return;
                                }
                                List<String> strings = new ArrayList<>();
                                for (NetMealList.DataDTO dataDTO : list) {
                                    strings.add(dataDTO.getName());
                                }

                                new PickerListDialog(getActivity())
                                        .title("选择套餐")
                                        .items(strings)
                                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                                            @Override
                                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                                dialog.dismiss();
                                                NetMealList.DataDTO dataDTO = list.get(position);
                                                mBinding.tvMealName.setText(dataDTO.getName());
                                                mDTO.setMeal_id(dataDTO.getId());
                                                mDTO.setMeal_name(dataDTO.getName());
                                            }
                                        })
                                        .show();
                            }
                        });

            }
        });
        //确定酒水
        mBinding.etConfirmWine.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mDTO == null) {
                    return;
                }
                mDTO.setConfirm_wine(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //礼台类型
        mBinding.tvPlatformType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new PickerListDialog(getActivity())
                        .title("选择礼台类型")
                        .items("单", "双", "无")
                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                            @Override
                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                dialog.dismiss();
                                int type = position + 1;
                                mDTO.setPlatform_type(type + "");
                                String name = "无";
                                if (type == 1) {
                                    name = "单";
                                } else if (type == 2) {
                                    name = "双";
                                }
                                mBinding.tvPlatformType.setText(name);
                            }
                        })
                        .show();
            }
        });
        //确定台型
        mBinding.etConfirmPlatrorm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mDTO == null) {
                    return;
                }
                mDTO.setConfirm_platrorm(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //确定菜单
        mBinding.tvUploadMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(1);
            }
        });
        mBinding.tvLookMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = mDTO.getConfirm_menu();
                ArrayList<String> imageList = new ArrayList<>();
                for (String s : list) {
                    imageList.add(HttpURLs.IMAGE_BASE + s);
                }
                PicListActivity.start(imageList, getThisFragment().hashCode() + 1);
            }
        });
        //台型图片
        mBinding.tvLookPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = mDTO.getPaltform_pic();
                ArrayList<String> imageList = new ArrayList<>();
                for (String s : list) {
                    imageList.add(HttpURLs.IMAGE_BASE + s);
                }
                PicListActivity.start(imageList, getThisFragment().hashCode() + 2);
            }
        });
        mBinding.tvUploadPlatform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage(2);
            }
        });

        EventBus.getDefault().register(this);
        refreshUI();
    }

    private void uploadImage(int type) {
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
                                .tag(getThisFragment())
                                .params("type", 2)
                                .params("header", new File(s))
                                .execute(new DialogJsonCallBack<NetUploadImage>() {
                                    @Override
                                    public void onSuccess(Response<NetUploadImage> response) {
                                        NetUploadImage body = response.body();
                                        NetUploadImage.DataDTO data = body.getData();
                                        if (body.getCode() != 200 || data == null || data.getUrl() == null) {
                                            return;
                                        }
                                        String dir = data.getDir();
                                        if (type == 1) {
                                            mDTO.getConfirm_menu().add(dir);
                                        } else if (type == 2) {
                                            mDTO.getPaltform_pic().add(dir);
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void deleteImage(DeleteImageEvent event) {
        int code = event.getCode();
        String image = MyImageUtils.getRelativePath(event.getImage());
        if (code == getThisFragment().hashCode() + 1) {
            mDTO.getConfirm_menu().remove(image);
        } else if (code == getThisFragment().hashCode() + 2) {
            mDTO.getPaltform_pic().remove(image);
        }

    }


    public void setData(NetRestoreStep5.DataDTO.BanquetNumDTO dto) {
        mDTO = dto;
        if (mDTO == null) {
            mDTO = new NetRestoreStep5.DataDTO.BanquetNumDTO();
        }
        if (isResumed()) {
            refreshUI();
        }
    }

    private void refreshUI() {
        if (mDTO == null) {
            return;
        }
        mBinding.tvDate.setText(mDTO.getDate());
        mBinding.etTableNumber.setText(mDTO.getTable_number());
        mBinding.tvMealName.setText(mDTO.getMeal_name());
        mBinding.etConfirmWine.setText(mDTO.getConfirm_wine());
        String platformType = mDTO.getPlatform_type();
        if (StringUtils.equals(platformType, "1")) {
            platformType = "单";
        } else if (StringUtils.equals(platformType, "2")) {
            platformType = "双";
        } else {
            platformType = "无";
        }
        mBinding.tvPlatformType.setText(platformType);
        mBinding.etConfirmPlatrorm.setText(mDTO.getConfirm_platrorm());
        mBinding.tvSegmentType.setText(mDTO.getSegment_name());
        mBinding.etPrepareNumber.setText(mDTO.getPrepare_number());
    }
}
