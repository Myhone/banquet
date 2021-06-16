package com.lingyan.banquet.ui.banquet;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.databinding.FragmentRestaurantListBinding;
import com.lingyan.banquet.databinding.ItemRestaurantBanquetBinding;
import com.lingyan.banquet.databinding.ItemRestaurantBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetHallList;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

/**
 * 餐厅状态 早餐 晚餐
 * Created by _hxb on 2021/1/10.
 */

public class RestaurantListFragment extends BaseFragment implements OnRefreshListener {

    private FragmentRestaurantListBinding mBinding;

    private String mDate;
    private String mSegmentType;

    public static RestaurantListFragment newInstance() {
        RestaurantListFragment fragment = new RestaurantListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRestaurantListBinding.inflate(inflater);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBinding.refreshLayout.setOnRefreshListener(this);

        if (!StringUtils.isEmpty(mDate) && !StringUtils.isEmpty(mSegmentType)) {
            requestNet();
        }

    }

    public void setData(String date, String segmentType) {
        mDate = date;
        mSegmentType = segmentType;
        boolean resumed = isResumed();
        if (resumed) {
            requestNet();
        }
    }

    private void requestNet() {
        mBinding.llRestaurantContainer.removeAllViews();
        OkGo.<NetBanquetHallList>post(HttpURLs.banquetHallList)
                .params("date", mDate)
                .params("segment_type", mSegmentType)
                .execute(new JsonCallback<NetBanquetHallList>() {
                    @Override
                    public void onSuccess(Response<NetBanquetHallList> response) {
                        NetBanquetHallList body = response.body();
                        List<NetBanquetHallList.DataDTO> list = body.getData();
                        if (ObjectUtils.isEmpty(list)) {
                            return;
                        }
                        mBinding.llRestaurantContainer.removeAllViews();
                        for (int i = 0; i < list.size(); i++) {

                            NetBanquetHallList.DataDTO dto = list.get(i);
                            ItemRestaurantBinding restaurantBinding = ItemRestaurantBinding.inflate(getLayoutInflater());
                            restaurantBinding.tvName.setText(dto.getName());

                            List<NetBanquetHallList.DataDTO.SCountDTO> sCount = dto.getS_count();
                            restaurantBinding.tvTopTag1.setVisibility(View.GONE);
                            restaurantBinding.tvTopTag2.setVisibility(View.GONE);
                            restaurantBinding.tvTopTag3.setVisibility(View.GONE);
                            if (ObjectUtils.isEmpty(sCount)) {
                                restaurantBinding.tvTopTag1.setVisibility(View.VISIBLE);
                                restaurantBinding.tvTopTag1.setBackgroundResource(R.drawable.shape_rect_gray_corners_90);
                                restaurantBinding.tvTopTag1.setText("空台");
                            } else {
                                int min = Math.min(3, sCount.size());
                                for (int j = 0; j < min; j++) {
                                    NetBanquetHallList.DataDTO.SCountDTO countDTO = sCount.get(j);
                                    String name = countDTO.getName();
                                    String status = countDTO.getStatus();
                                    if (j == 0) {
                                        restaurantBinding.tvTopTag1.setVisibility(View.VISIBLE);
                                        restaurantBinding.tvTopTag1.setBackgroundResource(R.drawable.shape_rect_gradient_gold_corners_90);
                                        restaurantBinding.tvTopTag1.setText(name + "x" + status);
                                    } else if (j == 1) {
                                        restaurantBinding.tvTopTag2.setVisibility(View.VISIBLE);
                                        restaurantBinding.tvTopTag2.setBackgroundResource(R.drawable.shape_rect_gradient_gold_corners_90);
                                        restaurantBinding.tvTopTag2.setText(name + "x" + status);
                                    } else if (j == 2) {
                                        restaurantBinding.tvTopTag3.setVisibility(View.VISIBLE);
                                        restaurantBinding.tvTopTag3.setBackgroundResource(R.drawable.shape_rect_gradient_gold_corners_90);
                                        restaurantBinding.tvTopTag3.setText(name + "x" + status);
                                    }
                                }
                            }

                            mBinding.llRestaurantContainer.addView(restaurantBinding.getRoot());

                            List<NetBanquetHallList.DataDTO.BanquetListDTO> banquetList = dto.getBanquet_list();

                            restaurantBinding.llRestBanquetContainer.removeAllViews();
                            if (ObjectUtils.isNotEmpty(banquetList)) {
                                for (NetBanquetHallList.DataDTO.BanquetListDTO banquetListDTO : banquetList) {
                                    ItemRestaurantBanquetBinding banquetBinding = ItemRestaurantBanquetBinding.inflate(getLayoutInflater());
                                    restaurantBinding.llRestBanquetContainer.addView(banquetBinding.getRoot());
                                    banquetBinding.tvBanquetNicheNameRealName.setVisibility(View.VISIBLE);
                                    banquetBinding.tvIntentManName.setVisibility(View.VISIBLE);
                                    banquetBinding.tvDate.setVisibility(View.VISIBLE);
                                    banquetBinding.tvChange.setVisibility(View.VISIBLE);

                                    banquetBinding.tvBanquetNicheNameRealName.setText(banquetListDTO.getNiche_name() +" "+banquetListDTO.getReal_name());
                                    banquetBinding.tvIntentManName.setText("跟单人："+banquetListDTO.getIntent_man_name());
                                    banquetBinding.tvDate.setText(banquetListDTO.getDate());
                                    MyImageUtils.display(banquetBinding.rivImage,dto.getFull_pic());
                                    banquetBinding.tvChange.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            BanquetStepManagerActivity.start(banquetListDTO.getBanquet_id(),Integer.parseInt(banquetListDTO.getStep()));
                                        }
                                    });
                                    if(StringUtils.equals(banquetListDTO.getIs_show(),"1")){
                                        banquetBinding.tvChange.setVisibility(View.VISIBLE);
                                    }else {
                                        banquetBinding.tvChange.setVisibility(View.GONE);
                                    }

                                    banquetBinding.tvMinMaxNumber.setVisibility(View.GONE);
                                    banquetBinding.tvExpectNum.setVisibility(View.GONE);
                                    banquetBinding.tvExpectMeal.setVisibility(View.GONE);
                                    banquetBinding.tvCreate.setVisibility(View.GONE);


                                }
                            } else {
                                ItemRestaurantBanquetBinding banquetBinding = ItemRestaurantBanquetBinding.inflate(getLayoutInflater());
                                banquetBinding.tvBanquetNicheNameRealName.setVisibility(View.GONE);
                                banquetBinding.tvIntentManName.setVisibility(View.GONE);
                                banquetBinding.tvDate.setVisibility(View.GONE);
                                banquetBinding.tvChange.setVisibility(View.GONE);

                                banquetBinding.tvMinMaxNumber.setVisibility(View.VISIBLE);
                                banquetBinding.tvExpectNum.setVisibility(View.VISIBLE);
                                banquetBinding.tvExpectMeal.setVisibility(View.VISIBLE);
                                String expecteddesknumlunch = dto.getExpecteddesknumlunch();
                                String expectedmeallunch = dto.getExpectedmeallunch();
                                banquetBinding.tvMinMaxNumber.setText(dto.getMin_number()+"-"+dto.getMax_number()+"桌");
                                banquetBinding.tvExpectNum.setText(String.format("预期桌数：%s桌", expecteddesknumlunch));
                                banquetBinding.tvExpectMeal.setText(String.format("预期餐标：%s元",expectedmeallunch ));
                                MyImageUtils.display(banquetBinding.rivImage,dto.getFull_pic());
                                banquetBinding.tvCreate.setVisibility(View.VISIBLE);
                                banquetBinding.tvCreate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        BanquetStepManagerActivity.start(null,1);
                                    }
                                });


                                restaurantBinding.llRestBanquetContainer.addView(banquetBinding.getRoot());
                            }
                        }
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mBinding.refreshLayout.finishRefresh();
                    }
                });


    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        requestNet();
    }
}
