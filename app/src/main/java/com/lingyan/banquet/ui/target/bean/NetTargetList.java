package com.lingyan.banquet.ui.target.bean;

import com.blankj.utilcode.util.StringUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by _hxb on 2021/2/22.
 */

public class NetTargetList {


    /**
     * code : 200
     * msg : ok
     * data : [{"id":"60","name":"小明","dept_id":18,"type":3,"dept_name":"技术","avatar_name":"小明","user_over_number":"0","user_number":"416.67","rate":"0.00","rate_num":"0"}]
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

    public static class DataDTO implements MultiItemEntity {
        /**
         * id : 60
         * name : 小明
         * dept_id : 18
         * type : 3
         * dept_name : 技术
         * avatar_name : 小明
         * user_over_number : 0
         * user_number : 416.67
         * rate : 0.00
         * rate_num : 0
         */

        private String id;
        private String name;
        private String dept_id;
        private String type;
        private String dept_name;
        private String avatar_name;
        private String user_over_number;
        private String user_number;
        private String rate;
        private String rate_num;
        private String avatar;
        private String b_type;

        public String getB_type() {
            return b_type;
        }

        public void setB_type(String b_type) {
            this.b_type = b_type;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDept_id() {
            return dept_id;
        }

        public void setDept_id(String dept_id) {
            this.dept_id = dept_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDept_name() {
            return dept_name;
        }

        public void setDept_name(String dept_name) {
            this.dept_name = dept_name;
        }

        public String getAvatar_name() {
            return avatar_name;
        }

        public void setAvatar_name(String avatar_name) {
            this.avatar_name = avatar_name;
        }

        public String getUser_over_number() {
            return user_over_number;
        }

        public void setUser_over_number(String user_over_number) {
            this.user_over_number = user_over_number;
        }

        public String getUser_number() {
            return user_number;
        }

        public void setUser_number(String user_number) {
            this.user_number = user_number;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getRate_num() {
            return rate_num;
        }

        public void setRate_num(String rate_num) {
            this.rate_num = rate_num;
        }

        @Override
        public int getItemType() {
            // @ com.lingyan.banquet.ui.target.adapter.TargetAdapter
            if (StringUtils.equals(type, "3")) {
                //员工
                return 2;
            }
            //部门
            return 1;
        }
    }
}
