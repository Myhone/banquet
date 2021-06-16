package com.lingyan.banquet.utils;

import android.widget.ImageView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.bumptech.glide.Glide;
import com.lingyan.banquet.R;
import com.lingyan.banquet.global.HttpURLs;


/**
 * Created by _hxb on 2021/2/4.
 */

public class MyImageUtils {
    public static void display(ImageView imageView, String string) {
        Glide.with(Utils.getApp())
                .load(string)
                .error(R.mipmap.ic_default_error_image)
                .placeholder(R.mipmap.ic_default_error_image)
                .into(imageView);
    }

    public static void display(ImageView imageView, String url, int errorImg, int placeholderImg) {
        Glide.with(Utils.getApp())
                .load(url)
                .error(errorImg)
                .placeholder(placeholderImg)
                .into(imageView);
    }

    public static void displayUseImageServer(ImageView imageView, String string) {
        if (StringUtils.isEmpty(string)) {
            return;
        }
        if (!string.startsWith("http")) {
            string = HttpURLs.IMAGE_BASE + string;
        }

        display(imageView, string);
    }


    public static String getRelativePath(String path) {
        if (path == null) {
            return null;
        }
        if (path.startsWith(HttpURLs.IMAGE_BASE)) {
            path = path.substring(HttpURLs.IMAGE_BASE.length());
        }

        return path;
    }
}
