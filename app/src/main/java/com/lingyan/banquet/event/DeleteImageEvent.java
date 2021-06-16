package com.lingyan.banquet.event;

/**
 * Created by _hxb on 2021/2/8.
 */

public class DeleteImageEvent {
    private int code;
    private String image;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public DeleteImageEvent(int code, String image) {
        this.code = code;
        this.image = image;
    }
}
