package com.lingyan.banquet.utils;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationManagerCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.lingyan.banquet.App;


/**
 * Created by _hxb on 2019/10/22.
 */
public class SettingUtils {


    /**
     * 判断通知是否打开
     *
     * @return
     */
    public static boolean NotificationIsOpen() {
        return NotificationManagerCompat.from(App.sApp).areNotificationsEnabled();
    }


    /**
     * 打开系统通知管理页面
     */
    public static void openNotificationPage() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0引导
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("android.provider.extra.APP_PACKAGE", App.sApp.getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
            intent.putExtra("app_package", App.sApp.getPackageName());
            intent.putExtra("app_uid", App.sApp.getApplicationInfo().uid);
        } else {
            // 其他
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", App.sApp.getPackageName(), null));
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            ActivityUtils.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
