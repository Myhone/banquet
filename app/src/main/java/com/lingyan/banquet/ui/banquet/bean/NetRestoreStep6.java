package com.lingyan.banquet.ui.banquet.bean;

import com.blankj.utilcode.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by _hxb on 2021/2/8.
 */

public class NetRestoreStep6 {

    /**
     * code : 200
     * msg : ok
     * data : {"is_lost":0,"id":"46","type":"1","status":"5","step":"5","remarks_6":"","domain":"http://api.forvery.top","balance":"0.00","receive_deposit":"13332","final_amount":"0.00","finance_confirmed":"0","is_click":"1","banquetNum":[{"id":"0","banquet_number_id":160,"date":"2021-02-18","segment_type":"1","hall_id":["6"],"meal_id":"4","table_number":"99","add_dish_money":"0.00","meal_loss_money":"0.00","water_bill_pic":[],"wine":"0.00","session_amount":"0.00","meal_name":"套餐一","segment_name":"午餐","detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":162,"date":"2021-02-18","segment_type":"1","hall_id":["6"],"meal_id":"4","table_number":"99","add_dish_money":"0.00","meal_loss_money":"0.00","water_bill_pic":[],"wine":"0.00","session_amount":"0.00","meal_name":"套餐一","segment_name":"午餐","detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":161,"date":"2021-02-10","segment_type":"2","hall_id":["9","6"],"meal_id":"7","table_number":"6666","add_dish_money":"0.00","meal_loss_money":"0.00","water_bill_pic":[],"wine":"0.00","session_amount":"0.00","meal_name":"套餐001","segment_name":"晚餐","detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":163,"date":"2021-02-10","segment_type":"2","hall_id":["9","6"],"meal_id":"4","table_number":"6666","add_dish_money":"0.00","meal_loss_money":"0.00","water_bill_pic":[],"wine":"0.00","session_amount":"0.00","meal_name":"套餐一","segment_name":"晚餐","detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"}],"is_status":"0"}
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
         * id : 46
         * type : 1
         * status : 5
         * step : 5
         * remarks_6 :
         * domain : http://api.forvery.top
         * balance : 0.00
         * receive_deposit : 13332
         * final_amount : 0.00
         * finance_confirmed : 0
         * is_click : 1
         * banquetNum : [{"id":"0","banquet_number_id":160,"date":"2021-02-18","segment_type":"1","hall_id":["6"],"meal_id":"4","table_number":"99","add_dish_money":"0.00","meal_loss_money":"0.00","water_bill_pic":[],"wine":"0.00","session_amount":"0.00","meal_name":"套餐一","segment_name":"午餐","detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":162,"date":"2021-02-18","segment_type":"1","hall_id":["6"],"meal_id":"4","table_number":"99","add_dish_money":"0.00","meal_loss_money":"0.00","water_bill_pic":[],"wine":"0.00","session_amount":"0.00","meal_name":"套餐一","segment_name":"午餐","detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":161,"date":"2021-02-10","segment_type":"2","hall_id":["9","6"],"meal_id":"7","table_number":"6666","add_dish_money":"0.00","meal_loss_money":"0.00","water_bill_pic":[],"wine":"0.00","session_amount":"0.00","meal_name":"套餐001","segment_name":"晚餐","detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"},{"id":"0","banquet_number_id":163,"date":"2021-02-10","segment_type":"2","hall_id":["9","6"],"meal_id":"4","table_number":"6666","add_dish_money":"0.00","meal_loss_money":"0.00","water_bill_pic":[],"wine":"0.00","session_amount":"0.00","meal_name":"套餐一","segment_name":"晚餐","detaile_pic":[],"detaile_change_pic":[],"domain":"http://api.forvery.top"}]
         * is_status : 0
         */

        private String is_lost;
        private String id;
        private String type;
        private String status;
        private String step;
        private String remarks_6;
        private String domain;
        private String balance;
        private String receive_deposit;
        private String final_amount;
        private String finance_confirmed;
        private String finance_confirmed3;
        private String is_click;
        private String is_status;
        private String pay_type;
        private String pay_name;
        private String pay_time;
        private String code;
        private String pay_user;
        private String real_name;
        private List<BanquetNumDTO> banquetNum;

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getFinance_confirmed3() {
            return finance_confirmed3;
        }

        public void setFinance_confirmed3(String finance_confirmed3) {
            this.finance_confirmed3 = finance_confirmed3;
        }

        public String getPay_user() {
            return pay_user;
        }

        public void setPay_user(String pay_user) {
            this.pay_user = pay_user;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
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

        public String getRemarks_6() {
            return remarks_6;
        }

        public void setRemarks_6(String remarks_6) {
            this.remarks_6 = remarks_6;
        }

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getReceive_deposit() {
            return receive_deposit;
        }

        public void setReceive_deposit(String receive_deposit) {
            this.receive_deposit = receive_deposit;
        }

        public String getFinal_amount() {
            return final_amount;
        }

        public void setFinal_amount(String final_amount) {
            this.final_amount = final_amount;
        }

        public String getFinance_confirmed() {
            if (StringUtils.isEmpty(finance_confirmed)) {
                finance_confirmed = "0";
            }
            return finance_confirmed;
        }

        public void setFinance_confirmed(String finance_confirmed) {
            this.finance_confirmed = finance_confirmed;
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
             * id : 0
             * banquet_number_id : 160
             * date : 2021-02-18
             * segment_type : 1
             * hall_id : ["6"]
             * meal_id : 4
             * table_number : 99
             * add_dish_money : 0.00
             * meal_loss_money : 0.00
             * water_bill_pic : []
             * wine : 0.00
             * session_amount : 0.00
             * meal_name : 套餐一
             * segment_name : 午餐
             * detaile_pic : []
             * detaile_change_pic : []
             * domain : http://api.forvery.top
             */

            private String id;
            private int banquet_number_id;
            private String date;
            private String segment_type;
            private String meal_id;
            private String table_number;
            private String add_dish_money;
            private String meal_loss_money;
            private String wine;
            private String session_amount;
            private String meal_name;
            private String price;
            private String segment_name;
            private String domain;
            private List<String> hall_id;
            private List<String> water_bill_pic;
            private List<String> detaile_pic;
            private List<String> detaile_change_pic;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getBanquet_number_id() {
                return banquet_number_id;
            }

            public void setBanquet_number_id(int banquet_number_id) {
                this.banquet_number_id = banquet_number_id;
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

            public String getAdd_dish_money() {
                return add_dish_money;
            }

            public void setAdd_dish_money(String add_dish_money) {
                this.add_dish_money = add_dish_money;
            }

            public String getMeal_loss_money() {
                return meal_loss_money;
            }

            public void setMeal_loss_money(String meal_loss_money) {
                this.meal_loss_money = meal_loss_money;
            }

            public String getWine() {
                return wine;
            }

            public void setWine(String wine) {
                this.wine = wine;
            }

            public String getSession_amount() {
                return session_amount;
            }

            public void setSession_amount(String session_amount) {
                this.session_amount = session_amount;
            }

            public String getMeal_name() {
                return meal_name;
            }

            public void setMeal_name(String meal_name) {
                this.meal_name = meal_name;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
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

            public List<String> getWater_bill_pic() {
                if (water_bill_pic == null) {
                    water_bill_pic = new ArrayList<>();
                }
                return water_bill_pic;
            }

            public void setWater_bill_pic(List<String> water_bill_pic) {
                this.water_bill_pic = water_bill_pic;
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
