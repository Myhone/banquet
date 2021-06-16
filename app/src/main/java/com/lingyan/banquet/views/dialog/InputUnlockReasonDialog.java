package com.lingyan.banquet.views.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.base.BaseDialog;
import com.lingyan.banquet.databinding.DialogInputReasonBinding;
import com.lingyan.banquet.event.ApplyUnlockOrderEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by _hxb on 2021/1/4.
 */

public class InputUnlockReasonDialog extends BaseDialog {

    private final DialogInputReasonBinding mBinding;
    private int type;
    private String id;
    public InputUnlockReasonDialog(@NonNull Context context) {
        super(context);
        mBinding = DialogInputReasonBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<NetBaseResp>post(HttpURLs.saveLoseOrder)
                        .params("id", getId())
                        .params("type", getType())
                        .params("reason", mBinding.etContent.getText().toString().trim())
                        .execute(new JsonCallback<NetBaseResp>() {
                            @Override
                            public void onSuccess(Response<NetBaseResp> response) {
                                NetBaseResp body = response.body();
                                String msg = body.getMsg();
                                int code = body.getCode();
                                ToastUtils.showShort(msg);
                                if (code == 200) {
                                    EventBus.getDefault().post(new ApplyUnlockOrderEvent());
                                    dismiss();
                                }
                            }
                        });
            }
        });


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (ScreenUtils.getAppScreenWidth() *0.9f);
        attributes.gravity = Gravity.CENTER;
        window.setAttributes(attributes);
        useDefaultAnim();
    }
}
