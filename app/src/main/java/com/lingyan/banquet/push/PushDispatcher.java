package com.lingyan.banquet.push;

import android.content.Context;
import android.content.Intent;

import com.blankj.utilcode.util.LogUtils;
import com.lingyan.banquet.global.Router;


/**
 * Created by _hxb on 2019/7/25.
 */
public class PushDispatcher {
    public static boolean dispatch(Intent intent, Context context) {
        LogUtils.i("dispatch", intent.getExtras());
        String path = intent.getStringExtra("path");
        if (path == null) {
            return false;
        }
        intent.removeExtra("path");
        boolean router = false;
        try {
            router = Router.navigation( path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return router;
    }
}
