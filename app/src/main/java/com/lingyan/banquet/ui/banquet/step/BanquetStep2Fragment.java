package com.lingyan.banquet.ui.banquet.step;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.google.android.material.tabs.TabLayout;
import com.lingyan.banquet.R;
import com.lingyan.banquet.bean.Province;
import com.lingyan.banquet.databinding.FragmentBanquetStep2Binding;
import com.lingyan.banquet.event.SaveReserveSuccessEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep2;
import com.lingyan.banquet.ui.banquet.session.IntentSessionFragment;
import com.lingyan.banquet.ui.celebration.bean.NetCelRestoreStep4;
import com.lingyan.banquet.ui.map.AMapActivity;
import com.lingyan.banquet.utils.AddressUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ??????
 * Created by _hxb on 2020/12/13.
 */

public class BanquetStep2Fragment extends BaseBanquetStepFragment {

    private FragmentBanquetStep2Binding mBinding;
    private FragmentManager mFragmentManager;
    private List<IntentSessionFragment> mIntentSessionFragmentList;
    private ArrayList<Province> mProvincesList;
    private List<Province> options1Items = new ArrayList<>();
    private ArrayList<List<Province.City>> options2Items = new ArrayList<>();
    private List<List<List<Province.City.Area>>> options3Items = new ArrayList<>();
    private Province mProvince;
    private Province.City mCity;
    private Province.City.Area mArea;
    private NetRestoreStep2.DataDTO mData;
    private boolean isOpenLocationService = false;//????????????????????????

    public static BanquetStep2Fragment newInstance() {
        BanquetStep2Fragment fragment = new BanquetStep2Fragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentBanquetStep2Binding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //??????????????????????????????
        LocationManager locationManager = (LocationManager) Objects.requireNonNull(getActivity()).getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            isOpenLocationService = true;
        }

        mBinding.tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getStepActivity().changeStep(3, null);
//                LogUtils.i(mData);
                if (
                        mData == null ||
                                !StringUtils.equals(mData.getFinance_confirmed(), "0") ||
                                !StringUtils.equals(mData.getIs_status(), "0")

                ) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }

                List<NetRestoreStep2.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
                if (ObjectUtils.isEmpty(list)) {
                    ToastUtils.showShort("????????????????????????");
                    return;
                }
                NetRestoreStep2.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
                String addressDetail = linkmen.getAddress_detail();
                String address = linkmen.getAddress();
                if (StringUtils.isTrimEmpty(address)) {
                    ToastUtils.showShort("?????????????????????");
                    return;
                }
                if (StringUtils.isTrimEmpty(addressDetail)) {
                    ToastUtils.showShort("???????????????????????????");
                    return;
                }

                for (int i = 0; i < list.size(); i++) {
                    NetRestoreStep2.DataDTO.BanquetNumDTO dto = list.get(i);
                    String segmentType = dto.getSegment_type();
                    String date = dto.getDate();
                    if (StringUtils.isTrimEmpty(date)) {
                        ToastUtils.showShort(String.format("????????????%d??????????????????", (i + 1)));
                        return;
                    }
                    if (StringUtils.isTrimEmpty(segmentType)) {
                        ToastUtils.showShort(String.format("????????????%d??????????????????", (i + 1)));
                        return;
                    }

                }


                mData.setIntentionality(mBinding.rbIntentionality.getRating() + "");
                mData.setRemarks_2(mBinding.etRemarks.getText().toString().trim());
                mData.setStep("2");
//                mData.setStatus(null);
                LogUtils.eTag("address", GsonUtils.toJson(mData));
                //??????
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
                                    EventBus.getDefault().post(new SaveReserveSuccessEvent(getBanquetId()));
                                    getStepActivity().changeStep(3);
                                } else {
                                    ToastUtils.showShort(msg);
                                }
                            }
                        });

            }
        });
        mIntentSessionFragmentList = new ArrayList<>();
        mFragmentManager = getChildFragmentManager();
        mBinding.tvAddSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentSessionFragment add = add();
                NetRestoreStep2.DataDTO.BanquetNumDTO dto = new NetRestoreStep2.DataDTO.BanquetNumDTO();
                add.setData(dto);
                List<NetRestoreStep2.DataDTO.BanquetNumDTO> banquetNum = mData.getBanquetNum();
                banquetNum.add(dto);
                ToastUtils.showShort("???????????????????????????");
            }
        });
        mBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                for (int i = 0; i < mIntentSessionFragmentList.size(); i++) {
                    IntentSessionFragment fragment = mIntentSessionFragmentList.get(i);
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
        mBinding.tvAddress.setOnClickListener(v -> {
            //?????????????????????????????????
            if (isOpenLocationService) {
                startAMapActivity();
            } else {
                //????????????
                showAddressPicker();
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
                NetRestoreStep2.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
                linkmen.setAddress_detail(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        restoreDataFromNet();

    }

    /**
     * ???????????????
     */
    private void startAMapActivity() {
        Intent intent = new Intent(Utils.getApp(), AMapActivity.class);
        NetRestoreStep2.DataDTO.LinkmenDTO linkmenDTO = mData.getLinkmen();
        if (linkmenDTO != null) {
            if (!StringUtils.isTrimEmpty(linkmenDTO.getLng()))
                intent.putExtra("lng", Double.parseDouble(mData.getLinkmen().getLng()));
            if (!StringUtils.isTrimEmpty(linkmenDTO.getLat()))
                intent.putExtra("lat", Double.parseDouble(mData.getLinkmen().getLat()));
        }
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

    private void restoreDataFromNet() {
        OkGo.<NetRestoreStep2>post(HttpURLs.banquetGetInfo)
                .params("id", getBanquetId())
                .params("step", 2)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetRestoreStep2>() {
                    @Override
                    public void onSuccess(Response<NetRestoreStep2> response) {
                        NetRestoreStep2 body = response.body();
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

        mBinding.tabLayout.removeAllTabs();
        if (ObjectUtils.isNotEmpty(mIntentSessionFragmentList)) {
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            for (Fragment fragment : mIntentSessionFragmentList) {
                transaction.remove(fragment);
            }
            transaction.commitAllowingStateLoss();
            mIntentSessionFragmentList.clear();
        }

        List<NetRestoreStep2.DataDTO.BanquetNumDTO> banquetNum = mData.getBanquetNum();
        if (ObjectUtils.isEmpty(banquetNum)) {
            IntentSessionFragment add = add();
            NetRestoreStep2.DataDTO.BanquetNumDTO dto = new NetRestoreStep2.DataDTO.BanquetNumDTO();
            //???????????????
            dto.setDate(StringUtils.isEmpty(dto.getDate()) ? mData.getDate() : dto.getDate());
            dto.setSegment_name(StringUtils.isEmpty(dto.getSegment_name()) ? "??????" : dto.getSegment_name());
            dto.setSegment_type(StringUtils.isEmpty(dto.getSegment_type()) ? "1" : dto.getSegment_type());
            add.setData(dto);
            banquetNum.add(dto);
        } else {
            for (int i = 0; i < banquetNum.size(); i++) {
                NetRestoreStep2.DataDTO.BanquetNumDTO dto = banquetNum.get(i);
                IntentSessionFragment fragment = add();
                fragment.setData(dto);
            }
        }
        NetRestoreStep2.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
        mBinding.tvAddress.setText(linkmen.getAddress());
        mBinding.etAddressDetail.setText(linkmen.getAddress_detail());

        String intentionality = mData.getIntentionality();
        if (StringUtils.isTrimEmpty(intentionality)) {
            intentionality = "5";
        }
        mBinding.rbIntentionality.setRating(Float.valueOf(intentionality));
        mBinding.etRemarks.setText(mData.getRemarks_2());
    }


    private IntentSessionFragment add() {
        int tabCount = mBinding.tabLayout.getTabCount();
        tabCount++;
        TabLayout.Tab tab = mBinding.tabLayout.newTab();
        tab.setText("???" + tabCount + "???");
        mBinding.tabLayout.addTab(tab);

        IntentSessionFragment fragment = IntentSessionFragment.newInstance();
        mIntentSessionFragmentList.add(fragment);
        fragment.setData(null);
        mFragmentManager.beginTransaction().add(R.id.fl_session_container, fragment).commitAllowingStateLoss();

        tab.select();

        //tab???????????????
        tab.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int nowCount = mBinding.tabLayout.getTabCount();
                if (nowCount <= 1) {
                    return false;
                }
                new MaterialDialog.Builder(getContext())
                        .title("??????")
                        .content("??????????????????????")
                        .positiveText("??????")
                        .negativeText("??????")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                int position = tab.getPosition();
                                List<NetRestoreStep2.DataDTO.BanquetNumDTO> list = mData.getBanquetNum();
                                list.remove(position);
                                mBinding.tabLayout.removeTab(tab);
                                mIntentSessionFragmentList.remove(fragment);
                                mFragmentManager.beginTransaction().remove(fragment).commitAllowingStateLoss();

                                for (int i = 0; i < nowCount - 1; i++) {
                                    mBinding.tabLayout.getTabAt(i).setText("???" + (i + 1) + "???");
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


    private OptionsPickerView mPvOptions;

    private void showAddressPicker() {//??????????????????????????????????????????

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
                    //?????????????????????????????????????????????
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
                    NetRestoreStep2.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
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
                    dialogWindow.setGravity(Gravity.BOTTOM);//??????Bottom,????????????
                    dialogWindow.setDimAmount(0.1f);
                }
            }
            mPvOptions.setPicker(options1Items, options2Items, options3Items);//???????????????
        }

        if (mPvOptions != null && !mPvOptions.isShowing()) {
            mPvOptions.show();
        }
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
                NetRestoreStep2.DataDTO.LinkmenDTO linkmen = mData.getLinkmen();
                linkmen.setCity_id(cityCode);
                linkmen.setCounty_id(countryCode);
                linkmen.setAddress(addressNameBefore);
                linkmen.setAddress_detail(addressNameAfter);
                linkmen.setLng(lng);
                linkmen.setLat(lat);
                linkmen.setProvince_id("");
            }
        }
    }
}
