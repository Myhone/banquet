package com.lingyan.banquet.ui.celebration.bean;

import com.blankj.utilcode.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/6.
 */

public class NetCelRestoreStep2 {


    /**
     * code : 200
     * msg : ok
     * data : {"is_lost":0,"id":"30","type":"2","status":"5","Intentionality":"4","step":"6","remarks_2":"","is_click":"0","linkmen":{"province_id":"110000","city_id":"110100","county_id":"110101","address":"北京市 市辖区 东城区 ","address_detail":"详细地址"},"budget":"100.00","plan_id_name":"小花","banquetNum":[{"id":"201","banquet_number_id":"149","type":"2","date":"2021-01-30","segment_type":"1","hall_id":["9","8"],"style":"","celebration_id":0,"color_system":"","celebration_name":null,"segment_name":"午餐","hall_info":[{"id":"9","name":"光明顶"},{"id":"8","name":"33"}]}],"is_status":"0"}
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
        if (data == null) {
            data = new DataDTO();
        }
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * is_lost : 0
         * id : 30
         * type : 2
         * status : 5
         * Intentionality : 4
         * step : 6
         * remarks_2 :
         * is_click : 0
         * linkmen : {"province_id":"110000","city_id":"110100","county_id":"110101","address":"北京市 市辖区 东城区 ","address_detail":"详细地址"}
         * budget : 100.00
         * plan_id_name : 小花
         * banquetNum : [{"id":"201","banquet_number_id":"149","type":"2","date":"2021-01-30","segment_type":"1","hall_id":["9","8"],"style":"","celebration_id":0,"color_system":"","celebration_name":null,"segment_name":"午餐","hall_info":[{"id":"9","name":"光明顶"},{"id":"8","name":"33"}]}]
         * is_status : 0
         */

        private String is_lost;
        private String id;
        private String type;
        private String status;
        private String Intentionality;
        private String step;
        private String remarks_2;
        private String is_click;
        private String date;
        private LinkmenDTO linkmen;
        private String budget;
        private String plan_id_name;
        private String plan_id;
        private String is_status;
        private String finance_confirmed;
        private List<BanquetNumDTO> banquetNum;

        public String getFinance_confirmed() {
            if(StringUtils.isEmpty(finance_confirmed)){
                finance_confirmed="0";
            }
            return finance_confirmed;
        }

        public void setFinance_confirmed(String finance_confirmed) {
            this.finance_confirmed = finance_confirmed;
        }

        public String getPlan_id() {
            return plan_id;
        }

        public void setPlan_id(String plan_id) {
            this.plan_id = plan_id;
        }

        public String getIs_lost() {
            if(StringUtils.isEmpty(is_lost)){
                is_lost="0";
            }
            return is_lost;
        }

        public void setIs_lost(String is_lost) {
            this.is_lost = is_lost;
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

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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

        public String getBudget() {
            return budget;
        }

        public void setBudget(String budget) {
            this.budget = budget;
        }

        public String getPlan_id_name() {
            return plan_id_name;
        }

        public void setPlan_id_name(String plan_id_name) {
            this.plan_id_name = plan_id_name;
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
             * province_id : 110000
             * city_id : 110100
             * county_id : 110101
             * address : 北京市 市辖区 东城区
             * address_detail : 详细地址
             */

            private String province_id;
            private String city_id;
            private String county_id;
            private String address;
            private String address_detail;
            private String lng;//经度
            private String lat;//纬度

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

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }
        }

        public static class BanquetNumDTO {
            /**
             * id : 201
             * banquet_number_id : 149
             * type : 2
             * date : 2021-01-30
             * segment_type : 1
             * hall_id : ["9","8"]
             * style :
             * celebration_id : 0
             * color_system :
             * celebration_name : null
             * segment_name : 午餐
             * hall_info : [{"id":"9","name":"光明顶"},{"id":"8","name":"33"}]
             */

            private String id= "0";
            private String banquet_number_id="0";
            private String type;
            private String date;
            private String segment_type;
            private String style;
            private String celebration_id;
            private String color_system;
            private String celebration_name;
            private String segment_name;

            private List<String> hall_id;
            private List<HallInfoDTO> hall_info;

            public String getCelebration_name() {
                return celebration_name;
            }

            public void setCelebration_name(String celebration_name) {
                this.celebration_name = celebration_name;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getSegment_type() {
                return segment_type;
            }

            public void setSegment_type(String segment_type) {
                this.segment_type = segment_type;
            }

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getCelebration_id() {
                return celebration_id;
            }

            public void setCelebration_id(String celebration_id) {
                this.celebration_id = celebration_id;
            }

            public String getColor_system() {
                return color_system;
            }

            public void setColor_system(String color_system) {
                this.color_system = color_system;
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
