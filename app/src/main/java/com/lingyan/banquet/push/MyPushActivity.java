package com.lingyan.banquet.push;

import android.content.Intent;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lingyan.banquet.MainActivity;
import com.lingyan.banquet.global.Router;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.ui.login.LoginActivity;
import com.umeng.message.UmengNotifyClickActivity;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

/**
 * Created by _hxb on 2021/3/2.
 */

public class MyPushActivity extends UmengNotifyClickActivity {

    @Override
    public void onMessage(Intent intent) {
        super.onMessage(intent);  //此方法必须调用，否则无法统计打开数
        MainActivity.start();
        final String body = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
        try {

            boolean login = UserInfoManager.getInstance().isLogin();
            if (!login) {
                LoginActivity.start();
            } else if (!StringUtils.isEmpty(body)) {
                JSONObject jsonObject = new JSONObject(body);
                JSONObject object = jsonObject.getJSONObject("extra");
                String string = object.getString("path");
                Router.navigation(string);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finish();
        }
    }
}
