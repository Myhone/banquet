package com.lingyan.banquet;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.base.BaseNetResponse;
import com.lingyan.banquet.databinding.ActivityMainBinding;
import com.lingyan.banquet.event.ChangeMainPageEvent;
import com.lingyan.banquet.event.LogoutEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.push.PushDispatcher;
import com.lingyan.banquet.ui.login.LoginActivity;
import com.lingyan.banquet.ui.main.HomeFragment;
import com.lingyan.banquet.ui.main.MessageFragment;
import com.lingyan.banquet.ui.main.MineFragment;
import com.lingyan.banquet.ui.main.bean.NetUserInfo;
import com.lingyan.banquet.views.CircleBarView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.umeng.message.entity.UMessage;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    private ActivityMainBinding mBinding;

    private List<BaseFragment> mFragmentList;
    private FragmentManager mFragmentManager;
    private final int mDefaultIndex = 0;
    private int mCurIndex = -1;

    private int[] mTabsUnselected = {R.mipmap.ic_main_home_unselected, R.mipmap.ic_main_message_unselected, R.mipmap.ic_main_me_unselected};
    private int[] mTabsSelected = {R.mipmap.ic_main_home_selected, R.mipmap.ic_main_message_selected, R.mipmap.ic_main_me_selected};

    public static void start() {
        ActivityUtils.startActivity(MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        EventBus.getDefault().register(this);
        HomeFragment homeFragment = HomeFragment.newInstance();
        MessageFragment messageFragment = MessageFragment.newInstance();
        MineFragment mineFragment = MineFragment.newInstance();

        mFragmentList = new ArrayList<>();
        mFragmentList.add(homeFragment);
        mFragmentList.add(messageFragment);
        mFragmentList.add(mineFragment);

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (int i = 0; i < mFragmentList.size(); i++) {
            BaseFragment fragment = mFragmentList.get(i);
            transaction.add(R.id.fl, fragment);
            transaction.hide(fragment);
        }
        transaction.commitAllowingStateLoss();


        View.OnClickListener tabClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mBinding.llTabMainHome) {
                    showPage(0);
                } else if (v == mBinding.llTabMainMessage) {
                    showPage(1);
                } else if (v == mBinding.llTabMainMine) {
                    showPage(2);
                }
            }
        };
        mBinding.llTabMainHome.setOnClickListener(tabClick);
        mBinding.llTabMainMessage.setOnClickListener(tabClick);
        mBinding.llTabMainMine.setOnClickListener(tabClick);
        mBinding.viewRedDot.setVisibility(View.INVISIBLE);
        showPage(mDefaultIndex);
//        new RestaurantSelectDialog(getContext()).show();
        PushDispatcher.dispatch(getIntent(), this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void changePage(ChangeMainPageEvent event) {
        int page = event.getPage();
        showPage(page);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receivedMessage(UMessage message) {
        checkNotReadCount();
    }

    private void checkNotReadCount() {
        OkGo.<NetUserInfo>post(HttpURLs.userInfo)
                .execute(new JsonCallback<NetUserInfo>() {
                    @Override
                    public void onSuccess(Response<NetUserInfo> response) {
                        NetUserInfo body = response.body();
                        NetUserInfo.DataDTO data = body.getData();
                        if (data == null) {
                            mBinding.viewRedDot.setVisibility(View.INVISIBLE);
                            return;
                        }
                        String notReadNum = data.getNot_read_num();
                        if (StringUtils.isTrimEmpty(notReadNum)) {
                            mBinding.viewRedDot.setVisibility(View.INVISIBLE);
                            return;
                        }
                        try {
                            int i = Integer.parseInt(notReadNum);
                            mBinding.viewRedDot.setVisibility(i > 0 ? View.VISIBLE : View.INVISIBLE);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    private void showPage(int index) {
        if (mCurIndex == index) {
            tabReselected(index);
            return;
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mCurIndex >= 0 && mCurIndex < mFragmentList.size()) {
            transaction.hide(mFragmentList.get(mCurIndex));
            tabUnselected(mCurIndex);

        }
        if (index >= 0 && index < mFragmentList.size()) {
            transaction.show(mFragmentList.get(index));
            tabSelected(index);
        }
        transaction.commitAllowingStateLoss();
        mCurIndex = index;
    }


    private void tabUnselected(int position) {

    }

    private void tabSelected(int position) {
        mBinding.ivHome.setImageResource(position == 0 ? mTabsSelected[0] : mTabsUnselected[0]);
        mBinding.ivMessage.setImageResource(position == 1 ? mTabsSelected[1] : mTabsUnselected[1]);
        mBinding.ivMe.setImageResource(position == 2 ? mTabsSelected[2] : mTabsUnselected[2]);
    }

    private void tabReselected(int position) {


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.i("intent",intent);
        PushDispatcher.dispatch(intent, this);
        checkNotReadCount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!UserInfoManager.getInstance().isLogin()) {
            LoginActivity.start();
        }
        checkNotReadCount();
    }
}