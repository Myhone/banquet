package com.lingyan.banquet.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;

import java.util.List;

/**
 * Created by _hxb on 2021/1/3.
 */

public class BanquetMonthView extends MonthView {


    private Paint mBgBorderPaint;
    private Paint mTextPaint;
    private Paint mCountBgPaint;


    private Calendar mCurCalendar;

    private final int mGrayColor = Color.parseColor("#BDBFC2");
    private final int mBlackColor = Color.parseColor("#333333");
    private final int mBlack2Color = Color.parseColor("#484848");
    private final int mGoldColor = Color.parseColor("#E0C488");
    private final int mBgGrayColor = Color.parseColor("#F5F5F5");

    private int mDayTextSize = ConvertUtils.dp2px(16);
    private int mCountTextSize = ConvertUtils.dp2px(12);
    private final int DP_1 = ConvertUtils.dp2px(1);
    private final int DP_5 = ConvertUtils.dp2px(5);
    private final int DP_2 = ConvertUtils.dp2px(2);

    public BanquetMonthView(Context context) {
        super(context);
        mBgBorderPaint = new Paint();
        mBgBorderPaint.setColor(Color.WHITE);
        mBgBorderPaint.setStyle(Paint.Style.FILL);
        mBgBorderPaint.setStrokeWidth(ConvertUtils.dp2px(2));
        mBgBorderPaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mCountBgPaint = new Paint();
        mCountBgPaint.setStyle(Paint.Style.FILL);
        setBackgroundColor(mBgGrayColor);
        mCurCalendar = new Calendar();
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        mCurCalendar.setYear(calendar.get(java.util.Calendar.YEAR));
        mCurCalendar.setMonth(calendar.get(java.util.Calendar.MONTH) + 1);
        mCurCalendar.setDay(calendar.get(java.util.Calendar.DATE));
    }

    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
        return false;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {
    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {


        if (calendar.isCurrentMonth()) {
            int dayX = x + mItemWidth / 4;
            int dayY = y + mItemHeight / 2;

            int[] leftLocation = {dayX, dayY};
            int[] rightTopLocation = {x + (mItemWidth / 4 * 3), y + (mItemHeight / 8 * 3)};
            int[] rightBottomLocation = {x + (mItemWidth / 4 * 3), y + (mItemHeight / 8 * 7)};
            int day = calendar.getDay();

            //画白色的背景
            initWhiteBgPaint();
            RectF bgRectF = new RectF(x + DP_1, y + DP_1, x + mItemWidth - DP_1, y + mItemHeight - DP_1);
            canvas.drawRoundRect(bgRectF, DP_5, DP_5, mBgBorderPaint);
            if (isSelected) {
                initGoldBorderPaint();
                canvas.drawRoundRect(bgRectF, DP_5, DP_5, mBgBorderPaint);
            }
            List<Calendar.Scheme> schemeList = calendar.getSchemes();
            if (calendar.compareTo(mCurCalendar) >= 0) {
                //今天以及以后的

                initDayBlackPaint();
                canvas.drawText(String.valueOf(day), leftLocation[0], leftLocation[1], mTextPaint);
            } else {
                initDayGrayPaint();
                canvas.drawText(String.valueOf(day), leftLocation[0], leftLocation[1], mTextPaint);
            }

            if (ObjectUtils.isNotEmpty(schemeList)) {

                for (Calendar.Scheme scheme : schemeList) {
                    String realScheme = scheme.getScheme();
                    if (scheme.getType() == 0 && !StringUtils.isTrimEmpty(realScheme) && Integer.valueOf(realScheme) > 0) {
                        RectF rightTopRect = new RectF(x + mItemWidth / 2, y + DP_1,
                                x + mItemWidth - DP_1, y + mItemHeight / 2);

                        mCountBgPaint.setColor(mGoldColor);

                        Path path = new Path();
                        float[] radii = {0, 0, DP_5, DP_5, 0, 0, 0, 0};
                        path.addRoundRect(rightTopRect, radii, Path.Direction.CW);
                        canvas.drawPath(path, mCountBgPaint);

                        initCountWhitePaint();
                        canvas.drawText(realScheme, rightTopLocation[0], rightTopLocation[1], mTextPaint);
                    } else if (scheme.getType() == 1 && !StringUtils.isTrimEmpty(realScheme) && Integer.valueOf(realScheme) > 0) {
                        RectF rightBottomRect = new RectF(x + mItemWidth / 2, y + mItemHeight / 2,
                                x + mItemWidth - DP_1, y + mItemHeight - DP_1);

                        mCountBgPaint.setColor(mBlack2Color);

                        Path path = new Path();
                        float[] radii = {0, 0, 0, 0, DP_5, DP_5, 0, 0};
                        path.addRoundRect(rightBottomRect, radii, Path.Direction.CW);
                        canvas.drawPath(path, mCountBgPaint);

                        initCountWhitePaint();
                        canvas.drawText(realScheme, rightBottomLocation[0], rightBottomLocation[1], mTextPaint);

                    } else if (scheme.getType() == 3 && !StringUtils.isTrimEmpty(realScheme)) {
                        initOvalPaint(Color.parseColor(realScheme));
                        RectF rightBottomRect = new RectF(x + mItemWidth / 4 - DP_2, y + mItemHeight / 4 * 3 - DP_2,
                                x + mItemWidth / 4 + DP_2, y + mItemHeight / 4 * 3 + DP_2);
                        canvas.drawOval(rightBottomRect, mBgBorderPaint);
                    }
                }
            }


        }


//        Rect rect = new Rect();
//        rect.left = x;
//        rect.top = y;
//        rect.right = rect.left + mItemWidth-ConvertUtils.dp2px(2);
//        rect.bottom = rect.top + mItemHeight-ConvertUtils.dp2px(2);
//        if (isSelected &&calendar.isCurrentMonth()) {
//            mBorderPaint.setColor(mGoldColor);
//        } else {
//            mBorderPaint.setColor(mBorderGrayColor);
//        }
//        canvas.drawRect(rect, mBorderPaint);
    }


    private void initDayGrayPaint() {
        mTextPaint.setTextSize(mDayTextSize);
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setColor(mGrayColor);
    }

    private void initDayBlackPaint() {
        mTextPaint.setTextSize(mDayTextSize);
        mTextPaint.setFakeBoldText(true);
        mTextPaint.setColor(mBlackColor);
    }

    private void initCountGoldPaint() {
        mTextPaint.setTextSize(mCountTextSize);
        mTextPaint.setFakeBoldText(false);
        mTextPaint.setColor(mGoldColor);
    }

    private void initCountBlack2Paint() {
        mTextPaint.setTextSize(mCountTextSize);
        mTextPaint.setFakeBoldText(false);
        mTextPaint.setColor(mBlack2Color);
    }

    private void initCountWhitePaint() {
        mTextPaint.setTextSize(mCountTextSize);
        mTextPaint.setFakeBoldText(false);
        mTextPaint.setColor(Color.WHITE);
    }

    private void initWhiteBgPaint() {
        mBgBorderPaint.setColor(Color.WHITE);
        mBgBorderPaint.setStyle(Paint.Style.FILL);
    }

    private void initGoldBorderPaint() {
        mBgBorderPaint.setColor(mGoldColor);
        mBgBorderPaint.setStyle(Paint.Style.STROKE);
        mBgBorderPaint.setStrokeWidth(ConvertUtils.dp2px(1));
    }

    private void initOvalPaint(int color) {
        mBgBorderPaint.setColor(color);
        mBgBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBgBorderPaint.setStrokeWidth(ConvertUtils.dp2px(1));
    }
}
