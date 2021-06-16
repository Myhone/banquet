package com.lingyan.banquet.ui.data.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class NetDataComplete {


    /**
     * code : 200
     * msg : ok
     * data : [{"date":"2021-02-01","table_number":"0","zhi_count":"0"},{"date":"2021-02-02","table_number":"0","zhi_count":"0"},{"date":"2021-02-03","table_number":"0","zhi_count":"0"},{"date":"2021-02-04","table_number":"0","zhi_count":"0"},{"date":"2021-02-05","table_number":"0","zhi_count":"0"},{"date":"2021-02-06","table_number":"0","zhi_count":"0"},{"date":"2021-02-07","table_number":"0","zhi_count":"0"},{"date":"2021-02-08","table_number":"0","zhi_count":"0"},{"date":"2021-02-09","table_number":"13530","zhi_count":"2"},{"date":"2021-02-10","table_number":"0","zhi_count":"0"},{"date":"2021-02-11","table_number":"0","zhi_count":"1"},{"date":"2021-02-12","table_number":"0","zhi_count":"0"},{"date":"2021-02-13","table_number":"0","zhi_count":"0"},{"date":"2021-02-14","table_number":"0","zhi_count":"0"},{"date":"2021-02-15","table_number":"0","zhi_count":"0"},{"date":"2021-02-16","table_number":"0","zhi_count":"0"},{"date":"2021-02-17","table_number":"0","zhi_count":"0"},{"date":"2021-02-18","table_number":"0","zhi_count":"0"},{"date":"2021-02-19","table_number":"0","zhi_count":"2"},{"date":"2021-02-20","table_number":"0","zhi_count":"0"},{"date":"2021-02-21","table_number":"0","zhi_count":"0"},{"date":"2021-02-22","table_number":"0","zhi_count":"0"},{"date":"2021-02-23","table_number":"0","zhi_count":"0"},{"date":"2021-02-24","table_number":"0","zhi_count":"0"},{"date":"2021-02-25","table_number":"0","zhi_count":"0"},{"date":"2021-02-26","table_number":"0","zhi_count":"0"},{"date":"2021-02-27","table_number":"0","zhi_count":"0"},{"date":"2021-02-28","table_number":"0","zhi_count":"0"}]
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
         * date : 2021-02-01
         * table_number : 0
         * zhi_count : 0
         */

        private String date;
        private String table_number;
        private String zhi_count;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getTable_number() {
            return table_number;
        }

        public void setTable_number(String table_number) {
            this.table_number = table_number;
        }

        public String getZhi_count() {
            return zhi_count;
        }

        public void setZhi_count(String zhi_count) {
            this.zhi_count = zhi_count;
        }
    }
}
