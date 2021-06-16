package com.lingyan.banquet.ui.target.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.lingyan.banquet.R;
import com.lingyan.banquet.ui.target.bean.NetTargetList;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lingyan.banquet.views.CircleBarView;
import com.lingyan.banquet.views.WordImageLayout;
import com.makeramen.roundedimageview.RoundedImageView;
import com.umeng.commonsdk.debug.E;

import java.util.List;

/**
 * Created by _hxb on 2021/1/29.
 */

public class TargetAdapter extends BaseMultiItemQuickAdapter<NetTargetList.DataDTO, BaseViewHolder> {
    public TargetAdapter(@Nullable List<NetTargetList.DataDTO> data) {
        super(data);
        addItemType(1, R.layout.item_target_department);
        addItemType(2, R.layout.item_target_person);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, NetTargetList.DataDTO dto) {
        int itemType = dto.getItemType();
        String userOverNumber = dto.getUser_over_number();//完成数
        String userNumber = dto.getUser_number();//目标数
        String rateNum = dto.getRate_num();//比例%
        if (rateNum != null
                && !rateNum.contains("%")) {
            rateNum += "%";
        }

        String name = dto.getName();

        switch (itemType) {
            case 1: {
                holder
                        .setText(R.id.tv_name, name)
                        .setText(R.id.tv_complete, String.format("完成：%s/%s", userOverNumber, userNumber))
                        .setText(R.id.tv_progress, rateNum);
                CircleBarView circleBarView = holder.getView(R.id.cbv);
                circleBarView.setProgressNum(getProgress(rateNum), 0, 0);
                break;
            }
            case 2: {

                holder
                        .setText(R.id.tv_dept_name, dto.getDept_name())
                        .setText(R.id.tv_name, name)
                        .setText(R.id.tv_complete, String.format("完成：%s/%s", userOverNumber, userNumber))
                        .setText(R.id.tv_progress, rateNum);
                CircleBarView circleBarView = holder.getView(R.id.cbv);
                circleBarView.setProgressNum(getProgress(rateNum), 0, 0);

                RoundedImageView rivAvatar = holder.getView(R.id.riv_avatar);
                WordImageLayout wordImageLayout = holder.getView(R.id.word_avatar);
                if (StringUtils.isEmpty(dto.getAvatar())) {
                    rivAvatar.setVisibility(View.GONE);
                    wordImageLayout.setVisibility(View.VISIBLE);

                    wordImageLayout.setWord(dto.getAvatar_name());

                } else {
                    rivAvatar.setVisibility(View.VISIBLE);
                    wordImageLayout.setVisibility(View.GONE);
                    MyImageUtils.displayUseImageServer(rivAvatar,dto.getAvatar());
                }


                break;
            }
        }
    }

    private float getProgress(String string) {
        float f = 0;
        try {
            String substring = string.substring(0, string.length() - 1);
            f = Float.parseFloat(substring);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return f;
    }

}
