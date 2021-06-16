package com.lingyan.banquet.event;

/**
 * Created by _hxb on 2020/12/28.
 */

public class ChangeMainPageEvent {
    private int page;

    public ChangeMainPageEvent(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
