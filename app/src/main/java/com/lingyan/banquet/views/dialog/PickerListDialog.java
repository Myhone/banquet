package com.lingyan.banquet.views.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseDialog;
import com.lingyan.banquet.databinding.DialogInputReasonBinding;
import com.lingyan.banquet.databinding.DialogPickerListBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by _hxb on 2021/1/4.
 */

public class PickerListDialog extends BaseDialog {

    private final DialogPickerListBinding mBinding;
    private final List<String> mRecData;
    private ItemSelectedCallBack mItemSelectedCallBack;
    private int mSelectedPosition;
    private final MyAdapter mAdapter;

    public interface ItemSelectedCallBack {
        void onItemSelected(int position, String text, PickerListDialog dialog);
    }

    public PickerListDialog(@NonNull Context context) {
        super(context);
        mBinding = DialogPickerListBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mSelectedPosition = -1;
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            private Paint mPaint = new Paint();

            {
                mPaint.setColor(Color.parseColor("#F5F5F5"));
                mPaint.setAntiAlias(true);
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

            }

            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.onDraw(c, parent, state);
                int childCount = parent.getChildCount();

                for (int i = 0; i < childCount; i++) {
                    View view = parent.getChildAt(i);
                    int dividerHeight = ConvertUtils.dp2px(1);
                    float dividerTop = view.getBottom();
                    float dividerLeft = parent.getPaddingLeft();
                    float dividerBottom = view.getBottom() + dividerHeight;
                    float dividerRight = parent.getWidth() - parent.getPaddingRight();

                    c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
                }
            }

            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(1);
            }
        });
        mRecData = new ArrayList<>();
        mAdapter = new MyAdapter(mRecData);
        mBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (mItemSelectedCallBack != null) {
                    mItemSelectedCallBack.onItemSelected(i, mRecData.get(i), PickerListDialog.this);
                }
            }
        });
    }


    public PickerListDialog selectedPosition(int position) {
        mSelectedPosition = position;
        return this;
    }

    public PickerListDialog title(String text) {
        mBinding.tvTitle.setText(text);
        return this;
    }

    public PickerListDialog items(String... strings) {
        List<String> list = new ArrayList<>();
        if (strings != null && strings.length > 0) {
            Collections.addAll(list, strings);
        }
        items(list);
        return this;
    }

    public PickerListDialog items(List<String> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        mRecData.clear();
        mRecData.addAll(list);
        mAdapter.notifyDataSetChanged();
        return this;
    }

    public PickerListDialog itemSelectedCallBack(ItemSelectedCallBack callBack) {

        mItemSelectedCallBack = callBack;

        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.height = (int) (ScreenUtils.getAppScreenHeight() * 0.6f);
        attributes.width = ScreenUtils.getAppScreenWidth();
        attributes.gravity = Gravity.BOTTOM;
        window.setAttributes(attributes);
        window.setWindowAnimations(R.style.view_bottom_slide_anim);
    }


    private class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public MyAdapter(@Nullable List<String> data) {
            super(R.layout.item_picker_list, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder holder, String s) {
            holder.setText(R.id.tv_text, s);
            int layoutPosition = holder.getLayoutPosition();
            if (layoutPosition != mSelectedPosition) {
                holder.setTextColor(R.id.tv_text, Color.parseColor("#333333"));
            } else {
                holder.setTextColor(R.id.tv_text, Color.parseColor("#C08348"));
            }
        }
    }


}
