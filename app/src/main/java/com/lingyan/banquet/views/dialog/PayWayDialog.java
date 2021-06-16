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
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseDialog;
import com.lingyan.banquet.databinding.DialogPayWayBinding;

/**
 * 支付方式
 * Created by _hxb on 2021/1/14.
 */

public class PayWayDialog extends BaseDialog {
    private int mPayWay = 0;
    private final DialogPayWayBinding mBinding;
    private OnPayWayClickListener mOnPayWayClickListener;

    public static interface OnPayWayClickListener {
        void onPayWayClick(int payWay, String name, PayWayDialog dialog);
    }

    public OnPayWayClickListener getOnPayWayClickListener() {
        return mOnPayWayClickListener;
    }

    public void setOnPayWayClickListener(OnPayWayClickListener onPayWayClickListener) {
        mOnPayWayClickListener = onPayWayClickListener;
    }

    public PayWayDialog(@NonNull Context context) {
        super(context);
        mBinding = DialogPayWayBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBinding.llZhiFuBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPayWay(1);
                if (mOnPayWayClickListener != null) {
                    mOnPayWayClickListener.onPayWayClick(1,"支付宝",PayWayDialog.this);
                }
            }
        });
        mBinding.llWeiXin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPayWay(2);
                if (mOnPayWayClickListener != null) {
                    mOnPayWayClickListener.onPayWayClick(2,"微信",PayWayDialog.this);
                }
            }
        });
        mBinding.llYinLian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPayWay(3);
                if (mOnPayWayClickListener != null) {
                    mOnPayWayClickListener.onPayWayClick(3,"银联支付",PayWayDialog.this);
                }
            }
        });
        mBinding.llXianJin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPayWay(4);
                if (mOnPayWayClickListener != null) {
                    mOnPayWayClickListener.onPayWayClick(4,"现金",PayWayDialog.this);
                }
            }
        });

        setPayWay(0);
    }

    public void setPayWay(int payWay) {
        mPayWay = payWay;
        mBinding.ivZhiFuBao.setImageResource(payWay == 1 ? R.mipmap.ic_ok_selected : R.mipmap.ic_circle_gray);
        mBinding.ivWeiXin.setImageResource(payWay == 2 ? R.mipmap.ic_ok_selected : R.mipmap.ic_circle_gray);
        mBinding.ivYinLian.setImageResource(payWay == 3 ? R.mipmap.ic_ok_selected : R.mipmap.ic_circle_gray);
        mBinding.ivXianJin.setImageResource(payWay == 4 ? R.mipmap.ic_ok_selected : R.mipmap.ic_circle_gray);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (ScreenUtils.getAppScreenWidth());
        attributes.gravity = Gravity.BOTTOM;
        window.setAttributes(attributes);
    }
}
