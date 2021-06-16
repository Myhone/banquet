package com.lingyan.banquet.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.lingyan.banquet.R;

/**
 * Created by _hxb on 2020/3/18.
 */
public class BaseDialog extends Dialog {

    protected Context mConstructContext;


    public BaseDialog(@NonNull Context context) {
        super(context);
        mConstructContext = context;
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mConstructContext = context;
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mConstructContext = context;
    }


    public void useDefaultAnim() {
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            LogUtils.i("attributes", attributes.windowAnimations);
            if (attributes.gravity == Gravity.BOTTOM) {
                window.setWindowAnimations(R.style.view_bottom_slide_anim);
            } else if (attributes.gravity == Gravity.CENTER) {
                window.setWindowAnimations(R.style.view_center_scale_anim);
            } else if (attributes.gravity == Gravity.TOP) {
                window.setWindowAnimations(R.style.view_top_slide_anim);
            }
        }
    }


    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
