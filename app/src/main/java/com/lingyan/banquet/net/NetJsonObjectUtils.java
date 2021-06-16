package com.lingyan.banquet.net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Created by _hxb on 2021/2/4.
 */

public class NetJsonObjectUtils {

    public static int NO_CODE = -404;

    public static int getCode(JsonObject jo) {
        if (jo.has("code")) {
            JsonElement codeJe = jo.get("code");
            if (codeJe.isJsonPrimitive()) {
                JsonPrimitive codeJp = codeJe.getAsJsonPrimitive();
                if (codeJp.isNumber()) {
                    Number number = codeJe.getAsNumber();
                    return number.intValue();
                }
            }
        }
        return NO_CODE;
    }

    public static String getMsg(JsonObject jo) {
        if (jo.has("msg")) {
            JsonElement msgJe = jo.get("msg");
            if (msgJe.isJsonPrimitive()) {
                JsonPrimitive msgJp = msgJe.getAsJsonPrimitive();
                if (msgJp.isString()) {
                    return msgJe.getAsString();
                }
            }
        }
        return null;
    }


}
