package com.lingyan.banquet.ui.login.bean;

/**
 * Created by _hxb on 2021/3/8.
 */

public class NetAgreement {
    /**
     * code : 200
     * msg : ok
     * data : {"url":"http://api.forvery.top/banquet/#/agreement"}
     */

    private int code;
    private String msg;
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * url : http://api.forvery.top/banquet/#/agreement
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
