package com.lingyan.banquet.global;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.lingyan.banquet.R;


/**
 * Created by _hxb on 2020/11/7.
 */

public  class ActivityLife implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        LogUtils.i("onActivityCreated",activity.getClass().getName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        View backView = activity.findViewById(R.id.iv_title_bar_left);
        if(backView!=null){
            backView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.finish();
                }
            });
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        LogUtils.i("onActivityDestroyed",activity.getClass().getName());
    }
}
