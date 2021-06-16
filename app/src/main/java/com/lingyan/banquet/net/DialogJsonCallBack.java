package com.lingyan.banquet.net;

import android.content.Context;
import android.content.DialogInterface;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ActivityUtils;
import com.lingyan.banquet.utils.DialogUtils;
import com.lzy.okgo.request.base.Request;


/**
 * Created by hxb on 2017/12/4.
 */

public abstract class DialogJsonCallBack<T> extends JsonCallback<T> implements DialogInterface.OnCancelListener {
    protected MaterialDialog mMaterialDialog;

    public DialogJsonCallBack() {
        this(ActivityUtils.getTopActivity());
    }
    public DialogJsonCallBack(Context context) {
        super();
        mMaterialDialog = DialogUtils.getLoadingMaterialDialog(context);
        if (mMaterialDialog != null) {
            mMaterialDialog.setOnCancelListener(this);
        }
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        try {
            if (mMaterialDialog != null) {
                mMaterialDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        try {
            if (mMaterialDialog != null && mMaterialDialog.isShowing()) {
                mMaterialDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {

    }
}
