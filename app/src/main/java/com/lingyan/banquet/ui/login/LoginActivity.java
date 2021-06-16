package com.lingyan.banquet.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.CacheDiskStaticUtils;
import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonObject;
import com.lingyan.banquet.MainActivity;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityLoginBinding;
import com.lingyan.banquet.event.LoginEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.net.DialogJsonCallBack;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.push.PushUtils;
import com.lingyan.banquet.ui.common.WebActivity;
import com.lingyan.banquet.ui.login.bean.NetAgreement;
import com.lingyan.banquet.ui.login.bean.NetLoginReq;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by _hxb on 2021/1/17.
 */

public class LoginActivity extends BaseActivity {

    private ActivityLoginBinding mBinding;
    private UserInfoManager mUserInfoManager;

    public static void start() {
        ActivityUtils.startActivity(LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.viewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startHomeActivity();
            }
        });
        mBinding.etUserName.setText(SPUtils.getInstance().getString("login_user_name"));
        mBinding.etPwd.setText(SPUtils.getInstance().getString("login_pwd"));

        mBinding.etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus&&mBinding.etUserName.getText().toString().length()>0){
                    mBinding.ivDeletePwd.setVisibility(View.VISIBLE);
                }else {
                    mBinding.ivDeletePwd.setVisibility(View.INVISIBLE);
                }
            }
        });
        mBinding.etUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus&&mBinding.etUserName.getText().toString().length()>0){
                    mBinding.ivDeleteUserName.setVisibility(View.VISIBLE);
                }else {
                    mBinding.ivDeleteUserName.setVisibility(View.INVISIBLE);
                }
            }
        });

        mBinding.etPwd.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (ObjectUtils.isNotEmpty(text)) {
                    mBinding.ivDeletePwd.setVisibility(View.VISIBLE);
                } else {
                    mBinding.ivDeletePwd.setVisibility(View.INVISIBLE);
                }
            }
        });
        mBinding.etUserName.addTextChangedListener(new TextWatcherImpl() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                if (ObjectUtils.isNotEmpty(text)) {
                    mBinding.ivDeleteUserName.setVisibility(View.VISIBLE);
                } else {
                    mBinding.ivDeleteUserName.setVisibility(View.INVISIBLE);
                }
            }
        });
        mBinding.ivDeleteUserName.setVisibility(View.INVISIBLE);
        mBinding.ivDeletePwd.setVisibility(View.INVISIBLE);
        mBinding.ivDeleteUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etUserName.setText("");
            }
        });
        mBinding.ivDeletePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.etPwd.setText("");
            }
        });
        mUserInfoManager = UserInfoManager.getInstance();
        mBinding.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = mBinding.etUserName.getText().toString().trim();
                String pwd = mBinding.etPwd.getText().toString().trim();
                if (ObjectUtils.isEmpty(userName)) {
                    ToastUtils.showShort("请输入账号");
                    return;
                }
                if (ObjectUtils.isEmpty(pwd)) {
                    ToastUtils.showShort("请输入密码");
                    return;
                }
                OkGo.<NetLoginReq>post(HttpURLs.login)
                        .params("username", userName)
                        .params("password", pwd)
                        .execute(new DialogJsonCallBack<NetLoginReq>() {
                            @Override
                            public void onSuccess(Response<NetLoginReq> response) {
                                NetLoginReq req = response.body();
                                NetLoginReq.DataDTO data = req.getData();
                                if (data != null && ObjectUtils.isNotEmpty(data.getToken())) {
                                    SPUtils.getInstance().put("login_user_name", userName);
                                    SPUtils.getInstance().put("login_pwd", pwd);
                                    SPUtils.getInstance().put("img_url", data.getImg_url());
                                    HttpURLs.IMAGE_BASE=data.getImg_url();

                                    String oldPushId = mUserInfoManager.get(UserInfoManager.PUSH_ID);
                                    PushUtils.deleteAlias(oldPushId);

                                    mUserInfoManager.put(UserInfoManager.ACCESS_TOKEN, data.getToken());
                                    mUserInfoManager.put(UserInfoManager.PUSH_ID, data.getPush_id());
                                    mUserInfoManager.put(UserInfoManager.ID, data.getUser().getId());
                                    mUserInfoManager.put(UserInfoManager.REAL_NAME, data.getUser().getRealname());
                                    mUserInfoManager.put(UserInfoManager.NIKE_NAME, data.getUser().getNickname());
                                    mUserInfoManager.put(UserInfoManager.IS_ADMIN, data.getUser().getIs_admin());
                                    mUserInfoManager.put(UserInfoManager.ALL_DEPART_ID, data.getUser().getAll_depte_id());
                                    PushUtils.addAlias(data.getPush_id());
                                    EventBus.getDefault().post(new LoginEvent());
                                    finish();
                                    if(!ActivityUtils.isActivityExistsInStack(MainActivity.class)){
                                        MainActivity.start();
                                    }
                                } else {
                                    ToastUtils.showShort(req.getMsg());
                                }
                            }
                        });
            }
        });
        mBinding.llAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<NetAgreement>post(HttpURLs.loginAgreement)
                        .execute(new JsonCallback<NetAgreement>() {
                            @Override
                            public void onSuccess(Response<NetAgreement> response) {
                                NetAgreement body = response.body();
                                NetAgreement.DataDTO data = body.getData();
                                if(data==null){
                                    return;
                                }
                                String url = data.getUrl();
                                WebActivity.start(url);
                            }
                        });
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        ActivityUtils.startHomeActivity();
    }
}
