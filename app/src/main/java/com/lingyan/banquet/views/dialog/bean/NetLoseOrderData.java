package com.lingyan.banquet.views.dialog.bean;

/**
 * Created by _hxb on 2021/2/19.
 */

public class NetLoseOrderData {

    /**
     * code : 200
     * msg : ok
     * data : {"id":"18","is_refund":"0","money":"0.00","reason_status":"","reason":""}
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
         * id : 18
         * is_refund : 0
         * money : 0.00
         * reason_status :
         * reason :
         */

        private String id;
        private String is_refund;
        private String money;
        private String reason_status;
        private String reason;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIs_refund() {
            return is_refund;
        }

        public void setIs_refund(String is_refund) {
            this.is_refund = is_refund;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getReason_status() {
            return reason_status;
        }

        public void setReason_status(String reason_status) {
            this.reason_status = reason_status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }
}
