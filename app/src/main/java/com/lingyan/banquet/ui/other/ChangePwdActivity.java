package com.lingyan.banquet.ui.other;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityChangePwdBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * Created by _hxb on 2021/3/10.
 */

public class ChangePwdActivity extends BaseActivity {

    private ActivityChangePwdBinding mBinding;
    private Runnable mRunnable;

    public static void start() {
        ActivityUtils.startActivity(ChangePwdActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityChangePwdBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("修改密码");
        mBinding.tvTip.setVisibility(View.GONE);
        mRunnable = new Runnable() {
            @Override
            public void run() {
                if (mBinding.tvTip == null || isDestroyed()) {
                    return;
                }
                mBinding.tvTip.setVisibility(View.GONE);
            }
        };
        mBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPwd = mBinding.etOldPwd.getText().toString();
                String newPwd = mBinding.etNewPassword.getText().toString();
                String newPwd2 = mBinding.etNewPassword2.getText().toString();
                if (StringUtils.isEmpty(oldPwd)) {
                    showError("请输入旧密码");
                    return;
                }
                if (StringUtils.isEmpty(newPwd)) {
                    showError("请输入新密码");
                    return;
                }
                if (StringUtils.isEmpty(newPwd2)) {
                    showError("请输入新密码");
                    return;
                }
                if (!StringUtils.equals(newPwd, newPwd2)) {
                    showError("两次新密码不一致");
                    return;
                }
                if (StringUtils.equals(oldPwd, newPwd2)) {
                    showError("新密码和旧密码不能相同");
                    return;
                }
                OkGo.<NetBaseResp>post(HttpURLs.userUpdatePwd)
                        .params("old_password", oldPwd)
                        .params("new_password", newPwd)
                        .execute(new JsonCallback<NetBaseResp>() {
                            @Override
                            public void onSuccess(Response<NetBaseResp> response) {
                                NetBaseResp body = response.body();
                                int code = body.getCode();
                                if (code == 200) {
                                    ToastUtils.showShort("修改密码成功");
                                    finish();
                                } else {
                                    showError(body.getMsg());
                                }
                            }
                        });
            }
        });
    }

    private void showError(String error) {
        mBinding.tvTip.setVisibility(View.VISIBLE);
        mBinding.tvTip.setText(error);
        ThreadUtils.getMainHandler().removeCallbacks(mRunnable);
        ThreadUtils.getMainHandler().postDelayed(mRunnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThreadUtils.getMainHandler().removeCallbacks(mRunnable);
    }
}
