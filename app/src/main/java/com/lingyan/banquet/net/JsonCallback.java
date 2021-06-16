package com.lingyan.banquet.net;

import com.blankj.utilcode.util.GsonUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.lingyan.banquet.global.UserInfoManager;
import com.lingyan.banquet.ui.login.LoginActivity;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.OkHttpClient;
import okhttp3.Response;


public abstract class JsonCallback<T> extends AbsCallback<T> {


    @Override
    public void onCacheSuccess(com.lzy.okgo.model.Response<T> response) {
        super.onCacheSuccess(response);
        onSuccess(response);
    }

    private Request mRequest;
    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        mRequest=request;
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        String string = response.body().string();

        JsonObject object = GsonUtils.fromJson(string, JsonObject.class);

        if(object.has("code") ){
            JsonElement codeJe = object.get("code");
            if(codeJe.isJsonPrimitive()){
                JsonPrimitive jp = codeJe.getAsJsonPrimitive();
                if(jp.isNumber()){
                    Number number = jp.getAsNumber();
                    if(number.intValue()==202){
                        UserInfoManager.getInstance().logout();
                        LoginActivity.start();
                    }
                }
            }
        }

        return GsonUtils.fromJson(string, type);
    }




}