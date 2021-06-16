package com.lingyan.banquet.event;

/**
 * Created by _hxb on 2021/3/4.
 */

public class ReadMessageEvent {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReadMessageEvent(String id) {
        this.id = id;
    }
}
