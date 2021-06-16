package com.lingyan.banquet.ui.banquet.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 优惠活动
 * Created by _hxb on 2021/2/7.
 */

public class NetMarketing implements Serializable {

    /**
     * code : 200
     * msg : ok
     * data : [{"id":"3","title":"测试2222www"},{"id":"4","title":"元旦大庆"},{"id":"2","title":"测试"},{"id":"0","title":"不使用活动"}]
     */

    private int code;
    private String msg;
    private List<DataDTO> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO implements Serializable{
        /**
         * id : 3
         * title : 测试2222www
         */

        private String id;
        private String title;

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
    }
}
