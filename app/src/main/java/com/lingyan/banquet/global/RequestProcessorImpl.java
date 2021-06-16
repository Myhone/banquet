package com.lingyan.banquet.global;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.ObjectUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.BodyRequest;
import com.lzy.okgo.request.base.Request;
import com.lzy.okgo.request.base.RequestProcessor;

import okhttp3.MediaType;

/**
 * Created by _hxb on 2021/1/17.
 */

public class RequestProcessorImpl<T, R extends Request> implements RequestProcessor<T, R> {
    private final UserInfoManager mUserInfoManager = UserInfoManager.getInstance();

    @Override
    public void process(Request<T, R> request) {
        if (request.getUrl().startsWith(HttpURLs.BASE)) {
            String accessToken = mUserInfoManager.getAccessToken();
            if (ObjectUtils.isNotEmpty(accessToken)) {
                HttpHeaders headers = request.getHeaders();
                headers.put("Authorization", mUserInfoManager.getTokenType() + " " + accessToken);
            }
            if (request instanceof BodyRequest) {
                BodyRequest bodyRequest = (BodyRequest) request;
                MediaType mediaType = bodyRequest.getMediaType();
                if (mediaType == HttpParams.MEDIA_TYPE_JSON) {
                    String json = bodyRequest.getContent();
                    try {
                        JsonElement jsonElement = JsonParser.parseString(json);
                        if (jsonElement instanceof JsonObject) {
                            JsonObject jo = (JsonObject) jsonElement;
                            jo.addProperty("specification", DeviceUtils.getManufacturer() + "/" + DeviceUtils.getModel());
                            jo.addProperty("client", "android");
                            jo.addProperty("client_version", AppUtils.getAppVersionCode());//app的版本号 代码
                            jo.addProperty("client_version_name", AppUtils.getAppVersionName());//app的版本号 名称
                            bodyRequest.upJson(jo.toString());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    HttpParams params = request.getParams();
                    params.put("specification", DeviceUtils.getManufacturer() + "/" + DeviceUtils.getModel());
                    params.put("client", "android");
                    params.put("client_version", AppUtils.getAppVersionCode());//app的版本号 代码
                    params.put("client_version_name", AppUtils.getAppVersionName());//app的版本号 名称
                }
            }


        }

        Object tag = request.getTag();
        if (tag == null) {
            request.tag(ActivityUtils.getTopActivity());
        }
    }
}
