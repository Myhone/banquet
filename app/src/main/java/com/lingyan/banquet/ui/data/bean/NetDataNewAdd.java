package com.lingyan.banquet.ui.data.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class NetDataNewAdd {

    /**
     * code : 200
     * msg : ok
     * data : [{"date":"2021-02-01","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-02","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-03","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-04","add_info":{"data1":"1","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-05","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-06","add_info":{"data1":"3","data2":"1","data3":"0","data4":"0"}},{"date":"2021-02-07","add_info":{"data1":"1","data2":"2","data3":"1","data4":"1"}},{"date":"2021-02-08","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-09","add_info":{"data1":"5","data2":"1","data3":"1","data4":"1"}},{"date":"2021-02-10","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-11","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-12","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-13","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-14","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-15","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-16","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-17","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-18","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-19","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-20","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-21","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-22","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-23","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-24","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-25","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-26","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-27","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}},{"date":"2021-02-28","add_info":{"data1":"0","data2":"0","data3":"0","data4":"0"}}]
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
         * add_info : {"data1":"0","data2":"0","data3":"0","data4":"0"}
         */

        private String date;
        private AddInfoDTO add_info;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public AddInfoDTO getAdd_info() {
            return add_info;
        }

        public void setAdd_info(AddInfoDTO add_info) {
            this.add_info = add_info;
        }

        public static class AddInfoDTO {
            /**
             * data1 : 0
             * data2 : 0
             * data3 : 0
             * data4 : 0
             */

            private String data1;
            private String data2;
            private String data3;
            private String data4;

            public String getData1() {
                return data1;
            }

            public void setData1(String data1) {
                this.data1 = data1;
            }

            public String getData2() {
                return data2;
            }

            public void setData2(String data2) {
                this.data2 = data2;
            }

            public String getData3() {
                return data3;
            }

            public void setData3(String data3) {
                this.data3 = data3;
            }

            public String getData4() {
                return data4;
            }

            public void setData4(String data4) {
                this.data4 = data4;
            }
        }
    }
}
