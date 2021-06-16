package com.lingyan.banquet.views.dialog;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseDialog;
import com.lingyan.banquet.databinding.DialogTwoLineTabSelectBinding;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetChildHall;
import com.lingyan.banquet.ui.banquet.bean.NetBanquetType;
import com.lingyan.banquet.views.dialog.adapter.RestaurantNameAdapter;
import com.lingyan.banquet.views.dialog.adapter.RestaurantTitleAdapter;
import com.lingyan.banquet.views.dialog.bean.TwoLineSelectBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 宴会厅选择
 * Created by _hxb on 2021/1/14.
 */

public class TwoLineTabSelectDialog extends BaseDialog {

    private final List<TwoLineSelectBean> mRecDataTitle;
    private final List<TwoLineSelectBean> mRecDataName;
    private final HashMap<String, List<TwoLineSelectBean>> mHashMap;

    private final RestaurantTitleAdapter mTitleAdapter;
    private final DialogTwoLineTabSelectBinding mBinding;
    private final RestaurantNameAdapter mNameAdapter;

    private boolean isSingleMode = true;


    public TwoLineTabSelectDialog(@NonNull Context context) {
        super(context);
        mBinding = DialogTwoLineTabSelectBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mRecDataTitle = new ArrayList<>();
        mTitleAdapter = new RestaurantTitleAdapter(mRecDataTitle);
        mBinding.recyclerViewTitle.setAdapter(mTitleAdapter);
        mBinding.recyclerViewTitle.setLayoutManager(new LinearLayoutManager(context));
        mHashMap = new HashMap<>();


        mBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> idList = new ArrayList<>();
                List<String> nameList = new ArrayList<>();
                Set<Map.Entry<String, List<TwoLineSelectBean>>> entries = mHashMap.entrySet();
                for (Map.Entry<String, List<TwoLineSelectBean>> entry : entries) {
                    List<TwoLineSelectBean> list = entry.getValue();
                    if (ObjectUtils.isNotEmpty(list)) {
                        for (TwoLineSelectBean bean : list) {
                            if (bean.isSelected()) {
                                String id = bean.getId();
                                String title = bean.getTitle();
                                idList.add(id);
                                nameList.add(title);
                            }
                        }
                    }
                }
                if(mOnMultipleSelectListener!=null){
                    mOnMultipleSelectListener.OnMultipleSelect(idList, nameList, TwoLineTabSelectDialog.this);
                }

            }
        });

        if (isSingleMode) {
            mBinding.tvConfirm.setVisibility(View.GONE);
        } else {
            mBinding.tvConfirm.setVisibility(View.VISIBLE);
        }


        mRecDataName = new ArrayList<>();
        mNameAdapter = new RestaurantNameAdapter(mRecDataName);
        mBinding.recyclerViewTab.setAdapter(mNameAdapter);
        mBinding.recyclerViewTab.setLayoutManager(new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false));
        mBinding.recyclerViewTab.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = ConvertUtils.dp2px(10);
                outRect.bottom = ConvertUtils.dp2px(5);
            }
        });

        mTitleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                TwoLineSelectBean bean = mRecDataTitle.get(i);
                for (TwoLineSelectBean titleBean : mRecDataTitle) {
                    if (bean == titleBean) {
                        titleBean.setSelected(true);
                    } else {
                        titleBean.setSelected(false);
                    }
                }
                mTitleAdapter.notifyDataSetChanged();

                List<TwoLineSelectBean> list = mHashMap.get(bean.getId());
                mRecDataName.clear();
                mRecDataName.addAll(list);
                mNameAdapter.notifyDataSetChanged();

            }
        });

        mNameAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                TwoLineSelectBean restNameBean = mRecDataName.get(i);
                if (isSingleMode) {
                    if (restNameBean.isSelected()) {
                        ToastUtils.showShort("已选择");
                        return;
                    }
                    if (mOnHallSelectListener != null) {
                        mOnHallSelectListener.OnHallSelect(restNameBean.getId(), restNameBean.getTitle(), TwoLineTabSelectDialog.this);
                    }
                } else {
                    restNameBean.setSelected(!restNameBean.isSelected());
                    baseQuickAdapter.notifyDataSetChanged();
                }
            }


        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (ScreenUtils.getAppScreenWidth());
        attributes.height = (int) (ScreenUtils.getAppScreenHeight() * 0.6f);
        attributes.gravity = Gravity.BOTTOM;
        window.setAttributes(attributes);
        window.setWindowAnimations(R.style.view_bottom_slide_anim);
    }


    public void setSingleMode(boolean singleMode) {
        isSingleMode = singleMode;
        if (isSingleMode) {
           mBinding.tvConfirm.setVisibility(View.GONE);
        } else {
            mBinding.tvConfirm.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(String text) {
        mBinding.tvTitle.setText(text);

    }

    /**
     * 宴会厅
     *
     * @param hallList
     * @param existHallList
     * @return
     */
    public boolean setData(List<NetBanquetChildHall.DataDTO> hallList, List<String> existHallList) {
        boolean hasData = false;
        if (ObjectUtils.isEmpty(hallList)) {
            return hasData;
        }

        mRecDataTitle.clear();
        mRecDataName.clear();
        mHashMap.clear();
        mNameAdapter.notifyDataSetChanged();
        mTitleAdapter.notifyDataSetChanged();

        for (NetBanquetChildHall.DataDTO dataDTO : hallList) {
            TwoLineSelectBean tittleBean = new TwoLineSelectBean();
            tittleBean.setId(dataDTO.getId());
            tittleBean.setTitle(dataDTO.getName());
            tittleBean.setSelected(false);
            List<NetBanquetChildHall.DataDTO.ChildrenDTO> children = dataDTO.getChildren();
            List<TwoLineSelectBean> childList = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(children)) {
                for (NetBanquetChildHall.DataDTO.ChildrenDTO child : children) {
                    TwoLineSelectBean childBean = new TwoLineSelectBean();
                    childBean.setTitle(child.getName());
                    childBean.setId(child.getId());
                    childList.add(childBean);
                    childBean.setSelected(false);
                    if (ObjectUtils.isNotEmpty(existHallList)) {
                        for (String exit : existHallList) {
                            if (StringUtils.equals(child.getId(), exit)) {
                                childBean.setSelected(true);
                                break;
                            }
                        }
                    }
                }
            }
            if (ObjectUtils.isNotEmpty(childList)) {
                mRecDataTitle.add(tittleBean);
                mHashMap.put(dataDTO.getId(), childList);
            }
        }
        if (ObjectUtils.isNotEmpty(mRecDataTitle)) {
            TwoLineSelectBean bean = mRecDataTitle.get(0);
            bean.setSelected(true);
            String id = bean.getId();
            List<TwoLineSelectBean> list = mHashMap.get(id);
            mRecDataName.addAll(list);
            hasData = true;
        }


        mNameAdapter.notifyDataSetChanged();
        mTitleAdapter.notifyDataSetChanged();

        return hasData;
    }

    /**
     * 宴会类型
     *
     * @param treeList
     * @param existHallList
     * @return
     */
    public boolean setData2(List<NetBanquetType.DataDTO> treeList, List<String> existHallList) {
        boolean hasData = false;
        if (ObjectUtils.isEmpty(treeList)) {
            return hasData;
        }

        mRecDataTitle.clear();
        mRecDataName.clear();
        mHashMap.clear();
        mNameAdapter.notifyDataSetChanged();
        mTitleAdapter.notifyDataSetChanged();


        for (NetBanquetType.DataDTO dataDTO : treeList) {
            TwoLineSelectBean tittleBean = new TwoLineSelectBean();

            tittleBean.setId(dataDTO.getId());
            tittleBean.setTitle(dataDTO.getName());
            tittleBean.setSelected(false);
            List<NetBanquetType.DataDTO.ChildrenDTO> children = dataDTO.getChildren();
            List<TwoLineSelectBean> childList = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(children)) {
                for (NetBanquetType.DataDTO.ChildrenDTO child : children) {
                    TwoLineSelectBean childBean = new TwoLineSelectBean();
                    childBean.setTitle(child.getName());
                    childBean.setId(child.getId());
                    childList.add(childBean);
                    childBean.setSelected(false);
                    if (ObjectUtils.isNotEmpty(existHallList)) {
                        for (String exit : existHallList) {
                            if (StringUtils.equals(child.getId(), exit)) {
                                childBean.setSelected(true);
                                break;
                            }
                        }
                    }
                }
            }
            if (ObjectUtils.isNotEmpty(childList)) {
                mRecDataTitle.add(tittleBean);
                mHashMap.put(dataDTO.getId(), childList);
            }

        }

        if (ObjectUtils.isNotEmpty(mRecDataTitle)) {
            TwoLineSelectBean bean = mRecDataTitle.get(0);
            bean.setSelected(true);
            String id = bean.getId();
            List<TwoLineSelectBean> list = mHashMap.get(id);
            mRecDataName.addAll(list);
            hasData = true;
        }


        mNameAdapter.notifyDataSetChanged();
        mTitleAdapter.notifyDataSetChanged();
        return hasData;
    }


    public static interface OnHallSelectListener {
        void OnHallSelect(String id, String name, TwoLineTabSelectDialog dialog);
    }

    public static interface OnMultipleSelectListener {
        void OnMultipleSelect(List<String> idList, List<String> nameList, TwoLineTabSelectDialog dialog);
    }


    private OnHallSelectListener mOnHallSelectListener;
    private OnMultipleSelectListener mOnMultipleSelectListener;

    public void setOnMultipleSelectListener(OnMultipleSelectListener multipleSelectListener) {
        mOnMultipleSelectListener = multipleSelectListener;
    }

    public void setOnHallSelectListener(OnHallSelectListener listener) {
        mOnHallSelectListener = listener;
    }
}
