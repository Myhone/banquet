package com.lingyan.banquet.ui.data.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class NetDepartList {

    /**
     * code : 200
     * msg : ok
     * data : {"dept_list":[{"id":"16","pid":1,"name":"财务","allNum":1,"currentNum":"1","d_type":"1","children":"0"},{"id":"18","pid":1,"name":"技术","allNum":2,"currentNum":"1","d_type":"1","children":"1"}],"user_list":[{"id":"54","realname":"小明","avatar":"","avatar_name":"小明","d_type":"2"},{"id":"1","realname":"管理员","avatar":"","avatar_name":"理员","d_type":"2"}]}
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
        private List<DepartBean> dept_list;
        private List<PersonBean> user_list;

        public List<DepartBean> getDept_list() {
            return dept_list;
        }

        public void setDept_list(List<DepartBean> dept_list) {
            this.dept_list = dept_list;
        }

        public List<PersonBean> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<PersonBean> user_list) {
            this.user_list = user_list;
        }

    }
}
