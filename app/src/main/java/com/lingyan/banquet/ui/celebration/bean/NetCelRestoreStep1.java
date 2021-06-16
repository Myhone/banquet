package com.lingyan.banquet.ui.celebration.bean;

import com.blankj.utilcode.util.StringUtils;

/**
 * Created by _hxb on 2021/2/6.
 */

public class NetCelRestoreStep1 {


    /**
     * code : 200
     * msg : ok
     * data : {"id":"43","type":"1","status":"1","step":"1","date":"2021-02-16","niche_type":"2","niche_name":"啊啊啊","niche_source_id":"54","niche_source_name":"小明","intent_man_id":"54","intent_man_name":"小明","customer_type":"4","customer_type_name":"介绍","customer_remarks":"11dfdsf","remarks_1":"","linkmen":{"real_name":"11111","sex":"0","mobile":"11111"},"is_click":"1"}
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
         * id : 43
         * type : 1
         * status : 1
         * step : 1
         * date : 2021-02-16
         * niche_type : 2
         * niche_name : 啊啊啊
         * niche_source_id : 54
         * niche_source_name : 小明
         * intent_man_id : 54
         * intent_man_name : 小明
         * customer_type : 4
         * customer_type_name : 介绍
         * customer_remarks : 11dfdsf
         * remarks_1 :
         * linkmen : {"real_name":"11111","sex":"0","mobile":"11111"}
         * is_click : 1
         */

        private String id;
        private String type;
        private String status;
        private String step;
        private String date;
        private String niche_type;
        private String niche_name;
        private String niche_source_id;
        private String niche_source_name;
        private String intent_man_id;
        private String intent_man_name;
        private String customer_type;
        private String customer_type_name;
        private String customer_remarks;
        private String remarks_1;
        private LinkmenDTO linkmen;
        private String is_click;
        private String is_lost;
        private String finance_confirmed;
        private String is_status;

        public String getIs_lost() {
            if(StringUtils.isEmpty(is_lost)){
                is_lost="0";
            }
            return is_lost;
        }

        public void setIs_lost(String is_lost) {
            this.is_lost = is_lost;
        }

        public String getFinance_confirmed() {
            if(StringUtils.isEmpty(finance_confirmed)){
                finance_confirmed="0";
            }
            return finance_confirmed;
        }

        public void setFinance_confirmed(String finance_confirmed) {
            this.finance_confirmed = finance_confirmed;
        }

        public String getIs_status() {
            if(StringUtils.isEmpty(is_status)){
                is_status="0";
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
            if(StringUtils.isEmpty(status)){
                status="0";
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

        public String getNiche_name() {
            return niche_name;
        }

        public void setNiche_name(String niche_name) {
            this.niche_name = niche_name;
        }

        public String getNiche_source_id() {
            return niche_source_id;
        }

        public void setNiche_source_id(String niche_source_id) {
            this.niche_source_id = niche_source_id;
        }

        public String getNiche_source_name() {
            return niche_source_name;
        }

        public void setNiche_source_name(String niche_source_name) {
            this.niche_source_name = niche_source_name;
        }

        public String getIntent_man_id() {
            return intent_man_id;
        }

        public void setIntent_man_id(String intent_man_id) {
            this.intent_man_id = intent_man_id;
        }

        public String getIntent_man_name() {
            return intent_man_name;
        }

        public void setIntent_man_name(String intent_man_name) {
            this.intent_man_name = intent_man_name;
        }

        public String getCustomer_type() {
            return customer_type;
        }

        public void setCustomer_type(String customer_type) {
            this.customer_type = customer_type;
        }

        public String getCustomer_type_name() {
            return customer_type_name;
        }

        public void setCustomer_type_name(String customer_type_name) {
            this.customer_type_name = customer_type_name;
        }

        public String getCustomer_remarks() {
            return customer_remarks;
        }

        public void setCustomer_remarks(String customer_remarks) {
            this.customer_remarks = customer_remarks;
        }

        public String getRemarks_1() {
            return remarks_1;
        }

        public void setRemarks_1(String remarks_1) {
            this.remarks_1 = remarks_1;
        }

        public LinkmenDTO getLinkmen() {
            if (linkmen == null) {
                linkmen = new LinkmenDTO();
            }
            return linkmen;
        }

        public void setLinkmen(LinkmenDTO linkmen) {
            this.linkmen = linkmen;
        }

        public String getIs_click() {
            return is_click;
        }

        public void setIs_click(String is_click) {
            this.is_click = is_click;
        }

        public static class LinkmenDTO {
            /**
             * real_name : 11111
             * sex : 0
             * mobile : 11111
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
    }
}
