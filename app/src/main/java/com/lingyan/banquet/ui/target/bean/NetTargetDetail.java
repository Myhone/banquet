package com.lingyan.banquet.ui.target.bean;

/**
 * Created by _hxb on 2021/2/23.
 */

public class NetTargetDetail {


    /**
     * code : 200
     * msg : ok
     * data : {"achievement_id":"12","name":"小明","obj_id":"60","type":"3","year":"2021","january":"416.67","february":"416.67","march":"416.67","april":"416.67","may":"416.67","june":"416.67","july":"416.67","august":"416.67","september":"416.67","october":"416.67","november":"416.67","december":"416.67","target_type":"1","yeartarget":"5000.04","first_target":"1250.01","second_target":"1250.01","third_target":"1250.01","fourth_target":"1250.01","create_time":"2021-02-19 14:06:28","avatar_name":"小明","dept_name":"技术","obj_id_str":"60","is_show":"0"}
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
         * achievement_id : 12
         * name : 小明
         * obj_id : 60
         * type : 3
         * year : 2021
         * january : 416.67
         * february : 416.67
         * march : 416.67
         * april : 416.67
         * may : 416.67
         * june : 416.67
         * july : 416.67
         * august : 416.67
         * september : 416.67
         * october : 416.67
         * november : 416.67
         * december : 416.67
         * target_type : 1
         * yeartarget : 5000.04
         * first_target : 1250.01
         * second_target : 1250.01
         * third_target : 1250.01
         * fourth_target : 1250.01
         * create_time : 2021-02-19 14:06:28
         * avatar_name : 小明
         * dept_name : 技术
         * obj_id_str : 60
         * is_show : 0
         */

        private String achievement_id;
        private String name;
        private String obj_id;
        private String type;
        private String year;
        private String january;
        private String february;
        private String march;
        private String april;
        private String may;
        private String june;
        private String july;
        private String august;
        private String september;
        private String october;
        private String november;
        private String december;
        private String target_type;
        private String yeartarget;
        private String first_target;
        private String second_target;
        private String third_target;
        private String fourth_target;
        private String create_time;
        private String avatar_name;
        private String dept_name;
        private String obj_id_str;
        private String is_show;

        //后加的
        private String id;
        private String user_id;
        private String dept_id;
        private String b_type;

        public String getB_type() {
            return b_type;
        }

        public void setB_type(String b_type) {
            this.b_type = b_type;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getDept_id() {
            return dept_id;
        }

        public void setDept_id(String dept_id) {
            this.dept_id = dept_id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAchievement_id() {
            return achievement_id;
        }

        public void setAchievement_id(String achievement_id) {
            this.achievement_id = achievement_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getJanuary() {
            return january;
        }

        public void setJanuary(String january) {
            this.january = january;
        }

        public String getFebruary() {
            return february;
        }

        public void setFebruary(String february) {
            this.february = february;
        }

        public String getMarch() {
            return march;
        }

        public void setMarch(String march) {
            this.march = march;
        }

        public String getApril() {
            return april;
        }

        public void setApril(String april) {
            this.april = april;
        }

        public String getMay() {
            return may;
        }

        public void setMay(String may) {
            this.may = may;
        }

        public String getJune() {
            return june;
        }

        public void setJune(String june) {
            this.june = june;
        }

        public String getJuly() {
            return july;
        }

        public void setJuly(String july) {
            this.july = july;
        }

        public String getAugust() {
            return august;
        }

        public void setAugust(String august) {
            this.august = august;
        }

        public String getSeptember() {
            return september;
        }

        public void setSeptember(String september) {
            this.september = september;
        }

        public String getOctober() {
            return october;
        }

        public void setOctober(String october) {
            this.october = october;
        }

        public String getNovember() {
            return november;
        }

        public void setNovember(String november) {
            this.november = november;
        }

        public String getDecember() {
            return december;
        }

        public void setDecember(String december) {
            this.december = december;
        }

        public String getTarget_type() {
            return target_type;
        }

        public void setTarget_type(String target_type) {
            this.target_type = target_type;
        }

        public String getYeartarget() {
            return yeartarget;
        }

        public void setYeartarget(String yeartarget) {
            this.yeartarget = yeartarget;
        }

        public String getFirst_target() {
            return first_target;
        }

        public void setFirst_target(String first_target) {
            this.first_target = first_target;
        }

        public String getSecond_target() {
            return second_target;
        }

        public void setSecond_target(String second_target) {
            this.second_target = second_target;
        }

        public String getThird_target() {
            return third_target;
        }

        public void setThird_target(String third_target) {
            this.third_target = third_target;
        }

        public String getFourth_target() {
            return fourth_target;
        }

        public void setFourth_target(String fourth_target) {
            this.fourth_target = fourth_target;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getAvatar_name() {
            return avatar_name;
        }

        public void setAvatar_name(String avatar_name) {
            this.avatar_name = avatar_name;
        }

        public String getDept_name() {
            return dept_name;
        }

        public void setDept_name(String dept_name) {
            this.dept_name = dept_name;
        }

        public String getObj_id_str() {
            return obj_id_str;
        }

        public void setObj_id_str(String obj_id_str) {
            this.obj_id_str = obj_id_str;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }
    }
}
