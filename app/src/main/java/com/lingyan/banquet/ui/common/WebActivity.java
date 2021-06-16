package com.lingyan.banquet.ui.common;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.IntentUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.lingyan.banquet.App;
import com.lingyan.banquet.base.BaseActivity;
import com.lingyan.banquet.databinding.ActivityWebBinding;

import java.util.List;

import static android.view.KeyEvent.KEYCODE_BACK;

/**
 * Created by _hxb on 2021/3/8.
 */

public class WebActivity extends BaseActivity {

    private ActivityWebBinding mBinding;
    private WebView mWebView;
    private WebSettings mWebSettings;
    private String mUrl;

    public static void start(String url) {
        Intent intent = new Intent(App.sApp, WebActivity.class);
        intent.putExtra("url", url);
        ActivityUtils.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("url");

        mBinding = ActivityWebBinding.inflate(getLayoutInflater());
        mBinding.llTitleBarRoot.tvTitleBarTitle.setText("");
        mBinding.llTitleBarRoot.ivTitleBarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setContentView(mBinding.getRoot());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mWebView = new WebView(getContext());
        mWebView.setLayoutParams(params);
        mBinding.ll.addView(mWebView);
        //声明WebSettings子类
        mWebSettings = mWebView.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setDomStorageEnabled(true);
        mWebSettings.setAppCacheEnabled(true);
        mWebSettings.setDatabaseEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                mBinding.llTitleBarRoot.tvTitleBarTitle.setText(title);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http")) {
                    view.loadUrl(url);
                } else {

//                    Intent intent = new Intent();
//                    intent.addCategory(Intent.CATEGORY_DEFAULT);
//                    intent.setAction(url);
//                    boolean available = IntentUtils.isIntentAvailable(intent);

                }
                return true;
            }
        });
        if (!StringUtils.isEmpty(mUrl)) {
            mWebView.loadUrl(mUrl);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
