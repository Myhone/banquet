package com.lingyan.banquet.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.lingyan.banquet.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SideIndexBar extends View {
    private static final String[] DEFAULT_INDEX_ITEMS = {" "};

    private List<String> mIndexItems;
    private float mItemHeight; //每个index的高度
    private int mTextSize;      //sp
    private int mTextColor;
    private int mTextTouchedColor;
    private int mCurrentIndex = -1;

    private Paint mPaint;
    private Paint mTouchedPaint;

    private int mWidth;
    private int mHeight;
    private float mTopMargin;   //居中绘制，文字绘制起点和控件顶部的间隔

    private TextView mOverlayTextView;
    private OnIndexTouchedChangedListener mOnIndexChangedListener;

 /*   private int navigationBarHeight;

    public void setNavigationBarHeight(int height) {
        this.navigationBarHeight = height;
    }*/

    public SideIndexBar(Context context) {
        this(context, null);
    }

    public SideIndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SideIndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mIndexItems = new ArrayList<>();
        mIndexItems.addAll(Arrays.asList(DEFAULT_INDEX_ITEMS));


        mTextSize = ConvertUtils.dp2px(15);
        mTextColor = getResources().getColor(R.color.textColorGray);
        mTextTouchedColor = getResources().getColor(R.color.gold);
        mItemHeight = ConvertUtils.dp2px(25);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

        mTouchedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTouchedPaint.setTextSize(mTextSize);
        mTouchedPaint.setColor(mTextTouchedColor);
    }

    public void setLetterList(List<String> list) {
        mIndexItems.clear();
        mIndexItems.add(0, "索引");
        mIndexItems.addAll(list);
        postInvalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = mIndexItems.size();
        mTopMargin = (getMeasuredHeight() - (size * mItemHeight)) / 2;
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        String index;
        for (int i = 0; i < size; i++) {
            index = mIndexItems.get(i);
            Paint.FontMetrics fm = mPaint.getFontMetrics();
            canvas.drawText(index,
                    (mWidth - mPaint.measureText(index)) / 2,
                    (fm.bottom - fm.top) / 2 + mItemHeight * i + mTopMargin,
                    i == mCurrentIndex ? mTouchedPaint : mPaint);
        }
    }

   /* @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        if (Math.abs(h - oldh) == navigationBarHeight) {
            //底部导航栏隐藏或显示
            mHeight = h;
        } else {
            //避免软键盘弹出时挤压
            mHeight = Math.max(getHeight(), oldh);
        }


    }*/

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
//                int touchedIndex = (int) (y / mItemHeight);


                int indexSize = mIndexItems.size();

                if (y < mTopMargin || y > (mTopMargin + indexSize * mItemHeight)) {
                    break;
                }
                int touchedIndex = (int) ((y - mTopMargin) / mItemHeight);
                if (touchedIndex < 0) {
                    touchedIndex = 0;
                } else if (touchedIndex >= indexSize) {
                    touchedIndex = indexSize - 1;
                }
                if (mOnIndexChangedListener != null && touchedIndex >= 0 && touchedIndex < indexSize) {
                    if (touchedIndex != mCurrentIndex) {
                        mCurrentIndex = touchedIndex;
                        String letter = mIndexItems.get(touchedIndex);
                        if (mOverlayTextView != null && !TextUtils.isEmpty(letter)) {
                            mOverlayTextView.setVisibility(VISIBLE);
                            mOverlayTextView.setText(letter);
                        }
                        mOnIndexChangedListener.onIndexChanged(letter, touchedIndex);
                        invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mCurrentIndex = -1;
                if (mOverlayTextView != null) {
                    mOverlayTextView.setVisibility(GONE);
                }
                invalidate();
                break;
        }
        return true;
    }

    public SideIndexBar setOverlayTextView(TextView overlay) {
        this.mOverlayTextView = overlay;
        return this;
    }

    public SideIndexBar setOnIndexChangedListener(OnIndexTouchedChangedListener listener) {
        this.mOnIndexChangedListener = listener;
        return this;
    }

    public interface OnIndexTouchedChangedListener {
        void onIndexChanged(String index, int position);
    }
}
