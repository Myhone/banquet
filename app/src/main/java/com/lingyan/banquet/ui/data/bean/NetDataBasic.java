package com.lingyan.banquet.ui.data.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class NetDataBasic {

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private CompleteData completeData;
        private ExpectData expectData;
        private IncompleteData incompleteData;
        private IncreaseData increaseData;
        private List<CustomerTypeData> customerTypeData;

        public CompleteData getCompleteData() {
            return completeData;
        }

        public void setCompleteData(CompleteData completeData) {
            this.completeData = completeData;
        }

        public ExpectData getExpectData() {
            return expectData;
        }

        public void setExpectData(ExpectData expectData) {
            this.expectData = expectData;
        }

        public IncompleteData getIncompleteData() {
            return incompleteData;
        }

        public void setIncompleteData(IncompleteData incompleteData) {
            this.incompleteData = incompleteData;
        }

        public IncreaseData getIncreaseData() {
            return increaseData;
        }

        public void setIncreaseData(IncreaseData increaseData) {
            this.increaseData = increaseData;
        }

        public List<CustomerTypeData> getCustomerTypeData() {
            return customerTypeData;
        }

        public void setCustomerTypeData(List<CustomerTypeData> customerTypeData) {
            this.customerTypeData = customerTypeData;
        }
    }

    public static class CompleteData {
        public String income;
        public String deposit;
        public String balance;
        public String final_amount;
        public String table_number;
        public String order_number;
    }

    public static class ExpectData {
        public String income;
        public String final_amount;
        public String table_number;
        public String prepare_number;
        public String order_number;
    }

    public static class IncompleteData {
        public String income;
        public String final_amount;
        public String table_number;
        public String prepare_number;
        public String order_number;
    }

    public static class IncreaseData {
        public String data1;
        public String data2;
        public String data3;
        public String data4;
        public String data5;
        public String data6;
        public String follow_all;
        public String follow_banquet;
    }

    public static class CustomerTypeData {
        public String name;
        public String value;
    }

}
