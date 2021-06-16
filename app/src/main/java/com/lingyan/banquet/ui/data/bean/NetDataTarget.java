package com.lingyan.banquet.ui.data.bean;

/**
 * Created by _hxb on 2021/2/21.
 */

public class NetDataTarget {

    /**
     * code : 200
     * msg : ok
     * data : {"user_number":"0","over_number":"0","rate":"0","rate_num":"0","sx_num":"100%"}
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
         * user_number : 0
         * over_number : 0
         * rate : 0
         * rate_num : 0
         * sx_num : 100%
         */

        private String user_number;
        private String over_number;
        private String rate;
        private String rate_num;
        private String sx_num;

        public String getUser_number() {
            return user_number;
        }

        public void setUser_number(String user_number) {
            this.user_number = user_number;
        }

        public String getOver_number() {
            return over_number;
        }

        public void setOver_number(String over_number) {
            this.over_number = over_number;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getRate_num() {
            return rate_num;
        }

        public void setRate_num(String rate_num) {
            this.rate_num = rate_num;
        }

        public String getSx_num() {
            return sx_num;
        }

        public void setSx_num(String sx_num) {
            this.sx_num = sx_num;
        }
    }
}
