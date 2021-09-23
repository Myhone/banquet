package com.lingyan.banquet.ui.order.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/5.
 */

public class NetOrderDetail {

    /**
     * code : 200
     * msg : ok
     * data : {"id":"25","step":"6","status":"5","status_name":"执行","create_time":"2021-01-26 15:00:22","date":"2021-01-31","customer_type_name":"介绍","niche_name":"婚宴2","niche_source_name":"张三","intent_man_name":"张三","is_yd_qd":"0","likemen":{"real_name":"雷冬凯","sex":"1","mobile":"17636655276"},"Intentionality":4,"number_info":{"count":3,"date":["2021-01-31 晚餐","2021-01-31 午餐","2021-01-28 晚餐"],"number_list":"宴会厅二,宴会厅一,宴会厅二,宴会厅一","table_number":"35","contract_no":"Ht202101261650335735656"},"fund_info":{"lock_money":"10000.00","sign_money":"3000.00","balance":"0.00","final_amount":"0.00"}}
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
         * id : 25
         * step : 6
         * status : 5
         * status_name : 执行
         * create_time : 2021-01-26 15:00:22
         * date : 2021-01-31
         * customer_type_name : 介绍
         * niche_name : 婚宴2
         * niche_source_name : 张三
         * intent_man_name : 张三
         * is_yd_qd : 0
         * likemen : {"real_name":"雷冬凯","sex":"1","mobile":"17636655276"}
         * Intentionality : 4
         * number_info : {"count":3,"date":["2021-01-31 晚餐","2021-01-31 午餐","2021-01-28 晚餐"],"number_list":"宴会厅二,宴会厅一,宴会厅二,宴会厅一","table_number":"35","contract_no":"Ht202101261650335735656"}
         * fund_info : {"lock_money":"10000.00","sign_money":"3000.00","balance":"0.00","final_amount":"0.00"}
         */

        private String id;
        private int step;
        private String status;
        private String status_name;
        private String create_time;
        private String date;
        private String customer_type_name;
        private String niche_name;
        private String niche_source_name;
        private String intent_man_name;
        private String is_yd_qd;
        private String gl_step;
        private String gl_id;
        private String is_modify_order;

        private LikemenDTO likemen;
        private String Intentionality;
        private NumberInfoDTO number_info;
        private FundInfoDTO fund_info;
        private String is_show;
        private int follow_time;
        private int is_modify_hall;
        private String contract_no;
        private List<NumberListDTO> number_list;

        public String getIs_modify_order() {
            return is_modify_order;
        }

        public void setIs_modify_order(String is_modify_order) {
            this.is_modify_order = is_modify_order;
        }

        public int getIs_modify_hall() {
            return is_modify_hall;
        }

        public void setIs_modify_hall(int is_modify_hall) {
            this.is_modify_hall = is_modify_hall;
        }

        public String getContract_no() {
            return contract_no;
        }

        public void setContract_no(String contract_no) {
            this.contract_no = contract_no;
        }

        public List<NumberListDTO> getNumber_list() {
            return number_list;
        }

        public void setNumber_list(List<NumberListDTO> number_list) {
            this.number_list = number_list;
        }

        public int getFollow_time() {
            return follow_time;
        }

        public void setFollow_time(int follow_time) {
            this.follow_time = follow_time;
        }

        public String getGl_step() {
            return gl_step;
        }

        public void setGl_step(String gl_step) {
            this.gl_step = gl_step;
        }

        public String getGl_id() {
            return gl_id;
        }

        public void setGl_id(String gl_id) {
            this.gl_id = gl_id;
        }

        public String getIs_show() {
            return is_show;
        }

        public void setIs_show(String is_show) {
            this.is_show = is_show;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getStep() {
            return step;
        }

        public void setStep(int step) {
            this.step = step;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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

        public String getNiche_source_name() {
            return niche_source_name;
        }

        public void setNiche_source_name(String niche_source_name) {
            this.niche_source_name = niche_source_name;
        }

        public String getIntent_man_name() {
            return intent_man_name;
        }

        public void setIntent_man_name(String intent_man_name) {
            this.intent_man_name = intent_man_name;
        }

        public String getIs_yd_qd() {
            return is_yd_qd;
        }

        public void setIs_yd_qd(String is_yd_qd) {
            this.is_yd_qd = is_yd_qd;
        }

        public LikemenDTO getLikemen() {
            return likemen;
        }

        public void setLikemen(LikemenDTO likemen) {
            this.likemen = likemen;
        }

        public String getIntentionality() {
            return Intentionality;
        }

        public void setIntentionality(String Intentionality) {
            this.Intentionality = Intentionality;
        }

        public NumberInfoDTO getNumber_info() {
            return number_info;
        }

        public void setNumber_info(NumberInfoDTO number_info) {
            this.number_info = number_info;
        }

        public FundInfoDTO getFund_info() {
            return fund_info;
        }

        public void setFund_info(FundInfoDTO fund_info) {
            this.fund_info = fund_info;
        }

        public static class NumberListDTO {

            /**
             * sort : 2-1
             * number_id : 1103
             * date_name : 2021-06-21 晚餐
             * hall_name : 卢浮宫厅/花海厅
             * hall_id : [11,13]
             * table_number : 40
             * prepare_number : 4
             * is_modify_hall : 1
             */

            private String sort;
            private String number_id;
            private String date_name;
            private String date;
            private String hall_name;
            private String segment_type;
            private List<String> hall_id;
            private String table_number;
            private String prepare_number;
            private int is_modify_hall;

            public String getSegment_type() {
                return segment_type;
            }

            public void setSegment_type(String segment_type) {
                this.segment_type = segment_type;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
            }

            public String getNumber_id() {
                return number_id;
            }

            public void setNumber_id(String number_id) {
                this.number_id = number_id;
            }

            public String getDate_name() {
                return date_name;
            }

            public void setDate_name(String date_name) {
                this.date_name = date_name;
            }

            public String getHall_name() {
                return hall_name;
            }

            public void setHall_name(String hall_name) {
                this.hall_name = hall_name;
            }

            public List<String> getHall_id() {
                return hall_id;
            }

            public void setHall_id(List<String> hall_id) {
                this.hall_id = hall_id;
            }

            public String getTable_number() {
                return table_number;
            }

            public void setTable_number(String table_number) {
                this.table_number = table_number;
            }

            public String getPrepare_number() {
                return prepare_number;
            }

            public void setPrepare_number(String prepare_number) {
                this.prepare_number = prepare_number;
            }

            public int getIs_modify_hall() {
                return is_modify_hall;
            }

            public void setIs_modify_hall(int is_modify_hall) {
                this.is_modify_hall = is_modify_hall;
            }
        }

        public static class LikemenDTO {
            /**
             * real_name : 雷冬凯
             * sex : 1
             * mobile : 17636655276
             */

            private String real_name;
            private String sex;
            private String mobile;

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }
        }

        public static class NumberInfoDTO {
            /**
             * count : 3
             * date : ["2021-01-31 晚餐","2021-01-31 午餐","2021-01-28 晚餐"]
             * number_list : 宴会厅二,宴会厅一,宴会厅二,宴会厅一
             * table_number : 35
             * contract_no : Ht202101261650335735656
             */

            private String count;
            private String number_list;
            private String table_number;
            private String prepare_number;
            private String contract_no;
            private List<String> date;

            public String getPrepare_number() {
                return prepare_number;
            }

            public void setPrepare_number(String prepare_number) {
                this.prepare_number = prepare_number;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getNumber_list() {
                return number_list;
            }

            public void setNumber_list(String number_list) {
                this.number_list = number_list;
            }

            public String getTable_number() {
                return table_number;
            }

            public void setTable_number(String table_number) {
                this.table_number = table_number;
            }

            public String getContract_no() {
                return contract_no;
            }

            public void setContract_no(String contract_no) {
                this.contract_no = contract_no;
            }

            public List<String> getDate() {
                return date;
            }

            public void setDate(List<String> date) {
                this.date = date;
            }
        }

        public static class FundInfoDTO {
            /**
             * lock_money : 10000.00
             * sign_money : 3000.00
             * balance : 0.00
             * final_amount : 0.00
             */

            private String lock_money;
            private String sign_money;
            private String balance;
            private String final_amount;

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

            public String getBalance() {
                return balance;
            }

            public void setBalance(String balance) {
                this.balance = balance;
            }

            public String getFinal_amount() {
                return final_amount;
            }

            public void setFinal_amount(String final_amount) {
                this.final_amount = final_amount;
            }
        }
    }
}
