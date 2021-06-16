package com.lingyan.banquet.views;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.databinding.LayoutImageBinding;
import com.lingyan.banquet.ui.common.ImageBrowseActivity;
import com.lingyan.banquet.utils.MyImageUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by _hxb on 2021/2/19.
 */

public class ImageLayout extends FrameLayout {
    private LayoutImageBinding mBinding;
    private ArrayList<String> mRecData;
    private ImageAdapter mAdapter;

    private View.OnClickListener mAddViewClickListener;
    private boolean isPreview;
    private RecyclerView.ItemDecoration mItemDecoration = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.right = ConvertUtils.dp2px(10);
        }
    };

    public ImageLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public ImageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ImageLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mBinding = LayoutImageBinding.inflate(getLayoutInflate());
        addView(mBinding.getRoot());
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false));
        mRecData = new ArrayList<>();
        if (!isPreview) {
            mRecData.add(AppUtils.getAppPackageName());
        }
        mAdapter = new ImageAdapter(mRecData);
        mBinding.recyclerView.addItemDecoration(mItemDecoration);
        mBinding.recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.bottom = ConvertUtils.dp2px(10);
            }
        });
        mBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                String s = mRecData.get(position);


                switch (view.getId()) {
                    case R.id.fl_delete:
                        mAdapter.remove(position);
                        break;
                    case R.id.iv_pic:
                        if (AppUtils.getAppPackageName().equals(s)) {
                            //添加
                            if (mAddViewClickListener != null) {
                                mAddViewClickListener.onClick(view);
                            }
                        } else {
                            //查看
                            ImageBrowseActivity.start(getContext(), s);
                        }

                        break;
                }
            }
        });
    }

    public void setAddViewClickListener(OnClickListener listener) {
        mAddViewClickListener = listener;
    }

    public void clear() {
        mRecData.clear();
        if (!isPreview) {
            mRecData.add(AppUtils.getAppPackageName());
        }
        mAdapter.setNewData(mRecData);
    }

    public void add(String s) {
        if (!isPreview) {
            int size = mRecData.size();
            mAdapter.addData(size - 1, s);
        } else {
            mAdapter.addData(s);
        }
    }

    public void add(Collection<String> collection) {
        if (!isPreview) {
            int size = mRecData.size();
            mAdapter.addData(size - 1, collection);
        } else {
            mAdapter.addData(collection);
        }


    }

    public List<String> getImageList() {
        ArrayList list = new ArrayList();
        for (String s : mRecData) {
            if (!StringUtils.equals(s, AppUtils.getAppPackageName())) {
                list.add(s);
            }
        }
        return list;
    }

    private LayoutInflater getLayoutInflate() {
        return LayoutInflater.from(getContext());
    }

    public void setPreview(boolean b) {
        isPreview = b;
    }

    private class ImageAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public ImageAdapter(@Nullable List<String> data) {
            super(R.layout.item_pic, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            if (AppUtils.getAppPackageName().equals(item)) {
                helper
                        .setImageResource(R.id.iv_pic, R.mipmap.ic_add_image)
                        .setGone(R.id.fl_delete, false);
                ;

            } else {
                MyImageUtils.display(helper.getView(R.id.iv_pic), item);
                helper.setGone(R.id.fl_delete, !isPreview);
            }

            helper
                    .addOnClickListener(R.id.fl_delete)
                    .addOnClickListener(R.id.iv_pic);
        }
    }
}
