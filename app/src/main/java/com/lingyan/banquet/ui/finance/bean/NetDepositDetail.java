package com.lingyan.banquet.ui.finance.bean;

/**
 * Created by _hxb on 2021/2/8.
 */

public class NetDepositDetail {


    /**
     * code : 200
     * msg : ok
     * data : {"id":"132","step":"2","banquet_type":"1","status":"商机","banquet_id":"97","pay_time":"2021-03-01 11:40:41","money":"5000.00","pay_type":"1","code":"ST202103011140417937444","pay_type_name":"支付宝","finance_confirmed":"2","is_collection":"否","pay_user":"","payee_time":"2021-03-01 11:55:47","payee":"牛梦帆","title":"宴会锁台定金","date":"2021-03-30","customer":"牛梦凡 15903431023"}
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
         * id : 132
         * step : 2
         * banquet_type : 1
         * status : 商机
         * banquet_id : 97
         * pay_time : 2021-03-01 11:40:41
         * money : 5000.00
         * pay_type : 1
         * code : ST202103011140417937444
         * pay_type_name : 支付宝
         * finance_confirmed : 2
         * is_collection : 否
         * pay_user :
         * payee_time : 2021-03-01 11:55:47
         * payee : 牛梦帆
         * title : 宴会锁台定金
         * date : 2021-03-30
         * customer : 牛梦凡 15903431023
         */

        private String id;
        private String step;
        private String banquet_type;
        private String status;
        private String banquet_id;
        private String pay_time;
        private String money;
        private String pay_type;
        private String code;
        private int type;
        private String pay_type_name;
        private String finance_confirmed;
        private String is_collection;
        private String pay_user;
        private String payee_time;
        private String payee;
        private String title;
        private String date;
        private String customer;
        private String refund_realname;
        private String refund_time;
        private String is_display;

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getIs_display() {
            return is_display;
        }

        public void setIs_display(String is_display) {
            this.is_display = is_display;
        }

        public String getRefund_realname() {
            return refund_realname;
        }

        public void setRefund_realname(String refund_realname) {
            this.refund_realname = refund_realname;
        }

        public String getRefund_time() {
            return refund_time;
        }

        public void setRefund_time(String refund_time) {
            this.refund_time = refund_time;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getBanquet_type() {
            return banquet_type;
        }

        public void setBanquet_type(String banquet_type) {
            this.banquet_type = banquet_type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBanquet_id() {
            return banquet_id;
        }

        public void setBanquet_id(String banquet_id) {
            this.banquet_id = banquet_id;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
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

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPay_type_name() {
            return pay_type_name;
        }

        public void setPay_type_name(String pay_type_name) {
            this.pay_type_name = pay_type_name;
        }

        public String getFinance_confirmed() {
            return finance_confirmed;
        }

        public void setFinance_confirmed(String finance_confirmed) {
            this.finance_confirmed = finance_confirmed;
        }

        public String getIs_collection() {
            return is_collection;
        }

        public void setIs_collection(String is_collection) {
            this.is_collection = is_collection;
        }

        public String getPay_user() {
            return pay_user;
        }

        public void setPay_user(String pay_user) {
            this.pay_user = pay_user;
        }

        public String getPayee_time() {
            return payee_time;
        }

        public void setPayee_time(String payee_time) {
            this.payee_time = payee_time;
        }

        public String getPayee() {
            return payee;
        }

        public void setPayee(String payee) {
            this.payee = payee;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCustomer() {
            return customer;
        }

        public void setCustomer(String customer) {
            this.customer = customer;
        }
    }
}
