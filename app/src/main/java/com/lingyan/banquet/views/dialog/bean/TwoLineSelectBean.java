package com.lingyan.banquet.views.dialog.bean;

/**
 * Created by _hxb on 2021/1/14.
 */

public class TwoLineSelectBean {

    private String title;
    private String id;
    private boolean isSelected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
