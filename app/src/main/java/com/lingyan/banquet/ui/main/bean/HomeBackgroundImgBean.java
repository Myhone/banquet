package com.lingyan.banquet.ui.main.bean;

/**
 * Created by wyq on 2021/6/16.
 */
public class HomeBackgroundImgBean {

    /**
     * code : 200
     * msg : ok
     * data : {"background_url":""}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * background_url :
         */

        private String background_url;

        public String getBackground_url() {
            return background_url;
        }

        public void setBackground_url(String background_url) {
            this.background_url = background_url;
        }
    }
}
