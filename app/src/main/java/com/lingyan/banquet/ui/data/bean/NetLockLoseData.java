package com.lingyan.banquet.ui.data.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/28.
 */

public class NetLockLoseData {

    /**
     * code : 200
     * msg : ok
     * data : {"count":3,"list":[{"e_id":"16","check_status":1,"id":"85","type":"1","customer_id":45,"niche_type":"6","intent_man_id":"61","status":"3","is_lost":0,"create_time":"2021-02-25 21:19:16","niche_name":"分类1","real_name":"刘杰","mobile":"888888","intent_man_name":"王五","date_str":"2021-02-27 晚餐","hall_str":"光明顶","total_number":"99","check_status_name":"审核通过"},{"e_id":"15","check_status":1,"id":"84","type":"1","customer_id":27,"niche_type":"5","intent_man_id":"60","status":"4","is_lost":0,"create_time":"2021-02-25 20:57:30","niche_name":"444","real_name":"葫芦娃","mobile":"110","intent_man_name":"小明","date_str":"2021-03-05 午餐","hall_str":"光明顶/22","total_number":"88","check_status_name":"审核通过"},{"e_id":"14","check_status":0,"id":"46","type":"1","customer_id":22,"niche_type":"6","intent_man_id":"55","status":"6","is_lost":0,"create_time":"2021-02-22 16:26:12","niche_name":"分类1","real_name":"张三","mobile":"112113","intent_man_name":"小花","date_str":"2021-02-18 午餐 / 2021-02-10 晚餐 / 2021-02-18 午餐 / 2021-02-10 晚餐","hall_str":"光明顶/22/22/光明顶/22/22","total_number":"13530","check_status_name":"审批中"}]}
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
         * count : 3
         * list : [{"e_id":"16","check_status":1,"id":"85","type":"1","customer_id":45,"niche_type":"6","intent_man_id":"61","status":"3","is_lost":0,"create_time":"2021-02-25 21:19:16","niche_name":"分类1","real_name":"刘杰","mobile":"888888","intent_man_name":"王五","date_str":"2021-02-27 晚餐","hall_str":"光明顶","total_number":"99","check_status_name":"审核通过"},{"e_id":"15","check_status":1,"id":"84","type":"1","customer_id":27,"niche_type":"5","intent_man_id":"60","status":"4","is_lost":0,"create_time":"2021-02-25 20:57:30","niche_name":"444","real_name":"葫芦娃","mobile":"110","intent_man_name":"小明","date_str":"2021-03-05 午餐","hall_str":"光明顶/22","total_number":"88","check_status_name":"审核通过"},{"e_id":"14","check_status":0,"id":"46","type":"1","customer_id":22,"niche_type":"6","intent_man_id":"55","status":"6","is_lost":0,"create_time":"2021-02-22 16:26:12","niche_name":"分类1","real_name":"张三","mobile":"112113","intent_man_name":"小花","date_str":"2021-02-18 午餐 / 2021-02-10 晚餐 / 2021-02-18 午餐 / 2021-02-10 晚餐","hall_str":"光明顶/22/22/光明顶/22/22","total_number":"13530","check_status_name":"审批中"}]
         */

        private int count;
        private List<ListDTO> list;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public static class ListDTO {
            /**
             * e_id : 16
             * check_status : 1
             * id : 85
             * type : 1
             * customer_id : 45
             * niche_type : 6
             * intent_man_id : 61
             * status : 3
             * is_lost : 0
             * create_time : 2021-02-25 21:19:16
             * niche_name : 分类1
             * real_name : 刘杰
             * mobile : 888888
             * intent_man_name : 王五
             * date_str : 2021-02-27 晚餐
             * hall_str : 光明顶
             * total_number : 99
             * check_status_name : 审核通过
             */

            private String e_id;
            private String check_status;
            private String id;
            private String type;
            private String customer_id;
            private String niche_type;
            private String intent_man_id;
            private String status;
            private String is_lost;
            private String create_time;
            private String niche_name;
            private String real_name;
            private String mobile;
            private String intent_man_name;
            private String date_str;
            private String hall_str;
            private String total_number;
            private String check_status_name;

            public String getE_id() {
                return e_id;
            }

            public void setE_id(String e_id) {
                this.e_id = e_id;
            }

            public String getCheck_status() {
                return check_status;
            }

            public void setCheck_status(String check_status) {
                this.check_status = check_status;
            }

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

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
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

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getIs_lost() {
                return is_lost;
            }

            public void setIs_lost(String is_lost) {
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

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getIntent_man_name() {
                return intent_man_name;
            }

            public void setIntent_man_name(String intent_man_name) {
                this.intent_man_name = intent_man_name;
            }

            public String getDate_str() {
                return date_str;
            }

            public void setDate_str(String date_str) {
                this.date_str = date_str;
            }

            public String getHall_str() {
                return hall_str;
            }

            public void setHall_str(String hall_str) {
                this.hall_str = hall_str;
            }

            public String getTotal_number() {
                return total_number;
            }

            public void setTotal_number(String total_number) {
                this.total_number = total_number;
            }

            public String getCheck_status_name() {
                return check_status_name;
            }

            public void setCheck_status_name(String check_status_name) {
                this.check_status_name = check_status_name;
            }
        }
    }
}
