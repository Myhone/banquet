package com.lingyan.banquet.global;

import com.blankj.utilcode.util.ObjectUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lingyan.banquet.event.LogoutEvent;
import com.lingyan.banquet.push.PushUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by _ on 2020/11/7.
 */

public class UserInfoManager {

    private UserInfoManager() {

    }

    private static UserInfoManager sUserInfoManager = new UserInfoManager();

    public static UserInfoManager getInstance() {
        return sUserInfoManager;
    }

    private static final String SP_NAME = "user_info";


    public static final String ACCESS_TOKEN = "token";
    public static final String PUSH_ID = "push_id";
    public static final String ID = "id";
    public static final String REAL_NAME = "real_name";
    public static final String NIKE_NAME = "nike_name";
    public static final String IS_ADMIN = "is_admin";
    public static final String ALL_DEPART_ID = "all_depart_id";


    public synchronized boolean isLogin() {
        if (ObjectUtils.isNotEmpty(getAccessToken())) {
            return true;
        }
        return false;
    }


    public synchronized String getAccessToken() {

        return getSpUtils().getString(ACCESS_TOKEN);
    }

    public String getTokenType() {
        return "Bearer";
    }


    public void put(String key, String value) {
        if (ObjectUtils.isEmpty(key) || ObjectUtils.isEmpty(value)) {
            return;
        }

        SPUtils spUtils = getSpUtils();
        spUtils.put(key, value);
    }

    public String get(String key) {
        if (ObjectUtils.isEmpty(key)) {
            return null;
        }
        return getSpUtils().getString(key);
    }


    private SPUtils getSpUtils() {
        return SPUtils.getInstance(SP_NAME);
    }

    public void logout() {
        PushUtils.deleteAlias(get(PUSH_ID));

        getSpUtils().clear(true);
        EventBus.getDefault().post(new LogoutEvent());

    }
}
