package com.lingyan.banquet.ui.data.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/21.
 */

public class NetDataSuccess {


    /**
     * code : 200
     * msg : ok
     * data : {"arr":[{"name":"商机","value":"14"},{"name":"意向","value":"7"},{"name":"锁台","value":"5"},{"name":"签定","value":"4"},{"name":"执行","value":"2"},{"name":"完成","value":"2"}],"p_arr":{"data1":"50%","data2":"71.428571428571%","data3":"80%","data4":"50%","data5":"100%"}}
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
         * arr : [{"name":"商机","value":"14"},{"name":"意向","value":"7"},{"name":"锁台","value":"5"},{"name":"签定","value":"4"},{"name":"执行","value":"2"},{"name":"完成","value":"2"}]
         * p_arr : {"data1":"50%","data2":"71.428571428571%","data3":"80%","data4":"50%","data5":"100%"}
         */

        private PArrDTO p_arr;
        private List<ArrDTO> arr;

        public PArrDTO getP_arr() {
            return p_arr;
        }

        public void setP_arr(PArrDTO p_arr) {
            this.p_arr = p_arr;
        }

        public List<ArrDTO> getArr() {
            return arr;
        }

        public void setArr(List<ArrDTO> arr) {
            this.arr = arr;
        }

        public static class PArrDTO {
            /**
             * data1 : 50%
             * data2 : 71.428571428571%
             * data3 : 80%
             * data4 : 50%
             * data5 : 100%
             */

            private String data1;
            private String data2;
            private String data3;
            private String data4;
            private String data5;

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

            public String getData5() {
                return data5;
            }

            public void setData5(String data5) {
                this.data5 = data5;
            }
        }

        public static class ArrDTO {
            /**
             * name : 商机
             * value : 14
             */

            private String name;
            private String value;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
