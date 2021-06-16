package com.lingyan.banquet.views.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.blankj.utilcode.util.ScreenUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseDialog;
import com.lingyan.banquet.databinding.DialogSelectDayBinding;
import com.lingyan.banquet.views.MyMonthView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by _hxb on 2021/1/9.
 */

public class SelectDayDialog extends BaseDialog {

    private final DialogSelectDayBinding mBinding;
    private final List<Calendar> mRecData;
    private final DayAdapter mAdapter;


    public SelectDayDialog(@NonNull Context context) {
        super(context);

        mBinding = DialogSelectDayBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());


        Calendar nowCalendar = Calendar.getInstance();


        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecData = new ArrayList<>();
        mAdapter = new DayAdapter(mRecData);
        mBinding.recyclerView.setAdapter(mAdapter);



        for (int i = -24; i <= 24; i++) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.MONTH, i);
            mAdapter.addData(c);
        }

        mBinding.recyclerView.scrollToPosition(24);
    }

    public void setOnDayClick(MyMonthView.OnDayClick onDayClick) {
        mAdapter.setOnDayClick(onDayClick);
    }


    private static class DayAdapter extends BaseQuickAdapter<Calendar, BaseViewHolder> {

        private MyMonthView.OnDayClick mOnDayClick;

        public DayAdapter(@Nullable List<Calendar> data) {
            super(R.layout.item_select_day, data);
        }

        public void setOnDayClick(MyMonthView.OnDayClick onDayClick) {
            mOnDayClick = onDayClick;
        }

        @Override
        protected void convert(@NonNull BaseViewHolder helper, Calendar item) {
            MyMonthView myMonthView = helper.getView(R.id.month_view);
            myMonthView.setCalendar(item);
            myMonthView.setOnDayClick(mOnDayClick);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = ScreenUtils.getAppScreenWidth();
        attributes.height = (int) (ScreenUtils.getAppScreenHeight() * 0.8);
        attributes.gravity = Gravity.BOTTOM;
        window.setAttributes(attributes);
        window.setWindowAnimations(R.style.view_bottom_slide_anim);
    }
}
