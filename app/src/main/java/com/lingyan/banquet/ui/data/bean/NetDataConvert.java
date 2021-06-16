package com.lingyan.banquet.ui.data.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class NetDataConvert {


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
        private String date;
        private DatacrDTO datacr;

        @Override
        public String toString() {
            return "DataDTO{" +
                    "date='" + date + '\'' +
                    ", datacr=" + datacr +
                    '}';
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public DatacrDTO getDatacr() {
            return datacr;
        }

        public void setDatacr(DatacrDTO datacr) {
            this.datacr = datacr;
        }

        public static class DatacrDTO {
            private String sj_cr;
            private String yx_cr;
            private String st_cr;

            @Override
            public String toString() {
                return "DatacrDTO{" +
                        "sj_cr='" + sj_cr + '\'' +
                        ", yx_cr='" + yx_cr + '\'' +
                        ", st_cr='" + st_cr + '\'' +
                        '}';
            }

            public String getSj_cr() {
                return sj_cr;
            }

            public void setSj_cr(String sj_cr) {
                this.sj_cr = sj_cr;
            }

            public String getYx_cr() {
                return yx_cr;
            }

            public void setYx_cr(String yx_cr) {
                this.yx_cr = yx_cr;
            }

            public String getSt_cr() {
                return st_cr;
            }

            public void setSt_cr(String st_cr) {
                this.st_cr = st_cr;
            }
        }
    }
}
