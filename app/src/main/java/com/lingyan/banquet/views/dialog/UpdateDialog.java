package com.lingyan.banquet.views.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.DialogUpdateBinding;
import com.lingyan.banquet.ui.main.bean.NetNewVersion;
import com.lingyan.banquet.utils.CheckUpdateUtils;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;

public class UpdateDialog extends BaseDialog {

    private DialogUpdateBinding mBinding;

    public UpdateDialog(@NonNull Context context) {
        super(context, R.style.NoTitleBarDialog);
        mBinding = DialogUpdateBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        setCancelable(false);
        mBinding.ivDoUpdate.setVisibility(View.VISIBLE);
        mBinding.progressBar.setVisibility(View.GONE);
        mBinding.tvInstall.setVisibility(View.GONE);
    }


    public void show(final NetNewVersion.DataBean data, final CheckUpdateUtils checkUpdateUtils) {
        initUI(data);
        mBinding.ivDoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path = PathUtils.getExternalAppCachePath();
                if (ObjectUtils.isEmpty(path)) {
                    path = PathUtils.getInternalAppCachePath();
                }

                checkUpdateUtils.downloadAPK(data.getAndroid_url(), new FileCallback(path, null) {

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        super.onStart(request);
                        mBinding.ivDoUpdate.setVisibility(View.GONE);
                        mBinding.progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        mBinding.progressBar.setProgress((int) (progress.fraction * 100));
                    }

                    @Override
                    public void onSuccess(final Response<File> response) {
                        final File file = response.body();
                        checkUpdateUtils.installApk(file);
                        mBinding.tvInstall.setVisibility(View.VISIBLE);
                        mBinding.tvInstall.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                checkUpdateUtils.installApk(file);
                            }
                        });
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        dismiss();
                        ToastUtils.showShort("下载失败");
                    }
                });
            }
        });
    }

    private void initUI(NetNewVersion.DataBean data) {
        String netVersion = data.getAndroid_vname();
        String description = data.getDescription();
        final String force = data.getForce();
        final boolean f = !ObjectUtils.isEmpty(force) && force.equals("1");
        mBinding.tvVersionName.setText(netVersion);
        mBinding.tvUpdateDes.setText(description);
        if (f) {
            mBinding.ivClose.setVisibility(View.INVISIBLE);
        }
        show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = ScreenUtils.getScreenWidth();
        window.setAttributes(attributes);
        mBinding.ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        LogUtils.i("type" + attributes.type);
    }


}
