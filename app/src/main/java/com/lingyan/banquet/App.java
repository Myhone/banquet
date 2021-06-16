package com.lingyan.banquet;

import android.content.Context;
import android.view.Gravity;

import androidx.multidex.MultiDexApplication;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.blankj.utilcode.util.Utils;
import com.lingyan.banquet.global.ActivityLife;
import com.lingyan.banquet.global.AuthInterceptor;
import com.lingyan.banquet.global.RequestProcessorImpl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.db.CacheManager;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.lzy.okgo.model.HttpHeaders;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import org.android.agoo.huawei.HuaWeiRegister;
import org.android.agoo.mezu.MeizuRegister;
import org.android.agoo.oppo.OppoRegister;
import org.android.agoo.vivo.VivoRegister;
import org.android.agoo.xiaomi.MiPushRegistar;
import org.greenrobot.eventbus.EventBus;

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
        initUtils();
        initHttp();
        initUmeng();

    }
    private void initUmeng(){
        UMConfigure.init(this, "603c4d736ee47d382b694f7d", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "97a3f9dcef10e390d8675191ad5bbcd5");

        //注册成功后会在tag：MiPushBroadcastReceiver下面打印log： onCommandResult is called.
        // regid= xxxxxxxxxxxxxxxxxxxxxxx接收到小米消息则会打印log：
        // onReceiveMessage,msg= xxxxxxxxxxxxxxxxxxxxxxx
        MiPushRegistar.register(this,"2882303761519214562","5451921456562");//YE4VxZuxZ+5k59nH/DNzZA==
        HuaWeiRegister.register(this);
        //OPPO通道，参数1为app key，参数2为app secret
        OppoRegister.register(this, "3155b4fc7b1b478b82b2794bbaaba124", "40bc31a592864a98a7dde4c52b6fa3f0");//30484851
        //vivo 通道
        VivoRegister.register(this);
        MeizuRegister.register(this,  "139507", "fa363011a52047f7a730e4dc07c03b43" );
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
//注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                LogUtils.i("注册成功：deviceToken：-------->  " + deviceToken);
            }
            @Override
            public void onFailure(String s, String s1) {
                LogUtils.e("注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

        mPushAgent.setMessageHandler(new UmengMessageHandler(){
            @Override
            public void handleMessage(Context context, UMessage uMessage) {
                super.handleMessage(context, uMessage);
                EventBus.getDefault().post(uMessage);
            }
        });

        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);//服务端控制声音
        mPushAgent.setNotificationPlayLights(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);//客户端允许呼吸灯点亮
        mPushAgent.setNotificationPlayVibrate(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);//客户端禁止振动
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

    /**
     * 初始化工具类
     */
    private void initUtils() {
        Utils.init(this);
        LogUtils.getConfig().setGlobalTag("appLog");
        //是否输出日志
        LogUtils.getConfig().setConsoleSwitch(BuildConfig.DEBUG);
        ToastUtils defaultMaker = ToastUtils.getDefaultMaker();
        defaultMaker.setBgColor(this.getResources().getColor(R.color.black));
        defaultMaker.setTextColor(this.getResources().getColor(R.color.white));
        defaultMaker.setGravity(Gravity.CENTER,0,0);
//        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
//        GsonUtils.setGsonDelegate(gson);
    }


}
