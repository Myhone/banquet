package com.lingyan.banquet.views;

import android.content.Context;
import android.view.View;

import androidx.annotation.LayoutRes;

import com.lingyan.banquet.R;


/**
 * Created by _hxb on 2020/8/3.
 */
public class AdapterLoadingView {

    private Context mContext;

    public AdapterLoadingView(Context context) {
        mContext = context;
    }
    public View getLoadingView(@LayoutRes int resource) {
        View view = View.inflate(mContext, resource, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    public View getLoadingView() {
        return getLoadingView(R.layout.layout_loading_view);
    }

    public View getEmptyView(View.OnClickListener onClickListener) {
        View view = View.inflate(mContext, R.layout.layout_load_no_content, null);
        view.setOnClickListener(onClickListener);
        return view;
    }

    public View getErrorView(View.OnClickListener onClickListener) {
        View view = View.inflate(mContext, R.layout.layout_load_net_error, null);
        view.setOnClickListener(onClickListener);
        return view;
    }
}
