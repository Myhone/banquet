package com.lingyan.banquet.views.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lingyan.banquet.base.BaseDialog;
import com.lingyan.banquet.databinding.DialogInputReasonBinding;
import com.lingyan.banquet.databinding.DialogLoseOrderBinding;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.utils.GroupViewUtils;
import com.lingyan.banquet.views.dialog.bean.NetLoseOrderData;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.HashMap;

/**
 * Created by _hxb on 2021/1/4.
 */

public class LoseOrderDialog extends BaseDialog {

    private final DialogLoseOrderBinding mBinding;
    private final GroupViewUtils mGroupViewUtils;
    private int type;
    private String id;

    public LoseOrderDialog(@NonNull Context context) {
        super(context);
        mBinding = DialogLoseOrderBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.tvConfirm.setVisibility(View.INVISIBLE);
        mBinding.llMoneyContainer.setVisibility(View.GONE);
        mBinding.llMoneyContainer.setVisibility(View.GONE);
        mBinding.llMoneyContainerBottomLine.setVisibility(View.GONE);

        mGroupViewUtils = new GroupViewUtils();
        mGroupViewUtils.add("is_refund", mBinding.tvRefund, "1", false);
        mGroupViewUtils.add("is_refund", mBinding.tvNotRefund, "0", false);

        mBinding.tvRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupViewUtils.select("is_refund", "1");
                mBinding.llMoneyContainer.setVisibility(View.VISIBLE);
                mBinding.llMoneyContainerBottomLine.setVisibility(View.VISIBLE);
            }
        });
        mBinding.tvNotRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGroupViewUtils.select("is_refund", "0");
                mBinding.llMoneyContainer.setVisibility(View.GONE);
                mBinding.llMoneyContainerBottomLine.setVisibility(View.GONE);
            }
        });


        mGroupViewUtils.add("reason_status", mBinding.tvReasonChange, "1");
        mGroupViewUtils.add("reason_status", mBinding.tvReasonCancel, "2");

        mBinding.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mBinding.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> selectedValueMap = mGroupViewUtils.getSelectedValueMap();

                String isRefund = selectedValueMap.get("is_refund");
                String reasonStatus = selectedValueMap.get("reason_status");
                if (isRefund == null) {
                    ToastUtils.showShort("请选择是否退款");
                    return;
                }
                if (reasonStatus == null) {
                    ToastUtils.showShort("请选择是否丢单原因");
                    return;
                }
                String money = null;
                if (StringUtils.equals(isRefund, "1")) {
                    money = mBinding.tvMoney.getText().toString();
                }

                OkGo.<NetBaseResp>post(HttpURLs.saveLoseOrder)
                        .params("id", getId())
                        .params("type", getType())
                        .params("is_refund", isRefund)
                        .params("money", money)
                        .params("reason_status", reasonStatus)
                        .params("reason", mBinding.etContent.getText().toString().trim())
                        .execute(new JsonCallback<NetBaseResp>() {
                            @Override
                            public void onSuccess(Response<NetBaseResp> response) {
                                NetBaseResp body = response.body();
                                String msg = body.getMsg();
                                int code = body.getCode();
                                ToastUtils.showShort(msg);
                                if (code == 200) {
                                    dismiss();
                                }
                            }
                        });


            }
        });

    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void show() {
        super.show();
        OkGo.<NetLoseOrderData>post(HttpURLs.lostOrderInfo)
                .params("id", getId())
                .execute(new JsonCallback<NetLoseOrderData>() {
                    @Override
                    public void onSuccess(Response<NetLoseOrderData> response) {
                        mBinding.tvConfirm.setVisibility(View.VISIBLE);
                        NetLoseOrderData body = response.body();
                        NetLoseOrderData.DataDTO data = body.getData();
                        if (data != null) {
                            mBinding.tvMoney.setText(data.getMoney());
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
        attributes.gravity = Gravity.BOTTOM;
        window.setAttributes(attributes);
        useDefaultAnim();
    }
}
