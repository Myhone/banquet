package com.lingyan.banquet.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by _hxb on 2020/10/28.
 */

public class BaseFragment  extends Fragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        OkGo.getInstance().cancelTag(this);
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public BaseFragment getThisFragment(){
        return  this;
    }

}
