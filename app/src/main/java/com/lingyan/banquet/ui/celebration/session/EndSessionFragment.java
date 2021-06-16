package com.lingyan.banquet.ui.celebration.session;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.FragmentCelEndSessionBinding;
import com.lingyan.banquet.event.DeleteImageEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.net.DialogJsonCallBack;
import com.lingyan.banquet.net.NetUploadImage;
import com.lingyan.banquet.ui.banquet.step.BanquetStep6Fragment;
import com.lingyan.banquet.ui.celebration.bean.NetCelRestoreStep6;
import com.lingyan.banquet.ui.celebration.step.CelStep6Fragment;
import com.lingyan.banquet.ui.common.PicListActivity;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.utils.PicSelectUtils;
import com.lingyan.banquet.views.MyMonthView;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lingyan.banquet.views.dialog.SelectDayDialog;
import com.lzy.okgo.OkGo;
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
 * 结算里面的场次
 * Created by _hxb on 2021/1/10.
 */

public class EndSessionFragment extends BaseFragment {

    private FragmentCelEndSessionBinding mBinding;

    private NetCelRestoreStep6.DataDTO.BanquetNumDTO mDTO;

    public static EndSessionFragment newInstance() {
        EndSessionFragment fragment = new EndSessionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCelEndSessionBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        refreshUI();



        mBinding.etSessionAmount.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString().trim();
                mDTO.setSession_amount(string);
                CelStep6Fragment fragment = (CelStep6Fragment) getParentFragment();
                if(fragment!=null){
                    fragment.setMoney();
                }
            }
        });
        //项目明细
        mBinding.tvLookDetailePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = mDTO.getDetaile_pic();
                ArrayList<String> imageList = new ArrayList<>();
                for (String s : list) {
                    imageList.add(HttpURLs.IMAGE_BASE+s);
                }

                PicListActivity.start(imageList, getThisFragment().hashCode() + 1);
            }
        });
        //变更明细
        mBinding.tvLookDetaileChangePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = mDTO.getDetaile_change_pic();
                ArrayList<String> imageList = new ArrayList<>();
                for (String s : list) {
                    imageList.add(HttpURLs.IMAGE_BASE+s);
                }

                PicListActivity.start(imageList, getThisFragment().hashCode() + 2);
            }
        });
        mBinding.tvUploadDetaileChangePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        EventBus.getDefault().register(this);
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
                                .execute(new DialogJsonCallBack<NetUploadImage>() {
                                    @Override
                                    public void onSuccess(Response<NetUploadImage> response) {
                                        NetUploadImage body = response.body();
                                        NetUploadImage.DataDTO data = body.getData();
                                        if (body.getCode() != 200 || data == null || data.getUrl() == null) {
                                            return;
                                        }
                                        String dir = data.getDir();
                                        mDTO.getDetaile_change_pic().add(dir);
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
        LogUtils.i("要删除的图片:"+image);
        if (code == getThisFragment().hashCode() + 1) {
            mDTO.getDetaile_pic().remove(image);
        } else if (code == getThisFragment().hashCode() + 2) {
            mDTO.getDetaile_change_pic().remove(image);
        }

    }

    public void setData(NetCelRestoreStep6.DataDTO.BanquetNumDTO dto) {
        mDTO = dto;
        if (mDTO == null) {
            mDTO = new NetCelRestoreStep6.DataDTO.BanquetNumDTO();
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
        mBinding.tvSegmentType.setText(mDTO.getSegment_name());
        mBinding.etSessionAmount.setText(mDTO.getSession_amount());

    }
}
