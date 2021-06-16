package com.lingyan.banquet.push;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lingyan.banquet.App;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

/**
 * Created by _hxb on 2021/3/2.
 */

public class PushUtils {

    public static void setAlias(String alias) {
        if (StringUtils.isEmpty(alias)) {
            return;
        }
        PushAgent pushAgent = PushAgent.getInstance(App.sApp);
        pushAgent.setAlias(alias, "android", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {
                LogUtils.i("设置别名",b,s);
            }
        });
    }

    public static void addAlias(String alias) {
        if (StringUtils.isEmpty(alias)) {
            return;
        }
        PushAgent pushAgent = PushAgent.getInstance(App.sApp);
        pushAgent.addAlias(alias, "android", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean b, String s) {
                LogUtils.i("添加别名",b,s);
            }
        });
    }

    public static void deleteAlias(String alias) {
        if (StringUtils.isEmpty(alias)) {
            return;
        }
        PushAgent mPushAgent = PushAgent.getInstance(App.sApp);
        mPushAgent.deleteAlias(alias, "android", new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
                LogUtils.i("删除别名",isSuccess,message);

            }
        });
    }
}
