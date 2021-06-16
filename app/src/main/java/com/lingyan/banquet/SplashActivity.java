package com.lingyan.banquet;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.ui.login.LoginActivity;

/**
 * Created by _hxb on 2021/2/25.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected  void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            finish();
        } else {
            if (!UserInfoManager.getInstance().isLogin()) {
                LoginActivity.start();
            } else {
                MainActivity.start();
            }
            finish();
        }
    }
}
