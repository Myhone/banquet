package com.lingyan.banquet.utils;

import com.blankj.utilcode.util.ObjectUtils;
import com.lingyan.banquet.App;
import com.meituan.android.walle.WalleChannelReader;

/**
 * Created by wyq on 2021/10/11.
 */
public class ChannelUtils {

    /**
     * @return 得到当前的渠道包名称
     */
    public static String getChannelName() {
        String channel = WalleChannelReader.getChannel(App.sApp);
        if (ObjectUtils.isEmpty(channel)) {
            channel = "test";
        }
        return channel;
    }
}
