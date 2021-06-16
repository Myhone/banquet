package com.lingyan.banquet.global;

import android.view.View;

import com.lingyan.banquet.ui.login.LoginActivity;

/**
 * Created by _hxb on 2021/2/19.
 */

public abstract class LoginCheckClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        boolean login = UserInfoManager.getInstance().isLogin();
        if (login) {
            onClickWithLogin(v);
        } else {
            onClickWithNoLogin(v);
        }
    }

    public abstract void onClickWithLogin(View view);

    public void onClickWithNoLogin(View v) {
        LoginActivity.start();
    }
}
