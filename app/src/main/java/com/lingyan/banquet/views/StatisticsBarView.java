package com.lingyan.banquet.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lingyan.banquet.databinding.LayoutStatisticsBarBinding;
import com.lingyan.banquet.ui.order.OrderListActivity;
import com.lingyan.banquet.ui.order.bean.OrderFilterCondition;

/**
 * Created by _hxb on 2021/1/1.
 */

public class StatisticsBarView extends FrameLayout {

    private LayoutStatisticsBarBinding mBinding;

    public StatisticsBarView(@NonNull Context context) {
        super(context);
        init();
    }

    public StatisticsBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StatisticsBarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        mBinding = LayoutStatisticsBarBinding.inflate(inflater);
        addView(mBinding.getRoot());
    }

    public void setData(String chance, String intent, String lock, String sign, String exec, String complete) {
        mBinding.tvChance.setText(chance);
        mBinding.tvIntent.setText(intent);
        mBinding.tvLock.setText(lock);
        mBinding.tvSign.setText(sign);
        mBinding.tvExec.setText(exec);
        mBinding.tvComplete.setText(complete);
    }

    public void setChanceViewVisibility(int visibility) {
        mBinding.llChanceContainer.setVisibility(visibility);
    }

    public void setOnClick(View.OnClickListener onClick) {
        mBinding.llChanceContainer.setOnClickListener(onClick);
        mBinding.llIntentContainer.setOnClickListener(onClick);
        mBinding.llLockContainer.setOnClickListener(onClick);
        mBinding.llSignContainer.setOnClickListener(onClick);
        mBinding.llExecContainer.setOnClickListener(onClick);
        mBinding.llCompleteContainer.setOnClickListener(onClick);
    }

    public void setCondition(OrderFilterCondition condition) {
        OnClickListener onClick = new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (v == mBinding.llChanceContainer) {
                    condition.status="1";
                    String text = mBinding.tvChance.getText().toString().trim();
                    if(StringUtils.equals(text,"0")){
                        return;
                    }
                } else if (v == mBinding.llIntentContainer) {
                    condition.status="2";
                    String text = mBinding.tvIntent.getText().toString().trim();
                    if(StringUtils.equals(text,"0")){
                        return;
                    }
                } else if (v == mBinding.llLockContainer) {
                    condition.status="3";
                    String text = mBinding.tvLock.getText().toString().trim();
                    if(StringUtils.equals(text,"0")){
                        return;
                    }
                } else if (v == mBinding.llSignContainer) {
                    condition.status="4";
                    String text = mBinding.tvSign.getText().toString().trim();
                    if(StringUtils.equals(text,"0")){
                        return;
                    }
                } else if (v == mBinding.llExecContainer) {
                    condition.status="5";
                    String text = mBinding.tvExec.getText().toString().trim();
                    if(StringUtils.equals(text,"0")){
                        return;
                    }
                } else if (v == mBinding.llCompleteContainer) {
                    condition.status="6";
                    String text = mBinding.tvComplete.getText().toString().trim();
                    if(StringUtils.equals(text,"0")){
                        return;
                    }
                }

                OrderFilterCondition condition1 = new OrderFilterCondition();
                condition1.type=condition.type;
                OrderListActivity.start(1,GsonUtils.toJson(condition1),GsonUtils.toJson(condition));
            }
        };
//        mBinding.llChanceContainer.setOnClickListener(onClick);
        mBinding.llIntentContainer.setOnClickListener(onClick);
        mBinding.llLockContainer.setOnClickListener(onClick);
        mBinding.llSignContainer.setOnClickListener(onClick);
        mBinding.llExecContainer.setOnClickListener(onClick);
        mBinding.llCompleteContainer.setOnClickListener(onClick);
    }
}
