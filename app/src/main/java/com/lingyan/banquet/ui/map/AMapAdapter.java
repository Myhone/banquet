package com.lingyan.banquet.ui.map;

import androidx.annotation.NonNull;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lingyan.banquet.R;

/**
 * 地图下方搜索结果列表
 * Created by wyq on 2021/9/14.
 */

public class AMapAdapter extends BaseQuickAdapter<PoiItem, BaseViewHolder> {

    public AMapAdapter() {
        super(R.layout.item_map_search_bottom);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, PoiItem dto) {

        baseViewHolder
                .setText(R.id.poi_field_id, dto.getTitle())
                .setText(R.id.poi_value_id, dto.getAdName());

    }


}
