package com.lingyan.banquet.ui.banquet.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by _hxb on 2021/2/4.
 */

public class NetSinglePersonList implements Serializable {


    /**
     * code : 200
     * msg : ok
     * data : [{"id":"58","realname":"张三","first":"Z"}]
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

    public static class DataDTO implements Serializable {
        /**
         * id : 58
         * realname : 张三
         * first : Z
         */

        private String id;
        private String realname;
        private String first;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }
    }
}
