package com.lingyan.banquet.ui.finance.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/11.
 */

public class NetDepositStatisticsData {

    /**
     * code : 200
     * msg : ok
     * data : [{"date":"2021-02","balance":"0.00","deposit":"0.00","surplus":"0.00","real_money":"0.00"},{"date":"2021-03","balance":"0.00","deposit":"0.00","surplus":"0.00","real_money":"0.00"}]
     */

    private int code;
    private String msg;
    private List<DataDTO> data;

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

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * date : 2021-02
         * balance : 0.00
         * deposit : 0.00
         * surplus : 0.00
         * real_money : 0.00
         */

        private String date;
        private String balance;
        private String deposit;
        private String surplus;
        private String real_money;
        private String money;
        private String count;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getDeposit() {
            return deposit;
        }

        public void setDeposit(String deposit) {
            this.deposit = deposit;
        }

        public String getSurplus() {
            return surplus;
        }

        public void setSurplus(String surplus) {
            this.surplus = surplus;
        }

        public String getReal_money() {
            return real_money;
        }

        public void setReal_money(String real_money) {
            this.real_money = real_money;
        }
    }
}
