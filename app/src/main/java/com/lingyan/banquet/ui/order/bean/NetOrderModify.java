package com.lingyan.banquet.ui.order.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyq on 2021/9/15.
 */

public class NetOrderModify {

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
        if (data == null) {
            data = new DataDTO();
        }
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {

        private String id;
        private List<BanquetNumDTO> banquetNum;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<BanquetNumDTO> getBanquetNum() {
            if (banquetNum == null) {
                banquetNum = new ArrayList<>();
            }
            return banquetNum;
        }

        public void setBanquetNum(List<BanquetNumDTO> banquetNum) {
            this.banquetNum = banquetNum;
        }

        public static class BanquetNumDTO {
            /**
             * id : 168
             * banquet_number_id : 141
             * date : 2021-01-26
             * type : 1
             * segment_type : 2
             * hall_id : ["9"]
             * meal_id : 6
             * table_number : 10
             * banquet_status : 4
             * meal_name : 满汉全席
             * segment_name : 晚餐
             * hall_info : [{"id":"9","name":"光明顶"}]
             * detaile_pic : []
             * domain : http://api.forvery.top
             */

            private String id = "0";
            private String banquet_number_id = "0";
            private String date;
            private String type;
            private String segment_type;
            private String meal_id;
            private String table_number;
            private String prepare_number;
            private String banquet_status;
            private String meal_name;
            private String segment_name;
            private List<String> hall_id;
            private List<HallInfoDTO> hall_info;


            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getBanquet_number_id() {
                return banquet_number_id;
            }

            public void setBanquet_number_id(String banquet_number_id) {
                this.banquet_number_id = banquet_number_id;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSegment_type() {
                return segment_type;
            }

            public void setSegment_type(String segment_type) {
                this.segment_type = segment_type;
            }

            public String getMeal_id() {
                return meal_id;
            }

            public void setMeal_id(String meal_id) {
                this.meal_id = meal_id;
            }

            public String getTable_number() {
                return table_number;
            }

            public void setTable_number(String table_number) {
                this.table_number = table_number;
            }

            public String getPrepare_number() {
                return prepare_number;
            }

            public void setPrepare_number(String prepare_number) {
                this.prepare_number = prepare_number;
            }

            public String getBanquet_status() {
                return banquet_status;
            }

            public void setBanquet_status(String banquet_status) {
                this.banquet_status = banquet_status;
            }

            public String getMeal_name() {
                return meal_name;
            }

            public void setMeal_name(String meal_name) {
                this.meal_name = meal_name;
            }

            public String getSegment_name() {
                return segment_name;
            }

            public void setSegment_name(String segment_name) {
                this.segment_name = segment_name;
            }


            public List<String> getHall_id() {
                if (hall_id == null) {
                    hall_id = new ArrayList<>();
                }
                return hall_id;
            }

            public void setHall_id(List<String> hall_id) {
                this.hall_id = hall_id;
            }

            public List<HallInfoDTO> getHall_info() {
                if (hall_info == null) {
                    hall_info = new ArrayList<>();
                }
                return hall_info;
            }

            public void setHall_info(List<HallInfoDTO> hall_info) {
                this.hall_info = hall_info;
            }


            public static class HallInfoDTO {
                /**
                 * id : 9
                 * name : 光明顶
                 */

                private String id;
                private String name;

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
            }
        }
    }
}
