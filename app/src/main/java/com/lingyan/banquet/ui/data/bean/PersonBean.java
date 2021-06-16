package com.lingyan.banquet.ui.data.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by _hxb on 2021/2/20.
 */

public class PersonBean implements MultiItemEntity {
    private String id;
    private String realname;
    private String avatar;
    private String avatar_name;
    private String d_type;
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar_name() {
        return avatar_name;
    }

    public void setAvatar_name(String avatar_name) {
        this.avatar_name = avatar_name;
    }

    public String getD_type() {
        return d_type;
    }

    public void setD_type(String d_type) {
        this.d_type = d_type;
    }

    @Override
    public int getItemType() {
        return 1;
    }
}
