package com.lingyan.banquet.views;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lingyan.banquet.databinding.LayoutReserveStepBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2020/12/13.
 */

public class ReserveStepView extends FrameLayout {

    private LayoutReserveStepBinding mBinding;
    private LinearLayout mContainer;
    private List<FrameLayout> mFrameLayoutList;
    private List<View> mLineList;
    private List<TextView> mTextViewList;

    public static interface OnStepClickListener {
        void onClick(int step);
    }

    private OnStepClickListener mListener;

    public void setOnStepClickListener(OnStepClickListener listener) {
        mListener = listener;
    }

    public ReserveStepView(@NonNull Context context) {
        super(context);
        init();
    }

    public ReserveStepView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReserveStepView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBinding = LayoutReserveStepBinding.inflate(LayoutInflater.from(getContext()), this, true);
        mContainer = mBinding.llSelectedDotContainer;
        int childCount = mContainer.getChildCount();
        mFrameLayoutList = new ArrayList<>();
        mLineList = new ArrayList<>();
        mTextViewList = new ArrayList<>();
        for (int i = 0; i < childCount; i++) {
            View view = mContainer.getChildAt(i);
            if (view instanceof FrameLayout) {
                mFrameLayoutList.add((FrameLayout) view);

            } else {
                mLineList.add(view);
            }
        }

        int descContainerChildCount = mBinding.llDescContainer.getChildCount();
        for (int i = 0; i < descContainerChildCount; i++) {
            View view = mBinding.llDescContainer.getChildAt(i);
            if (view instanceof TextView) {
                mTextViewList.add((TextView) view);
            }
        }
        for (int i = 0; i < mFrameLayoutList.size(); i++) {
            FrameLayout layout = mFrameLayoutList.get(i);
            int finalI = i;
            layout.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClick(finalI + 1);
                    }
                }
            });
        }

        setStep(1);
    }

    /**
     * @param step 1 - 6
     */
    public void setStep(int step) {
        for (int i = 0; i < mFrameLayoutList.size(); i++) {
            FrameLayout frameLayout = mFrameLayoutList.get(i);

            View dotView = null;
            View selectedView = null;
            for (int j = 0; j < frameLayout.getChildCount(); j++) {
                View child = frameLayout.getChildAt(j);
                if (child instanceof ImageView) {
                    selectedView = child;
                } else {
                    dotView = child;
                }
            }


            selectedView.setVisibility(i < step ? VISIBLE : GONE);
            dotView.setVisibility(i >= step ? VISIBLE : GONE);
        }


        for (int i = 0; i < mLineList.size(); i++) {
            View view = mLineList.get(i);
            if (i < step - 1) {
                view.setBackgroundColor(Color.parseColor("#E0BB81"));
            } else {
                view.setBackgroundColor(Color.parseColor("#7E6E57"));
            }
        }
        for (int i = 0; i < mTextViewList.size(); i++) {
            TextView view = mTextViewList.get(i);
            if (i <= step - 1) {
                view.setTextColor(Color.parseColor("#F2DFC2"));
            } else {
                view.setTextColor(Color.parseColor("#7F7059"));
            }
        }


    }


}
