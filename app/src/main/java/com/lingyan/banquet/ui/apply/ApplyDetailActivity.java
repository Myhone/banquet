package com.lingyan.banquet.ui.apply;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.JsonObject;
import com.lingyan.banquet.App;
import com.lingyan.banquet.R;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityApplyDetailBinding;
import com.lingyan.banquet.databinding.ActivityBanquetOrderDetailBinding;
import com.lingyan.banquet.databinding.ItemApplyBinding;
import com.lingyan.banquet.databinding.ItemApplyStepBinding;
import com.lingyan.banquet.event.ApplyRecordEvent;
import com.lingyan.banquet.global.ApplyRecordType;
import com.lingyan.banquet.global.BanquetCelebrationType;
import com.lingyan.banquet.global.HttpURLs;
import com.lingyan.banquet.net.DialogJsonCallBack;
import com.lingyan.banquet.net.JsonCallback;
import com.lingyan.banquet.net.NetBaseResp;
import com.lingyan.banquet.ui.apply.bean.ApplyStepBean;
import com.lingyan.banquet.ui.apply.bean.NetApplyDetailInfo;
import com.lingyan.banquet.ui.apply.dialog.InputRefuseReasonDialog;
import com.lingyan.banquet.ui.banquet.BanquetStepManagerActivity;
import com.lingyan.banquet.ui.celebration.CelStepManagerActivity;
import com.lingyan.banquet.ui.order.OrderDetailActivity;
import com.lingyan.banquet.utils.MyImageUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/1/3.
 */

public class ApplyDetailActivity extends BaseActivity implements OnRefreshListener {

    private ActivityApplyDetailBinding mBinding;
    private String mId;
    private String mBy;

    public static void start(String id, String by) {
        Intent intent = new Intent(App.sApp, ApplyDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("by", by);
        ActivityUtils.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mId = intent.getStringExtra("id");
        mBy = intent.getStringExtra("by");


        mBinding = ActivityApplyDetailBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("审批详情");
        mBinding.tvRemoveCheck.setVisibility(View.GONE);
        mBinding.llBottomActionContainer.setVisibility(View.GONE);
        if (StringUtils.equals(mBy, ApplyRecordType.TYPE_SEND)) {
            //发起的
            mBinding.tvRemoveCheck.setVisibility(View.VISIBLE);
        } else if (StringUtils.equals(mBy, ApplyRecordType.TYPE_RECEIVED)) {
            //收到的
            mBinding.llBottomActionContainer.setVisibility(View.VISIBLE);
        }

        //驳回
        mBinding.tvExamineCheckRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputRefuseReasonDialog dialog = new InputRefuseReasonDialog(getActivity());
                dialog.getBinding().tvConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //点击了确认驳回
                        String text = dialog.getBinding().etText.getText().toString();
                        if (StringUtils.isTrimEmpty(text)) {
                            ToastUtils.showShort("请输入驳回理由");
                            return;
                        }
                        OkGo.<NetBaseResp>post(HttpURLs.examineCheck)
                                .params("id", mId)
                                .params("status", 2)
                                .params("content", text)
                                .execute(new JsonCallback<NetBaseResp>() {
                                    @Override
                                    public void onSuccess(Response<NetBaseResp> response) {
                                        NetBaseResp body = response.body();
                                        ToastUtils.showShort(body.getMsg());
                                        onRefresh(mBinding.refreshLayout);
                                    }
                                });
                    }
                });
                dialog.show();
            }
        });
        //通过
        mBinding.tvExamineCheckPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("提示")
                        .content("确定同意该审批吗？")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                OkGo.<NetBaseResp>post(HttpURLs.examineCheck)
                                        .params("id", mId)
                                        .params("status", 1)
                                        .execute(new JsonCallback<NetBaseResp>() {
                                            @Override
                                            public void onSuccess(Response<NetBaseResp> response) {
                                                NetBaseResp body = response.body();
                                                ToastUtils.showShort(body.getMsg());
                                                onRefresh(mBinding.refreshLayout);
                                                dialog.dismiss();
                                                EventBus.getDefault().post(new ApplyRecordEvent());
                                            }
                                        });
                            }
                        })
                        .show();
            }
        });
        //撤销
        mBinding.tvRemoveCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog.Builder(getActivity())
                        .title("提示")
                        .content("确定撤销该审批吗？")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                OkGo.<NetBaseResp>post(HttpURLs.examineRevokeCheck)
                                        .params("id", mId)
                                        .execute(new JsonCallback<NetBaseResp>() {
                                            @Override
                                            public void onSuccess(Response<NetBaseResp> response) {
                                                NetBaseResp body = response.body();
                                                ToastUtils.showShort(body.getMsg());
                                                onRefresh(mBinding.refreshLayout);
                                                dialog.dismiss();
                                                EventBus.getDefault().post(new ApplyRecordEvent());
                                            }
                                        });
                            }
                        })
                        .show();
            }
        });

        mBinding.refreshLayout.setOnRefreshListener(this);
        initUI();
        onRefresh(mBinding.refreshLayout);
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", mId);
        OkGo.<NetApplyDetailInfo>post(HttpURLs.examineInfo)
                .upJson(jsonObject.toString())
                .execute(new JsonCallback<NetApplyDetailInfo>() {
                    @Override
                    public void onSuccess(Response<NetApplyDetailInfo> response) {
                        NetApplyDetailInfo.DataDTO data = response.body().getData();
                        if (data == null) {
                            return;
                        }
                        String checkStatus = data.getCheck_status();
                        //0-审批中 1-审批通过 2-审批驳回 3-撤销
                        mBinding.tvRemoveCheck.setVisibility(View.GONE);
                        mBinding.llBottomActionContainer.setVisibility(View.GONE);
                        if (StringUtils.equals(mBy, ApplyRecordType.TYPE_SEND)) {
                            //发起的
                            if (StringUtils.equals(checkStatus, "0")) {
                                mBinding.tvRemoveCheck.setVisibility(View.VISIBLE);
                            }

                        } else if (StringUtils.equals(mBy, ApplyRecordType.TYPE_RECEIVED)) {
                            //收到的
                            if (StringUtils.equals(checkStatus, "0")) {
                                mBinding.llBottomActionContainer.setVisibility(View.VISIBLE);
                            }
                        }


                        String banquetId = data.getBanquet_id();
                        mBinding.tvCheckStatus.setText(data.getCheck_status_name());
                        mBinding.tvTitle.setText(data.getTitle());
                        mBinding.tvCode.setText(data.getId());
                        mBinding.tvReason.setText(data.getReason());
                        mBinding.tvContractNo.setText(banquetId);
                        mBinding.tvRealName.setText(data.getReal_name());
                        mBinding.tvDateR.setText(data.getDate_r());
                        mBinding.tvHallList.setText(data.getHall_list());
                        mBinding.tvIntentManName.setText(data.getIntent_man_name());

                        mBinding.tvOrderDetail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String type = data.getBanquet_type();
                                OrderDetailActivity.start(HttpURLs.banquetOrderInfo,banquetId,Integer.parseInt(type));
                            }
                        });
                        mBinding.llStepContainer.removeAllViews();
                        List<NetApplyDetailInfo.DataDTO.StepListDTO> stepList = data.getStepList();
                        if (ObjectUtils.isEmpty(stepList)) {
                            return;
                        }

                        List<ApplyStepBean> applyStepBeanList = new ArrayList<>();
                        for (int i = 0; i < stepList.size(); i++) {
                            NetApplyDetailInfo.DataDTO.StepListDTO dto = stepList.get(i);
                            ApplyStepBean bean = new ApplyStepBean();
                            bean.avatarName = dto.getAvatar_name();
                            bean.realName = dto.getReal_name();
                            bean.checkType = dto.getCheck_type();
                            bean.checkContent=dto.getCheck_content();
                            if (i == 0) {
                                bean.checkType = "1";//提交人应该是个对勾
                            }
                            bean.title = dto.getTitle();
                            bean.time = dto.getTime();
                            bean.avatar=dto.getAvatar();
                            applyStepBeanList.add(bean);
                        }

                        int size = applyStepBeanList.size();
                        for (int i = 0; i < applyStepBeanList.size(); i++) {
                            ItemApplyStepBinding stepBinding = ItemApplyStepBinding.inflate(getLayoutInflater());
                            mBinding.llStepContainer.addView(stepBinding.getRoot());
                            stepBinding.ivOkError.setVisibility(View.GONE);
                            ApplyStepBean dto = applyStepBeanList.get(i);
                            if(StringUtils.isEmpty(dto.avatar)){
                                //没有头像
                                stepBinding.wordAvatar.setVisibility(View.VISIBLE);
                                stepBinding.rivAvatar.setVisibility(View.GONE);
                                stepBinding.wordAvatar.setWord(dto.avatarName);
                            }else {
                                stepBinding.wordAvatar.setVisibility(View.GONE);
                                stepBinding.rivAvatar.setVisibility(View.VISIBLE);
                                MyImageUtils.displayUseImageServer(stepBinding.rivAvatar,dto.avatar);
                            }
                            String checkType = dto.checkType;
                            if (StringUtils.equals(checkType, "1")) {
                                stepBinding.ivOkError.setVisibility(View.VISIBLE);
                                stepBinding.ivOkError.setImageResource(R.mipmap.ic_ok_green);
                            } else if (StringUtils.equals(checkType, "2") || StringUtils.equals(checkType, "3")) {
                                stepBinding.ivOkError.setVisibility(View.VISIBLE);
                                stepBinding.ivOkError.setImageResource(R.mipmap.ic_error_red);
                            } else {
                                stepBinding.ivOkError.setVisibility(View.GONE);
                            }
                            stepBinding.tvRefuseReason.setVisibility(View.GONE);
                            if(!StringUtils.isTrimEmpty(dto.checkContent)){
                                stepBinding.tvRefuseReason.setVisibility(View.VISIBLE);
                                stepBinding.tvRefuseReason.setText(dto.checkContent);
                            }
                            if (i == 0) {
                                //第一步
                                stepBinding.viewTopLine.setVisibility(View.GONE);
                            }
                            if (i == size - 1) {
                                //最后一步
                                stepBinding.viewBottomLine.setVisibility(View.GONE);
                            }

                            stepBinding.tvTitle.setText(dto.title);
                            stepBinding.tvTime.setText(dto.time);
                            stepBinding.tvRealName.setText(dto.realName);
                        }

                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        mBinding.refreshLayout.finishRefresh();
                    }
                });
    }

    private void initUI() {

        mBinding.tvCheckStatus.setText("");
        mBinding.tvTitle.setText("");
        mBinding.tvCode.setText("");
        mBinding.tvReason.setText("");
        mBinding.tvContractNo.setText("");
        mBinding.tvRealName.setText("");
        mBinding.tvDateR.setText("");
        mBinding.tvHallList.setText("");
        mBinding.tvIntentManName.setText("");
        mBinding.tvRemoveCheck.setVisibility(View.GONE);
        mBinding.llBottomActionContainer.setVisibility(View.GONE);
    }
}
