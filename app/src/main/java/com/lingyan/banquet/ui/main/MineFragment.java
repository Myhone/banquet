package com.lingyan.banquet.ui.main;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.FragmentMainMineBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.LoginCheckClickListener;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.net.DialogJsonCallBack;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.net.NetUploadImage;
import com.lingyan.banquet.ui.main.bean.NetUserInfo;
import com.lingyan.banquet.ui.recharge.RechargeActivity;
import com.lingyan.banquet.ui.other.SetActivity;
import com.lingyan.banquet.ui.report.ReportActivity;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.utils.PicSelectUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.List;


/**
 * Created by _hxb on 2021/1/1.
 */

public class MineFragment extends BaseFragment implements OnRefreshListener {

    private FragmentMainMineBinding mBinding;
    private UserInfoManager mUserInfoManager;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMainMineBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.llSet.setOnClickListener(new LoginCheckClickListener() {
            @Override
            public void onClickWithLogin(View view) {
                SetActivity.start();
            }
        });
        mBinding.llRecharge.setOnClickListener(new LoginCheckClickListener() {
            @Override
            public void onClickWithLogin(View view) {
                RechargeActivity.start();
            }
        });
        mBinding.llReport.setOnClickListener(new LoginCheckClickListener() {
            @Override
            public void onClickWithLogin(View view) {
                ReportActivity.start();
            }
        });
        mBinding.civAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        mUserInfoManager = UserInfoManager.getInstance();
        mBinding.refreshLayout.setOnRefreshListener(this);
        init();
        onRefresh(mBinding.refreshLayout);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isHidden()) {
            onRefresh(mBinding.refreshLayout);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onRefresh(mBinding.refreshLayout);
        }
    }

    private void uploadImage() {
        PicSelectUtils.start(new PicSelectUtils.CallBack() {
            @Override
            public void before(Matisse matisse, int code) {
                matisse.choose(MimeType.ofImage())
                        .countable(false)
                        .maxSelectable(1)
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
                    String s = list.get(0);
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
                                    uploadAvatar(dir);
                                }
                            });
                }
            }
        });
    }

    private void uploadAvatar(String s) {
        OkGo.<NetBaseResp>post(HttpURLs.uploadAvatar)
                .params("avatar", s)
                .tag(getThisFragment())
                .execute(new DialogJsonCallBack<NetBaseResp>() {
                    @Override
                    public void onSuccess(Response<NetBaseResp> response) {
                        NetBaseResp body = response.body();
                        int code = body.getCode();
                        if (code == 200) {
                            onRefresh(mBinding.refreshLayout);
                        } else {
                            ToastUtils.showShort("上传头像失败");
                        }
                    }
                });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        OkGo.<NetUserInfo>post(HttpURLs.userInfo)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetUserInfo>() {
                    @Override
                    public void onSuccess(Response<NetUserInfo> response) {
                        NetUserInfo body = response.body();
                        NetUserInfo.DataDTO data = body.getData();
                        if (data == null) {
                            init();
                            return;
                        }
                        mUserInfoManager.put(UserInfoManager.REAL_NAME, data.getRealname());
                        mUserInfoManager.put(UserInfoManager.NIKE_NAME, data.getNickname());


                        Glide.with(Utils.getApp())
                                .load(HttpURLs.IMAGE_BASE+data.getAvatar())
                                .error(R.mipmap.ic_default_image)
                                .placeholder(R.mipmap.ic_default_image)
                                .into(mBinding.civAvatar);
                        MyImageUtils.displayUseImageServer(mBinding.civAvatar,data.getAvatar());
                        mBinding.tvRealname.setText(data.getRealname());
                        mBinding.tvDeptName.setText(data.getDept_name());
                        mBinding.tvRestaurantName.setText(data.getRestaurant_name());
                        String isShow = data.getIs_show();
                        if (StringUtils.equals(isShow, "1")) {
                            mBinding.viewChargeBottomLine.setVisibility(View.VISIBLE);
                            mBinding.llRecharge.setVisibility(View.VISIBLE);
                        } else if (StringUtils.equals(isShow, "0")) {
                            mBinding.viewChargeBottomLine.setVisibility(View.GONE);
                            mBinding.llRecharge.setVisibility(View.GONE);
                        }
                        mBinding.tvExpireTime.setText(data.getExpire_time());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mBinding.refreshLayout.finishRefresh();
                    }
                });
    }

    private void init() {
        mBinding.civAvatar.setImageDrawable(new ColorDrawable());
        mBinding.tvRealname.setText("");
        mBinding.tvDeptName.setText("");
        mBinding.tvRestaurantName.setText("");
        mBinding.tvExpireTime.setText("");

    }
}
