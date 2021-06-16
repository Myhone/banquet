package com.lingyan.banquet.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.TextView;

import com.blankj.utilcode.util.ConvertUtils;
import com.lingyan.banquet.R;

/**
 * Created by anlia on 2017/10/10.
 */

public class CircleBarView extends View {

    private Paint bgPaint;//绘制背景圆弧的画笔
    private Paint progressPaint;//绘制圆弧的画笔
    private Paint secondProgressPaint;

    private RectF mRectF;//绘制圆弧的矩形区域

    private CircleBarAnim anim;

    private float progressNum;//可以更新的进度条数值
    private float secondProgressNum;//
    private float maxNum;//进度条最大值

    private int progressColor;//进度条圆弧颜色
    private int secondProgressColor;//
    private int bgColor;//背景圆弧颜色
    private float startAngle;//背景圆弧的起始角度
    private float sweepAngle;//背景圆弧扫过的角度

    private float barWidth;//圆弧进度条宽度

    private int defaultSize;//自定义View默认的宽高
    private float progressSweepAngle;//进度条圆弧扫过的角度
    private float secondSweepAngle;

    private OnAnimationListener onAnimationListener;

    public CircleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleBarView);

        progressColor = typedArray.getColor(R.styleable.CircleBarView_progress_color, Color.GREEN);
        secondProgressColor = typedArray.getColor(R.styleable.CircleBarView_second_progress_color, Color.BLACK);
        bgColor = typedArray.getColor(R.styleable.CircleBarView_bg_color, Color.GRAY);

        startAngle = typedArray.getFloat(R.styleable.CircleBarView_start_angle, 0);
        sweepAngle = typedArray.getFloat(R.styleable.CircleBarView_sweep_angle, 360);

        barWidth = typedArray.getDimension(R.styleable.CircleBarView_bar_width, ConvertUtils.dp2px(10));
        typedArray.recycle();

        progressNum = 0;
        secondProgressNum = 0;
        maxNum = 100;
        defaultSize = ConvertUtils.dp2px(100);
        mRectF = new RectF();

        progressPaint = new Paint();
        progressPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
        progressPaint.setColor(progressColor);
        progressPaint.setAntiAlias(true);//设置抗锯齿
        progressPaint.setStrokeWidth(barWidth);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔为圆角

        secondProgressPaint = new Paint();
        secondProgressPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
        secondProgressPaint.setColor(secondProgressColor);
        secondProgressPaint.setAntiAlias(true);//设置抗锯齿
        secondProgressPaint.setStrokeWidth(barWidth);
        secondProgressPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔为圆角

        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.STROKE);//只描边，不填充
        bgPaint.setColor(bgColor);
        bgPaint.setAntiAlias(true);//设置抗锯齿
        bgPaint.setStrokeWidth(barWidth);
        bgPaint.setStrokeCap(Paint.Cap.ROUND);

        anim = new CircleBarAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = measureSize(defaultSize, heightMeasureSpec);
        int width = measureSize(defaultSize, widthMeasureSpec);
        int min = Math.min(width, height);// 获取View最短边的长度
        setMeasuredDimension(min, min);// 强制改View为以最短边为长度的正方形

        if (min >= barWidth * 2) {
            mRectF.set(barWidth / 2, barWidth / 2, min - barWidth / 2, min - barWidth / 2);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(mRectF, startAngle, sweepAngle, false, bgPaint);
        canvas.drawArc(mRectF, startAngle, secondSweepAngle, false, secondProgressPaint);
        canvas.drawArc(mRectF, startAngle, progressSweepAngle, false, progressPaint);
    }

    public class CircleBarAnim extends Animation {

        public CircleBarAnim() {
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {//interpolatedTime从0渐变成1,到1时结束动画,持续时间由setDuration（time）方法设置
            super.applyTransformation(interpolatedTime, t);
            progressSweepAngle = interpolatedTime * sweepAngle * progressNum / maxNum;
            secondSweepAngle = interpolatedTime * sweepAngle * secondProgressNum / maxNum;
            if (onAnimationListener != null) {
                onAnimationListener.howTiChangeProgressColor(interpolatedTime, interpolatedTime * progressNum / maxNum,
                        interpolatedTime * secondProgressNum / maxNum, maxNum);
            }
            postInvalidate();
        }
    }

    private int measureSize(int defaultSize, int measureSpec) {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }


    /**
     * 设置进度条最大值
     *
     * @param maxNum
     */
    public void setMaxNum(float maxNum) {
        this.maxNum = maxNum;
    }

    /**
     * 设置进度条数值
     *
     * @param progressNum 进度条数值
     * @param time        动画持续时间
     */
    public void setProgressNum(float progressNum, float secondProgressNum, int time) {
        this.progressNum = progressNum;
        this.secondProgressNum = secondProgressNum;
        this.progressSweepAngle =  sweepAngle * progressNum / maxNum;
        this.secondSweepAngle =  sweepAngle * secondProgressNum / maxNum;
        anim.cancel();
        if (time > 0) {
            anim.setDuration(time);
            this.startAnimation(anim);
        }

    }


    public interface OnAnimationListener {

        void howTiChangeProgressColor(float interpolatedTime, float updateNum, float secondNum, float maxNum);

    }

    public void setOnAnimationListener(OnAnimationListener onAnimationListener) {
        this.onAnimationListener = onAnimationListener;
    }
}
