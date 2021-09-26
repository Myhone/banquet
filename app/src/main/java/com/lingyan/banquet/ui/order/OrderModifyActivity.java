package com.lingyan.banquet.ui.order;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.JsonObject;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityOrderModifyBinding;
import com.lingyan.banquet.event.OrderDetailRefreshEvent;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.Constant;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep4;
import com.lingyan.banquet.ui.banquet.session.SignSessionFragment;
import com.lingyan.banquet.ui.order.bean.NetOrderModify;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyq on 2021/9/15.
 */
public class OrderModifyActivity extends BaseActivity {

    private ActivityOrderModifyBinding mBinding;
    private FragmentManager mFragmentManager;
    private List<OrderModifyListFragment> mFragmentList;
    private String mId;//订单id
    private int mType;//类型：宴会or庆典
    private NetOrderModify.DataDTO mData;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityOrderModifyBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.rlTitleBar.tvTitleBarTitle.setText("修改订单");
        mBinding.rlTitleBar.rlTitleBarContainer.setBackgroundColor(getColor(R.color.black2f));

        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mType = intent.getIntExtra("type", BanquetCelebrationType.TYPE_BANQUET);
        initView();
        loadData();
    }

    private void initView() {

        mFragmentList = new ArrayList<>();
        mFragmentManager = getSupportFragmentManager();
        mBinding.tvModify.setOnClickListener(v -> saveModify());
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                for (int i = 0; i < mFragmentList.size(); i++) {
                    OrderModifyListFragment fragment = mFragmentList.get(i);
                    if (i == position) {
                        transaction.show(fragment);
                    } else {
                        transaction.hide(fragment);
                    }
                }
                transaction.commitAllowingStateLoss();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        mBinding.tvAddSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData == null) {
                    return;
                }
                List<NetOrderModify.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
                OrderModifyListFragment fragment = add();
                NetOrderModify.DataDTO.BanquetNumDTO dto = new NetOrderModify.DataDTO.BanquetNumDTO();
                fragment.setData(dto);
                list.add(dto);
                ToastUtils.showShort("长按左边场次可删除");
            }
        });

        mBinding.rlTitleBar.ivTitleBarLeft.setOnClickListener(v -> setResultAndFinishActivity());
    }

    private void loadData() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", mId);
        OkGo.<NetOrderModify>post(HttpURLs.banquetModifyInfo)
                .upJson(jsonObject.toString())
                .execute(new JsonCallback<NetOrderModify>() {
                    @Override
                    public void onSuccess(Response<NetOrderModify> response) {
                        NetOrderModify body = response.body();
                        if (body == null) {
                            return;
                        }
                        mData = body.getData();
                        if (mData == null) {
                            return;
                        }
                        refreshUI();
                    }
                });
    }

    /**
     * 保存修改数据
     */
    private void saveModify() {
        List<NetOrderModify.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
        if (ObjectUtils.isEmpty(list)) {
            ToastUtils.showShort("当前状态不可操作");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            NetOrderModify.DataDTO.BanquetNumDTO dto = list.get(i);
            String segmentType = dto.getSegment_type();
            String date = dto.getDate();
            String mealName = dto.getMeal_name();
            List<String> hallId = dto.getHall_id();
            if (StringUtils.isTrimEmpty(date)) {
                ToastUtils.showShort(String.format("请选择第%d场的场次时间", (i + 1)));
                return;
            }
            if (StringUtils.isTrimEmpty(segmentType)) {
                ToastUtils.showShort(String.format("请选择第%d场的用餐时间", (i + 1)));
                return;
            }
            if (ObjectUtils.isEmpty(hallId)) {
                ToastUtils.showShort(String.format("请选择第%d场的宴会厅", (i + 1)));
                return;
            }
            if (ObjectUtils.isEmpty(mealName)) {
                ToastUtils.showShort(String.format("请选择第%d场的套餐", (i + 1)));
                return;
            }
        }

        mData.setId(mId);
        LogUtils.eTag("modify", GsonUtils.toJson(mData));
        OkGo.<NetOrderModify>post(HttpURLs.banquetModifySave)
                .upJson(GsonUtils.toJson(mData))
                .execute(new JsonCallback<NetOrderModify>() {
                    @Override
                    public void onSuccess(Response<NetOrderModify> response) {
                        NetOrderModify body = response.body();
                        if (body == null) {
                            return;
                        }
                        ToastUtils.showShort(body.getMsg());
                        //点击保存时刷新订单详情页
                        EventBus.getDefault().post(new OrderDetailRefreshEvent());

                        setResultAndFinishActivity();
                    }

                    @Override
                    public void onError(Response<NetOrderModify> response) {
                        super.onError(response);
                        ToastUtils.showShort(response.message());
                    }
                });
    }

    private void setResultAndFinishActivity() {
        setResult(Constant.Code.OPEN_ORDER_MODIFY_CODE_RESULT);
        finish();
    }

    private void refreshUI() {
        if (ObjectUtils.isEmpty(mData)) {
            return;
        }
        mBinding.tabLayout.removeAllTabs();
        if (ObjectUtils.isNotEmpty(mFragmentList)) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            for (Fragment fragment : mFragmentList) {
                transaction.remove(fragment);
            }
            transaction.commitAllowingStateLoss();
            mFragmentList.clear();
        }

        List<NetOrderModify.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
        if (list.size() == 0) {
            OrderModifyListFragment fragment = add();
            NetOrderModify.DataDTO.BanquetNumDTO dto = new NetOrderModify.DataDTO.BanquetNumDTO();
            fragment.setData(dto);
            list.add(dto);
        } else {
            for (int i = 0; i < list.size(); i++) {
                NetOrderModify.DataDTO.BanquetNumDTO dto = list.get(i);
                OrderModifyListFragment fragment = add();
                fragment.setData(dto);
            }
        }
    }


    private OrderModifyListFragment add() {
        int tabCount = mBinding.tabLayout.getTabCount();
        tabCount++;
        TabLayout.Tab tab = mBinding.tabLayout.newTab();
        tab.setText("第" + tabCount + "场");
        mBinding.tabLayout.addTab(tab);

        OrderModifyListFragment fragment = OrderModifyListFragment.newInstance(mType);
        mFragmentList.add(fragment);

        mFragmentManager.beginTransaction().add(R.id.fl_session_container, fragment).commitAllowingStateLoss();

        tab.select();

        tab.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int nowCount = mBinding.tabLayout.getTabCount();
                if (nowCount <= 1) {
                    return false;
                }
                new MaterialDialog.Builder(getContext())
                        .title("提示")
                        .content("确定删除该场次?")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                int position = tab.getPosition();
                                List<NetOrderModify.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
                                list.remove(position);
                                mBinding.tabLayout.removeTab(tab);
                                mFragmentList.remove(fragment);
                                mFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();

                                for (int i = 0; i < nowCount - 1; i++) {
                                    mBinding.tabLayout.getTabAt(i).setText("第" + (i + 1) + "场");
                                }

                                refreshUI();

                            }
                        })
                        .show();

                return true;
            }
        });

        return fragment;

    }

    @Override
    public void onBackPressed() {
        setResultAndFinishActivity();
        super.onBackPressed();
    }
}
