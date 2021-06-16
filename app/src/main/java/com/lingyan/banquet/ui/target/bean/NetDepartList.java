package com.lingyan.banquet.ui.target.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by _hxb on 2021/2/22.
 */

public class NetDepartList implements Serializable {

    /**
     * code : 200
     * msg : ok
     * data : [{"id":16,"label":"财务"},{"id":18,"label":"技术"},{"id":25,"label":"技术1部门"},{"id":26,"label":"技术2部"},{"id":27,"label":"技术3部"},{"id":1,"label":" 全公司"}]
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
         * id : 16
         * label : 财务
         */

        private String id;
        private String label;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
