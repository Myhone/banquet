package com.lingyan.banquet.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.lingyan.banquet.databinding.LayoutWordImageBinding;

/**
 * Created by _hxb on 2021/1/31.
 */

public class WordImageLayout extends FrameLayout {

    private TextView mTextView;
    private int mWordColor = Color.parseColor("#C7A876");
    private int mCorners = ConvertUtils.dp2px(2);
    private Path mPath = new Path();
    private RectF mRectF;

    public WordImageLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public WordImageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LayoutWordImageBinding binding = LayoutWordImageBinding.inflate(inflater);
        mTextView = binding.getRoot();
        addView(mTextView);
        FrameLayout.LayoutParams params = (LayoutParams) mTextView.getLayoutParams();
        params.gravity = Gravity.CENTER;
        mTextView.setLayoutParams(params);
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        if (mRectF == null) {
            mRectF = new RectF();
        }
        mRectF.left = 0;
        mRectF.top = 0;
        mRectF.right = getMeasuredWidth();
        mRectF.bottom = getMeasuredHeight();
        mPath.addRoundRect(mRectF, mCorners, mCorners, Path.Direction.CCW);
        canvas.clipPath(mPath);
        canvas.drawColor(mWordColor);
        super.onDraw(canvas);

    }

    public void setCorners(int corners) {
        if (mCorners == corners) {
            return;
        }
        mCorners = corners;
        postInvalidate();
    }

    public void setWordColor(int color) {
        mWordColor = color;
        postInvalidate();
    }

    public void setWord(String text) {
        if (StringUtils.isEmpty(text)) {
            text = "宴";
        }
        mTextView.setText(text);
    }

    public void setTextSize(float size) {
        mTextView.setTextSize(size);
    }
}
