package com.lingyan.banquet.views.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.lingyan.banquet.R;
import com.lingyan.banquet.global.Constant;
import com.lingyan.banquet.ui.common.WebActivity;

/**
 * Created by wyq on 2021/10/11.
 */
public class PrivacyDialog extends Dialog {

    private BtnClickListener listener;

    private String titleDef = "隐私政策";
    private String contentDef = "宴帮帮根据法律法规及监管要求，制定并更新了《用户服务条款》和《隐私政策》，特向你说明如下：\n" +
            "\n" +
            "1、为向您提供相关基本功能，我们会收集、使用必要的信息，你可以通过《隐私政策》了解我们会收集你个人信息的类型，了解我们如何收集、使用、存储你的个人信息，以及你拥有哪些权利等事宜。\n" +
            "\n" +
            "2、未经你的同意，我们不会向第三方主动提供、分享你的信息。\n" +
            "\n" +
            "3、若你同意，请点击“同意”确认，宴帮帮将严格按照《用户服务条款》和《隐私政策》向你提供服务；若不同意你可能无法使用宴帮帮提供的服务和产品。请仔细阅读并确保充分理解相关条款，特别是以加粗方式进行标识的对你权利义务有重要影响的条款。\n" +
            "\n" +
            "请您仔细阅读《用户服务条款》和《隐私政策》，继续使用本应用即表明您同意接受此用户协议与隐私政策，我们依法尽全力保护您的个人信息。\n";

    public PrivacyDialog(Context context) {
        super(context, R.style.NoTitleBarDialog);
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_privacy, null);
        TextView mTvConfirm = view.findViewById(R.id.tv_confirm);
        TextView mTvCancel = view.findViewById(R.id.tv_cancel);
        TextView mTvContent = view.findViewById(R.id.tv_content);
        TextView mTvCheck = view.findViewById(R.id.tv_check);
        SpannableStringBuilder builder = new SpanUtils()
                .append("查看").setForegroundColor(Color.parseColor("#999999"))
                .append("《用户服务条款》").setForegroundColor(Color.parseColor("#2778C3")).setClickSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                    }

                    @Override
                    public void onClick(@NonNull View widget) {
                        WebActivity.start(Constant.Http.LOING_USER_AGREEMENT);
                    }
                })
                .append("及").setForegroundColor(Color.parseColor("#999999"))
                .append("《隐私政策》").setForegroundColor(Color.parseColor("#2778C3")).setClickSpan(new ClickableSpan() {
                    @Override
                    public void updateDrawState(@NonNull TextPaint ds) {
                    }

                    @Override
                    public void onClick(@NonNull View widget) {
                        WebActivity.start(Constant.Http.LOING_PRIVATE_AGREEMENT);
                    }
                }).create();
        mTvCheck.setMovementMethod(LinkMovementMethod.getInstance());
        mTvCheck.setText(builder);
        mTvContent.setText(contentDef);
        mTvConfirm.setOnClickListener(v -> {
            dismiss();
            listener.confirmCallback();
        });
        mTvCancel.setOnClickListener(v -> {
            dismiss();
            listener.cancelCallback();
        });
        setContentView(view);
    }

    public void setListener(BtnClickListener listener) {
        this.listener = listener;
    }

    public interface BtnClickListener {
        void confirmCallback();

        void cancelCallback();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable());
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = (int) (ScreenUtils.getAppScreenWidth() * 0.8f);
//        attributes.height = (int) (ScreenUtils.getAppScreenWidth() * 0.8f * 1.3);
        attributes.gravity = Gravity.CENTER;
        window.setAttributes(attributes);
    }
}
