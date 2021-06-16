package com.lingyan.banquet.ui.banquet.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/4.
 */

public class NetBanquetHallList {


    /**
     * code : 200
     * msg : ok
     * data : [{"id":"10","name":"宴会厅一","type":"1","pic":"","min_number":"10","max_number":"50","full_pic":"","expecteddesknumlunch":"","expectedmeallunch":"","s_count":[{"status":1,"name":"签定"}],"banquet_list":[{"id":"158","banquet_id":42,"date":"2021-02-10","segment_type":1,"table_number":10,"meal_id":6,"intent_man_id":"58","customer_id":18,"niche_type":"4","status":"4","step":"4","niche_name":"婚宴","intent_man_name":"张三","real_name":"哈哈","mobile":"18235143055","status_name":"签定"}]}]
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
         * id : 10
         * name : 宴会厅一
         * type : 1
         * pic :
         * min_number : 10
         * max_number : 50
         * full_pic :
         * expecteddesknumlunch :
         * expectedmeallunch :
         * s_count : [{"status":1,"name":"签定"}]
         * banquet_list : [{"id":"158","banquet_id":42,"date":"2021-02-10","segment_type":1,"table_number":10,"meal_id":6,"intent_man_id":"58","customer_id":18,"niche_type":"4","status":"4","step":"4","niche_name":"婚宴","intent_man_name":"张三","real_name":"哈哈","mobile":"18235143055","status_name":"签定"}]
         */

        private String id;
        private String name;
        private String type;
        private String pic;
        private String min_number;
        private String max_number;
        private String full_pic;
        private String expecteddesknumlunch;
        private String expectedmeallunch;
        private List<SCountDTO> s_count;
        private List<BanquetListDTO> banquet_list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getMin_number() {
            return min_number;
        }

        public void setMin_number(String min_number) {
            this.min_number = min_number;
        }

        public String getMax_number() {
            return max_number;
        }

        public void setMax_number(String max_number) {
            this.max_number = max_number;
        }

        public String getFull_pic() {
            return full_pic;
        }

        public void setFull_pic(String full_pic) {
            this.full_pic = full_pic;
        }

        public String getExpecteddesknumlunch() {
            return expecteddesknumlunch;
        }

        public void setExpecteddesknumlunch(String expecteddesknumlunch) {
            this.expecteddesknumlunch = expecteddesknumlunch;
        }

        public String getExpectedmeallunch() {
            return expectedmeallunch;
        }

        public void setExpectedmeallunch(String expectedmeallunch) {
            this.expectedmeallunch = expectedmeallunch;
        }

        public List<SCountDTO> getS_count() {
            return s_count;
        }

        public void setS_count(List<SCountDTO> s_count) {
            this.s_count = s_count;
        }

        public List<BanquetListDTO> getBanquet_list() {
            return banquet_list;
        }

        public void setBanquet_list(List<BanquetListDTO> banquet_list) {
            this.banquet_list = banquet_list;
        }

        public static class SCountDTO {
            /**
             * status : 1
             * name : 签定
             */

            private String status;
            private String name;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class BanquetListDTO {
            /**
             * id : 158
             * banquet_id : 42
             * date : 2021-02-10
             * segment_type : 1
             * table_number : 10
             * meal_id : 6
             * intent_man_id : 58
             * customer_id : 18
             * niche_type : 4
             * status : 4
             * step : 4
             * niche_name : 婚宴
             * intent_man_name : 张三
             * real_name : 哈哈
             * mobile : 18235143055
             * status_name : 签定
             */

            private String id;
            private String banquet_id;
            private String date;
            private String segment_type;
            private String table_number;
            private String meal_id;
            private String intent_man_id;
            private String customer_id;
            private String niche_type;
            private String status;
            private String step;
            private String niche_name;
            private String intent_man_name;
            private String real_name;
            private String mobile;
            private String status_name;
            private String is_show;

            public String getIs_show() {
                return is_show;
            }

            public void setIs_show(String is_show) {
                this.is_show = is_show;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }


            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }


            public String getIntent_man_id() {
                return intent_man_id;
            }

            public void setIntent_man_id(String intent_man_id) {
                this.intent_man_id = intent_man_id;
            }


            public String getNiche_type() {
                return niche_type;
            }

            public void setNiche_type(String niche_type) {
                this.niche_type = niche_type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStep() {
                if (step == null) {
                    step = "1";
                }
                return step;
            }

            public void setStep(String step) {
                this.step = step;
            }

            public String getNiche_name() {
                return niche_name;
            }

            public void setNiche_name(String niche_name) {
                this.niche_name = niche_name;
            }

            public String getIntent_man_name() {
                return intent_man_name;
            }

            public void setIntent_man_name(String intent_man_name) {
                this.intent_man_name = intent_man_name;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getStatus_name() {
                return status_name;
            }

            public void setStatus_name(String status_name) {
                this.status_name = status_name;
            }

            public String getBanquet_id() {
                return banquet_id;
            }

            public void setBanquet_id(String banquet_id) {
                this.banquet_id = banquet_id;
            }

            public String getSegment_type() {
                return segment_type;
            }

            public void setSegment_type(String segment_type) {
                this.segment_type = segment_type;
            }

            public String getTable_number() {
                return table_number;
            }

            public void setTable_number(String table_number) {
                this.table_number = table_number;
            }

            public String getMeal_id() {
                return meal_id;
            }

            public void setMeal_id(String meal_id) {
                this.meal_id = meal_id;
            }

            public String getCustomer_id() {
                return customer_id;
            }

            public void setCustomer_id(String customer_id) {
                this.customer_id = customer_id;
            }
        }
    }
}
