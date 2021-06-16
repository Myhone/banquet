package com.lingyan.banquet.event;

/**
 * 预定成功
 * Created by _hxb on 2021/2/7.
 */

public class SaveReserveSuccessEvent {

    private String id;

    public SaveReserveSuccessEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
