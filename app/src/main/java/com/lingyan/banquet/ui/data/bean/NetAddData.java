package com.lingyan.banquet.ui.data.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/28.
 */

public class NetAddData {

    /**
     * code : 200
     * msg : ok
     * data : [{"id":"85","type":"1","date":"2021-03-13","niche_type":"6","intent_man_id":"61","real_name":"刘杰","status":"3","is_lost":0,"create_time":"2021-02-25 21:17:06","niche_name":"分类1","intent_man_name":"王五","total_num":1,"total_number":99,"date_list":"2021-02-27 晚餐","hall_list":"光明顶"}]
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

    public static class DataDTO {
        /**
         * id : 85
         * type : 1
         * date : 2021-03-13
         * niche_type : 6
         * intent_man_id : 61
         * real_name : 刘杰
         * status : 3
         * is_lost : 0
         * create_time : 2021-02-25 21:17:06
         * niche_name : 分类1
         * intent_man_name : 王五
         * total_num : 1
         * total_number : 99
         * date_list : 2021-02-27 晚餐
         * hall_list : 光明顶
         */

        private String id;
        private String type;
        private String date;
        private String niche_type;
        private String intent_man_id;
        private String real_name;
        private String status;
        private int is_lost;
        private String create_time;
        private String niche_name;
        private String intent_man_name;
        private int total_num;
        private int total_number;
        private String date_list;
        private String hall_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getNiche_type() {
            return niche_type;
        }

        public void setNiche_type(String niche_type) {
            this.niche_type = niche_type;
        }

        public String getIntent_man_id() {
            return intent_man_id;
        }

        public void setIntent_man_id(String intent_man_id) {
            this.intent_man_id = intent_man_id;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getIs_lost() {
            return is_lost;
        }

        public void setIs_lost(int is_lost) {
            this.is_lost = is_lost;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getNiche_name() {
            return niche_name;
        }

        public void setNiche_name(String niche_name) {
            this.niche_name = niche_name;
        }

        public String getIntent_man_name() {
            return intent_man_name;
        }

        public void setIntent_man_name(String intent_man_name) {
            this.intent_man_name = intent_man_name;
        }

        public int getTotal_num() {
            return total_num;
        }

        public void setTotal_num(int total_num) {
            this.total_num = total_num;
        }

        public int getTotal_number() {
            return total_number;
        }

        public void setTotal_number(int total_number) {
            this.total_number = total_number;
        }

        public String getDate_list() {
            return date_list;
        }

        public void setDate_list(String date_list) {
            this.date_list = date_list;
        }

        public String getHall_list() {
            return hall_list;
        }

        public void setHall_list(String hall_list) {
            this.hall_list = hall_list;
        }
    }
}
