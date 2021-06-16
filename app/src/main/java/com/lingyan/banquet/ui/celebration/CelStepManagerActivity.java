package com.lingyan.banquet.ui.celebration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityReserveBinding;
import com.lingyan.banquet.ui.celebration.step.BaseCelStepFragment;
import com.lingyan.banquet.ui.celebration.step.CelStep1Fragment;
import com.lingyan.banquet.ui.celebration.step.CelStep2Fragment;
import com.lingyan.banquet.ui.celebration.step.CelStep3Fragment;
import com.lingyan.banquet.ui.celebration.step.CelStep4Fragment;
import com.lingyan.banquet.ui.celebration.step.CelStep5Fragment;
import com.lingyan.banquet.ui.celebration.step.CelStep6Fragment;
import com.lingyan.banquet.views.ReserveStepView;

/**
 * 庆典预定
 * Created by _hxb on 2020/12/13.
 */

public class CelStepManagerActivity extends BaseActivity {

    private ActivityReserveBinding mBinding;
    private FragmentManager mFragmentManager;
    //宴会的id
    private String mId;
    private int mMaxStep;
    private int mCurStep;
    private int mTargetStep;

    public static void start(String id, int step) {
        Intent intent = new Intent(Utils.getApp(), CelStepManagerActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("step", step);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        mId = intent.getStringExtra("id");
        mTargetStep = intent.getIntExtra("step", 1);
        if (mTargetStep < 1) {
            mTargetStep = 1;
        }
        LogUtils.i("id " + mId, "step " + mTargetStep);

        mBinding = ActivityReserveBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.rlTitleBarContainer.tvTitleBarTitle.setText("庆典预定");
        mBinding.rlTitleBarContainer.tvTitleBarRight.setText("刷新");
        mBinding.rlTitleBarContainer.tvTitleBarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStepInner(mCurStep, true);
            }
        });
        mFragmentManager = getSupportFragmentManager();

        if (mId == null) {
            changeStep(1);
            setMaxStep(1);
        } else {
            changeStep(mTargetStep);
            setMaxStep(mTargetStep);
        }

        mBinding.stepView.setOnStepClickListener(new ReserveStepView.OnStepClickListener() {
            @Override
            public void onClick(int step) {
                if (step > mMaxStep) {
                    ToastUtils.showShort("请先完成前面的步骤");
                    return;
                }
                changeStep(step);
            }
        });
    }

    public int getTargetStep() {
        return mTargetStep;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }


    public void setMaxStep(int step) {
        if (step < mMaxStep) {
            return;
        }
        if (step > 6) {
            step = 6;
        }
        LogUtils.i("setMaxStep", step);
        mMaxStep = step;
    }

    /**
     * 1-6
     *
     * @param step
     */
    public void changeStep(int step) {
        changeStepInner(step, false);
    }

    private void changeStepInner(int step, boolean force) {
        if (mCurStep == step && !force) {
            return;
        }
        mCurStep = step;

        BaseCelStepFragment fragment = null;
        switch (step) {
            case 1: {
                fragment = CelStep1Fragment.newInstance();
                mBinding.stepView.setStep(1);
            }
            break;
            case 2: {
                fragment = CelStep2Fragment.newInstance();
                mBinding.stepView.setStep(2);
            }
            break;
            case 3: {
                fragment = CelStep3Fragment.newInstance();
                mBinding.stepView.setStep(3);
            }
            break;
            case 4: {
                fragment = CelStep4Fragment.newInstance();
                mBinding.stepView.setStep(4);
            }
            break;
            case 5: {
                fragment = CelStep5Fragment.newInstance();
                mBinding.stepView.setStep(5);
            }
            break;
            case 6: {
                fragment = CelStep6Fragment.newInstance();
                mBinding.stepView.setStep(6);
            }
            break;
        }

        if (fragment == null) {
            return;
        }
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        transaction.replace(R.id.fl, fragment);
        transaction.commitAllowingStateLoss();
    }


}
