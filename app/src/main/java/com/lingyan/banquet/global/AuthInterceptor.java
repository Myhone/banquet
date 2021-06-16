package com.lingyan.banquet.global;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by _hxb on 2021/1/17.
 */

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept( Chain chain) throws IOException {

        Request request = chain.request();
        String url = request.url().url().toString();
        Response response = chain.proceed(request);
        ResponseBody responseBody = response.body();
        if (ObjectUtils.isNotEmpty(url) && url.startsWith(HttpURLs.BASE) && responseBody != null && response.isSuccessful()) {
            MediaType mediaType = responseBody.contentType();
            String contentType = mediaType.toString();
            //Content-Type: application/json; charset=utf-8
            if (ObjectUtils.isNotEmpty(contentType) && contentType.contains("json")) {
                BufferedSource source = responseBody.source();
                source.request(Long.MAX_VALUE);
                Buffer buffer = source.getBuffer();
                Charset charset = Charset.forName("UTF-8");
                String json = buffer.clone().readString(charset);
                JsonElement jsonElement = GsonUtils.fromJson(json, JsonElement.class);
                if (jsonElement.isJsonObject()) {
                    JsonObject jo = jsonElement.getAsJsonObject();
                    int code = jo.get("code").getAsInt();
                    if (code == 203) {
                        LogUtils.w("token过期了需要重新请求接口");
                        String token = jo.get("data").getAsJsonObject().get("token").getAsString();
                        UserInfoManager.getInstance().put(UserInfoManager.ACCESS_TOKEN, token);
                        Request newRequest = request.newBuilder().header("token", "Bearer " + token).build();
                        return chain.proceed(newRequest);
                    }
                }

            }
        }

        return response;
    }

}
