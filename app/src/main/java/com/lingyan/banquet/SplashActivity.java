package com.lingyan.banquet;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.SPUtils;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.global.Constant;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.ui.login.LoginActivity;
import com.lingyan.banquet.utils.InitSDKUtils;
import com.lingyan.banquet.views.dialog.PrivacyDialog;

/**
 * Created by _hxb on 2021/2/25.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //用户隐私协议弹窗
        handleAgreement();

    }

    private void handleAgreement() {
        if (!SPUtils.getInstance().getBoolean(Constant.SP.IS_AGREED_PRIVACY_AGREEMENT)) {
            PrivacyDialog dialog = new PrivacyDialog(SplashActivity.this);
            dialog.setCancelable(false);
            dialog.setListener(privacyClickListener);
            dialog.show();
        } else {
            next();
        }
    }

    PrivacyDialog.BtnClickListener privacyClickListener = new PrivacyDialog.BtnClickListener() {
        @Override
        public void confirmCallback() {
            //用户点击隐私协议同意按钮后，初始化SDK
            InitSDKUtils.init();
            //保存本地下次不再弹出
            SPUtils.getInstance().put(Constant.SP.IS_AGREED_PRIVACY_AGREEMENT, true);
            next();
        }

        @Override
        public void cancelCallback() {
            finish();
        }
    };

    private void next() {
        if (isTaskRoot()) {
            if (!UserInfoManager.getInstance().isLogin()) {
                LoginActivity.start();
            } else {
                MainActivity.start();
            }
        }
        finish();
    }
}
