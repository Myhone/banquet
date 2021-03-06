package com.lingyan.banquet.ui.banquet.session;

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
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.FragmentEndSessionBinding;
import com.lingyan.banquet.event.DeleteImageEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.net.DialogJsonCallBack;
import com.lingyan.banquet.net.NetUploadImage;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep6;
import com.lingyan.banquet.ui.banquet.step.BanquetStep6Fragment;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 结算里面的场次
 * Created by _hxb on 2021/1/10.
 */

public class EndSessionFragment extends BaseFragment {

    private FragmentEndSessionBinding mBinding;

    private NetRestoreStep6.DataDTO.BanquetNumDTO mDTO;

    public static EndSessionFragment newInstance() {
        EndSessionFragment fragment = new EndSessionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentEndSessionBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
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

        //确定桌数
        mBinding.etTableNumber.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString().trim();
                if (StringUtils.isTrimEmpty(string)) string = "0";
                mDTO.setTable_number(string);
                //计算场次金额
                BigDecimal tables = new BigDecimal(string);//桌数
                BigDecimal price = new BigDecimal(mDTO.getPrice());//单价
                mBinding.etSessionAmount.setText(tables.multiply(price).toPlainString());
            }
        });
        mBinding.etAddDishMoney.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString().trim();
                mDTO.setAdd_dish_money(string);
            }
        });
        mBinding.etMealLossMoney.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString().trim();
                mDTO.setMeal_loss_money(string);
            }
        });
        mBinding.etWine.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString().trim();
                mDTO.setWine(string);
            }
        });

        mBinding.etSessionAmount.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String string = s.toString().trim();
                if (StringUtils.isTrimEmpty(string)) string = "0";
                mDTO.setSession_amount(string);
                BanquetStep6Fragment fragment = (BanquetStep6Fragment) getParentFragment();
                if (fragment != null) {
                    fragment.setMoney();
                }
            }
        });
        mBinding.tvUploadWaterBillPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        mBinding.tvLookWaterBillPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = mDTO.getWater_bill_pic();
                ArrayList<String> imageList = new ArrayList<>();
                for (String s : list) {
                    imageList.add(HttpURLs.IMAGE_BASE + s);
                }
                PicListActivity.start(imageList, getThisFragment().hashCode() + 3);
            }
        });

        refreshUI();

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
                                        mDTO.getWater_bill_pic().add(dir);
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
        if (code == getThisFragment().hashCode() + 3) {
            mDTO.getWater_bill_pic().remove(image);
        }

    }

    public void setData(NetRestoreStep6.DataDTO.BanquetNumDTO dto) {
        mDTO = dto;
        if (mDTO == null) {
            mDTO = new NetRestoreStep6.DataDTO.BanquetNumDTO();
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
        mBinding.etAddDishMoney.setText(mDTO.getAdd_dish_money());
        mBinding.etMealLossMoney.setText(mDTO.getMeal_loss_money());
        mBinding.etWine.setText(mDTO.getWine());
        //默认场次金额 = 桌数 * 套餐单价
        BigDecimal tables = new BigDecimal(StringUtils.isTrimEmpty(mDTO.getTable_number()) ? "0": mDTO.getTable_number());//桌数
        BigDecimal price = new BigDecimal(StringUtils.isTrimEmpty(mDTO.getPrice()) ? "0": mDTO.getPrice());//单价
        mBinding.etSessionAmount.setText(tables.multiply(price).toPlainString());
        mBinding.tvSegmentType.setText(mDTO.getSegment_name());

    }
}
