package com.lingyan.banquet.ui.celebration.bean;

import com.blankj.utilcode.util.StringUtils;

/**
 * Created by _hxb on 2021/2/7.
 */

public class NetCelRestoreStep3 {

    /**
     * code : 200
     * msg : ok
     * data : {"id":"37","type":"1","status":"3","step":"3","remarks_3":"备注3333","money":"120.00","pay_type":"1","pay_name":"支付宝","pay_time":"2021-02-03 21:47","code":"ST20210203214707154043","pay_user":"付款人1","payee":1,"payee_name":"管理员","finance_confirmed":"2","is_click":"1"}
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
        if(data==null){
            return new DataDTO();
        }
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * id : 37
         * type : 1
         * status : 3
         * step : 3
         * remarks_3 : 备注3333
         * money : 120.00
         * pay_type : 1
         * pay_name : 支付宝
         * pay_time : 2021-02-03 21:47
         * code : ST20210203214707154043
         * pay_user : 付款人1
         * payee : 1
         * payee_name : 管理员
         * finance_confirmed : 2
         * is_click : 1
         */

        private String id;
        private String type;
        private String status;
        private String step;
        private String remarks_3;
        private String money;
        private String pay_type;
        private String pay_name;
        private String pay_time;
        private String code;
        private String pay_user;
        private String payee;
        private String payee_name;
        private String finance_confirmed;
        private String finance_confirmed1;
        private String is_click;
        private String is_lost;
        private String is_status;
        private String real_name;

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getFinance_confirmed1() {
            return finance_confirmed1;
        }

        public void setFinance_confirmed1(String finance_confirmed1) {
            this.finance_confirmed1 = finance_confirmed1;
        }

        public String getIs_lost() {
            if(StringUtils.isEmpty(is_lost)){
                is_lost="0";
            }
            return is_lost;
        }

        public void setIs_lost(String is_lost) {
            this.is_lost = is_lost;
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

        public String getRemarks_3() {
            return remarks_3;
        }

        public void setRemarks_3(String remarks_3) {
            this.remarks_3 = remarks_3;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
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

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPay_user() {
            return pay_user;
        }

        public void setPay_user(String pay_user) {
            this.pay_user = pay_user;
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
            if(StringUtils.isEmpty(finance_confirmed)){
                finance_confirmed="0";
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
    }
}
