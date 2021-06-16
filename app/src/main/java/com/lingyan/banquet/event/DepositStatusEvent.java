package com.lingyan.banquet.event;

/**
 * 定金状态改变
 * Created by _hxb on 2021/2/9.
 */

public class DepositStatusEvent {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DepositStatusEvent(String id) {
        this.id = id;
    }
}
