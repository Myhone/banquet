package com.lingyan.banquet.ui.banquet.bean;

import com.blankj.utilcode.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/7.
 */

public class NetRestoreStep4 {


    /**
     * code : 200
     * msg : ok
     * data : {"id":"23","type":"1","status":"6","step":"6","marketing_type":3,"marketing_type_name":"测试2222www","preferential_fee":"10.00","budget_money":"10.00","sign_money":"10.00","contractor_user":55,"contractor_user_name":"小花","contractor_customer":"小明","contract_no":"Ht20210125133216143191","remarks_4":"","domain":"http://api.forvery.top","pay_type":"1","pay_name":"支付宝","code":"QD20210125133216132441","pay_time":"2021-01-25 13:32:16","payee":1,"payee_name":"管理员","finance_confirmed":"2","is_click":"0","banquetNum":[{"id":"168","banquet_number_id":"141","date":"2021-01-26","type":"1","segment_type":"2","hall_id":["9"],"meal_id":"6","table_number":"10","banquet_status":"4","meal_name":"满汉全席","segment_name":"晚餐","hall_info":[{"id":"9","name":"光明顶"}],"detaile_pic":[],"domain":"http://api.forvery.top"}]}
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
        if (data == null) {
            data = new DataDTO();
        }
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * id : 23
         * type : 1
         * status : 6
         * step : 6
         * marketing_type : 3
         * marketing_type_name : 测试2222www
         * preferential_fee : 10.00
         * budget_money : 10.00
         * sign_money : 10.00
         * contractor_user : 55
         * contractor_user_name : 小花
         * contractor_customer : 小明
         * contract_no : Ht20210125133216143191
         * remarks_4 :
         * domain : http://api.forvery.top
         * pay_type : 1
         * pay_name : 支付宝
         * code : QD20210125133216132441
         * pay_time : 2021-01-25 13:32:16
         * payee : 1
         * payee_name : 管理员
         * finance_confirmed : 2
         * is_click : 0
         * banquetNum : [{"id":"168","banquet_number_id":"141","date":"2021-01-26","type":"1","segment_type":"2","hall_id":["9"],"meal_id":"6","table_number":"10","banquet_status":"4","meal_name":"满汉全席","segment_name":"晚餐","hall_info":[{"id":"9","name":"光明顶"}],"detaile_pic":[],"domain":"http://api.forvery.top"}]
         */

        private String id;
        private String type;
        private String status;
        private String step;
        private String marketing_type;
        private String marketing_type_name;
        private String preferential_fee;
        private String budget_money;
        private String sign_money;
        private String contractor_user;
        private String contractor_user_name;
        private String contractor_customer;
        private String contract_no;
        private String remarks_4;
        private String domain;
        private String pay_type;
        private String pay_name;
        private String code;
        private String pay_time;
        private String payee;
        private String payee_name;
        private String finance_confirmed;
        private String finance_confirmed2;
        private String is_click;
        private String is_lost;
        private String is_status;
        private String intent_man_name;
        private String intent_man_id;
        private String real_name;

        public String getIntent_man_name() {
            return intent_man_name;
        }

        public void setIntent_man_name(String intent_man_name) {
            this.intent_man_name = intent_man_name;
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

        private List<BanquetNumDTO> banquetNum;

        public String getFinance_confirmed2() {
            return finance_confirmed2;
        }

        public void setFinance_confirmed2(String finance_confirmed2) {
            this.finance_confirmed2 = finance_confirmed2;
        }

        public String getIs_lost() {
            if (StringUtils.isEmpty(is_lost)) {
                is_lost = "0";
            }
            return is_lost;
        }

        public void setIs_lost(String is_lost) {
            this.is_lost = is_lost;
        }

        public String getIs_status() {
            if (StringUtils.isEmpty(is_status)) {
                is_status = "0";
            }
            return is_status;
        }

        public void setIs_status(String is_status) {
            this.is_status = is_status;
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

        public String getStatus() {
            if (StringUtils.isEmpty(status)) {
                status = "0";
            }
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

        public String getMarketing_type() {
            return marketing_type;
        }

        public void setMarketing_type(String marketing_type) {
            this.marketing_type = marketing_type;
        }

        public String getMarketing_type_name() {
            return marketing_type_name;
        }

        public void setMarketing_type_name(String marketing_type_name) {
            this.marketing_type_name = marketing_type_name;
        }

        public String getPreferential_fee() {
            return preferential_fee;
        }

        public void setPreferential_fee(String preferential_fee) {
            this.preferential_fee = preferential_fee;
        }

        public String getBudget_money() {
            return budget_money;
        }

        public void setBudget_money(String budget_money) {
            this.budget_money = budget_money;
        }

        public String getSign_money() {
            return sign_money;
        }

        public void setSign_money(String sign_money) {
            this.sign_money = sign_money;
        }

        public String getContractor_user() {
            return contractor_user;
        }

        public void setContractor_user(String contractor_user) {
            this.contractor_user = contractor_user;
        }

        public String getContractor_user_name() {
            return contractor_user_name;
        }

        public void setContractor_user_name(String contractor_user_name) {
            this.contractor_user_name = contractor_user_name;
        }

        public String getContractor_customer() {
            return contractor_customer;
        }

        public void setContractor_customer(String contractor_customer) {
            this.contractor_customer = contractor_customer;
        }

        public String getContract_no() {
            return contract_no;
        }

        public void setContract_no(String contract_no) {
            this.contract_no = contract_no;
        }

        public String getRemarks_4() {
            return remarks_4;
        }

        public void setRemarks_4(String remarks_4) {
            this.remarks_4 = remarks_4;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getPayee() {
            return payee;
        }

        public void setPayee(String payee) {
            this.payee = payee;
        }

        public String getPayee_name() {
            return payee_name;
        }

        public void setPayee_name(String payee_name) {
            this.payee_name = payee_name;
        }

        public String getFinance_confirmed() {
            if (StringUtils.isEmpty(finance_confirmed)) {
                finance_confirmed = "0";
            }
            return finance_confirmed;
        }

        public void setFinance_confirmed(String finance_confirmed) {
            this.finance_confirmed = finance_confirmed;
        }

        public String getIs_click() {
            return is_click;
        }

        public void setIs_click(String is_click) {
            this.is_click = is_click;
        }

        public List<BanquetNumDTO> getBanquetNum() {
            if (banquetNum == null) {
                banquetNum = new ArrayList<>();
            }
            return banquetNum;
        }

        public void setBanquetNum(List<BanquetNumDTO> banquetNum) {
            this.banquetNum = banquetNum;
        }

        public static class BanquetNumDTO {
            /**
             * id : 168
             * banquet_number_id : 141
             * date : 2021-01-26
             * type : 1
             * segment_type : 2
             * hall_id : ["9"]
             * meal_id : 6
             * table_number : 10
             * banquet_status : 4
             * meal_name : 满汉全席
             * segment_name : 晚餐
             * hall_info : [{"id":"9","name":"光明顶"}]
             * detaile_pic : []
             * domain : http://api.forvery.top
             */

            private String id = "0";
            private String banquet_number_id = "0";
            private String date;
            private String type;
            private String segment_type;
            private String meal_id;
            private String table_number;
            private String prepare_number;
            private String banquet_status;
            private String meal_name;
            private String segment_name;
            private List<String> hall_id;
            private List<HallInfoDTO> hall_info;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getBanquet_number_id() {
                return banquet_number_id;
            }

            public void setBanquet_number_id(String banquet_number_id) {
                this.banquet_number_id = banquet_number_id;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSegment_type() {
                return segment_type;
            }

            public void setSegment_type(String segment_type) {
                this.segment_type = segment_type;
            }

            public String getMeal_id() {
                return meal_id;
            }

            public void setMeal_id(String meal_id) {
                this.meal_id = meal_id;
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

            public String getBanquet_status() {
                return banquet_status;
            }

            public void setBanquet_status(String banquet_status) {
                this.banquet_status = banquet_status;
            }

            public String getMeal_name() {
                return meal_name;
            }

            public void setMeal_name(String meal_name) {
                this.meal_name = meal_name;
            }

            public String getSegment_name() {
                return segment_name;
            }

            public void setSegment_name(String segment_name) {
                this.segment_name = segment_name;
            }


            public List<String> getHall_id() {
                if (hall_id == null) {
                    hall_id = new ArrayList<>();
                }
                return hall_id;
            }

            public void setHall_id(List<String> hall_id) {
                this.hall_id = hall_id;
            }

            public List<HallInfoDTO> getHall_info() {
                if (hall_info == null) {
                    hall_info = new ArrayList<>();
                }
                return hall_info;
            }

            public void setHall_info(List<HallInfoDTO> hall_info) {
                this.hall_info = hall_info;
            }


            public static class HallInfoDTO {
                /**
                 * id : 9
                 * name : 光明顶
                 */

                private String id;
                private String name;

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
            }
        }
    }
}
