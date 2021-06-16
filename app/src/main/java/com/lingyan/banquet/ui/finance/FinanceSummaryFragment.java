package com.lingyan.banquet.ui.finance;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.FragmentFinanceSummaryBinding;
import com.lingyan.banquet.databinding.LayoutFinanceFutureStatisticsDataBinding;
import com.lingyan.banquet.databinding.LayoutFinanceStatisticsDataBinding;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.finance.bean.NetDepositHeaderChartData;
import com.lingyan.banquet.ui.finance.bean.NetDepositHeaderData;
import com.lingyan.banquet.ui.finance.bean.NetDepositStatisticsData;
import com.lingyan.banquet.utils.BanquetChartHelper;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 财务汇总
 * Created by _hxb on 2021/1/6.
 */

public class FinanceSummaryFragment extends BaseFragment implements OnRefreshListener {

    private FragmentFinanceSummaryBinding mBinding;

    private int mTopSummaryType = BanquetCelebrationType.TYPE_BANQUET;
    private String mTopSummaryTime = "month";
    //中间的数据
    private String mCenterTime = "month";
    private Calendar mCenterStartCalender;
    private Calendar mCenterEndCalender;
    //下面未来的数据
    private String mFutureTime = "month";
    private Calendar mFutureStartCalender;
    private Calendar mFutureEndCalender;

    public static FinanceSummaryFragment newInstance() {
        FinanceSummaryFragment fragment = new FinanceSummaryFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentFinanceSummaryBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    private int[] mColors = new int[]{
            Color.parseColor("#C7A876"),
            Color.parseColor("#777D95"),
            Color.parseColor("#D75E32")

    };

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCenterStartCalender = Calendar.getInstance();
        mCenterStartCalender.add(Calendar.MONTH, -1);
        //默认月
        mCenterEndCalender = Calendar.getInstance();

        mFutureStartCalender = Calendar.getInstance();
        mFutureEndCalender = Calendar.getInstance();
        mFutureEndCalender.add(Calendar.MONTH, 1);


        setCenterTimeRangeUI();
        setFutureTimeRangeUI();


        BanquetChartHelper.init(mBinding.lineChart);

        mBinding.tvBanquetCelebrationType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //顶部的宴会和庆典选择

                new MaterialDialog.Builder(getActivity())
                        .title("请选择")
                        .items("宴会", "庆典")
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                if (position == 0) {
                                    mTopSummaryType = BanquetCelebrationType.TYPE_BANQUET;
                                    mBinding.tvBanquetCelebrationType.setText("宴会");
                                } else {
                                    mTopSummaryType = BanquetCelebrationType.TYPE_CELEBRATION;
                                    mBinding.tvBanquetCelebrationType.setText("庆典");
                                }
                                onRefresh(mBinding.refreshLayout);
                            }
                        })
                        .show();

            }
        });
        mBinding.tvTimeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //顶部的本日，本月，本年选择

                new MaterialDialog.Builder(getActivity())
                        .title("请选择")
                        .items("本日", "本月", "今年")
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                if (position == 0) {
                                    mTopSummaryTime = "today";
                                    mBinding.tvTimeSelect.setText("本日");
                                } else if (position == 1) {
                                    mTopSummaryTime = "month";
                                    mBinding.tvTimeSelect.setText("本月");
                                } else if (position == 2) {
                                    mTopSummaryTime = "year";
                                    mBinding.tvTimeSelect.setText("今年");
                                }

                                onRefresh(mBinding.refreshLayout);
                            }
                        })
                        .show();
            }
        });
        //中间的年月日
        View.OnClickListener unitClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("请选择")
                        .items("日", "月", "年")
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                if (v == mBinding.tvCenterTimeSelect) {
                                    if (position == 0) {
                                        mCenterTime = "today";
                                    } else if (position == 1) {
                                        mCenterTime = "month";
                                    } else if (position == 2) {
                                        mCenterTime = "year";
                                    }
                                } else if (v == mBinding.tvFutureTimeSelect) {
                                    if (position == 0) {
                                        mFutureTime = "today";
                                    } else if (position == 1) {
                                        mFutureTime = "month";
                                    } else if (position == 2) {
                                        mFutureTime = "year";
                                    }
                                }
                                setCenterTimeRangeUI();
                                setFutureTimeRangeUI();
                                onRefresh(mBinding.refreshLayout);
                            }
                        })
                        .show();
            }
        };
        mBinding.tvCenterTimeSelect.setOnClickListener(unitClickListener);
        mBinding.tvFutureTimeSelect.setOnClickListener(unitClickListener);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean[] booleans = null;
                String title = "";
                if (v == mBinding.tvStartTime || v == mBinding.tvEndTime) {
                    if (StringUtils.equals(mCenterTime, "year")) {
                        booleans = new boolean[]{true, false, false, false, false, false};
                    } else if (StringUtils.equals(mCenterTime, "month")) {
                        booleans = new boolean[]{true, true, false, false, false, false};
                    } else {
                        booleans = new boolean[]{true, true, true, false, false, false};
                    }
                } else if (v == mBinding.tvFutureStartTime || v == mBinding.tvFutureEndTime) {
                    if (StringUtils.equals(mFutureTime, "year")) {
                        booleans = new boolean[]{true, false, false, false, false, false};
                    } else if (StringUtils.equals(mFutureTime, "month")) {
                        booleans = new boolean[]{true, true, false, false, false, false};
                    } else {
                        booleans = new boolean[]{true, true, true, false, false, false};
                    }
                }
                if (v == mBinding.tvStartTime) {
                    title = "选择开始日期";
                } else {
                    title = "选择结束日期";
                }

                if (booleans == null) {
                    return;
                }
                new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View view) {
                        LogUtils.i(TimeUtils.date2String(date));
                        if (v == mBinding.tvStartTime) {
                            mCenterStartCalender.setTime(date);
                        } else if (v == mBinding.tvEndTime) {
                            mCenterEndCalender.setTime(date);
                        } else if (v == mBinding.tvFutureStartTime) {
                            mFutureStartCalender.setTime(date);
                        } else if (v == mBinding.tvFutureEndTime) {
                            mFutureEndCalender.setTime(date);
                        }
                        setCenterTimeRangeUI();
                        onRefresh(mBinding.refreshLayout);
                    }
                })
                        .setTitleText(title)
                        .setItemVisibleCount(12)
                        .setType(booleans)
                        .build()
                        .show();
            }
        };
        mBinding.tvStartTime.setOnClickListener(onClickListener);
        mBinding.tvEndTime.setOnClickListener(onClickListener);
        mBinding.tvFutureStartTime.setOnClickListener(onClickListener);
        mBinding.tvFutureEndTime.setOnClickListener(onClickListener);


        mBinding.refreshLayout.setOnRefreshListener(this);

        mBinding.tvRealIncome.setText("");
        mBinding.tvExpectIncome.setText("");
        mBinding.tvRealBalance.setText("");
        mBinding.tvRealDeposit.setText("");
        mBinding.tvRefundDeposit.setText("");
        mBinding.tvRealSurplusIncome.setText("");
        mBinding.tvCompleteOrder.setText("");
        mBinding.tvSignOrder.setText("");

        onRefresh(mBinding.refreshLayout);

    }

    private void setCenterTimeRangeUI() {
        String format = "";
        if (StringUtils.equals(mCenterTime, "year")) {
            mBinding.tvCenterTimeSelect.setText("年");
            format = "yyyy";
        } else if (StringUtils.equals(mCenterTime, "month")) {
            format = "yyyy-MM";
            mBinding.tvCenterTimeSelect.setText("月");
        } else {
            format = "yyyy-MM-dd";
            mBinding.tvCenterTimeSelect.setText("日");
        }
        mBinding.tvStartTime.setText(TimeUtils.date2String(mCenterStartCalender.getTime(), format));
        mBinding.tvEndTime.setText(TimeUtils.date2String(mCenterEndCalender.getTime(), format));

    }

    private void setFutureTimeRangeUI() {
        String format = "";
        if (StringUtils.equals(mFutureTime, "year")) {
            mBinding.tvFutureTimeSelect.setText("年");
            format = "yyyy";
        } else if (StringUtils.equals(mFutureTime, "month")) {
            format = "yyyy-MM";
            mBinding.tvFutureTimeSelect.setText("月");
        } else {
            format = "yyyy-MM-dd";
            mBinding.tvFutureTimeSelect.setText("日");
        }
        mBinding.tvFutureStartTime.setText(TimeUtils.date2String(mFutureStartCalender.getTime(), format));
        mBinding.tvFutureEndTime.setText(TimeUtils.date2String(mFutureEndCalender.getTime(), format));

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshTopSummary();
        refreshChart();
        refreshStatisticsData();
        refreshFutureStatisticsData();
    }

    private void refreshFutureStatisticsData() {

        OkGo.<NetDepositStatisticsData>post(HttpURLs.depositStatisticsData)
                .params("banquet_type", mTopSummaryType)
                .params("key", mFutureTime)
                .params("start_time", TimeUtils.date2String(mFutureStartCalender.getTime(), "yyyy-MM-dd"))
                .params("end_time", TimeUtils.date2String(mFutureEndCalender.getTime(), "yyyy-MM-dd"))
                .params("tt_type", 2)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetDepositStatisticsData>() {
                    @Override
                    public void onSuccess(Response<NetDepositStatisticsData> response) {
                        NetDepositStatisticsData body = response.body();
                        List<NetDepositStatisticsData.DataDTO> list = body.getData();
                        int code = body.getCode();
                        if (code != 200) {
                            ToastUtils.showShort(body.getMsg());
                        }
                        if (ObjectUtils.isEmpty(list)) {
                            return;
                        }

                        mBinding.llFutureDataContainer.removeAllViews();
                        for (NetDepositStatisticsData.DataDTO dto : list) {
                            LayoutFinanceFutureStatisticsDataBinding inflate = LayoutFinanceFutureStatisticsDataBinding.inflate(getLayoutInflater());
                            mBinding.llFutureDataContainer.addView(inflate.getRoot());
                            inflate.tvDate.setText(dto.getDate());
                            inflate.tvMoney.setText(dto.getMoney());
                            inflate.tvCount.setText(dto.getCount());

                        }
                    }
                });
    }

    private void refreshStatisticsData() {

        OkGo.<NetDepositStatisticsData>post(HttpURLs.depositStatisticsData)
                .params("banquet_type", mTopSummaryType)
                .params("key", mCenterTime)
                .params("start_time", TimeUtils.date2String(mCenterStartCalender.getTime(), "yyyy-MM-dd"))
                .params("end_time", TimeUtils.date2String(mCenterEndCalender.getTime(), "yyyy-MM-dd"))
                .params("tt_type", 1)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetDepositStatisticsData>() {
                    @Override
                    public void onSuccess(Response<NetDepositStatisticsData> response) {
                        NetDepositStatisticsData body = response.body();
                        List<NetDepositStatisticsData.DataDTO> list = body.getData();
                        int code = body.getCode();
                        if (code != 200) {
                            ToastUtils.showShort(body.getMsg());
                        }
                        if (ObjectUtils.isEmpty(list)) {
                            return;
                        }


                        mBinding.llCenterDataContainer.removeAllViews();
                        for (NetDepositStatisticsData.DataDTO dto : list) {
                            LayoutFinanceStatisticsDataBinding inflate = LayoutFinanceStatisticsDataBinding.inflate(getLayoutInflater());
                            mBinding.llCenterDataContainer.addView(inflate.getRoot());
                            inflate.tvDate.setText(dto.getDate());
                            inflate.tvBalance.setText(dto.getBalance());
                            inflate.tvDeposit.setText(dto.getDeposit());
                            inflate.tvSurplus.setText(dto.getSurplus());
                            inflate.tvRealMoney.setText(dto.getReal_money());

                        }
                    }
                });
    }

    private void refreshChart() {
        OkGo.<NetDepositHeaderChartData>post(HttpURLs.depositHeaderChartData)
                .params("banquet_type", mTopSummaryType)
                .params("key", mTopSummaryTime)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetDepositHeaderChartData>() {
                    @Override
                    public void onSuccess(Response<NetDepositHeaderChartData> response) {
                        NetDepositHeaderChartData body = response.body();
                        NetDepositHeaderChartData.DataDTO data = body.getData();
                        if (data == null) {
                            return;
                        }
                        List<String> dateList = data.getDateList();

                        List<String> realList = data.getReal_List();
                        List<String> depositList = data.getDeposit_List();
                        List<String> surplusList = data.getSurplus_List();

                        if (dateList == null) {
                            return;
                        }
                        XAxis xAxis = mBinding.lineChart.getXAxis();
                        YAxis axisLeft = mBinding.lineChart.getAxisLeft();
                        xAxis.setLabelCount(xAxis.getLabelCount(), true);
                        xAxis.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getAxisLabel(float value, AxisBase axis) {
                                //日期
                                return dateList.get((int) value);
                            }
                        });

                        axisLeft.setValueFormatter(new ValueFormatter() {
                            @Override
                            public String getAxisLabel(float value, AxisBase axis) {
                                if (value == 0) {
                                    return "(万) 0";
                                }
                                BigDecimal bigDecimal = new BigDecimal(value);
                                bigDecimal= bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
                                float floatValue = bigDecimal.floatValue();
                                return String.valueOf(floatValue);
                            }
                        });

                        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

                        for (int z = 0; z < 3; z++) {
                            List<String> stringList;
                            String label;
                            if (z == 0) {
                                stringList = realList;
                                label = "实收尾款";
                            } else if (z == 1) {
                                stringList = depositList;
                                label = "实收定金";
                            } else {
                                stringList = surplusList;
                                label = "退出定金";
                            }

                            ArrayList<Entry> values = new ArrayList<Entry>();
                            for (int i = 0; i < stringList.size(); i++) {
                                float f = 0;
                                try {
                                    f = Float.parseFloat(stringList.get(i)) / 10000;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                values.add(new Entry(i, f));
                            }
                            LineDataSet d = new LineDataSet(values, label);
                            d.setDrawCircleHole(false);
                            d.setDrawCircles(false);
                            d.setValueTextSize(0);
                            int color = mColors[z % mColors.length];
                            d.setColor(color);
                            d.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                            d.setLineWidth(2);
                            dataSets.add(d);
                        }
                        LineData lineData = new LineData(dataSets);
                        mBinding.lineChart.setData(lineData);
                        mBinding.lineChart.invalidate();

                    }
                });
    }

    private void refreshTopSummary() {
        OkGo.<NetDepositHeaderData>post(HttpURLs.depositHeaderData)
                .params("banquet_type", mTopSummaryType)
                .params("key", mTopSummaryTime)
                .tag(getThisFragment())
                .execute(new JsonCallback<NetDepositHeaderData>() {
                             @Override
                             public void onSuccess(Response<NetDepositHeaderData> response) {
                                 NetDepositHeaderData.DataDTO data = response.body().getData();
                                 if (data == null) {
                                     return;
                                 }

                                 mBinding.tvRealIncome.setText(data.getReal_income());
                                 mBinding.tvExpectIncome.setText(data.getExpect_income());
                                 mBinding.tvRealBalance.setText(data.getReal_balance());
                                 mBinding.tvRealDeposit.setText(data.getReal_deposit());
                                 mBinding.tvRefundDeposit.setText(data.getRefund_deposit());
                                 mBinding.tvRealSurplusIncome.setText(data.getReal_surplus_income());
                                 mBinding.tvCompleteOrder.setText(data.getComplete_order());
                                 mBinding.tvSignOrder.setText(data.getSign_order());


                             }

                             @Override
                             public void onFinish() {
                                 super.onFinish();
                                 mBinding.refreshLayout.finishRefresh();
                             }
                         }

                );
    }

}
