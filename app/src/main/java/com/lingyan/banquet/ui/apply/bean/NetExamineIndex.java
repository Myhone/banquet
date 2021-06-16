package com.lingyan.banquet.ui.apply.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/14.
 */

public class NetExamineIndex {


    /**
     * code : 200
     * msg : ok
     * data : {"count":1,"list":[{"e_id":"10","create_user_id":57,"banquet_id":42,"title":"雷提交的宴会丢单申请","type":"1","check_status":3,"check_user_id":"57","create_time":"2021-02-07 17:43:47","id":"42","reason":"豪横","contract_no":"Ht202102081732025738128","customer_id":18,"intent_man_id":"58","over_time":null,"real_name":"哈哈","mobile":"18235143055","intent_man_name":"张三","create_name":"雷","date_str":"2021-02-10 晚餐 / 2021-02-10 午餐","hall_str":"宴会厅一/宴会厅二","check_status_name":"撤回","is_check":"1","is_recheck":"0"}]}
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
         * count : 1
         * list : [{"e_id":"10","create_user_id":57,"banquet_id":42,"title":"雷提交的宴会丢单申请","type":"1","check_status":3,"check_user_id":"57","create_time":"2021-02-07 17:43:47","id":"42","reason":"豪横","contract_no":"Ht202102081732025738128","customer_id":18,"intent_man_id":"58","over_time":null,"real_name":"哈哈","mobile":"18235143055","intent_man_name":"张三","create_name":"雷","date_str":"2021-02-10 晚餐 / 2021-02-10 午餐","hall_str":"宴会厅一/宴会厅二","check_status_name":"撤回","is_check":"1","is_recheck":"0"}]
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
             * e_id : 10
             * create_user_id : 57
             * banquet_id : 42
             * title : 雷提交的宴会丢单申请
             * type : 1
             * check_status : 3
             * check_user_id : 57
             * create_time : 2021-02-07 17:43:47
             * id : 42
             * reason : 豪横
             * contract_no : Ht202102081732025738128
             * customer_id : 18
             * intent_man_id : 58
             * over_time : null
             * real_name : 哈哈
             * mobile : 18235143055
             * intent_man_name : 张三
             * create_name : 雷
             * date_str : 2021-02-10 晚餐 / 2021-02-10 午餐
             * hall_str : 宴会厅一/宴会厅二
             * check_status_name : 撤回
             * is_check : 1
             * is_recheck : 0
             */

            private String e_id;
            private String create_user_id;
            private String banquet_id;
            private String title;
            private String type;
            private String check_status;
            private String check_user_id;
            private String create_time;
            private String id;
            private String reason;
            private String contract_no;
            private String customer_id;
            private String intent_man_id;
            private String over_time;
            private String real_name;
            private String mobile;
            private String intent_man_name;
            private String create_name;
            private String date_str;
            private String hall_str;
            private String check_status_name;
            private String is_check;
            private String is_recheck;

            public String getE_id() {
                return e_id;
            }

            public void setE_id(String e_id) {
                this.e_id = e_id;
            }



            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }



            public String getCheck_user_id() {
                return check_user_id;
            }

            public void setCheck_user_id(String check_user_id) {
                this.check_user_id = check_user_id;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getContract_no() {
                return contract_no;
            }

            public void setContract_no(String contract_no) {
                this.contract_no = contract_no;
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

            public String getCreate_name() {
                return create_name;
            }

            public void setCreate_name(String create_name) {
                this.create_name = create_name;
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

            public String getCheck_status_name() {
                return check_status_name;
            }

            public void setCheck_status_name(String check_status_name) {
                this.check_status_name = check_status_name;
            }

            public String getCreate_user_id() {
                return create_user_id;
            }

            public void setCreate_user_id(String create_user_id) {
                this.create_user_id = create_user_id;
            }

            public String getBanquet_id() {
                return banquet_id;
            }

            public void setBanquet_id(String banquet_id) {
                this.banquet_id = banquet_id;
            }

            public String getCheck_status() {
                return check_status;
            }

            public void setCheck_status(String check_status) {
                this.check_status = check_status;
            }

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }

            public String getOver_time() {
                return over_time;
            }

            public void setOver_time(String over_time) {
                this.over_time = over_time;
            }

            public String getIs_check() {
                return is_check;
            }

            public void setIs_check(String is_check) {
                this.is_check = is_check;
            }

            public String getIs_recheck() {
                return is_recheck;
            }

            public void setIs_recheck(String is_recheck) {
                this.is_recheck = is_recheck;
            }
        }
    }
}
