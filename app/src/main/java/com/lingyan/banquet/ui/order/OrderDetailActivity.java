package com.lingyan.banquet.ui.order;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.RegexUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lingyan.banquet.App;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityBanquetOrderDetailBinding;
import com.lingyan.banquet.event.ApplyRecordEvent;
import com.lingyan.banquet.event.FollowRefreshEvent;
import com.lingyan.banquet.event.OrderDetailRefreshEvent;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.Constant;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.banquet.BanquetStepManagerActivity;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetChildHall;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetType;
import com.lingyan.banquet.ui.banquet.bean.NetRestoreStep2;
import com.lingyan.banquet.ui.celebration.CelStepManagerActivity;
import com.lingyan.banquet.ui.follow.FollowDetailActivity;
import com.lingyan.banquet.ui.follow.adapter.FollowListAdapter;
import com.lingyan.banquet.ui.follow.bean.FollowList;
import com.lingyan.banquet.ui.order.adapter.RoomListAdapter;
import com.lingyan.banquet.ui.order.bean.ModifyHallBean;
import com.lingyan.banquet.ui.order.bean.NetOrderDetail;
import com.lingyan.banquet.ui.order.bean.NetRelation;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.views.RecycleViewDivider;
import com.lingyan.banquet.views.dialog.TwoLineTabSelectDialog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/1/3.
 */

public class OrderDetailActivity extends BaseActivity implements OnRefreshListener {

    private String mUrl;
    private ActivityBanquetOrderDetailBinding mBinding;
    private String mId;
    private String numberId;
    private String realName;
    private int mStep;
    private NetOrderDetail.DataDTO mData;
    private int mType;
    private String mBanCelId;
    private List<NetBanquetType.DataDTO> mBanquetCelebrationTypeList;
    private RoomListAdapter mAdapter;
    private List<NetOrderDetail.DataDTO.NumberListDTO> mRecData;

    public static void start(String url, String id, int type) {
        Intent intent = new Intent(App.sApp, OrderDetailActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("id", id);
        intent.putExtra("type", type);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        mBinding = ActivityBanquetOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("订单详情");
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");
        mId = intent.getStringExtra("id");
        mType = intent.getIntExtra("type", BanquetCelebrationType.TYPE_BANQUET);
        LogUtils.i("id" + mId, "mBanCelId" + mBanCelId);
        mBinding.refreshLayout.setOnRefreshListener(this);
        mBinding.tvOrderDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BanquetCelebrationType.TYPE_BANQUET == mType) {
                    BanquetStepManagerActivity.start(mBanCelId, mStep);
                } else if (BanquetCelebrationType.TYPE_CELEBRATION == mType) {
                    CelStepManagerActivity.start(mBanCelId, mStep);
                }

            }
        });

        //修改订单
        mBinding.tvOrderModify.setOnClickListener(v -> {
            Intent intentModify = new Intent(getContext(), OrderModifyActivity.class);
            intentModify.putExtra("id", mId);
            intentModify.putExtra("type", mType);
            startActivityForResult(intentModify, Constant.Code.OPEN_ORDER_MODIFY_CODE_REQUEST);
        });

        initUI();
        onRefresh(mBinding.refreshLayout);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", mId);
        jsonObject.addProperty("type", mType);
        OkGo.<NetOrderDetail>post(mUrl)
                .upJson(jsonObject.toString())
                .execute(new JsonCallback<NetOrderDetail>() {
                    @Override
                    public void onSuccess(Response<NetOrderDetail> response) {
                        initUI();
                        NetOrderDetail body = response.body();
                        if (body == null) {
                            return;
                        }
                        mData = body.getData();
                        if (mData == null) {
                            return;
                        }
                        mStep = mData.getStep();
                        mBanCelId = mData.getId();
                        mBinding.tvId.setText(mBanCelId);
                        mBinding.tvStatus.setText(mData.getStatus_name());
                        mBinding.tvCreateTime.setText(mData.getCreate_time());
                        mBinding.tvDate.setText(mData.getDate());
                        mBinding.tvCustomerTypeName.setText(mData.getCustomer_type_name());
                        mBinding.tvNicheName.setText(mData.getNiche_name());
                        mBinding.tvNicheSourceName.setText(mData.getNiche_source_name());
                        mBinding.tvIntentManName.setText(mData.getIntent_man_name());
                        mBinding.tvFollowCount.setText(String.valueOf(mData.getFollow_time()));

                        //判断是否显示修改订单按钮
                        if ("1".equals(mData.getIs_modify_order())) {
                            mBinding.tvOrderModify.setVisibility(View.VISIBLE);
                        } else {
                            mBinding.tvOrderModify.setVisibility(View.GONE);
                        }
                        NetOrderDetail.DataDTO.LikemenDTO likemen = mData.getLikemen();
                        if (likemen != null) {
                            realName = likemen.getReal_name();
                            mBinding.tvRealName.setText(realName);
                            String mobile = likemen.getMobile();
                            mBinding.tvMobile.setText(mobile);
                            if (RegexUtils.isMobileSimple(mobile)) {
                                mBinding.tvMobile.setTextColor(Color.BLUE);
                                mBinding.tvMobile.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        PhoneUtils.dial(mobile);
                                    }
                                });
                            } else {
                                mBinding.tvMobile.setOnClickListener(null);
                                mBinding.tvMobile.setTextColor(Color.parseColor("#333333"));
                            }
                        }
                        String intentionality = mData.getIntentionality();
                        float rb = 0;
                        try {
                            rb = Float.parseFloat(intentionality);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mBinding.rbIntentionality.setRating(rb);
                        NetOrderDetail.DataDTO.NumberInfoDTO numberInfo = mData.getNumber_info();
                        if (numberInfo != null) {
                            mBinding.tvContractNo.setText(numberInfo.getContract_no());
                        }

                        if (mData.getNumber_list() != null) {
                            mAdapter.addData(mData.getNumber_list());
                        }
                        NetOrderDetail.DataDTO.FundInfoDTO fundInfo = mData.getFund_info();
                        if (fundInfo != null) {
                            mBinding.tvFundInfoLockMoney.setText(fundInfo.getLock_money());
                            mBinding.tvFundInfoSignMoney.setText(fundInfo.getSign_money());
                            mBinding.tvFundInfoBalance.setText(fundInfo.getBalance());
//                            mBinding.tvFundInfoFinalAmount.setText(fundInfo.getFinal_amount());
                        }

                        String glId = mData.getGl_id();
                        String glStep = mData.getGl_step();
                        boolean isOrderAnother = !StringUtils.equals("0", glId);
                        mBinding.tvIsYdQd.setText(isOrderAnother ? "是" : "否");
                        if (isOrderAnother) {
                            mBinding.tvLookAnother.setVisibility(View.VISIBLE);
                            mBinding.tvLookAnother.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (BanquetCelebrationType.TYPE_BANQUET == mType) {
                                        //当前是宴会
                                        OrderDetailActivity.start(HttpURLs.banquetOrderInfo, glId, BanquetCelebrationType.TYPE_CELEBRATION);
                                    } else if (BanquetCelebrationType.TYPE_CELEBRATION == mType) {
                                        //当前是庆典
                                        OrderDetailActivity.start(HttpURLs.banquetOrderInfo, glId, BanquetCelebrationType.TYPE_BANQUET);
                                    }
                                }
                            });

                            mBinding.tvGoAnother.setText("查看" + (BanquetCelebrationType.TYPE_BANQUET == mType ? "庆典" : "宴会"));
                            mBinding.tvGoAnother.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (BanquetCelebrationType.TYPE_BANQUET == mType) {
                                        //当前是宴会
                                        CelStepManagerActivity.start(glId, Integer.parseInt(glStep));
                                    } else if (BanquetCelebrationType.TYPE_CELEBRATION == mType) {
                                        //当前是庆典
                                        BanquetStepManagerActivity.start(glId, Integer.parseInt(glStep));
                                    }
                                }
                            });
                        } else {
                            mBinding.tvLookAnother.setVisibility(View.INVISIBLE);
                            mBinding.tvGoAnother.setText("预定" + (BanquetCelebrationType.TYPE_BANQUET == mType ? "庆典" : "宴会"));
                            mBinding.tvGoAnother.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    OkGo.<NetRelation>post(HttpURLs.banquetReloationOrder)
                                            .params("id", mId)
                                            .execute(new JsonCallback<NetRelation>() {
                                                @Override
                                                public void onSuccess(Response<NetRelation> response) {
                                                    NetRelation body1 = response.body();
                                                    NetRelation.DataDTO data = body1.getData();
                                                    if (data == null) {
                                                        ToastUtils.showShort(body1.getMsg());
                                                        return;
                                                    }
                                                    String id = data.getId();
                                                    String type = data.getType();

                                                    if (BanquetCelebrationType.TYPE_BANQUET == Integer.parseInt(type)) {
                                                        BanquetStepManagerActivity.start(id, 1);
                                                    } else if (BanquetCelebrationType.TYPE_CELEBRATION == Integer.parseInt(type)) {
                                                        CelStepManagerActivity.start(id, 1);
                                                    }

                                                    onRefresh(mBinding.refreshLayout);
                                                }
                                            });
                                }
                            });
                        }


                        if (StringUtils.equals("1", mData.getIs_show())) {
                            mBinding.tvOrderDetail.setVisibility(View.VISIBLE);
                            mBinding.tvGoAnother.setVisibility(View.VISIBLE);
                            mBinding.tvFollowDetail.setVisibility(View.VISIBLE);
                        } else {
                            mBinding.tvOrderDetail.setVisibility(View.INVISIBLE);
                            mBinding.tvGoAnother.setVisibility(View.INVISIBLE);
                            mBinding.tvFollowDetail.setVisibility(View.INVISIBLE);
                        }

                        mBinding.tvFollowDetail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FollowDetailActivity.start(mId, realName);
                            }
                        });

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mBinding.refreshLayout.finishRefresh();
                    }
                });
    }

    //宴会类型
    private void showSelectBanquetCelebrationTypeDialog(int position) {
        if (ObjectUtils.isEmpty(mBanquetCelebrationTypeList)) {
            return;
        }

        //已存在的
        List<String> existIdList = new ArrayList<>();
        if (mData != null && mData.getNumber_list() != null
                && mData.getNumber_list().get(position) != null
                && mData.getNumber_list().get(position).getHall_id() != null) {
            existIdList = mData.getNumber_list().get(position).getHall_id();
        }

        TwoLineTabSelectDialog dialog = new TwoLineTabSelectDialog(getContext());
        dialog.setSingleMode(false);
        dialog.setData2(mBanquetCelebrationTypeList, existIdList);
        dialog.setTitle("选择包间");
        dialog.setOnMultipleSelectListener(new TwoLineTabSelectDialog.OnMultipleSelectListener() {
            @Override
            public void OnMultipleSelect(List<String> idList, List<String> nameList, TwoLineTabSelectDialog dialog) {
                JsonObject jsonObject = new JsonObject();
                if (idList.size() > 0) {
                    JsonArray jsonArray = new JsonArray();
                    for (String s : idList) {
                        jsonArray.add(s);
                    }
                    jsonObject.add("hall_ids", jsonArray);
                }
                jsonObject.addProperty("banquet_id", Integer.valueOf(mId));
                jsonObject.addProperty("number_id", Integer.valueOf(numberId));

                //修改包间
                OkGo.<ModifyHallBean>post(HttpURLs.modifyHall)
                        .tag(this)
                        .upJson(jsonObject.toString())
                        .execute(new JsonCallback<ModifyHallBean>() {
                            @Override
                            public void onSuccess(Response<ModifyHallBean> response) {
                                ModifyHallBean body = response.body();
                                if (body == null) {
                                    return;
                                }

                                onRefresh(mBinding.refreshLayout);
                                ToastUtils.showShort(body.getMsg());
                                dialog.dismiss();
                            }
                        });
            }
        });
        dialog.show();
    }

    private void initUI() {
        mBinding.tvId.setText("");
        mBinding.tvStatus.setText("");
        mBinding.tvCreateTime.setText("");
        mBinding.tvDate.setText("");
        mBinding.tvCustomerTypeName.setText("");
        mBinding.tvNicheName.setText("");
        mBinding.tvNicheSourceName.setText("");
        mBinding.tvIntentManName.setText("");
        mBinding.tvIsYdQd.setText("");

        mBinding.tvRealName.setText("");
        mBinding.tvMobile.setText("");
        mBinding.rbIntentionality.setNumStars(5);
        mBinding.tvContractNo.setText("");
        mBinding.tvFundInfoLockMoney.setText("");
        mBinding.tvFundInfoSignMoney.setText("");
        mBinding.tvFundInfoBalance.setText("");
//        mBinding.tvFundInfoFinalAmount.setText("");
        mBinding.tvFollowCount.setText("");

        mBinding.rvRoomList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.rvRoomList.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));
        mRecData = new ArrayList<>();
        mAdapter = new RoomListAdapter(mRecData, mType);
        mBinding.rvRoomList.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (mData != null && mData.getNumber_list() != null) {
                    numberId = mData.getNumber_list().get(i).getNumber_id();
                }

                JsonObject jsonObject = new JsonObject();
                List<String> list = mData.getNumber_list().get(i).getHall_id();
                if (list.size() > 0) {
                    JsonArray jsonArray = new JsonArray();
                    for (String s : list) {
                        jsonArray.add(s);
                    }
                    jsonObject.add("hall_ids", jsonArray);
                }
                jsonObject.addProperty("hall_type", "2");
                jsonObject.addProperty("s_type", "2");
                jsonObject.addProperty("type", mType);
                jsonObject.addProperty("date", mData.getNumber_list().get(i).getDate());
                jsonObject.addProperty("segment_type", mData.getNumber_list().get(i).getSegment_type());
                //获取包间列表
                OkGo.<NetBanquetType>post(HttpURLs.listBanquetHall)
                        .tag(this)
                        .upJson(jsonObject.toString())
                        .execute(new JsonCallback<NetBanquetType>() {
                            @Override
                            public void onSuccess(Response<NetBanquetType> response) {
                                NetBanquetType body = response.body();
                                mBanquetCelebrationTypeList = body.getData();
                                if (ObjectUtils.isEmpty(mBanquetCelebrationTypeList)) {
                                    return;
                                }

                                showSelectBanquetCelebrationTypeDialog(i);
                            }
                        });
            }
        });

        if (BanquetCelebrationType.TYPE_BANQUET == mType) {
            //当前是宴会
            mBinding.tvIsOrderAnother.setText("是否预定庆典：");
            mBinding.tvLookAnother.setText("查看庆典详情");
        } else if (BanquetCelebrationType.TYPE_CELEBRATION == mType) {
            //当前是庆典
            mBinding.tvIsOrderAnother.setText("是否预定宴会：");
            mBinding.tvLookAnother.setText("查看宴会详情");
        }

        mBinding.tvOrderDetail.setVisibility(View.INVISIBLE);
        mBinding.tvLookAnother.setVisibility(View.INVISIBLE);
        mBinding.tvGoAnother.setVisibility(View.INVISIBLE);
        mBinding.tvFollowDetail.setVisibility(View.INVISIBLE);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void change(OrderDetailRefreshEvent event) {
        onRefresh(mBinding.refreshLayout);
    }

}
