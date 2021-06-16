package com.lingyan.banquet.base;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.lingyan.banquet.MainActivity;
import com.lingyan.banquet.R;
import com.lzy.okgo.OkGo;
import com.umeng.message.PushAgent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by _hxb on 2020/10/28.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(getContext()).onAppStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
//        if(!ActivityUtils.isActivityExistsInStack(MainActivity.class)){
//            MainActivity.start();
//        }
    }

    @NonNull
    @Override
    public LayoutInflater getLayoutInflater() {
        return super.getLayoutInflater();
    }


    public BaseActivity getActivity() {
        return this;
    }

    public Context getContext() {
        return this;
    }
}
