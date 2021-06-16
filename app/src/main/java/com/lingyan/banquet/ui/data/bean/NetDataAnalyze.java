package com.lingyan.banquet.ui.data.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/21.
 */

public class NetDataAnalyze {


    /**
     * code : 200
     * msg : ok
     * data : {"data1":{"hk_arr":[{"count":1,"customer_type":"3","customer_type_name":"外来","prop":"100%"}],"ht_arr":[{"b_budget_money":"66666.00","customer_type":"3","customer_type_name":"外来","prop":"100%"}]},"data2":{"hk_arr":[{"count":1,"niche_type":"6","niche_name":"分类1","prop":"100%"}],"ht_arr":[{"b_budget_money":"66666.00","niche_type":"6","niche_name":"分类1","prop":"100%"}]}}
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

        private DataListDTO data1;
        private DataListDTO data2;
        private DataListDTO data3;

        public DataListDTO getData1() {
            return data1;
        }

        public void setData1(DataListDTO data1) {
            this.data1 = data1;
        }

        public DataListDTO getData2() {
            return data2;
        }

        public void setData2(DataListDTO data2) {
            this.data2 = data2;
        }

        public DataListDTO getData3() {
            return data3;
        }

        public void setData3(DataListDTO data3) {
            this.data3 = data3;
        }
    }

    public static class DataListDTO{
        private   List<ArrDTO>  hs_arr;
        private   List<ArrDTO>  hk_arr;
        private   List<ArrDTO>  ht_arr;

        public List<ArrDTO> getHs_arr() {
            return hs_arr;
        }

        public void setHs_arr(List<ArrDTO> hs_arr) {
            this.hs_arr = hs_arr;
        }

        public List<ArrDTO> getHk_arr() {
            return hk_arr;
        }

        public void setHk_arr(List<ArrDTO> hk_arr) {
            this.hk_arr = hk_arr;
        }

        public List<ArrDTO> getHt_arr() {
            return ht_arr;
        }

        public void setHt_arr(List<ArrDTO> ht_arr) {
            this.ht_arr = ht_arr;
        }
    }


    public static class ArrDTO {
        /**
         * count : 1
         * customer_type : 3
         * customer_type_name : 外来
         * prop : 100%
         */

        private String count;
        private String customer_type;
        private String customer_type_name;
        private String prop;
        private String hall_name;
        private String niche_name;
        private String b_budget_money;

        public String getB_budget_money() {
            return b_budget_money;
        }

        public void setB_budget_money(String b_budget_money) {
            this.b_budget_money = b_budget_money;
        }

        public String getNiche_name() {
            return niche_name;
        }

        public void setNiche_name(String niche_name) {
            this.niche_name = niche_name;
        }

        public String getHall_name() {
            return hall_name;
        }

        public void setHall_name(String hall_name) {
            this.hall_name = hall_name;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCustomer_type() {
            if (customer_type == null) {
                return "0";
            }
            return customer_type;
        }

        public void setCustomer_type(String customer_type) {
            this.customer_type = customer_type;
        }

        public String getCustomer_type_name() {
            return customer_type_name;
        }

        public void setCustomer_type_name(String customer_type_name) {
            this.customer_type_name = customer_type_name;
        }

        public String getProp() {
            return prop;
        }

        public void setProp(String prop) {
            this.prop = prop;
        }
    }


}
