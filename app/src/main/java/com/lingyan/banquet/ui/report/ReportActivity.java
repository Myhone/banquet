package com.lingyan.banquet.ui.report;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityRechargeBinding;
import com.lingyan.banquet.databinding.ActivityReportBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.DialogJsonCallBack;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.net.NetUploadImage;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.utils.PicSelectUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/1/11.
 */

public class ReportActivity extends BaseActivity {

    private ActivityReportBinding mBinding;

    public static void start() {
        ActivityUtils.startActivity(ReportActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityReportBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("意见反馈");
        mBinding.llTitleBarRoot.tvTitleBarRight.setText("反馈记录");
        mBinding.llTitleBarRoot.tvTitleBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportListActivity.start();
            }
        });

        mBinding.imageLayout.setAddViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        mBinding.tvCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mBinding.etContent.getText().toString();
                if (StringUtils.isTrimEmpty(content)) {
                    ToastUtils.showShort("请输入反馈意见");
                    return;
                }
                List<String> imageList = mBinding.imageLayout.getImageList();
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("content", content);
                if (imageList.size() > 0) {
                    JsonArray jsonArray = new JsonArray();
                    for (String s : imageList) {
                        jsonArray.add(MyImageUtils.getRelativePath(s));
                    }
                    jsonObject.add("img_url", jsonArray);
                }
                OkGo.<NetBaseResp>post(HttpURLs.insertFeedBack)
                        .upJson(jsonObject.toString())
                        .execute(new JsonCallback<NetBaseResp>() {
                            @Override
                            public void onSuccess(Response<NetBaseResp> response) {
                                NetBaseResp body = response.body();
                                String msg = body.getMsg();
                                ToastUtils.showShort(msg);
                                if (body.getCode() == 200) {
                                    mBinding.imageLayout.clear();
                                    mBinding.etContent.setText("");
                                }
                            }
                        });
            }
        });
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
                                .execute(new JsonCallback<NetUploadImage>() {
                                    @Override
                                    public void onSuccess(Response<NetUploadImage> response) {
                                        NetUploadImage body = response.body();
                                        NetUploadImage.DataDTO data = body.getData();
                                        if (body.getCode() != 200 || data == null || data.getUrl() == null) {
                                            return;
                                        }
                                        String dir = data.getDir();
                                        mBinding.imageLayout.add(HttpURLs.IMAGE_BASE + dir);
                                    }
                                });
                    }
                }
            }
        });
    }
}
