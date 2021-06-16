package com.lingyan.banquet.ui.main.bean;

/**
 * Created by _hxb on 2021/2/14.
 */

public class NetUserInfo {

    /**
     * code : 200
     * msg : ok
     * data : {"id":"57","nickname":"雷","avatar":"","realname":"雷","dept_id":21,"dept_name":"全公司","str_name":"雷","restaurant_name":"雷","expire_time":"2021-07-14","is_show":"1"}
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
         * id : 57
         * nickname : 雷
         * avatar :
         * realname : 雷
         * dept_id : 21
         * dept_name : 全公司
         * str_name : 雷
         * restaurant_name : 雷
         * expire_time : 2021-07-14
         * is_show : 1
         */

        private String id;
        private String nickname;
        private String avatar;
        private String realname;
        private int dept_id;
        private String not_read_num;
        private String dept_name;
        private String str_name;
        private String restaurant_name;
        private String expire_time;
        private String is_show;

        public String getNot_read_num() {
            return not_read_num;
        }

        public void setNot_read_num(String not_read_num) {
            this.not_read_num = not_read_num;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getDept_id() {
            return dept_id;
        }

        public void setDept_id(int dept_id) {
            this.dept_id = dept_id;
        }

        public String getDept_name() {
            return dept_name;
        }

        public void setDept_name(String dept_name) {
            this.dept_name = dept_name;
        }

        public String getStr_name() {
            return str_name;
        }

        public void setStr_name(String str_name) {
            this.str_name = str_name;
        }

        public String getRestaurant_name() {
            return restaurant_name;
        }

        public void setRestaurant_name(String restaurant_name) {
            this.restaurant_name = restaurant_name;
        }

        public String getExpire_time() {
            return expire_time;
        }

        public void setExpire_time(String expire_time) {
            this.expire_time = expire_time;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }
    }
}
