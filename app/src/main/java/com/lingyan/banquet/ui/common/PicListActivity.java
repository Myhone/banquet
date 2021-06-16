package com.lingyan.banquet.ui.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.App;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityPicListBinding;
import com.lingyan.banquet.event.DeleteImageEvent;
import com.lingyan.banquet.utils.MyImageUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/8.
 */

public class PicListActivity extends BaseActivity {

    private ActivityPicListBinding mBinding;
    private ArrayList<String> mPics;
    private MyAdapter mAdapter;
    private int mCode;

    public static void start(ArrayList<String> pics, int code) {
        Intent intent = new Intent(App.sApp, PicListActivity.class);
        intent.putExtra("pics", pics);
        intent.putExtra("code", code);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityPicListBinding.inflate(getLayoutInflater());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("查看照片");
        setContentView(mBinding.getRoot());
        mBinding.recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3, GridLayoutManager.VERTICAL, false));
        Intent intent = getIntent();
        mPics = intent.getStringArrayListExtra("pics");
        mCode = intent.getIntExtra("code", 0);
        if (mPics == null) {
            mPics = new ArrayList<>();
        }
        mAdapter = new MyAdapter(mPics);
        mBinding.recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                String s = mPics.get(i);
                ImageBrowseActivity.start(getContext(),mPics,i);
            }
        });

        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                String s = mPics.get(i);
                switch (view.getId()) {
                    case R.id.view_delete:
                        new MaterialDialog.Builder(getContext())
                                .title("提示")
                                .content("删除该照片")
                                .positiveText("确定")
                                .negativeText("取消")
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        mAdapter.remove(i);
                                        EventBus.getDefault().post(new DeleteImageEvent(mCode, s));
                                    }
                                })
                                .show();
                        break;
                }
            }
        });

    }


    private static class MyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public MyAdapter(@Nullable List<String> data) {
            super(R.layout.item_browse_pic, data);
        }

        @Override
        protected void convert(@NonNull BaseViewHolder holder, String s) {
            MyImageUtils.display(holder.getView(R.id.riv_image), s);
            holder.addOnClickListener(R.id.view_delete);

        }
    }
}
