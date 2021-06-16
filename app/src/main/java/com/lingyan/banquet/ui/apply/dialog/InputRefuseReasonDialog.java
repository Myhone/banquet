package com.lingyan.banquet.ui.apply.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ScreenUtils;
import com.lingyan.banquet.base.BaseDialog;
import com.lingyan.banquet.databinding.DialogInputRefuseReasonBinding;

/**
 * Created by _hxb on 2021/1/4.
 */

public class InputRefuseReasonDialog extends BaseDialog {

    private final DialogInputRefuseReasonBinding mBinding;

    public InputRefuseReasonDialog(@NonNull Context context) {
        super(context);
        mBinding = DialogInputRefuseReasonBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public DialogInputRefuseReasonBinding getBinding() {
        return mBinding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (ScreenUtils.getAppScreenWidth() * 0.9f);
        attributes.gravity = Gravity.CENTER;
        window.setAttributes(attributes);
    }
}
