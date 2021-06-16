package com.lingyan.banquet.ui.data.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.data.bean.NetPkData;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.views.WordImageLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by _hxb on 2021/2/28.
 */

public class PkListAdapter extends BaseQuickAdapter<NetPkData.DataDTO.DataInfoDTO, BaseViewHolder> {
    public PkListAdapter(@Nullable List<NetPkData.DataDTO.DataInfoDTO> data) {
        super(R.layout.item_pk_list, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, NetPkData.DataDTO.DataInfoDTO dto) {

        holder
                .setText(R.id.tv_sort, dto.getSort() + "")
                .setText(R.id.tv_user_name, dto.getUser_name() + "")
                .setText(R.id.tv_count, dto.getCount() + "")
                .setGone(R.id.word_avatar, false)
                .setGone(R.id.civ_avatar, false)
        ;

        String avatar = dto.getAvatar();
        String avatarName = dto.getAvatar_name();
        if (StringUtils.isEmpty(avatar)) {
            WordImageLayout wordImageLayout = holder.getView(R.id.word_avatar);
            wordImageLayout.setVisibility(View.VISIBLE);
            wordImageLayout.setWord(avatarName);
            wordImageLayout.setCorners(ConvertUtils.dp2px(90));
        } else {
            CircleImageView imageView = holder.getView(R.id.civ_avatar);
            imageView.setVisibility(View.VISIBLE);
            MyImageUtils.displayUseImageServer(imageView, avatar);
        }

    }
}
