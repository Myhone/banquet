package com.lingyan.banquet.ui.data.controller;

import android.view.View;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lingyan.banquet.databinding.LayoutSuccessOrderAnalyzeBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.ui.data.DataHomeActivity;
import com.lingyan.banquet.ui.data.bean.ConditionFilter;
import com.lingyan.banquet.ui.data.bean.NetDataSuccess;
import com.lingyan.banquet.ui.data.bean.NetDataTarget;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * Created by _hxb on 2021/2/21.
 */

public class DataSuccessController {
    private final LayoutSuccessOrderAnalyzeBinding mBinding;
    private final DataHomeActivity mActivity;

    public DataSuccessController(LayoutSuccessOrderAnalyzeBinding binding, DataHomeActivity activity) {
        mBinding = binding;
        mActivity = activity;
        mBinding.flDataSuccessRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh(activity.getConditionFilter());
            }
        });
        mBinding.tv1.setText("");
        mBinding.tv2.setText("");
        mBinding.tv3.setText("");
        mBinding.tv4.setText("");
        mBinding.tv5.setText("");
    }

    public void refresh(ConditionFilter condition) {
        String json = GsonUtils.toJson(condition);
        JsonObject jo = (JsonObject) JsonParser.parseString(json);
        jo.addProperty("order", 6);

        OkGo.<NetDataSuccess>post(HttpURLs.screenData1)
                .upJson(jo.toString())
                .execute(new JsonCallback<NetDataSuccess>() {
                    @Override
                    public void onSuccess(Response<NetDataSuccess> response) {
                        NetDataSuccess.DataDTO data = response.body().getData();
                        if(data==null){
                            return;
                        }
                        NetDataSuccess.DataDTO.PArrDTO pArr = data.getP_arr();
                        mBinding.tv1.setText(pArr.getData1());
                        mBinding.tv2.setText(pArr.getData2());
                        mBinding.tv3.setText(pArr.getData3());
                        mBinding.tv4.setText(pArr.getData4());
                        mBinding.tv5.setText(pArr.getData5());

                     try {
                         List<NetDataSuccess.DataDTO.ArrDTO> list = data.getArr();

                         mBinding.tvNameChance.setText(list.get(0).getName()+"("+list.get(0).getValue()+")");
                         mBinding.tvNameIntent.setText(list.get(1).getName()+"("+list.get(1).getValue()+")");
                         mBinding.tvNameLock.setText(list.get(2).getName()+"("+list.get(2).getValue()+")");
                         mBinding.tvNameSign.setText(list.get(3).getName()+"("+list.get(3).getValue()+")");
                         mBinding.tvExec.setText(list.get(4).getName()+"("+list.get(4).getValue()+")");
                         mBinding.tvComplete.setText(list.get(5).getName()+"("+list.get(5).getValue()+")");
                     }catch (Exception e){
                         e.printStackTrace();
                     }



                    }
                });
    }
}
