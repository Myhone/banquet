package com.lingyan.banquet.ui.banquet.bean;

import com.blankj.utilcode.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by _hxb on 2021/2/6.
 */

public class NetRestoreStep2 {


    /**
     * code : 200
     * msg : ok
     * data : {"id":"42","type":"1","status":"2","Intentionality":"3","step":"2","remarks_2":"","is_click":"1","linkmen":{"province_id":"140000","city_id":"140100","county_id":"140105","address":"山西省,太原市,小店区","address_detail":"你猜你猜"},"banquetNum":[{"id":"214","banquet_number_id":"157","date":"2021-02-10","type":"1","segment_type":"2","hall_id":["11"],"meal_id":"5","table_number":"20","banquet_status":"2","meal_name":"套餐一111","segment_name":"晚餐","hall_info":[{"id":"11","name":"宴会厅二"}]},{"id":"215","banquet_number_id":"158","date":"2021-02-10","type":"1","segment_type":"1","hall_id":["10"],"meal_id":"6","table_number":"10","banquet_status":"2","meal_name":"满汉全席","segment_name":"午餐","hall_info":[{"id":"10","name":"宴会厅一"}]}]}
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
         * id : 42
         * type : 1
         * status : 2
         * Intentionality : 3
         * step : 2
         * remarks_2 :
         * is_click : 1
         * linkmen : {"province_id":"140000","city_id":"140100","county_id":"140105","address":"山西省,太原市,小店区","address_detail":"你猜你猜"}
         * banquetNum : [{"id":"214","banquet_number_id":"157","date":"2021-02-10","type":"1","segment_type":"2","hall_id":["11"],"meal_id":"5","table_number":"20","banquet_status":"2","meal_name":"套餐一111","segment_name":"晚餐","hall_info":[{"id":"11","name":"宴会厅二"}]},{"id":"215","banquet_number_id":"158","date":"2021-02-10","type":"1","segment_type":"1","hall_id":["10"],"meal_id":"6","table_number":"10","banquet_status":"2","meal_name":"满汉全席","segment_name":"午餐","hall_info":[{"id":"10","name":"宴会厅一"}]}]
         */

        private String id;
        private String type;
        private String Intentionality;
        private String step;
        private String remarks_2;
        private String is_click;
        private String status;
        private String is_lost;
        private String finance_confirmed;
        private String is_status;
        private LinkmenDTO linkmen;
        private List<BanquetNumDTO> banquetNum;

        public String getIs_lost() {
            if(StringUtils.isEmpty(is_lost)){
                is_lost="0";
            }
            return is_lost;
        }

        public void setIs_lost(String is_lost) {
            this.is_lost = is_lost;
        }

        public String getFinance_confirmed() {
            if(StringUtils.isEmpty(finance_confirmed)){
                finance_confirmed="0";
            }
            return finance_confirmed;
        }

        public void setFinance_confirmed(String finance_confirmed) {
            this.finance_confirmed = finance_confirmed;
        }

        public String getIs_status() {
            if(StringUtils.isEmpty(is_status)){
                is_status="0";
            }
            return is_status;
        }

        public void setIs_status(String is_status) {
            this.is_status = is_status;
        }

        @Override
        public String toString() {
            return "DataDTO{" +
                    "id='" + id + '\'' +
                    ", type='" + type + '\'' +
                    ", status='" + status + '\'' +
                    ", Intentionality='" + Intentionality + '\'' +
                    ", step='" + step + '\'' +
                    ", remarks_2='" + remarks_2 + '\'' +
                    ", is_click='" + is_click + '\'' +
                    ", linkmen=" + linkmen +
                    ", banquetNum=" + banquetNum +
                    '}';
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            if(StringUtils.isEmpty(status)){
                status="0";
            }
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIntentionality() {
            return Intentionality;
        }

        public void setIntentionality(String Intentionality) {
            this.Intentionality = Intentionality;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getRemarks_2() {
            return remarks_2;
        }

        public void setRemarks_2(String remarks_2) {
            this.remarks_2 = remarks_2;
        }

        public String getIs_click() {
            return is_click;
        }

        public void setIs_click(String is_click) {
            this.is_click = is_click;
        }

        public LinkmenDTO getLinkmen() {
            if (linkmen == null) {
                linkmen = new LinkmenDTO();
            }
            return linkmen;
        }

        public void setLinkmen(LinkmenDTO linkmen) {
            this.linkmen = linkmen;
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

        public static class LinkmenDTO {
            /**
             * province_id : 140000
             * city_id : 140100
             * county_id : 140105
             * address : 山西省,太原市,小店区
             * address_detail : 你猜你猜
             */

            private String province_id;
            private String city_id;
            private String county_id;
            private String address;
            private String address_detail;

            @Override
            public String toString() {
                return "LinkmenDTO{" +
                        "province_id='" + province_id + '\'' +
                        ", city_id='" + city_id + '\'' +
                        ", county_id='" + county_id + '\'' +
                        ", address='" + address + '\'' +
                        ", address_detail='" + address_detail + '\'' +
                        '}';
            }

            public String getProvince_id() {
                return province_id;
            }

            public void setProvince_id(String province_id) {
                this.province_id = province_id;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getCounty_id() {
                return county_id;
            }

            public void setCounty_id(String county_id) {
                this.county_id = county_id;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getAddress_detail() {
                return address_detail;
            }

            public void setAddress_detail(String address_detail) {
                this.address_detail = address_detail;
            }
        }

        public static class BanquetNumDTO {
            /**
             * id : 214
             * banquet_number_id : 157
             * date : 2021-02-10
             * type : 1
             * segment_type : 2
             * hall_id : ["11"]
             * meal_id : 5
             * table_number : 20
             * banquet_status : 2
             * meal_name : 套餐一111
             * segment_name : 晚餐
             * hall_info : [{"id":"11","name":"宴会厅二"}]
             */

            private String id= "0";
            private String banquet_number_id ="0";
            private String date;
            private String type;
            private String segment_type;
            private String meal_id;
            private String table_number;
            private String banquet_status;
            private String meal_name;
            private String segment_name;
            private List<String> hall_id;
            private List<HallInfoDTO> hall_info;

            @Override
            public String toString() {
                return "BanquetNumDTO{" +
                        "id='" + id + '\'' +
                        ", banquet_number_id='" + banquet_number_id + '\'' +
                        ", date='" + date + '\'' +
                        ", type='" + type + '\'' +
                        ", segment_type='" + segment_type + '\'' +
                        ", meal_id='" + meal_id + '\'' +
                        ", table_number='" + table_number + '\'' +
                        ", banquet_status='" + banquet_status + '\'' +
                        ", meal_name='" + meal_name + '\'' +
                        ", segment_name='" + segment_name + '\'' +
                        ", hall_id=" + hall_id +
                        ", hall_info=" + hall_info +
                        '}';
            }

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
                 * id : 11
                 * name : 宴会厅二
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

                @Override
                public boolean equals(Object o) {
                    if (this == o) return true;
                    if (!(o instanceof HallInfoDTO)) return false;
                    HallInfoDTO dto = (HallInfoDTO) o;
                    return Objects.equals(getId(), dto.getId()) &&
                            Objects.equals(getName(), dto.getName());
                }

                @Override
                public int hashCode() {
                    return Objects.hash(getId(), getName());
                }

                @Override
                public String toString() {
                    return "HallInfoDTO{" +
                            "id='" + id + '\'' +
                            ", name='" + name + '\'' +
                            '}';
                }
            }
        }
    }
}
