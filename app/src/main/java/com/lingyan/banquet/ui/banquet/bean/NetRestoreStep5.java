package com.lingyan.banquet.ui.banquet.bean;

import com.blankj.utilcode.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/8.
 */

public class NetRestoreStep5 {

    /**
     * code : 200
     * msg : ok
     * data : {"is_lost":0,"id":"46","type":"1","status":"4","step":"4","remarks_5":"","domain":"http://api.forvery.top","is_click":"1","banquetNum":[{"id":"0","banquet_number_id":160,"type":1,"date":"2021-02-18","segment_type":"1","hall_id":["6"],"meal_id":"4","table_number":"99","confirm_wine":"","platform_type":0,"confirm_platrorm":"","paltform_pic":[],"meal_name":"套餐一","segment_name":"午餐","confirm_menu":[],"detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":162,"type":1,"date":"2021-02-18","segment_type":"1","hall_id":["6"],"meal_id":"4","table_number":"99","confirm_wine":"","platform_type":0,"confirm_platrorm":"","paltform_pic":[],"meal_name":"套餐一","segment_name":"午餐","confirm_menu":[],"detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":161,"type":1,"date":"2021-02-10","segment_type":"2","hall_id":["9","6"],"meal_id":"7","table_number":"6666","confirm_wine":"","platform_type":0,"confirm_platrorm":"","paltform_pic":[],"meal_name":"套餐001","segment_name":"晚餐","confirm_menu":[],"detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":163,"type":1,"date":"2021-02-10","segment_type":"2","hall_id":["9","6"],"meal_id":"7","table_number":"6666","confirm_wine":"","platform_type":0,"confirm_platrorm":"","paltform_pic":[],"meal_name":"套餐001","segment_name":"晚餐","confirm_menu":[],"detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"}],"is_status":"0"}
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
            return new DataDTO();
        }
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public static class DataDTO {
        /**
         * is_lost : 0
         * id : 46
         * type : 1
         * status : 4
         * step : 4
         * remarks_5 :
         * domain : http://api.forvery.top
         * is_click : 1
         * banquetNum : [{"id":"0","banquet_number_id":160,"type":1,"date":"2021-02-18","segment_type":"1","hall_id":["6"],"meal_id":"4","table_number":"99","confirm_wine":"","platform_type":0,"confirm_platrorm":"","paltform_pic":[],"meal_name":"套餐一","segment_name":"午餐","confirm_menu":[],"detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":162,"type":1,"date":"2021-02-18","segment_type":"1","hall_id":["6"],"meal_id":"4","table_number":"99","confirm_wine":"","platform_type":0,"confirm_platrorm":"","paltform_pic":[],"meal_name":"套餐一","segment_name":"午餐","confirm_menu":[],"detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":161,"type":1,"date":"2021-02-10","segment_type":"2","hall_id":["9","6"],"meal_id":"7","table_number":"6666","confirm_wine":"","platform_type":0,"confirm_platrorm":"","paltform_pic":[],"meal_name":"套餐001","segment_name":"晚餐","confirm_menu":[],"detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":163,"type":1,"date":"2021-02-10","segment_type":"2","hall_id":["9","6"],"meal_id":"7","table_number":"6666","confirm_wine":"","platform_type":0,"confirm_platrorm":"","paltform_pic":[],"meal_name":"套餐001","segment_name":"晚餐","confirm_menu":[],"detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"}]
         * is_status : 0
         */

        private String is_lost;
        private String id;
        private String type;
        private String status;
        private String step;
        private String remarks_5;
        private String domain;
        private String is_click;
        private String is_status;
        private String finance_confirmed;

        private List<BanquetNumDTO> banquetNum;

        public String getFinance_confirmed() {
            if (StringUtils.isEmpty(finance_confirmed)) {
                finance_confirmed = "0";
            }
            return finance_confirmed;
        }

        public void setFinance_confirmed(String finance_confirmed) {
            this.finance_confirmed = finance_confirmed;
        }

        public String getIs_lost() {
            if (StringUtils.isEmpty(is_lost)) {
                is_lost = "0";
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
            if (StringUtils.isEmpty(status)) {
                status = "0";
            }
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStep() {
            return step;
        }

        public void setStep(String step) {
            this.step = step;
        }

        public String getRemarks_5() {
            return remarks_5;
        }

        public void setRemarks_5(String remarks_5) {
            this.remarks_5 = remarks_5;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getIs_click() {
            return is_click;
        }

        public void setIs_click(String is_click) {
            this.is_click = is_click;
        }

        public String getIs_status() {
            if (StringUtils.isEmpty(is_status)) {
                is_status = "0";
            }
            return is_status;
        }

        public void setIs_status(String is_status) {
            this.is_status = is_status;
        }

        public List<BanquetNumDTO> getBanquetNum() {
            return banquetNum;
        }

        public void setBanquetNum(List<BanquetNumDTO> banquetNum) {
            this.banquetNum = banquetNum;
        }

        public static class BanquetNumDTO {
            /**
             * id : 0
             * banquet_number_id : 160
             * type : 1
             * date : 2021-02-18
             * segment_type : 1
             * hall_id : ["6"]
             * meal_id : 4
             * table_number : 99
             * confirm_wine :
             * platform_type : 0
             * confirm_platrorm :
             * paltform_pic : []
             * meal_name : 套餐一
             * segment_name : 午餐
             * confirm_menu : []
             * detaile_pic : []
             * detaile_change_pic : []
             * domain : http://api.forvery.top
             */

            private String id = "0";
            private String banquet_number_id = "0";
            private String type;
            private String date;
            private String segment_type;
            private String meal_id;
            private String table_number;
            private String prepare_number ;
            private String confirm_wine;
            private String platform_type;
            private String confirm_platrorm;
            private String meal_name;
            private String segment_name;
            private String domain;
            private List<String> hall_id;
            private List<String> paltform_pic;
            private List<String> confirm_menu;
            private List<String> detaile_pic;
            private List<String> detaile_change_pic;

            public String getPrepare_number() {
                return prepare_number;
            }

            public void setPrepare_number(String prepare_number) {
                this.prepare_number = prepare_number;
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

            public String getConfirm_wine() {
                return confirm_wine;
            }

            public void setConfirm_wine(String confirm_wine) {
                this.confirm_wine = confirm_wine;
            }

            public String getPlatform_type() {
                return platform_type;
            }

            public void setPlatform_type(String platform_type) {
                this.platform_type = platform_type;
            }

            public String getConfirm_platrorm() {
                return confirm_platrorm;
            }

            public void setConfirm_platrorm(String confirm_platrorm) {
                this.confirm_platrorm = confirm_platrorm;
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

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
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

            public List<String> getPaltform_pic() {
                return paltform_pic;
            }

            public void setPaltform_pic(List<String> paltform_pic) {
                this.paltform_pic = paltform_pic;
            }

            public List<String> getConfirm_menu() {
                if (confirm_menu == null) {
                    confirm_menu = new ArrayList<>();
                }
                return confirm_menu;
            }

            public void setConfirm_menu(List<String> confirm_menu) {
                this.confirm_menu = confirm_menu;
            }

            public List<String> getDetaile_pic() {
                if (detaile_pic == null) {
                    detaile_pic = new ArrayList<>();
                }
                return detaile_pic;
            }

            public void setDetaile_pic(List<String> detaile_pic) {
                this.detaile_pic = detaile_pic;
            }

            public List<String> getDetaile_change_pic() {
                if (detaile_change_pic == null) {
                    detaile_change_pic = new ArrayList<>();
                }
                return detaile_change_pic;
            }

            public void setDetaile_change_pic(List<String> detaile_change_pic) {
                this.detaile_change_pic = detaile_change_pic;
            }
        }
    }
}
