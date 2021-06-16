package com.lingyan.banquet.ui.other;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.NotificationUtils;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivitySetBinding;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.ui.login.LoginActivity;
import com.lingyan.banquet.utils.SettingUtils;

/**
 * Created by _hxb on 2021/1/11.
 */

public class SetActivity extends BaseActivity {

    private ActivitySetBinding mBinding;
    public static void start(){
        ActivityUtils.startActivity(SetActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivitySetBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("设置");
        mBinding.llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserInfoManager.getInstance().logout();
                finish();
                LoginActivity.start();
            }
        });
        mBinding.tvNowVersion.setText(AppUtils.getAppVersionName());
        mBinding.llNotifyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingUtils.openNotificationPage();
            }
        });
        mBinding.llChangePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePwdActivity.start();
            }
        });
    }
}
