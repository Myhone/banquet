package com.lingyan.banquet.utils;

import android.app.Activity;
import android.content.Intent;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.main.bean.NetNewVersion;
import com.lingyan.banquet.views.dialog.UpdateDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;

import java.io.File;

public class CheckUpdateUtils {

    public void check(final boolean showToastIfNewest) {

        Activity topActivity = ActivityUtils.getTopActivity();
        if (!ActivityUtils.isActivityAlive(topActivity)) {
            return;
        }

        OkGo.<NetNewVersion>post(HttpURLs.updateApp)
                .params("type", 2)
                .execute(new JsonCallback<NetNewVersion>() {
                    @Override
                    public void onSuccess(Response<NetNewVersion> response) {
                        NetNewVersion body = response.body();
                        NetNewVersion.DataBean data = body.getData();
                        boolean update = checkNeedUpdate(data);
                        if (update) {
                            Activity topActivity = ActivityUtils.getTopActivity();
                            if (!ActivityUtils.isActivityAlive(topActivity)) {
                                return;
                            }
                            new UpdateDialog(topActivity).show(data, CheckUpdateUtils.this);
                        } else if (showToastIfNewest) {
                            ToastUtils.showShort("已经是最新版本了");
                        }
                    }
                });
    }

    public void downloadAPK(String url, FileCallback fileCallback) {
        OkGo.<File>get(url)
                .execute(fileCallback);
    }

    public void installApk(File file) {
        //, "com.hxb.coupon.fileprovider"
        Intent installAppIntent = IntentUtils.getInstallAppIntent(file);
//        installAppIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Activity topActivity = ActivityUtils.getTopActivity();
        if (!ActivityUtils.isActivityAlive(topActivity)) {
            return;
        }
        topActivity.startActivity(installAppIntent);
    }


    private boolean checkNeedUpdate(NetNewVersion.DataBean data) {
        if (data == null) {
            return false;
        }
        int netVersionCode = data.getAndroid_vcode();
        int appVersionCode = AppUtils.getAppVersionCode();
        return netVersionCode > appVersionCode;

    }
}
