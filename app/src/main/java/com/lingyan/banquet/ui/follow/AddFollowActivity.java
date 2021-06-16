package com.lingyan.banquet.ui.follow;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lingyan.banquet.App;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityAddFollowBinding;
import com.lingyan.banquet.event.FollowRefreshEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetUploadImage;
import com.lingyan.banquet.ui.follow.bean.FollowList;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.utils.PicSelectUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by wyq on 2021/6/9.
 */
public class AddFollowActivity extends BaseActivity {

    private ActivityAddFollowBinding mBinding;
    private String banquet_id;
    private int followType = 1;
    private int noticeType;
    private String name;

    public static void start(String banquet_id, String name) {
        Intent intent = new Intent(App.sApp, AddFollowActivity.class);
        intent.putExtra("banquet_id", banquet_id);
        intent.putExtra("name", name);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAddFollowBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("新增跟进");
        mBinding.llTitleBarRoot.tvTitleBarRight.setText("发布");
        mBinding.llTitleBarRoot.tvTitleBarRight.setTextColor(getResources().getColor(R.color.gold));
        Intent intent = getIntent();
        banquet_id = intent.getStringExtra("banquet_id");
        name = intent.getStringExtra("name");
        mBinding.tvName.setText(name);

        mBinding.llTitleBarRoot.tvTitleBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                release();
            }
        });
        mBinding.imageLayout.setAddViewClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        mBinding.tvChoiceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        mBinding.tvChoiceTime.setText(TimeUtils.date2String(date, "yyyy-MM-dd HH:mm"));
                    }
                })
                        .setTitleText("请选择提醒时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, true, true, true, false})
                        .build()
                        .show();
            }
        });

        mBinding.rgFollowType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton button = group.findViewById(checkedId);
                if (button.getText().equals("当面跟进")) {
                    followType = 1;
                } else if (button.getText().equals("电话跟进")) {
                    followType = 2;
                } else if (button.getText().equals("网络跟进")) {
                    followType = 3;
                }
            }
        });

        mBinding.switchNotice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    noticeType = 1;
                    mBinding.llNotice.setVisibility(View.VISIBLE);
                } else {
                    noticeType = 0;
                    mBinding.llNotice.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    //发布
    private void release() {
        if (!check()) return;

        List<String> imageList = mBinding.imageLayout.getImageList();
        JsonObject jsonObject = new JsonObject();
        if (imageList.size() > 0) {
            JsonArray jsonArray = new JsonArray();
            for (String s : imageList) {
                jsonArray.add(MyImageUtils.getRelativePath(s));
            }
            jsonObject.add("img_url", jsonArray);
        }

        jsonObject.addProperty("banquet_id", banquet_id);
        jsonObject.addProperty("follow_type", followType);
        jsonObject.addProperty("content", mBinding.etContentFollow.getText().toString());
        jsonObject.addProperty("is_notice", noticeType);
        jsonObject.addProperty("notice_content", mBinding.etContentNotice.getText().toString());
        jsonObject.addProperty("notice_time", mBinding.tvChoiceTime.getText().toString());
        OkGo.<FollowList>post(HttpURLs.followAdd)
                .upJson(jsonObject.toString())
                .execute(new JsonCallback<FollowList>() {
                    @Override
                    public void onSuccess(Response<FollowList> response) {
                        FollowList body = response.body();
                        if (body != null) {
                            ToastUtils.showShort(body.getMsg());
                            EventBus.getDefault().post(new FollowRefreshEvent());
                            finish();
                        }
                    }

                    @Override
                    public void onError(Response<FollowList> response) {
                        super.onError(response);
                        ToastUtils.showShort("发布失败！");
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                    }
                });

    }

    private boolean check() {
        if (StringUtils.isEmpty(mBinding.etContentFollow.getText())) {
            ToastUtils.showShort("请填写跟进内容");
            return false;
        }

        if (mBinding.switchNotice.isChecked() && StringUtils.isEmpty(mBinding.tvChoiceTime.getText())) {
            ToastUtils.showShort("请选择提醒时间");
            return false;
        }

        if (mBinding.switchNotice.isChecked() && StringUtils.isEmpty(mBinding.etContentNotice.getText())) {
            ToastUtils.showShort("请填写提醒内容");
            return false;
        }
        return true;
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
