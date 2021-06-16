package com.lingyan.banquet.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by _hxb on 2021/2/7.
 */

public class FlShadowLayout extends FrameLayout {
    public boolean mIsIntercept = false;//是否拦截子项点击事件 默认不拦截

    public FlShadowLayout(@NonNull Context context) {
        super(context);
    }

    public FlShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FlShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isIntercept() ? isIntercept() : super.onInterceptTouchEvent(ev);
    }

    public boolean isIntercept() {
        return mIsIntercept;
    }

    public void setIntercept(boolean intercept) {
        mIsIntercept = intercept;
    }
}
