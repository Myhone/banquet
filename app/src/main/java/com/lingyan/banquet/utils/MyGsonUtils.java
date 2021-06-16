package com.lingyan.banquet.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

/**
 * Created by _hxb on 2021/2/14.
 */

public class MyGsonUtils {

    public static String getString(JsonObject jsonObject, String name) {
        if (jsonObject == null || name == null) {
            return null;
        }
        JsonElement je = jsonObject.get(name);
        if (je != null && je.isJsonPrimitive()) {
            JsonPrimitive jp = je.getAsJsonPrimitive();
            if (jp.isString()) {
                return jp.getAsString();
            }
        }
        return null;
    }

    public static Number getNumber(JsonObject jsonObject, String name) {
        if (jsonObject == null || name == null) {
            return null;
        }
        JsonElement je = jsonObject.get(name);
        if (je != null && je.isJsonPrimitive()) {
            JsonPrimitive jp = je.getAsJsonPrimitive();
            if (jp.isNumber()) {
                return jp.getAsNumber();
            }
        }
        return null;
    }

    public static void assignAndRemove(JsonObject oldJo, JsonObject newJo, String... names) {
        for (String name : names) {
            if (oldJo.has(name)) {
                JsonElement je = oldJo.get(name);
                newJo.add(name, je);
            } else {
                newJo.remove(name);
            }
        }
    }
}
