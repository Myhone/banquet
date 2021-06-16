package com.lingyan.banquet.ui.order.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/5.
 */

public class NetBanquetOrderList {

    /**
     * code : 200
     * msg : ok
     * data : {"count":6,"list":[{"id":"42","type":"1","create_time":"2021-02-05 08:32:31","status":"1","step":"1","is_lost":0,"date":"2021-02-12","customer_type":"3","real_name":"哈哈","intent_man_id":"58","niche_type":"4","niche_source_id":"58","mobile":"18235143055","Intentionality":0,"contract_no":"","lock_money":"0.00","sign_money":"0.00","final_amount":"0.00","balance":"0.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"外来","niche_name":"婚宴","total_number":0,"date_list":"","status_name":"商机","hall_list":""},{"id":"40","type":"1","create_time":"2021-02-04 23:02:11","status":"1","step":"1","is_lost":0,"date":"2021-02-05","customer_type":"3","real_name":"哈哈","intent_man_id":"58","niche_type":"4","niche_source_id":"58","mobile":"18235143055","Intentionality":0,"contract_no":"","lock_money":"0.00","sign_money":"0.00","final_amount":"0.00","balance":"0.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"外来","niche_name":"婚宴","total_number":0,"date_list":"","status_name":"商机","hall_list":""},{"id":"35","type":"1","create_time":"2021-01-30 15:49:20","status":"1","step":"1","is_lost":0,"date":"2021-01-31","customer_type":"4","real_name":"雷冬凯","intent_man_id":"58","niche_type":"8","niche_source_id":"58","mobile":"17636655276","Intentionality":0,"contract_no":"","lock_money":"0.00","sign_money":"0.00","final_amount":"0.00","balance":"0.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"介绍","niche_name":"婚宴2","total_number":0,"date_list":"","status_name":"商机","hall_list":""},{"id":"25","type":"1","create_time":"2021-01-26 15:00:22","status":"5","step":"6","is_lost":0,"date":"2021-01-31","customer_type":"4","real_name":"雷冬凯","intent_man_id":"58","niche_type":"8","niche_source_id":"58","mobile":"17636655276","Intentionality":4,"contract_no":"Ht202101261650335735656","lock_money":"10000.00","sign_money":"3000.00","final_amount":"15000.00","balance":"2000.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"介绍","niche_name":"婚宴2","total_num":3,"total_number":35,"date_list":"2021-01-31 晚餐 / 2021-01-31 午餐 / 2021-01-28 晚餐","status_name":"执行","hall_list":"宴会厅一/宴会厅二/宴会厅二/宴会厅一"},{"id":"26","type":"1","create_time":"2021-01-27 14:43:10","status":"2","step":"3","is_lost":0,"date":"2021-01-30","customer_type":"3","real_name":"张磊","intent_man_id":"58","niche_type":"7","niche_source_id":"58","mobile":"18513092808","Intentionality":3,"contract_no":"","lock_money":"1000.00","sign_money":"0.00","final_amount":"0.00","balance":"0.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"外来","niche_name":"婚宴1","total_num":1,"total_number":30,"date_list":"2021-01-30 午餐","status_name":"意向","hall_list":"宴会厅二/宴会厅一"},{"id":"17","type":"1","create_time":"2021-01-22 20:01:53","status":"3","step":"2","is_lost":0,"date":"2021-01-28","customer_type":"4","real_name":"雷冬凯","intent_man_id":"58","niche_type":"8","niche_source_id":"58","mobile":"17636655276","Intentionality":4,"contract_no":"","lock_money":"1000.00","sign_money":"0.00","final_amount":"0.00","balance":"0.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"介绍","niche_name":"婚宴2","total_num":2,"total_number":46,"date_list":"2021-02-01 晚餐 / 2021-02-02 晚餐","status_name":"锁台","hall_list":"宴会厅一/宴会厅二/宴会厅一"}]}
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
         * count : 6
         * list : [{"id":"42","type":"1","create_time":"2021-02-05 08:32:31","status":"1","step":"1","is_lost":0,"date":"2021-02-12","customer_type":"3","real_name":"哈哈","intent_man_id":"58","niche_type":"4","niche_source_id":"58","mobile":"18235143055","Intentionality":0,"contract_no":"","lock_money":"0.00","sign_money":"0.00","final_amount":"0.00","balance":"0.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"外来","niche_name":"婚宴","total_number":0,"date_list":"","status_name":"商机","hall_list":""},{"id":"40","type":"1","create_time":"2021-02-04 23:02:11","status":"1","step":"1","is_lost":0,"date":"2021-02-05","customer_type":"3","real_name":"哈哈","intent_man_id":"58","niche_type":"4","niche_source_id":"58","mobile":"18235143055","Intentionality":0,"contract_no":"","lock_money":"0.00","sign_money":"0.00","final_amount":"0.00","balance":"0.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"外来","niche_name":"婚宴","total_number":0,"date_list":"","status_name":"商机","hall_list":""},{"id":"35","type":"1","create_time":"2021-01-30 15:49:20","status":"1","step":"1","is_lost":0,"date":"2021-01-31","customer_type":"4","real_name":"雷冬凯","intent_man_id":"58","niche_type":"8","niche_source_id":"58","mobile":"17636655276","Intentionality":0,"contract_no":"","lock_money":"0.00","sign_money":"0.00","final_amount":"0.00","balance":"0.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"介绍","niche_name":"婚宴2","total_number":0,"date_list":"","status_name":"商机","hall_list":""},{"id":"25","type":"1","create_time":"2021-01-26 15:00:22","status":"5","step":"6","is_lost":0,"date":"2021-01-31","customer_type":"4","real_name":"雷冬凯","intent_man_id":"58","niche_type":"8","niche_source_id":"58","mobile":"17636655276","Intentionality":4,"contract_no":"Ht202101261650335735656","lock_money":"10000.00","sign_money":"3000.00","final_amount":"15000.00","balance":"2000.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"介绍","niche_name":"婚宴2","total_num":3,"total_number":35,"date_list":"2021-01-31 晚餐 / 2021-01-31 午餐 / 2021-01-28 晚餐","status_name":"执行","hall_list":"宴会厅一/宴会厅二/宴会厅二/宴会厅一"},{"id":"26","type":"1","create_time":"2021-01-27 14:43:10","status":"2","step":"3","is_lost":0,"date":"2021-01-30","customer_type":"3","real_name":"张磊","intent_man_id":"58","niche_type":"7","niche_source_id":"58","mobile":"18513092808","Intentionality":3,"contract_no":"","lock_money":"1000.00","sign_money":"0.00","final_amount":"0.00","balance":"0.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"外来","niche_name":"婚宴1","total_num":1,"total_number":30,"date_list":"2021-01-30 午餐","status_name":"意向","hall_list":"宴会厅二/宴会厅一"},{"id":"17","type":"1","create_time":"2021-01-22 20:01:53","status":"3","step":"2","is_lost":0,"date":"2021-01-28","customer_type":"4","real_name":"雷冬凯","intent_man_id":"58","niche_type":"8","niche_source_id":"58","mobile":"17636655276","Intentionality":4,"contract_no":"","lock_money":"1000.00","sign_money":"0.00","final_amount":"0.00","balance":"0.00","intent_man_name":"张三","niche_source_name":"张三","customer_type_name":"介绍","niche_name":"婚宴2","total_num":2,"total_number":46,"date_list":"2021-02-01 晚餐 / 2021-02-02 晚餐","status_name":"锁台","hall_list":"宴会厅一/宴会厅二/宴会厅一"}]
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
             * id : 42
             * type : 1
             * create_time : 2021-02-05 08:32:31
             * status : 1
             * step : 1
             * is_lost : 0
             * date : 2021-02-12
             * customer_type : 3
             * real_name : 哈哈
             * intent_man_id : 58
             * niche_type : 4
             * niche_source_id : 58
             * mobile : 18235143055
             * Intentionality : 0
             * contract_no :
             * lock_money : 0.00
             * sign_money : 0.00
             * final_amount : 0.00
             * balance : 0.00
             * intent_man_name : 张三
             * niche_source_name : 张三
             * customer_type_name : 外来
             * niche_name : 婚宴
             * total_number : 0
             * date_list :
             * status_name : 商机
             * hall_list :
             * total_num : 3
             */

            private String id;
            private String banquet_id;
            private String type;
            private String create_time;
            private String status;
            private String step;
            private int is_lost;
            private String date;
            private String customer_type;
            private String real_name;
            private String intent_man_id;
            private String niche_type;
            private String niche_source_id;
            private String mobile;
            private int Intentionality;
            private String contract_no;
            private String lock_money;
            private String sign_money;
            private String final_amount;
            private String balance;
            private String intent_man_name;
            private String niche_source_name;
            private String customer_type_name;
            private String niche_name;
            private int total_number;
            private String date_list;
            private String status_name;
            private String hall_list;
            private int total_num;

            public String getBanquet_id() {
                return banquet_id;
            }

            public void setBanquet_id(String banquet_id) {
                this.banquet_id = banquet_id;
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

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStep() {
                return step;
            }

            public void setStep(String step) {
                this.step = step;
            }

            public int getIs_lost() {
                return is_lost;
            }

            public void setIs_lost(int is_lost) {
                this.is_lost = is_lost;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCustomer_type() {
                return customer_type;
            }

            public void setCustomer_type(String customer_type) {
                this.customer_type = customer_type;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public String getIntent_man_id() {
                return intent_man_id;
            }

            public void setIntent_man_id(String intent_man_id) {
                this.intent_man_id = intent_man_id;
            }

            public String getNiche_type() {
                return niche_type;
            }

            public void setNiche_type(String niche_type) {
                this.niche_type = niche_type;
            }

            public String getNiche_source_id() {
                return niche_source_id;
            }

            public void setNiche_source_id(String niche_source_id) {
                this.niche_source_id = niche_source_id;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getIntentionality() {
                return Intentionality;
            }

            public void setIntentionality(int Intentionality) {
                this.Intentionality = Intentionality;
            }

            public String getContract_no() {
                return contract_no;
            }

            public void setContract_no(String contract_no) {
                this.contract_no = contract_no;
            }

            public String getLock_money() {
                return lock_money;
            }

            public void setLock_money(String lock_money) {
                this.lock_money = lock_money;
            }

            public String getSign_money() {
                return sign_money;
            }

            public void setSign_money(String sign_money) {
                this.sign_money = sign_money;
            }

            public String getFinal_amount() {
                return final_amount;
            }

            public void setFinal_amount(String final_amount) {
                this.final_amount = final_amount;
            }

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getIntent_man_name() {
                return intent_man_name;
            }

            public void setIntent_man_name(String intent_man_name) {
                this.intent_man_name = intent_man_name;
            }

            public String getNiche_source_name() {
                return niche_source_name;
            }

            public void setNiche_source_name(String niche_source_name) {
                this.niche_source_name = niche_source_name;
            }

            public String getCustomer_type_name() {
                return customer_type_name;
            }

            public void setCustomer_type_name(String customer_type_name) {
                this.customer_type_name = customer_type_name;
            }

            public String getNiche_name() {
                return niche_name;
            }

            public void setNiche_name(String niche_name) {
                this.niche_name = niche_name;
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

            public String getStatus_name() {
                return status_name;
            }

            public void setStatus_name(String status_name) {
                this.status_name = status_name;
            }

            public String getHall_list() {
                return hall_list;
            }

            public void setHall_list(String hall_list) {
                this.hall_list = hall_list;
            }

            public int getTotal_num() {
                return total_num;
            }

            public void setTotal_num(int total_num) {
                this.total_num = total_num;
            }
        }
    }
}
