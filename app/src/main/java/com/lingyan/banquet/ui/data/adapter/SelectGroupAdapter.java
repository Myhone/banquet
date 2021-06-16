package com.lingyan.banquet.ui.data.adapter;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ObjectUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.data.bean.DepartBean;
import com.lingyan.banquet.ui.data.bean.PersonBean;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.views.WordImageLayout;

import java.util.List;

/**
 * Created by _hxb on 2021/1/31.
 */

public class SelectGroupAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public SelectGroupAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(0, R.layout.item_select_group_department);
        addItemType(1, R.layout.item_select_group_person);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, MultiItemEntity multiItemEntity) {
        if (multiItemEntity instanceof DepartBean) {
            DepartBean departBean = (DepartBean) multiItemEntity;
            holder
                    .setText(R.id.tv_name, departBean.getName())
                    .setText(R.id.tv_number, String.format("(%s人)", departBean.getAllNum()))
            ;

            holder.setImageResource(R.id.iv_selected, departBean.isSelected() ? R.mipmap.ic_ok_selected : R.mipmap.ic_circle_gray);

            holder
                    .addOnClickListener(R.id.iv_depart_children)
                    .addOnClickListener(R.id.iv_selected);


        } else if (multiItemEntity instanceof PersonBean) {
            PersonBean personBean = (PersonBean) multiItemEntity;
            holder.setText(R.id.tv_name, personBean.getRealname());

            holder.setImageResource(R.id.iv_selected, personBean.isSelected() ? R.mipmap.ic_ok_selected : R.mipmap.ic_circle_gray);
            holder
                    .addOnClickListener(R.id.iv_selected)
            ;

            String avatar = personBean.getAvatar();
            if (ObjectUtils.isNotEmpty(avatar)) {
                holder
                        .setGone(R.id.civ_avatar,true)
                        .setGone(R.id.word_avatar,false);
                MyImageUtils.displayUseImageServer(holder.getView(R.id.civ_avatar),avatar);
            } else {
                holder
                        .setGone(R.id.civ_avatar,false)
                        .setGone(R.id.word_avatar,true);
                String avatarName = personBean.getAvatar_name();
                WordImageLayout wordImageLayout = holder.getView(R.id.word_avatar);
                wordImageLayout.setWord(avatarName);
            }

        }
    }
}
