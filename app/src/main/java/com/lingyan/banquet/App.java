package com.lingyan.banquet;

import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lingyan.banquet.global.ActivityLife;
import com.lingyan.banquet.global.AuthInterceptor;
import com.lingyan.banquet.global.Constant;
import com.lingyan.banquet.global.RequestProcessorImpl;
import com.lingyan.banquet.utils.InitSDKUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.CacheManager;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;

/**
 * Created by _hxb on 2020/12/3.
 */

public class App extends MultiDexApplication {
    public static App sApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        registerActivityLifecycleCallbacks(new ActivityLife());
        initHttp();
        //用户同意隐私政策后再初始化三方SDK
        if (SPUtils.getInstance().getBoolean(Constant.SP.IS_AGREED_PRIVACY_AGREEMENT)) {
            InitSDKUtils.init();
        }
    }

    private void initHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
            loggingInterceptor.setColorLevel(Level.INFO);
            builder.addInterceptor(loggingInterceptor);
        }
        builder.addInterceptor(new AuthInterceptor());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.put("client", "Android");
        httpHeaders.put("client_version", AppUtils.getAppVersionCode() + "");
        httpHeaders.put("client_version_name", AppUtils.getAppVersionName());
        HttpHeaders.setUserAgent("YanBangBang/1.0.6 Android;"+HttpHeaders.getUserAgent());
//        httpHeaders.put("r","X46dBXa79");

        //全局的读取超时时间
        builder.readTimeout(10, TimeUnit.SECONDS);
        //全局的写入超时时间
        builder.writeTimeout(10, TimeUnit.SECONDS);
        //全局的连接超时时间
        builder.connectTimeout(10, TimeUnit.SECONDS);
        OkGo.getInstance().init(sApp)
                .addCommonHeaders(httpHeaders)
                .setOkHttpClient(builder.build())
                .setRetryCount(0)
                .setRequestProcessor(new RequestProcessorImpl());
        ;
        CacheManager.getInstance().clear();
    }
}
