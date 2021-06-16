package com.lingyan.banquet.ui.banquet.step;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseFragment;
import com.lingyan.banquet.ui.banquet.BanquetStepManagerActivity;
import com.lingyan.banquet.views.dialog.LoseOrderDialog;

/**
 * Created by _hxb on 2021/1/10.
 */

public abstract class BaseBanquetStepFragment extends BaseFragment {


    public BanquetStepManagerActivity getStepActivity() {
        return (BanquetStepManagerActivity) getActivity();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        View view = getView();
        if (view != null) {
            View.OnClickListener loseClick = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!canLoseOrder()){
                        ToastUtils.showShort("当前状态不可操作");
                        return;
                    }

                    LoseOrderDialog dialog = new LoseOrderDialog(getActivity());
                    dialog.setType(1);
                    dialog.setId(getBanquetId());
                    dialog.show();
                }
            };
            View view1 = view.findViewById(R.id.tv_lose_order);
            View view2 = view.findViewById(R.id.tv_lose_order_2);
            View view3 = view.findViewById(R.id.tv_lose_order_3);

            if (view1 != null) {
                view1.setOnClickListener(loseClick);
            }
            if (view2 != null) {
                view2.setOnClickListener(loseClick);
            }
            if (view3 != null) {
                view3.setOnClickListener(loseClick);
            }

        }

    }

    public String getBanquetId() {
        return getStepActivity().getId();
    }

    public void setMaxStep(int step) {
        BanquetStepManagerActivity stepActivity = getStepActivity();
        if(stepActivity==null){
            return;
        }
        stepActivity.setMaxStep(step);
    }


    public abstract  boolean canLoseOrder();
}
