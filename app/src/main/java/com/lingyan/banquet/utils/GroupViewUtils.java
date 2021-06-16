package com.lingyan.banquet.utils;


import android.view.View;

import com.blankj.utilcode.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by _hxb on 2021/2/14.
 */

public class GroupViewUtils {

    private HashMap<String, HashMap<View, String>> mMap;
    private HashMap<String, String> mSelectedValueMap;
    private HashMap<String, View> mSelectedViewMap;

    public GroupViewUtils() {
        mMap = new HashMap<>();
        mSelectedValueMap = new HashMap<>();
        mSelectedViewMap = new HashMap<>();
    }

    public void add(String groupName, View view, String value) {
        add(groupName, view, value, true);
    }

    public void add(String groupName, View view, String value, boolean addClick) {
        HashMap<View, String> viewStringHashMap = mMap.get(groupName);
        if (viewStringHashMap == null) {
            viewStringHashMap = new HashMap<>();
            mMap.put(groupName, viewStringHashMap);
        }
        viewStringHashMap.put(view, value);
        if (addClick) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    select(groupName, value);
                }
            });
        }
    }


    public boolean select(String groupName, String value) {
        HashMap<View, String> viewStringHashMap = mMap.get(groupName);
        if (viewStringHashMap == null) {
            return false;
        }
        String existValue = mSelectedValueMap.get(groupName);
        if (StringUtils.equals(existValue, value)) {
            //取消就可以了
            mSelectedValueMap.remove(groupName);
            View removeView = mSelectedViewMap.remove(groupName);
            if (removeView != null) {
                removeView.setSelected(false);
            }
            return false;
        }


        mSelectedValueMap.remove(groupName);
        mSelectedViewMap.remove(groupName);

        Set<Map.Entry<View, String>> entrySet = viewStringHashMap.entrySet();
        for (Map.Entry<View, String> entry : entrySet) {
            View view = entry.getKey();
            if (view != null) {
                String bindValue = entry.getValue();
                boolean equals = StringUtils.equals(bindValue, value);
                view.setSelected(equals);
                if (equals) {
                    mSelectedValueMap.put(groupName, value);
                    mSelectedViewMap.put(groupName, view);
                }
            }
        }
        return true;
    }

    public HashMap<String, String> getSelectedValueMap() {
        return mSelectedValueMap;
    }

    public void reset() {
        Set<Map.Entry<String, View>> entrySet = mSelectedViewMap.entrySet();
        for (Map.Entry<String, View> entry : entrySet) {
            View view = entry.getValue();
            if (view != null) {
                view.setSelected(false);
            }
        }
        mSelectedValueMap.clear();
        mSelectedViewMap.clear();
    }
}
