package com.lingyan.banquet.ui.main;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarUtil;
import com.haibin.calendarview.CalendarView;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.FragmentMainHomeBinding;
import com.lingyan.banquet.databinding.LayoutHomeHallBinding;
import com.lingyan.banquet.event.LoginEvent;
import com.lingyan.banquet.event.LogoutEvent;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.global.Router;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetChildHall;
import com.lingyan.banquet.ui.main.bean.HomeBackgroundImgBean;
import com.lingyan.banquet.ui.main.bean.HomeCalenderScheme;
import com.lingyan.banquet.ui.main.bean.NetHomeBottomHallData;
import com.lingyan.banquet.ui.main.bean.NetHomeTab;
import com.lingyan.banquet.ui.main.bean.NetHomeTabData;
import com.lingyan.banquet.ui.main.bean.NetMonthData;
import com.lingyan.banquet.ui.order.OrderListActivity;
import com.lingyan.banquet.ui.main.adapter.TabAdapter;
import com.lingyan.banquet.ui.main.bean.TabBean;
import com.lingyan.banquet.ui.order.bean.OrderFilterCondition;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.views.BanquetMonthView;
import com.lingyan.banquet.views.StatisticsBarView;
import com.lingyan.banquet.views.dialog.PickerListDialog;
import com.lingyan.banquet.views.dialog.TwoLineTabSelectDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by _hxb on 2021/1/1.
 */

public class HomeFragment extends BaseFragment implements OnRefreshListener {

    private List<TabBean> mTabRecData;
    private TabAdapter mAdapter;
    private FragmentMainHomeBinding mBinding;
    private List<NetBanquetChildHall.DataDTO> mHallList;
    //1-宴会 2-庆典
    private int mType = 1;
    //宴会厅id
    private String mHallId;
    private String mHallName;
    //请求接口yyyy-MM
    private String mMonthDate;
    private Calendar mLastClickCalendar;
    //1-宴会 2-庆典
    private int mTabType = 1;


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentMainHomeBinding.inflate(inflater);
        mBinding.flStBarRoot.llCompleteContainer.setVisibility(View.GONE);
        mBinding.flStBarRoot.tvLockTitle.setText("待跟进");
        mBinding.flStBarRoot.tvSignTitle.setText("待结算");
        mBinding.flStBarRoot.tvExecTitle.setText("待处理");
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        EventBus.getDefault().register(this);
        mBinding.flTabBanquetContaienr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.viewBanquetIndicator.setVisibility(View.VISIBLE);
                mBinding.viewCelIndicator.setVisibility(View.INVISIBLE);
                mBinding.ivBanquetIcon.setImageResource(R.mipmap.ic_main_home_banquet_selected);
                mBinding.ivCelIcon.setImageResource(R.mipmap.ic_main_home_cel_unselected);
                mTabType = 1;
                //选择宴会
                getTabData();
            }
        });
        mBinding.flTabCelConatiner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBinding.viewBanquetIndicator.setVisibility(View.INVISIBLE);
                mBinding.viewCelIndicator.setVisibility(View.VISIBLE);
                mBinding.ivBanquetIcon.setImageResource(R.mipmap.ic_main_home_banquet_unselected);
                mBinding.ivCelIcon.setImageResource(R.mipmap.ic_main_home_cel_selected);
                //选择庆典
                mTabType = 2;
                getTabData();
            }
        });

        mTabRecData = new ArrayList<>();
        mAdapter = new TabAdapter(mTabRecData);
        mBinding.recyclerViewTab.setLayoutManager(new GridLayoutManager(getContext(), 4));
        mBinding.recyclerViewTab.setAdapter(mAdapter);
        mBinding.recyclerViewTab.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ConvertUtils.dp2px(18);
                int position = parent.getChildLayoutPosition(view);
                if (position > 3) {
                    outRect.bottom = ConvertUtils.dp2px(18);
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                TabBean tabBean = mTabRecData.get(position);
                NetHomeTab.DataDTO dto = (NetHomeTab.DataDTO) tabBean.getObject();
                if (dto == null) {
                    return;
                }
                Router.navigation(dto.getComponent());
            }
        });

        mBinding.tvNowYearMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        mMonthDate = TimeUtils.date2String(date, "yyyy-MM");
                        mBinding.calenderView.scrollToCalendar(date.getYear() + 1900, date.getMonth() + 1, 1);
                        mBinding.calenderView.clearSingleSelect();
                    }
                })
                        .setTitleText("请选择开始时间")
                        .setItemVisibleCount(12)
                        .setType(new boolean[]{true, true, false, false, false, false})
                        .build()
                        .show();
            }
        });

        mBinding.calenderView.setMonthView(BanquetMonthView.class);
        int itemHeight = (int) (ScreenUtils.getAppScreenWidth() / 7f);
        int curYear = mBinding.calenderView.getCurYear();
        int curMonth = mBinding.calenderView.getCurMonth();
        //1代表星期日
        int tY = itemHeight * 6 - CalendarUtil.getMonthViewHeight(curYear,
                curMonth, itemHeight, 1);
        mBinding.llBottomViewContainer.setTranslationY(-tY);


        mBinding.calenderView.setMonthViewScrollable(false);
        mBinding.calenderView.setCalendarItemHeight(itemHeight);
        mBinding.calenderView.setOnMonthChangeListener(new CalendarView.OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                LogUtils.i("onMonthChange");
                mMonthDate = year + "-" + (month < 10 ? "0" + month : month);
                getMonthData();
                getBottomHallData();

                //1代表星期日
                int tY = itemHeight * 6 - CalendarUtil.getMonthViewHeight(year,
                        month, itemHeight, 1);

                ObjectAnimator.ofFloat(mBinding.llBottomViewContainer, "translationY", -tY).start();

            }
        });
        mBinding.calenderView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {

                int year = calendar.getYear();
                int month = calendar.getMonth();

                if ((isClick && mLastClickCalendar == null) || (isClick && mLastClickCalendar != null && mLastClickCalendar.compareTo(calendar) != 0)) {
                    //第一次点击
                    int day = calendar.getDay();
                    mMonthDate = year + "-" + (month < 10 ? "0" + month : month) + "-" + (day < 10 ? "0" + day : day);
                    mLastClickCalendar = calendar;
                } else {
                    String monthDate = year + "-" + (month < 10 ? "0" + month : month);
                    if (isClick) {
                        //取消了当前选择
                        mLastClickCalendar = null;
                        mBinding.calenderView.clearSingleSelect();
                    }
                    mMonthDate = monthDate;

                }

                getMonthData();
                getBottomHallData();
            }
        });
        //segment_type 1午餐  2晚餐

        //将公历转成农历
//        String lunarText = LunarCalendar.getLunarText(2020, 1, 1);
//        LogUtils.i(lunarText);
        mBinding.calenderView.setDefaultMonthViewSelectDay();
//        mBinding.calenderView.setSelectSingleMode();
//        mBinding.calenderView.setSelectDefaultMode();
        mBinding.calenderView.clearSingleSelect();

        mBinding.tvBanquetCelebrationType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = new ArrayList<>();
                list.add("宴会");
                list.add("庆典");
                new PickerListDialog(getActivity())
                        .items(list)
                        .itemSelectedCallBack(new PickerListDialog.ItemSelectedCallBack() {
                            @Override
                            public void onItemSelected(int position, String text, PickerListDialog dialog) {
                                mType = position + 1;
                                getMonthData();
                                getBottomHallData();
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        mBinding.tvHallName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkGo.<NetBanquetChildHall>post(HttpURLs.listBanquetHall)
                        .params("s_type", 0)
                        .params("type", mType)
                        .tag(getThisFragment())
                        .execute(new JsonCallback<NetBanquetChildHall>() {
                            @Override
                            public void onSuccess(Response<NetBanquetChildHall> response) {
                                NetBanquetChildHall body = response.body();
                                if (body == null) {
                                    return;
                                }
                                mHallList = body.getData();
                                showSelectHallDialog();
                            }
                        });
            }
        });


        mBinding.refreshLayout.setOnRefreshListener(this);
        onRefresh(mBinding.refreshLayout);
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        getTabData();
        OkGo.<NetHomeTab>post(HttpURLs.homeTab)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetHomeTab>() {
                    @Override
                    public void onSuccess(Response<NetHomeTab> response) {
                        NetHomeTab body = response.body();
                        if (body == null) {
                            return;
                        }
                        mAdapter.setNewData(mTabRecData);
                        List<NetHomeTab.DataDTO> list = body.getData();
                        if (ObjectUtils.isEmpty(list)) {
                            return;
                        }
                        mTabRecData.clear();
                        for (NetHomeTab.DataDTO dataDTO : list) {
                            TabBean tabBean = new TabBean();
                            tabBean.setName(dataDTO.getTitle());
                            tabBean.setImageUrl(dataDTO.getApp_icon());
                            tabBean.setObject(dataDTO);
                            mAdapter.addData(tabBean);
                        }

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        refreshLayout.finishRefresh();
                    }
                });

        if (ObjectUtils.isEmpty(mHallList)) {
            OkGo.<NetBanquetChildHall>post(HttpURLs.listBanquetHall)
                    .params("s_type", 0)
                    .params("type", mType)
                    .tag(getThisFragment())
                    .execute(new JsonCallback<NetBanquetChildHall>() {
                        @Override
                        public void onSuccess(Response<NetBanquetChildHall> response) {
                            NetBanquetChildHall body = response.body();
                            if (body == null) {
                                return;
                            }
                            mHallList = body.getData();

                            if (ObjectUtils.isNotEmpty(mHallList)) {
                                NetBanquetChildHall.DataDTO dto = mHallList.get(0);
                                List<NetBanquetChildHall.DataDTO.ChildrenDTO> children = dto.getChildren();
                                if (ObjectUtils.isNotEmpty(children)) {
                                    NetBanquetChildHall.DataDTO.ChildrenDTO childrenDTO = children.get(0);
                                    mHallId = childrenDTO.getId();
                                    mHallName = childrenDTO.getName();
                                    mMonthDate = TimeUtils.getNowString(new SimpleDateFormat("yyyy-MM"));
                                    getMonthData();
                                    getBottomHallData();
                                }
                            }

                        }
                    });
        } else {
            getMonthData();
            getBottomHallData();
        }

        getHomeBackgroundImg();
    }

    //获取首页顶部背景图
    private void getHomeBackgroundImg() {
        OkGo.<HomeBackgroundImgBean>post(HttpURLs.homeBackgroundImg)
                .tag(getThisFragment())
                .execute(new JsonCallback<HomeBackgroundImgBean>() {
                    @Override
                    public void onSuccess(Response<HomeBackgroundImgBean> response) {
                        HomeBackgroundImgBean body = response.body();
                        if (body == null) {
                            return;
                        }

                        if (body.getData() != null && !StringUtils.isEmpty(body.getData().getBackground_url())) {
                            MyImageUtils.display(mBinding.ivBgTop, body.getData().getBackground_url(), R.color.gold, R.color.white);
                        }
                    }
                });
    }

    private void getTabData() {
        OkGo.<NetHomeTabData>post(HttpURLs.indexIntentCount)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetHomeTabData>() {
                    @Override
                    public void onSuccess(Response<NetHomeTabData> response) {
                        NetHomeTabData body = response.body();
                        if (body == null) {
                            return;
                        }
                        NetHomeTabData.DataBean data = body.getData();
                        if (data == null) {
                            return;
                        }
                        NetHomeTabData.DataBean.YhBean yh = data.getYh();
                        NetHomeTabData.DataBean.QdBean qd = data.getQd();
                        if (yh == null || qd == null) {
                            return;
                        }

                        if (mTabType == 1) {
                            showCount(yh.getData1().getCount(), yh.getData2().getCount(), yh.getData11().getCount(), yh.getData12().getCount(), yh.getData13().getCount());
                            setClickListener(yh.getData1(), yh.getData2(), yh.getData11(), yh.getData12(), yh.getData13());
                        } else {
                            showCount(qd.getData1().getCount(), qd.getData2().getCount(), qd.getData11().getCount(), qd.getData12().getCount(), qd.getData13().getCount());
                            setClickListener(qd.getData1(), qd.getData2(), qd.getData11(), qd.getData12(), qd.getData13());
                        }
                    }
                });
    }

    private void showCount(String count1, String count2, String count11, String count12, String count13) {
        mBinding.flStBarRoot.tvChance.setText(count1);
        mBinding.flStBarRoot.tvIntent.setText(count2);
        mBinding.flStBarRoot.tvLock.setText(count11);
        mBinding.flStBarRoot.tvSign.setText(count12);
        mBinding.flStBarRoot.tvExec.setText(count13);
    }

    private void setClickListener(NetHomeTabData.DataBean.DataChildBean info1, NetHomeTabData.DataBean.DataChildBean info2, NetHomeTabData.DataBean.DataChildBean info11, NetHomeTabData.DataBean.DataChildBean info12, NetHomeTabData.DataBean.DataChildBean info13) {
        mBinding.flStBarRoot.llChanceContainer.setOnClickListener(new TabClick("1", info1));
        mBinding.flStBarRoot.llIntentContainer.setOnClickListener(new TabClick("2", info2));
        mBinding.flStBarRoot.llLockContainer.setOnClickListener(new TabClick("11", info11));
        mBinding.flStBarRoot.llSignContainer.setOnClickListener(new TabClick("12", info12));
        mBinding.flStBarRoot.llExecContainer.setOnClickListener(new TabClick("13", info13));
    }

    private class TabClick implements View.OnClickListener {
        private final String mStatus;
        private final NetHomeTabData.DataBean.DataChildBean mInfo;

        public TabClick(String status, NetHomeTabData.DataBean.DataChildBean info) {
            mStatus = status;
            mInfo = info;
        }

        @Override
        public void onClick(View v) {
            if (StringUtils.isEmpty(mInfo.getCount()) || "0".equals(mInfo.getCount())) {
                return;
            }
            OrderFilterCondition condition = new OrderFilterCondition();
            condition.status = mStatus;
            condition.type = mTabType;
            condition.intent_man_id = mInfo.getIntent_man_id();
            condition.number_date = mInfo.getNumber_date();
            condition.sx_intent_man_id = mInfo.getSx_intent_man_id();
            OrderListActivity.start(0, GsonUtils.toJson(condition), GsonUtils.toJson(condition));
        }
    }

    private void getBottomHallData() {
        OkGo.<NetHomeBottomHallData>post(HttpURLs.indexHallIndex)
                .params("date", mMonthDate)
                .params("type", mType)
                .tag(getThisFragment())
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new JsonCallback<NetHomeBottomHallData>() {
                    @Override
                    public void onSuccess(Response<NetHomeBottomHallData> response) {
                        NetHomeBottomHallData body = response.body();
                        List<NetHomeBottomHallData.DataDTO> list = body.getData();
                        if (ObjectUtils.isEmpty(list)) {
                            return;
                        }
                        int size = list.size();
                        int childCount = mBinding.llBottomHallContainer.getChildCount();
                        if (size > childCount) {
                            int d = size - childCount;
                            while (d > 0) {
                                d--;

                                LayoutHomeHallBinding inflate = LayoutHomeHallBinding.inflate(getLayoutInflater());
                                FrameLayout root = inflate.getRoot();
                                root.setTag(R.layout.layout_home_hall, inflate);
                                mBinding.llBottomHallContainer.addView(root);
                            }
                        } else if (size < childCount) {
                            int d = childCount - size;
                            while (d > 0) {
                                d--;
                                mBinding.llBottomHallContainer.removeViewAt(0);
                            }
                        }
                        for (int i = 0; i < list.size(); i++) {
                            NetHomeBottomHallData.DataDTO dataDTO = list.get(i);
                            View view = mBinding.llBottomHallContainer.getChildAt(i);
                            LayoutHomeHallBinding inflate = (LayoutHomeHallBinding) view.getTag(R.layout.layout_home_hall);
                            String name = dataDTO.getName();
                            NetHomeBottomHallData.DataDTO.InfoDTO info = dataDTO.getInfo();
                            inflate.tvName.setText(name);
                            inflate.sbv.setData(info.getChance(), info.getIntent(), info.getLock(),
                                    info.getSign(), info.getExec(), info.getComplete());
                            inflate.sbv.setChanceViewVisibility(View.GONE);

                            setSbvCondition(inflate.sbv, dataDTO.getId(), name);
                        }


                    }
                });
    }

    private void getMonthData() {
        //1-宴会 2-庆典
        mBinding.tvBanquetCelebrationType.setText(mType == 1 ? "宴会" : "庆典");
        if (!StringUtils.isEmpty(mMonthDate)) {
            Date date = TimeUtils.string2Date(mMonthDate, mMonthDate.length() > 7 ? "yyyy-MM-dd" : "yyyy-MM");
            mBinding.tvNowYearMonth.setText(TimeUtils.date2String(date, "yyyy年MM月"));
        }

        mBinding.tvHallName.setText(mHallName);

        String date = mMonthDate;
        if (date.length() > 7) {
            date = date.substring(0, 7);
        }

        //这个接口刷新日历
        OkGo.<NetMonthData>post(HttpURLs.monthIndex)
                .params("date", date)
                .params("type", mType)
                .params("hall_id", mHallId)
                .tag(getThisFragment())
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new JsonCallback<NetMonthData>() {
                    @Override
                    public void onSuccess(Response<NetMonthData> response) {
                        NetMonthData body = response.body();
                        NetMonthData.DataDTO data = body.getData();
                        if (data == null) {
                            return;
                        }

                        List<NetMonthData.DataDTO.BanquetlistDTO> banquetlist = data.getBanquetlist();
                        HashMap<String, HomeCalenderScheme> map = new HashMap<>();

                        if (ObjectUtils.isNotEmpty(banquetlist)) {
                            for (NetMonthData.DataDTO.BanquetlistDTO dto : banquetlist) {
                                String date = dto.getDate();
                                String segmentType = dto.getSegment_type();
                                String count = dto.getCount();
                                HomeCalenderScheme scheme = map.get(date);
                                if (scheme == null) {
                                    scheme = new HomeCalenderScheme();
                                    map.put(date, scheme);
                                }
                                if (StringUtils.equals(segmentType, "1")) {
                                    //午餐
                                    scheme.lunchCount = count;
                                } else if (StringUtils.equals(segmentType, "2")) {
                                    //晚餐
                                    scheme.dinnerCount = count;
                                }
                            }
                        }

                        List<NetMonthData.DataDTO.GooddayListDTO> gooddayList = data.getGooddayList();
                        if (ObjectUtils.isNotEmpty(gooddayList)) {
                            for (NetMonthData.DataDTO.GooddayListDTO dto : gooddayList) {
                                String date = dto.getDate();
                                String color = dto.getColor();
                                HomeCalenderScheme scheme = map.get(date);
                                if (scheme == null) {
                                    scheme = new HomeCalenderScheme();
                                    map.put(date, scheme);
                                }
                                scheme.isGoodDay = true;
                                if (!StringUtils.isTrimEmpty(color)) {
                                    if (color.length() == 4) {
                                        String r = color.substring(1, 2);
                                        String g = color.substring(2, 3);
                                        String b = color.substring(3, 4);
                                        scheme.goodDayColor = "#" + r + r + g + g + b + b;
                                    } else if (color.length() == 7) {
                                        scheme.goodDayColor = color;
                                    }
                                }
                            }
                        }
                        if (map.size() == 0) {
                            return;
                        }
                        Map<String, Calendar> schemeMap = new HashMap<>();
                        Set<Map.Entry<String, HomeCalenderScheme>> entrySet = map.entrySet();
//                        LogUtils.i("------------------------------");
                        for (Map.Entry<String, HomeCalenderScheme> entry : entrySet) {
                            String key = entry.getKey();
                            HomeCalenderScheme value = entry.getValue();

                            Date date = TimeUtils.string2Date(key, "yyyy-MM-dd");
                            Calendar calendar = new Calendar();
                            calendar.setYear(date.getYear() + 1900);
                            calendar.setMonth(date.getMonth() + 1);
                            calendar.setDay(date.getDate());

                            calendar.addScheme(0, Color.WHITE, value.lunchCount);
                            calendar.addScheme(1, Color.WHITE, value.dinnerCount);
                            if (value.isGoodDay) {
                                calendar.addScheme(3, Color.WHITE, value.goodDayColor);
                            }
                            schemeMap.put(calendar.toString(), calendar);
//                            LogUtils.i(key, value, calendar);
                        }
                        mBinding.calenderView.setSchemeDate(schemeMap);
                    }
                });


        OkGo.<NetMonthData>post(HttpURLs.monthIndex)
                .params("date", mMonthDate)
                .params("type", mType)
                .params("hall_id", mHallId)
                .tag(getThisFragment())
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new JsonCallback<NetMonthData>() {
                    @Override
                    public void onSuccess(Response<NetMonthData> response) {
                        NetMonthData body = response.body();
                        NetMonthData.DataDTO data = body.getData();
                        if (data == null) {
                            return;
                        }
                        NetMonthData.DataDTO.StatusArrDTO statusArr = data.getStatus_arr();
                        if (statusArr != null) {
                            mBinding.sbvSummary.setData(statusArr.getChance(), statusArr.getIntent(), statusArr.getLock(),
                                    statusArr.getSign(), statusArr.getExec(), statusArr.getComplete());
                            setSbvCondition(mBinding.sbvSummary, mHallId, mHallName);
                        }

                    }
                });
    }

    private void showSelectHallDialog() {
        if (ObjectUtils.isEmpty(mHallList)) {
            return;
        }
        TwoLineTabSelectDialog dialog = new TwoLineTabSelectDialog(getContext());
        dialog.setData(mHallList, null);
        dialog.setOnHallSelectListener(new TwoLineTabSelectDialog.OnHallSelectListener() {
            @Override
            public void OnHallSelect(String id, String name, TwoLineTabSelectDialog dialog) {
                mHallId = id;
                mHallName = name;
                dialog.dismiss();
                getMonthData();
            }
        });
        dialog.show();


    }

    public void setSbvCondition(StatisticsBarView barView, String hallId, String hallName) {
        OrderFilterCondition condition = new OrderFilterCondition();
        condition.type = mType;
//        LogUtils.i("mMonthDate",mMonthDate);
        Date date = TimeUtils.string2Date(mMonthDate, mMonthDate.length() > 7 ? "yyyy-MM-dd" : "yyyy-MM");
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTime(date);
        if (mMonthDate.length() > 7) {
            //日
            String day = TimeUtils.date2String(date, "yyyy/MM/dd");
            condition.number_date = day + "-" + day;
        } else {
            //月
            int max = calendar.getActualMaximum(java.util.Calendar.DATE);
            calendar.set(java.util.Calendar.DAY_OF_MONTH, max);
            String endDay = TimeUtils.millis2String(calendar.getTimeInMillis(), "yyyy/MM/dd");
            String startDay = TimeUtils.date2String(date, "yyyy/MM/dd");
            condition.number_date = startDay + "-" + endDay;
        }
        condition.hall_id = hallId;
        condition.hall_id_name = hallName;
        barView.setCondition(condition);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(LoginEvent event) {
        onRefresh(mBinding.refreshLayout);

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void logout(LogoutEvent event) {
        onRefresh(mBinding.refreshLayout);
    }

}
