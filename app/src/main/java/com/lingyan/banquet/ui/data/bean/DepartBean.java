package com.lingyan.banquet.ui.data.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by _hxb on 2021/2/20.
 */

public class DepartBean implements MultiItemEntity {

    private String id;
    private String pid;
    private String name;
    private String allNum;
    private String currentNum;
    private String d_type;
    private String children;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllNum() {
        return allNum;
    }

    public void setAllNum(String allNum) {
        this.allNum = allNum;
    }

    public String getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(String currentNum) {
        this.currentNum = currentNum;
    }

    public String getD_type() {
        return d_type;
    }

    public void setD_type(String d_type) {
        this.d_type = d_type;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    @Override
    public int getItemType() {
        return 0;
    }
}
