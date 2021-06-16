package com.lingyan.banquet.utils;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lingyan.banquet.R;


/**
 * Created by _hxb on 2020/8/20.
 */
public class DialogUtils {

    public static MaterialDialog getLoadingMaterialDialog(Context context) {
        MaterialDialog dialog = null;
        try {
            dialog = new MaterialDialog.Builder(context)
                    .customView(R.layout.dialog_loading, false)
                    .build();
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.gravity = Gravity.CENTER;
                window.setAttributes(attributes);
                window.setWindowAnimations(R.style.view_center_scale_anim);
                window.setBackgroundDrawable(new ColorDrawable());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dialog;
    }
}
