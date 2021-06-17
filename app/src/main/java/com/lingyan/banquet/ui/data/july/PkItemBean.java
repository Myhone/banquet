package com.lingyan.banquet.ui.data.july;

import java.util.List;

/**
 * Created by wyq on 2021/6/17.
 */
public class PkItemBean {

    /**
     * code : 200
     * msg : ok
     * data : [{"title":"回款王","key":"income"},{"title":"签单王","key":"data1"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * title : 回款王
         * key : income
         */

        private String title;
        private String key;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
