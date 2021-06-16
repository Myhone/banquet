package com.lingyan.banquet.utils;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static android.app.Activity.RESULT_OK;

/**
 * Created by _hxb on 2020/12/29.
 */

public class PicSelectUtils {
    private static final int RESULT_PIC_SELECT = 1000;

    public static interface CallBack {
        void before(Matisse matisse, int code);

        void onSuccess(List<String> list);
    }


    public static void start(CallBack callBack) {
        FragmentActivity topActivity = (FragmentActivity) ActivityUtils.getTopActivity();
        FragmentManager fragmentManager = topActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        PicFragment picFragment = new PicFragment();
        picFragment.setCallBack(callBack);
        transaction.add(android.R.id.content, picFragment);
        transaction.commitAllowingStateLoss();
    }


    public static class PicFragment extends Fragment {

        private CallBack mCallBack;

        public void setCallBack(CallBack callBack) {
            this.mCallBack = callBack;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            permission();
        }

        private void permission() {

            PermissionUtils permission = PermissionUtils.permission(Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.CAMERA
            );
            permission.callback(new PermissionUtils.SimpleCallback() {
                @Override
                public void onGranted() {
                    selectPic();
                }

                @Override
                public void onDenied() {
                    ToastUtils.showShort("授权失败！");
                    removeMe();
                    PermissionUtils.launchAppDetailsSettings();
                }
            });
            permission.request();
        }

        private int progress = 0;

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == RESULT_PIC_SELECT && resultCode == RESULT_OK) {
                List<String> list = Matisse.obtainPathResult(data);

                int size = list.size();

                progress = 0;
                String cachePath = PathUtils.getCachePathExternalFirst();
                List<String> newPicList = new ArrayList<>();

                for (int i = 0; i < size; i++) {

                    String oldPic = list.get(i);

                    Luban.with(getContext())
                            .load(oldPic)
                            .ignoreBy(100)
                            .setTargetDir(cachePath)
                            .filter(new CompressionPredicate() {
                                @Override
                                public boolean apply(String path) {
                                    return !(ObjectUtils.isEmpty(path));
                                }
                            })
                            .setCompressListener(new OnCompressListener() {
                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onSuccess(final File file) {
                                    newPicList.add(file.getPath());
                                    progress++;

                                    if (progress == size) {
                                        if (mCallBack != null) {
                                            mCallBack.onSuccess(newPicList);
                                        }
                                    }
                                }

                                @Override
                                public void onError(Throwable e) {
                                    newPicList.add(oldPic);
                                    progress++;
                                    if (progress == size) {
                                        if (mCallBack != null) {
                                            mCallBack.onSuccess(newPicList);
                                        }
                                    }
                                }
                            }).launch();
                }


            }
            removeMe();
        }


        private void removeMe() {
            FragmentActivity activity = getActivity();
            FragmentManager manager = activity.getSupportFragmentManager();
            manager.beginTransaction().remove(this).commitAllowingStateLoss();
        }

        private void selectPic() {
            Matisse matisse = Matisse.from(this);
            if (mCallBack != null) {
                mCallBack.before(matisse, RESULT_PIC_SELECT);
            }


        }

        @Override
        public void onDestroy() {
            super.onDestroy();
        }
    }


}
