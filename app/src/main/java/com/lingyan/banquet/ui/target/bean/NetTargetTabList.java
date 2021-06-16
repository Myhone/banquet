package com.lingyan.banquet.ui.target.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by _hxb on 2021/2/21.
 */

public class NetTargetTabList implements Serializable {

    /**
     * code : 200
     * msg : ok
     * data : [{"id":"5","title":"新增商机","obj_id":"18","type":"1","a_type":"1"},{"id":"6","title":"新增意向","obj_id":"16,18","type":"1","a_type":"2"},{"id":"7","title":"锁台数","obj_id":"","type":"1","a_type":"3"},{"id":"10","title":"签定数","obj_id":"","type":"1","a_type":"4"},{"id":"8","title":"签定定金","obj_id":"","type":"1","a_type":"6"}]
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

    public static class DataDTO  implements Serializable{
        /**
         * id : 5
         * title : 新增商机
         * obj_id : 18
         * type : 1
         * a_type : 1
         */

        private String id;
        private String title;
        private String obj_id;
        private String type;
        private String a_type;

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

        public String getObj_id() {
            return obj_id;
        }

        public void setObj_id(String obj_id) {
            this.obj_id = obj_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getA_type() {
            return a_type;
        }

        public void setA_type(String a_type) {
            this.a_type = a_type;
        }
    }
}
