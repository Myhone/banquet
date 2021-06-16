package com.lingyan.banquet.ui.finance.bean;

/**
 * Created by _hxb on 2021/2/11.
 */

public class NetDepositHeaderData {
    /**
     * code : 200
     * msg : ok
     * data : {"real_income":"0.00","expect_income":"13332.00","real_balance":"0.00","real_deposit":"0.00","refund_deposit":"0.00","real_surplus_income":"13332.00","complete_order":"0","sign_order":"0"}
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
         * real_income : 0.00
         * expect_income : 13332.00
         * real_balance : 0.00
         * real_deposit : 0.00
         * refund_deposit : 0.00
         * real_surplus_income : 13332.00
         * complete_order : 0
         * sign_order : 0
         */

        private String real_income;
        private String expect_income;
        private String real_balance;
        private String real_deposit;
        private String refund_deposit;
        private String real_surplus_income;
        private String complete_order;
        private String sign_order;

        public String getReal_income() {
            return real_income;
        }

        public void setReal_income(String real_income) {
            this.real_income = real_income;
        }

        public String getExpect_income() {
            return expect_income;
        }

        public void setExpect_income(String expect_income) {
            this.expect_income = expect_income;
        }

        public String getReal_balance() {
            return real_balance;
        }

        public void setReal_balance(String real_balance) {
            this.real_balance = real_balance;
        }

        public String getReal_deposit() {
            return real_deposit;
        }

        public void setReal_deposit(String real_deposit) {
            this.real_deposit = real_deposit;
        }

        public String getRefund_deposit() {
            return refund_deposit;
        }

        public void setRefund_deposit(String refund_deposit) {
            this.refund_deposit = refund_deposit;
        }

        public String getReal_surplus_income() {
            return real_surplus_income;
        }

        public void setReal_surplus_income(String real_surplus_income) {
            this.real_surplus_income = real_surplus_income;
        }

        public String getComplete_order() {
            return complete_order;
        }

        public void setComplete_order(String complete_order) {
            this.complete_order = complete_order;
        }

        public String getSign_order() {
            return sign_order;
        }

        public void setSign_order(String sign_order) {
            this.sign_order = sign_order;
        }
    }
}
