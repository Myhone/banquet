package com.lingyan.banquet.ui.finance.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/11.
 */

public class NetDepositHeaderChartData {

    /**
     * code : 200
     * msg : ok
     * data : {"dateList":["02-01","02-02","02-03","02-04","02-05","02-06","02-07","02-08","02-09","02-10","02-11","02-12","02-13","02-14","02-15","02-16","02-17","02-18","02-19","02-20","02-21","02-22","02-23","02-24","02-25","02-26","02-27","02-28"],"real_List":["0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","99999999.99","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00"],"deposit_List":["0.00","0.00","120.00","0.00","0.00","0.00","3333.00","0.00","456789.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00"],"surplus_List":["0.00","0.00","0.00","0.00","0.00","0.00","9999.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00","0.00"]}
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
        private List<String> dateList;
        private List<String> real_List;
        private List<String> deposit_List;
        private List<String> surplus_List;

        public List<String> getDateList() {
            return dateList;
        }

        public void setDateList(List<String> dateList) {
            this.dateList = dateList;
        }

        public List<String> getReal_List() {
            return real_List;
        }

        public void setReal_List(List<String> real_List) {
            this.real_List = real_List;
        }

        public List<String> getDeposit_List() {
            return deposit_List;
        }

        public void setDeposit_List(List<String> deposit_List) {
            this.deposit_List = deposit_List;
        }

        public List<String> getSurplus_List() {
            return surplus_List;
        }

        public void setSurplus_List(List<String> surplus_List) {
            this.surplus_List = surplus_List;
        }
    }
}
