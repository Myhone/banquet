package com.lingyan.banquet.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.lingyan.banquet.R;

/**
 * Created by _hxb on 2021/1/29.
 */

public class TrapezoidView extends View {

    private int mColor = getContext().getResources().getColor(R.color.gold);
    private Paint mPaint;

    public TrapezoidView(Context context) {
        super(context);
        init();
    }

    public TrapezoidView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(getMeasuredWidth(), 0);
        path.lineTo(getMeasuredWidth() - ConvertUtils.dp2px(30), getMeasuredHeight());
        path.lineTo(ConvertUtils.dp2px(30), getMeasuredHeight());
        path.close();
        canvas.drawPath(path, mPaint);
    }
}
