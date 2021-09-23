package com.lingyan.banquet.ui.celebration.step;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.android.material.tabs.TabLayout;
import com.lingyan.banquet.R;
import com.lingyan.banquet.bean.Province;
import com.lingyan.banquet.databinding.FragmentCelStep2Binding;
import com.lingyan.banquet.event.SaveReserveSuccessEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.TextWatcherImpl;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep2;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep5;
import com.lingyan.banquet.ui.banquet.bean.NetSinglePersonList;
import com.lingyan.banquet.ui.celebration.bean.NetCelRestoreStep2;
import com.lingyan.banquet.ui.celebration.session.IntentSessionFragment;
import com.lingyan.banquet.ui.map.AMapActivity;
import com.lingyan.banquet.utils.AddressUtils;
import com.lingyan.banquet.views.dialog.PersonPickerDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 意向
 * Created by _hxb on 2020/12/13.
 */

public class CelStep2Fragment extends BaseCelStepFragment {

    private FragmentCelStep2Binding mBinding;
    private FragmentManager mFragmentManager;
    private List<IntentSessionFragment> mFragmentList;
    private ArrayList<Province> mProvincesList;
    private List<Province> options1Items = new ArrayList<>();
    private ArrayList<List<Province.City>> options2Items = new ArrayList<>();
    private List<List<List<Province.City.Area>>> options3Items = new ArrayList<>();
    private Province mProvince;
    private Province.City mCity;
    private Province.City.Area mArea;
    private NetCelRestoreStep2.DataDTO mData;
    private boolean isOpenLocationService = false;//是否打开定位功能

    public static CelStep2Fragment newInstance() {
        CelStep2Fragment fragment = new CelStep2Fragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentCelStep2Binding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //判断是否打开定位功能
        LocationManager locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            isOpenLocationService = true;
        }

        mBinding.tvPlanId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonPickerDialog dialog = new PersonPickerDialog(getActivity());
                dialog.setOnPersonClickListener(new PersonPickerDialog.OnPersonClickListener() {
                    @Override
                    public void onPersonClick(String name, String id, PersonPickerDialog dialog) {
                        mBinding.tvPlanId.setText(name);
                        mData.setPlan_id(id);
                        mData.setPlan_id_name(name);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        mBinding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (
                        mData == null ||
                                !StringUtils.equals(mData.getFinance_confirmed(), "0") ||
                                !StringUtils.equals(mData.getIs_status(), "0")

                ) {
                    ToastUtils.showShort("当前状态不可操作");
                    return;
                }

                NetCelRestoreStep2.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
                String addressDetail = linkmen.getAddress_detail();
                String address = linkmen.getAddress();
                if (StringUtils.isTrimEmpty(address)) {
                    ToastUtils.showShort("请选择客户地址");
                    return;
                }
                if (StringUtils.isTrimEmpty(addressDetail)) {
                    ToastUtils.showShort("请输入客户详细地址");
                    return;
                }
                List<NetCelRestoreStep2.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
                if (ObjectUtils.isEmpty(list)) {
                    ToastUtils.showShort("当前状态不可操作");
                    return;
                }
                for (int i = 0; i < list.size(); i++) {
                    NetCelRestoreStep2.DataDTO.BanquetNumDTO dto = list.get(i);
                    String segmentType = dto.getSegment_type();
                    String date = dto.getDate();
                    if (StringUtils.isTrimEmpty(date)) {
                        ToastUtils.showShort(String.format("请选择第%d场的场次时间", (i + 1)));
                        return;
                    }
                    if (StringUtils.isTrimEmpty(segmentType)) {
                        ToastUtils.showShort(String.format("请选择第%d场的用餐时间", (i + 1)));
                        return;
                    }

                }


                mData.setIntentionality(mBinding.rbIntentionality.getRating() + "");
                mData.setRemarks_2(mBinding.etRemarks.getText().toString().trim());
                mData.setBudget(mBinding.etBudget.getText().toString().trim());
                mData.setStep("2");
//                mData.setStatus(null);
                //保存
                OkGo.<NetBaseResp>post(HttpURLs.saveBanquetStep2)
                        .upJson(GsonUtils.toJson(mData))
                        .tag(getThisFragment())
                        .execute(new JsonCallback<NetBaseResp>() {
                            @Override
                            public void onSuccess(Response<NetBaseResp> response) {
                                NetBaseResp body = response.body();
                                String msg = body.getMsg();
                                int code = body.getCode();
                                if (code == 200) {
                                    setMaxStep(2 + 1);
                                    EventBus.getDefault().post(new SaveReserveSuccessEvent(getCelId()));
                                    getStepActivity().changeStep(3);
                                } else {
                                    ToastUtils.showShort(msg);
                                }
                            }
                        });

            }
        });
        mFragmentList = new ArrayList<>();
        mFragmentManager = getChildFragmentManager();
        mBinding.tvAddSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentSessionFragment add = add();
                NetCelRestoreStep2.DataDTO.BanquetNumDTO dto = new NetCelRestoreStep2.DataDTO.BanquetNumDTO();
                add.setData(dto);
                List<NetCelRestoreStep2.DataDTO.BanquetNumDTO> banquetNum = mData.getBanquetNum();
                banquetNum.add(dto);
                ToastUtils.showShort("长按左边场次可删除");
            }
        });
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                for (int i = 0; i < mFragmentList.size(); i++) {
                    IntentSessionFragment fragment = mFragmentList.get(i);
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

        mProvincesList = AddressUtils.parseXMLWithPull();
        mBinding.tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //若打开定位则跳转地图页
                if (isOpenLocationService) {
                    startAMapActivity();
                } else {
                    //自己选择
                    showAddressPicker();
                }
            }
        });

        if (isOpenLocationService) {
            mBinding.etAddressDetail.setFocusable(false);
            mBinding.etAddressDetail.setOnClickListener(v -> startAMapActivity());
        }

        mBinding.etAddressDetail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mData == null) return;
                NetCelRestoreStep2.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
                linkmen.setAddress_detail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        restoreDataFromNet();

    }

    private void restoreDataFromNet() {
        OkGo.<NetCelRestoreStep2>post(HttpURLs.banquetGetInfo)
                .params("id", getCelId())
                .params("step", 2)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetCelRestoreStep2>() {
                    @Override
                    public void onSuccess(Response<NetCelRestoreStep2> response) {
                        NetCelRestoreStep2 body = response.body();
                        mData = body.getData();
                        String step = mData.getStep();
                        if (ObjectUtils.isNotEmpty(step)) {
                            setMaxStep(Integer.valueOf(step));
                        }
                        refreshUI();
                    }
                });
    }

    private void refreshUI() {
        if (mData == null) {
            return;
        }
        List<NetCelRestoreStep2.DataDTO.BanquetNumDTO> banquetNum = mData.getBanquetNum();
        if (ObjectUtils.isEmpty(banquetNum)) {
            IntentSessionFragment add = add();
            NetCelRestoreStep2.DataDTO.BanquetNumDTO dto = new NetCelRestoreStep2.DataDTO.BanquetNumDTO();
            //设置默认值
            dto.setDate(StringUtils.isEmpty(dto.getDate()) ? mData.getDate() : dto.getDate());
            dto.setSegment_name(StringUtils.isEmpty(dto.getSegment_name()) ? "午餐" : dto.getSegment_name());
            dto.setSegment_type(StringUtils.isEmpty(dto.getSegment_type()) ? "1" : dto.getSegment_type());
            add.setData(dto);
            banquetNum.add(dto);
        } else {
            for (int i = 0; i < banquetNum.size(); i++) {
                NetCelRestoreStep2.DataDTO.BanquetNumDTO dto = banquetNum.get(i);
                IntentSessionFragment fragment = add();
                fragment.setData(dto);
            }
        }
        NetCelRestoreStep2.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
        mBinding.tvAddress.setText(linkmen.getAddress());
        mBinding.etAddressDetail.setText(linkmen.getAddress_detail());

        String intentionality = mData.getIntentionality();
        if (StringUtils.isTrimEmpty(intentionality)) {
            intentionality = "5";
        }
        mBinding.rbIntentionality.setRating(Float.valueOf(intentionality));
        mBinding.etRemarks.setText(mData.getRemarks_2());
        mBinding.etBudget.setText(mData.getBudget());
        mBinding.tvPlanId.setText(mData.getPlan_id_name());
    }


    private IntentSessionFragment add() {
        int tabCount = mBinding.tabLayout.getTabCount();
        tabCount++;
        TabLayout.Tab tab = mBinding.tabLayout.newTab();
        tab.setText("第" + tabCount + "场");
        mBinding.tabLayout.addTab(tab);

        IntentSessionFragment fragment = IntentSessionFragment.newInstance();
        mFragmentList.add(fragment);
        fragment.setData(null);
        mFragmentManager.beginTransaction().add(R.id.fl_session_container, fragment).commitAllowingStateLoss();

        tab.select();

        //tab的长按事件
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
                                List<NetCelRestoreStep2.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
                                list.remove(position);
                                mBinding.tabLayout.removeTab(tab);
                                mFragmentList.remove(fragment);
                                mFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();

                                for (int i = 0; i < nowCount - 1; i++) {
                                    mBinding.tabLayout.getTabAt(i).setText("第" + (i + 1) + "场");
                                }
                            }
                        })
                        .show();


                return true;
            }
        });


        return fragment;
    }


    private OptionsPickerView mPvOptions;

    private void showAddressPicker() {//条件选择器初始化，自定义布局

        if (mPvOptions == null) {
            options1Items = mProvincesList;
            ArrayList<List<Province.City>> allCityList = new ArrayList<>();
            List<List<List<Province.City.Area>>> proCityAreaList = new ArrayList<>();
            for (int i = 0; i < mProvincesList.size(); i++) {
                Province province = mProvincesList.get(i);
                List<Province.City> cities = province.getCities();
                List<List<Province.City.Area>> cityAreaList = new ArrayList<>();
                for (int j = 0; j < cities.size(); j++) {
                    Province.City city = cities.get(j);
                    List<Province.City.Area> areas = city.getAreas();
                    cityAreaList.add(areas);
                }

                proCityAreaList.add(cityAreaList);
                allCityList.add(cities);
            }

            options2Items = allCityList;
            options3Items = proCityAreaList;

            mPvOptions = new OptionsPickerBuilder(getContext(), new OnOptionsSelectListener() {
                @Override
                public void onOptionsSelect(int options1, int options2, int options3, View v) {
                    LogUtils.i(options1, options2, options3, v);
                    //返回的分别是三个级别的选中位置
                    String opt1tx = options1Items.size() > 0 ?
                            options1Items.get(options1).getName() : "";

                    String opt2tx = options2Items.size() > 0
                            && options2Items.get(options1).size() > 0 ?
                            options2Items.get(options1).get(options2).getName() : "";

                    String opt3tx = options2Items.size() > 0
                            && options3Items.get(options1).size() > 0
                            && options3Items.get(options1).get(options2).size() > 0 ?
                            options3Items.get(options1).get(options2).get(options3).getName() : "";

                    String tx = opt1tx + opt2tx + opt3tx;


                    mProvince = options1Items.get(options1);
                    mCity = options2Items.get(options1).get(options2);
                    mArea = options3Items.get(options1).get(options2).get(options3);


                    mBinding.tvAddress.setText(tx);
                    NetCelRestoreStep2.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
                    linkmen.setProvince_id(mProvince.getCode());
                    linkmen.setCity_id(mCity.getCode());
                    linkmen.setCounty_id(mArea.getCode());
                    linkmen.setAddress(tx);

                }
            }).setLayoutRes(R.layout.pickerview_custom_options, new CustomListener() {
                @Override
                public void customLayout(View v) {
                    v.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mPvOptions.dismiss();
                        }
                    });

                    v.findViewById(R.id.tv_finish).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mPvOptions.returnData();
                            mPvOptions.dismiss();
                        }
                    });
                }
            })

                    .setLineSpacingMultiplier(2f)
                    .setDividerColor(Color.parseColor("#E2E2E2"))
                    .setTextColorCenter(getResources().getColor(R.color.gold))
                    .setTextColorOut(getResources().getColor(R.color.textColorGray))
                    .build();
            Dialog mDialog = mPvOptions.getDialog();
            if (mDialog != null) {

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM);

                params.leftMargin = 0;
                params.rightMargin = 0;
                mPvOptions.getDialogContainerLayout().setLayoutParams(params);

                Window dialogWindow = mDialog.getWindow();
                if (dialogWindow != null) {
                    dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                    dialogWindow.setDimAmount(0.1f);
                }
            }
            mPvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        }

        if (mPvOptions != null && !mPvOptions.isShowing()) {
            mPvOptions.show();
        }
    }

    /**
     * 打开地图页
     */
    private void startAMapActivity() {
        Intent intent = new Intent(Utils.getApp(), AMapActivity.class);
        intent.putExtra("id", "1");
        startActivityForResult(intent, 1001);
    }

    @Override
    public boolean canLoseOrder() {
        if (mData == null) {
            return false;
        }
        if (StringUtils.equals(mData.getStatus(), "6")
                || StringUtils.equals(mData.getIs_lost(), "1")
                || StringUtils.equals(mData.getFinance_confirmed(), "1")
                || !StringUtils.equals(mData.getIs_status(), "0")
        ) {
            return false;
        }


        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            if (requestCode == 1001) {
                String addressNameBefore = data.getStringExtra("ADDRESS_BEFORE");
                String addressNameAfter = data.getStringExtra("ADDRESS_AFTER");
                String cityCode = data.getStringExtra("ADDRESS_CITY_CODE");
                String countryCode = data.getStringExtra("ADDRESS_COUNTRY_CODE");
                String lng = data.getStringExtra("ADDRESS_LNG");
                String lat = data.getStringExtra("ADDRESS_LAT");
                mBinding.tvAddress.setText(addressNameBefore);
                mBinding.etAddressDetail.setText(addressNameAfter);
                NetCelRestoreStep2.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
                linkmen.setCity_id(cityCode);
                linkmen.setCounty_id(countryCode);
                linkmen.setAddress(addressNameBefore);
                linkmen.setAddress_detail(addressNameAfter);
                linkmen.setLng(lng);
                linkmen.setLat(lat);
            }
        }
    }
}
