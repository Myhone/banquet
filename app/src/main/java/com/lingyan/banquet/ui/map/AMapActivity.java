package com.lingyan.banquet.ui.map;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lingyan.banquet.App;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityAMapBinding;
import com.lingyan.banquet.utils.AMapUtil;
import com.lingyan.banquet.views.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 高德地图页面
 * Created by wyq on 2021/9/11.
 */
public class AMapActivity extends BaseActivity implements AMap.OnPOIClickListener, PoiSearch.OnPoiSearchListener,
        GeocodeSearch.OnGeocodeSearchListener, Inputtips.InputtipsListener, TextWatcher, AMap.OnCameraChangeListener {

    private ActivityAMapBinding mBinding;
    private int cameraChangeCount = 0;
    private AMapAdapter mAdapter;

    private AMap aMap;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    private GeocodeSearch geocoderSearch = null;
    private LatLonPoint latLonPoint;
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiSearch.Query query;// Poi查询条件类
    private Marker screenMarker = null;//大头针
    private String addressName, countryCode, cityCode, addressNameBefore, addressNameAfter;
    private boolean isClickSearch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityAMapBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        Intent intent = getIntent();
        double lng = intent.getDoubleExtra("lng", 0);
        double lat = intent.getDoubleExtra("lat", 0);
        if (lng != 0 || lat != 0) {
            latLonPoint = new LatLonPoint(lat, lng);
        }


        mBinding.layoutBar.tvTitleBarTitle.setText("选择地址");
        mBinding.layoutBar.ivBack.setOnClickListener(v -> clickBtnBack());
        mBinding.etAddressDetail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == 0 || actionId == 3) && event != null) {
                    clickSearchBtn();
                }
                return false;
            }
        });

        mBinding.btnSearch.setOnClickListener(v -> clickSearchBtn());

        mBinding.etAddressDetail.addTextChangedListener(this);
        initRecycleView();
        //高德地图，必须重写该方法
        mBinding.map.onCreate(savedInstanceState);
        initMap();

    }

    /**
     * 点击搜索按钮
     */
    private void clickSearchBtn() {
        String searchContent = mBinding.etAddressDetail.getText().toString();
        if (StringUtils.isTrimEmpty(searchContent)) return;
        isClickSearch = true;
        doPoiSearch(0, searchContent);
        mBinding.lvSearchList.setVisibility(View.GONE);
        KeyboardUtils.hideSoftInput(AMapActivity.this);
    }

    /**
     * 初始化高德地图
     */
    private void initMap() {
        if (aMap == null) {
            aMap = mBinding.map.getMap();
        }

        //初始化逆地理编码搜索
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

        //设置定位蓝点样式
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(2);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.setOnPOIClickListener(this);

        //地图加载完成后添加大头针
        aMap.addOnMapLoadedListener(this::addMarkerInScreenCenter);

        //初始化定位
//        AMapLocationClient mLocationClient = new AMapLocationClient(App.sApp);
//        AMapLocationClientOption mLocationClientOption = new AMapLocationClientOption();
//        //设置为高精度定位模式
//        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
//        //设置定位参数
//        mLocationClient.setLocationOption(mLocationClientOption);
//        //启动定位
//        mLocationClient.startLocation();

        // 设置可视范围变化时的回调的接口方法
        aMap.setOnCameraChangeListener(this);
    }

    /**
     * 屏幕里地图变化 OnCameraChangeListener
     *
     * @param cameraPosition;
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
    }

    /**
     * 屏幕里地图变化 OnCameraChangeListener
     *
     * @param cameraPosition;
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        cameraChangeCount += 1;
        if (cameraChangeCount == 1 && latLonPoint != null) {

        } else {
            double latitude = cameraPosition.target.latitude;
            double longitude = cameraPosition.target.longitude;
            latLonPoint = new LatLonPoint(latitude, longitude);
        }
        //根据经纬度搜索地址
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置异步逆地理编码请求
        //大头针跳动
        startJumpAnimation();
    }

    /**
     * 逆地理编码查询 OnGeocodeSearchListener
     *
     * @param result;
     * @param rCode;
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                RegeocodeAddress address = result.getRegeocodeAddress();
                countryCode = address.getCountryCode();
                cityCode = address.getCityCode();
                addressName = address.getFormatAddress();
                addressNameBefore = address.getProvince() + address.getCity() + address.getDistrict();
                addressNameAfter = addressName.replace(addressNameBefore, "");
                if (cameraChangeCount == 1) {
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            AMapUtil.convertToLatLng(latLonPoint), 16));
                }
                //查询大头针周边poi
                doPoiSearch(1, addressNameAfter);
            } else {
                ToastUtils.showShort("无结果");
            }
        } else {
            ToastUtils.showShort("无结果: " + rCode);
        }
    }

    /**
     * 逆地理编码查询 OnGeocodeSearchListener
     *
     * @param result;
     * @param rCode;
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
    }

    /**
     * 开始进行poi搜索
     *
     * @param type    : 0 ->相关poi； 1 ->周边poi
     * @param keyWord : 关键词
     */
    protected void doPoiSearch(int type, String keyWord) {
        currentPage = 0;
        if (type == 0) {
            query = new PoiSearch.Query(keyWord, "", cityCode);// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        } else {
            query = new PoiSearch.Query("", "汽车服务|汽车销售|汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|住宿服务|风景名胜|商务住宅|政府机构及社 会团体|科教文化服务|交通设施服务|金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施", cityCode);
        }
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页

        // POI搜索
        PoiSearch poiSearch = new PoiSearch(getActivity(), query);
        poiSearch.setOnPoiSearchListener(this);
        if (type == 1) {
            poiSearch.setBound(new PoiSearch.SearchBound(latLonPoint, 500));//设置周边搜索的中心点以及半径
        }
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onPOIClick(Poi poi) {
        double latitude = poi.getCoordinate().latitude;
        double longitude = poi.getCoordinate().longitude;
        latLonPoint = new LatLonPoint(latitude, longitude);
        //根据经纬度搜索地址
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                AMapUtil.convertToLatLng(latLonPoint), 16));
    }

    @Override
    public void onPoiSearched(PoiResult result, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    // 取得搜索到的poiItems有多少页
                    ArrayList<PoiItem> poiItems = result.getPois();// 取得第一页的poiItem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = result
                            .getSearchSuggestionCitys();// 当搜索不到poiItem数据时，会返回含有搜索关键字的城市信息

                    if (poiItems != null && poiItems.size() > 0) {
                        //如果点击的搜索按钮，则默认定位到第一条数据的地址
                        if (isClickSearch) {
                            PoiItem item = poiItems.get(0);
                            latLonPoint = item.getLatLonPoint();
                            aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    AMapUtil.convertToLatLng(latLonPoint), 16));
                        }

                        //显示到屏幕下方recyclerview
                        mAdapter.setNewData(poiItems);
                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        ToastUtils.showShort("无数据");
                    }

                }
            } else {
                ToastUtils.showShort("无结果");

            }
        } else {
            ToastUtils.showShort("无结果: " + rCode);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
    }

    /**
     * 输入提示 InputtipsListener
     *
     * @param tipList;
     * @param rCode;
     */
    @Override
    public void onGetInputtips(List<Tip> tipList, int rCode) {
        if (rCode == AMapException.CODE_AMAP_SUCCESS && mBinding.etAddressDetail.getText().length() > 0) {
            List<HashMap<String, String>> listString = new ArrayList<HashMap<String, String>>();
            if (tipList != null) {
                int size = tipList.size();
                for (int i = 0; i < size; i++) {
                    Tip tip = tipList.get(i);
                    if (tip != null) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("name", tipList.get(i).getName());
                        map.put("address", tipList.get(i).getDistrict());
                        listString.add(map);
                    }
                }

                SimpleAdapter aAdapter = new SimpleAdapter(getApplicationContext(), listString, R.layout.item_map_search,
                        new String[]{"name", "address"}, new int[]{R.id.poi_field_id, R.id.poi_value_id});

                mBinding.lvSearchList.setVisibility(View.VISIBLE);
                mBinding.lvSearchList.setAdapter(aAdapter);
                mBinding.lvSearchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        HashMap<String, String> map = (HashMap<String, String>) aAdapter.getItem(position);
                        String addressName = map.get("name");
                        mBinding.etAddressDetail.removeTextChangedListener(AMapActivity.this);
                        mBinding.etAddressDetail.setText(addressName);
                        mBinding.lvSearchList.setVisibility(View.GONE);
                        mBinding.etAddressDetail.addTextChangedListener(AMapActivity.this);

                        latLonPoint = tipList.get(position).getPoint();
                        //根据经纬度搜索地址
                        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                AMapUtil.convertToLatLng(latLonPoint), 16));
                        doPoiSearch(0, addressName);

                        //收起键盘
                        KeyboardUtils.hideSoftInput(AMapActivity.this);
                    }
                });
                aAdapter.notifyDataSetChanged();
            }
        } else {
            mBinding.lvSearchList.setVisibility(View.GONE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        if (newText.length() < 1) {
            mBinding.lvSearchList.setVisibility(View.GONE);
            return;
        }
        InputtipsQuery inputquery = new InputtipsQuery(newText, cityCode);
        inputquery.setCityLimit(true);
        Inputtips inputTips = new Inputtips(AMapActivity.this, inputquery);
        inputTips.setInputtipsListener(this);
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void initRecycleView() {
        mBinding.rvLocation.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvLocation.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mAdapter = new AMapAdapter();
        mBinding.rvLocation.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                PoiItem address = (PoiItem) baseQuickAdapter.getData().get(i);
                latLonPoint = address.getLatLonPoint();
                addressNameBefore = address.getProvinceName() + address.getCityName() + address.getAdName();
                addressNameAfter = address.getTitle();
                clickBtnBack();
            }
        });
    }

    /**
     * 在屏幕中心添加一个Marker(大头针)
     */
    private void addMarkerInScreenCenter() {
        LatLng latLng = aMap.getCameraPosition().target;
        Point screenPosition = aMap.getProjection().toScreenLocation(latLng);
        screenMarker = aMap.addMarker(new MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)));
        //设置Marker在屏幕上,不跟随地图移动
        screenMarker.setPositionByPixels(screenPosition.x, screenPosition.y);
    }

    /**
     * 大头针跳动
     */
    public void startJumpAnimation() {

        if (screenMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = screenMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= dip2px(Objects.requireNonNull(getActivity()), 125);
            LatLng target = aMap.getProjection()
                    .fromScreenLocation(point);
            //使用TranslateAnimation,填写一个需要移动的目标点
            Animation animation = new TranslateAnimation(target);
            animation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            animation.setDuration(600);
            //设置动画
            screenMarker.setAnimation(animation);
            //开始动画
            screenMarker.startAnimation();

        } else {
            Log.e("amap", "screenMarker is null");
        }
    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        ToastUtils.showShort("infomation:" + infomation);

    }

    /**
     * 返回上一个页面传递参数
     */
    private void clickBtnBack() {
        if (!StringUtils.isEmpty(addressNameBefore)) {
            Intent intent = new Intent();
            intent.putExtra("ADDRESS_BEFORE", addressNameBefore);
            intent.putExtra("ADDRESS_AFTER", addressNameAfter);
            intent.putExtra("ADDRESS_CITY_CODE", cityCode);
            intent.putExtra("ADDRESS_COUNTRY_CODE", countryCode);
            intent.putExtra("ADDRESS_LNG", String.valueOf(latLonPoint.getLongitude()));
            intent.putExtra("ADDRESS_LAT", String.valueOf(latLonPoint.getLatitude()));
            setResult(200, intent);
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBinding.map.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBinding.map.onPause();
    }

    @Override
    public void onBackPressed() {
        clickBtnBack();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        mBinding.map.onDestroy();
        super.onDestroy();
    }

    //dip和px转换
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
