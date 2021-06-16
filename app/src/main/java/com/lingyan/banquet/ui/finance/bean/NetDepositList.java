package com.lingyan.banquet.ui.finance.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by _hxb on 2021/2/8.
 */

public class NetDepositList {

    /**
     * code : 200
     * msg : ok
     * data : {"header_info":{"receive_money":"13804.00","retired_money":"0.00"},"count":11,"list":[{"id":"56","banquet_id":46,"banquet_type":1,"type":2,"money":"9999.00","pay_type":"2","pay_time":"2021-02-07 20:39:03","code":"QD20210207203903134725","pay_user":"的哇哈哈","payee":1,"payee_time":"2021-02-07 21:00:20","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":55,"is_display":0,"b_type":1,"b_date":"2021-02-25 00:00:00","final_amount":"0.00","is_lost":0,"status":5,"step":6,"real_name":"张三","mobile":"112113","payee_name":"管理员","refund_realname":"","title":"宴会签定定金","pay_type_name":"微信支付","status_name":"完成","date_list":"","hall_list":""},{"id":"51","banquet_id":46,"banquet_type":1,"type":1,"money":"3333.00","pay_type":"1","pay_time":"2021-02-07 14:08:47","code":"ST20210207140847118815","pay_user":"哈哈哈哈哈哈","payee":1,"payee_time":"2021-02-07 15:27:44","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":55,"is_display":0,"b_type":1,"b_date":"2021-02-25 00:00:00","final_amount":"0.00","is_lost":0,"status":5,"step":6,"real_name":"张三","mobile":"112113","payee_name":"管理员","refund_realname":"","title":"宴会锁台定金","pay_type_name":"支付宝","status_name":"完成","date_list":"","hall_list":""},{"id":"49","banquet_id":37,"banquet_type":1,"type":1,"money":"120.00","pay_type":"1","pay_time":"2021-02-03 21:47:07","code":"ST20210203214707154043","pay_user":"付款人1","payee":1,"payee_time":"2021-02-03 21:48:06","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":44,"is_display":0,"b_type":1,"b_date":"2021-02-26 00:00:00","final_amount":"0.00","is_lost":0,"status":3,"step":3,"real_name":"张数据","mobile":"13423432121","payee_name":"管理员","refund_realname":"","title":"宴会锁台定金","pay_type_name":"支付宝","status_name":"锁台","date_list":"","hall_list":""},{"id":"48","banquet_id":34,"banquet_type":1,"type":1,"money":"12.00","pay_type":"1","pay_time":"2021-01-30 14:29:52","code":"ST20210130142952113058","pay_user":"1","payee":1,"payee_time":"2021-01-30 14:36:08","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":null,"is_display":0,"b_type":1,"b_date":"2021-01-31 00:00:00","final_amount":"0.00","is_lost":0,"status":3,"step":3,"real_name":"老李","mobile":"13500000000","payee_name":"管理员","refund_realname":"","title":"宴会锁台定金","pay_type_name":"支付宝","status_name":"锁台","date_list":"","hall_list":""},{"id":"45","banquet_id":30,"banquet_type":2,"type":2,"money":"10.00","pay_type":"1","pay_time":"2021-01-29 17:42:17","code":"QD20210129174217174284","pay_user":"1010","payee":1,"payee_time":"2021-01-29 18:06:23","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":1,"is_display":0,"b_type":2,"b_date":"2021-01-30 00:00:00","final_amount":"0.00","is_lost":0,"status":5,"step":6,"real_name":"小郑","mobile":"13000000000","payee_name":"管理员","refund_realname":"","title":"庆典签定定金","pay_type_name":"支付宝","status_name":"完成","date_list":"2021-02-20 午餐","hall_list":"光明顶/33/22/33333"},{"id":"43","banquet_id":30,"banquet_type":2,"type":1,"money":"100.00","pay_type":"1","pay_time":"2021-01-29 15:49:55","code":"ST20210129154955157660","pay_user":"2","payee":1,"payee_time":"2021-01-29 16:11:18","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":null,"is_display":0,"b_type":2,"b_date":"2021-01-30 00:00:00","final_amount":"0.00","is_lost":0,"status":5,"step":6,"real_name":"小郑","mobile":"13000000000","payee_name":"管理员","refund_realname":"","title":"庆典锁台定金","pay_type_name":"支付宝","status_name":"完成","date_list":"","hall_list":""},{"id":"39","banquet_id":27,"banquet_type":1,"type":1,"money":"100.00","pay_type":"1","pay_time":"2021-01-28 14:44:49","code":"ST20210128144449158369","pay_user":"100","payee":1,"payee_time":"2021-01-28 14:45:12","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":null,"is_display":0,"b_type":1,"b_date":"2021-01-29 00:00:00","final_amount":"0.00","is_lost":0,"status":3,"step":3,"real_name":"小郑","mobile":"13000000000","payee_name":"管理员","refund_realname":"","title":"宴会锁台定金","pay_type_name":"支付宝","status_name":"锁台","date_list":"","hall_list":""},{"id":"34","banquet_id":23,"banquet_type":1,"type":2,"money":"10.00","pay_type":"1","pay_time":"2021-01-25 13:32:16","code":"QD20210125133216132441","pay_user":"小明","payee":1,"payee_time":"2021-01-25 13:33:03","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":1,"is_display":0,"b_type":1,"b_date":"2021-01-29 00:00:00","final_amount":"0.00","is_lost":0,"status":6,"step":6,"real_name":"小明","mobile":"13000000012","payee_name":"管理员","refund_realname":"","title":"宴会签定定金","pay_type_name":"支付宝","status_name":"完成","date_list":"2021-01-31 午餐","hall_list":"22"},{"id":"33","banquet_id":23,"banquet_type":1,"type":1,"money":"10.00","pay_type":"1","pay_time":"2021-01-25 13:18:46","code":"ST20210125131846151136","pay_user":"10","payee":1,"payee_time":"2021-01-25 13:27:36","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":1,"is_display":0,"b_type":1,"b_date":"2021-01-29 00:00:00","final_amount":"0.00","is_lost":0,"status":6,"step":6,"real_name":"小明","mobile":"13000000012","payee_name":"管理员","refund_realname":"","title":"宴会锁台定金","pay_type_name":"支付宝","status_name":"完成","date_list":"","hall_list":""},{"id":"29","banquet_id":21,"banquet_type":1,"type":2,"money":"100.00","pay_type":"1","pay_time":"2021-01-25 11:58:51","code":"QD20210125115851129523","pay_user":"111","payee":1,"payee_time":"2021-01-25 15:36:51","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":1,"is_display":0,"b_type":1,"b_date":"2021-01-28 00:00:00","final_amount":"1000.00","is_lost":0,"status":6,"step":6,"real_name":"小郑","mobile":"13000000000","payee_name":"管理员","refund_realname":"","title":"宴会签定定金","pay_type_name":"支付宝","status_name":"完成","date_list":"","hall_list":""}]}
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
         * header_info : {"receive_money":"13804.00","retired_money":"0.00"}
         * count : 11
         * list : [{"id":"56","banquet_id":46,"banquet_type":1,"type":2,"money":"9999.00","pay_type":"2","pay_time":"2021-02-07 20:39:03","code":"QD20210207203903134725","pay_user":"的哇哈哈","payee":1,"payee_time":"2021-02-07 21:00:20","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":55,"is_display":0,"b_type":1,"b_date":"2021-02-25 00:00:00","final_amount":"0.00","is_lost":0,"status":5,"step":6,"real_name":"张三","mobile":"112113","payee_name":"管理员","refund_realname":"","title":"宴会签定定金","pay_type_name":"微信支付","status_name":"完成","date_list":"","hall_list":""},{"id":"51","banquet_id":46,"banquet_type":1,"type":1,"money":"3333.00","pay_type":"1","pay_time":"2021-02-07 14:08:47","code":"ST20210207140847118815","pay_user":"哈哈哈哈哈哈","payee":1,"payee_time":"2021-02-07 15:27:44","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":55,"is_display":0,"b_type":1,"b_date":"2021-02-25 00:00:00","final_amount":"0.00","is_lost":0,"status":5,"step":6,"real_name":"张三","mobile":"112113","payee_name":"管理员","refund_realname":"","title":"宴会锁台定金","pay_type_name":"支付宝","status_name":"完成","date_list":"","hall_list":""},{"id":"49","banquet_id":37,"banquet_type":1,"type":1,"money":"120.00","pay_type":"1","pay_time":"2021-02-03 21:47:07","code":"ST20210203214707154043","pay_user":"付款人1","payee":1,"payee_time":"2021-02-03 21:48:06","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":44,"is_display":0,"b_type":1,"b_date":"2021-02-26 00:00:00","final_amount":"0.00","is_lost":0,"status":3,"step":3,"real_name":"张数据","mobile":"13423432121","payee_name":"管理员","refund_realname":"","title":"宴会锁台定金","pay_type_name":"支付宝","status_name":"锁台","date_list":"","hall_list":""},{"id":"48","banquet_id":34,"banquet_type":1,"type":1,"money":"12.00","pay_type":"1","pay_time":"2021-01-30 14:29:52","code":"ST20210130142952113058","pay_user":"1","payee":1,"payee_time":"2021-01-30 14:36:08","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":null,"is_display":0,"b_type":1,"b_date":"2021-01-31 00:00:00","final_amount":"0.00","is_lost":0,"status":3,"step":3,"real_name":"老李","mobile":"13500000000","payee_name":"管理员","refund_realname":"","title":"宴会锁台定金","pay_type_name":"支付宝","status_name":"锁台","date_list":"","hall_list":""},{"id":"45","banquet_id":30,"banquet_type":2,"type":2,"money":"10.00","pay_type":"1","pay_time":"2021-01-29 17:42:17","code":"QD20210129174217174284","pay_user":"1010","payee":1,"payee_time":"2021-01-29 18:06:23","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":1,"is_display":0,"b_type":2,"b_date":"2021-01-30 00:00:00","final_amount":"0.00","is_lost":0,"status":5,"step":6,"real_name":"小郑","mobile":"13000000000","payee_name":"管理员","refund_realname":"","title":"庆典签定定金","pay_type_name":"支付宝","status_name":"完成","date_list":"2021-02-20 午餐","hall_list":"光明顶/33/22/33333"},{"id":"43","banquet_id":30,"banquet_type":2,"type":1,"money":"100.00","pay_type":"1","pay_time":"2021-01-29 15:49:55","code":"ST20210129154955157660","pay_user":"2","payee":1,"payee_time":"2021-01-29 16:11:18","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":null,"is_display":0,"b_type":2,"b_date":"2021-01-30 00:00:00","final_amount":"0.00","is_lost":0,"status":5,"step":6,"real_name":"小郑","mobile":"13000000000","payee_name":"管理员","refund_realname":"","title":"庆典锁台定金","pay_type_name":"支付宝","status_name":"完成","date_list":"","hall_list":""},{"id":"39","banquet_id":27,"banquet_type":1,"type":1,"money":"100.00","pay_type":"1","pay_time":"2021-01-28 14:44:49","code":"ST20210128144449158369","pay_user":"100","payee":1,"payee_time":"2021-01-28 14:45:12","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":null,"is_display":0,"b_type":1,"b_date":"2021-01-29 00:00:00","final_amount":"0.00","is_lost":0,"status":3,"step":3,"real_name":"小郑","mobile":"13000000000","payee_name":"管理员","refund_realname":"","title":"宴会锁台定金","pay_type_name":"支付宝","status_name":"锁台","date_list":"","hall_list":""},{"id":"34","banquet_id":23,"banquet_type":1,"type":2,"money":"10.00","pay_type":"1","pay_time":"2021-01-25 13:32:16","code":"QD20210125133216132441","pay_user":"小明","payee":1,"payee_time":"2021-01-25 13:33:03","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":1,"is_display":0,"b_type":1,"b_date":"2021-01-29 00:00:00","final_amount":"0.00","is_lost":0,"status":6,"step":6,"real_name":"小明","mobile":"13000000012","payee_name":"管理员","refund_realname":"","title":"宴会签定定金","pay_type_name":"支付宝","status_name":"完成","date_list":"2021-01-31 午餐","hall_list":"22"},{"id":"33","banquet_id":23,"banquet_type":1,"type":1,"money":"10.00","pay_type":"1","pay_time":"2021-01-25 13:18:46","code":"ST20210125131846151136","pay_user":"10","payee":1,"payee_time":"2021-01-25 13:27:36","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":1,"is_display":0,"b_type":1,"b_date":"2021-01-29 00:00:00","final_amount":"0.00","is_lost":0,"status":6,"step":6,"real_name":"小明","mobile":"13000000012","payee_name":"管理员","refund_realname":"","title":"宴会锁台定金","pay_type_name":"支付宝","status_name":"完成","date_list":"","hall_list":""},{"id":"29","banquet_id":21,"banquet_type":1,"type":2,"money":"100.00","pay_type":"1","pay_time":"2021-01-25 11:58:51","code":"QD20210125115851129523","pay_user":"111","payee":1,"payee_time":"2021-01-25 15:36:51","finance_confirmed":"2","refund_time":0,"refund_user":0,"intent_man_id":1,"is_display":0,"b_type":1,"b_date":"2021-01-28 00:00:00","final_amount":"1000.00","is_lost":0,"status":6,"step":6,"real_name":"小郑","mobile":"13000000000","payee_name":"管理员","refund_realname":"","title":"宴会签定定金","pay_type_name":"支付宝","status_name":"完成","date_list":"","hall_list":""}]
         */

        private HeaderInfoDTO header_info;
        private int count;
        private List<ListDTO> list;

        public HeaderInfoDTO getHeader_info() {
            return header_info;
        }

        public void setHeader_info(HeaderInfoDTO header_info) {
            this.header_info = header_info;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public static class HeaderInfoDTO {
            /**
             * receive_money : 13804.00
             * retired_money : 0.00
             */

            private String receive_money;
            private String retired_money;

            private String toal_money;
            private String balance_money;
            private String not_receive;

            public String getToal_money() {
                return toal_money;
            }

            public void setToal_money(String toal_money) {
                this.toal_money = toal_money;
            }

            public String getBalance_money() {
                return balance_money;
            }

            public void setBalance_money(String balance_money) {
                this.balance_money = balance_money;
            }

            public String getNot_receive() {
                return not_receive;
            }

            public void setNot_receive(String not_receive) {
                this.not_receive = not_receive;
            }

            public String getReceive_money() {
                return receive_money;
            }

            public void setReceive_money(String receive_money) {
                this.receive_money = receive_money;
            }

            public String getRetired_money() {
                return retired_money;
            }

            public void setRetired_money(String retired_money) {
                this.retired_money = retired_money;
            }
        }

        public static class ListDTO implements MultiItemEntity {
            /**
             * id : 56
             * banquet_id : 46
             * banquet_type : 1
             * type : 2
             * money : 9999.00
             * pay_type : 2
             * pay_time : 2021-02-07 20:39:03
             * code : QD20210207203903134725
             * pay_user : 的哇哈哈
             * payee : 1
             * payee_time : 2021-02-07 21:00:20
             * finance_confirmed : 2
             * refund_time : 0
             * refund_user : 0
             * intent_man_id : 55
             * is_display : 0
             * b_type : 1
             * b_date : 2021-02-25 00:00:00
             * final_amount : 0.00
             * is_lost : 0
             * status : 5
             * step : 6
             * real_name : 张三
             * mobile : 112113
             * payee_name : 管理员
             * refund_realname :
             * title : 宴会签定定金
             * pay_type_name : 微信支付
             * status_name : 完成
             * date_list :
             * hall_list :
             */

            private String id;
            private String banquet_id;
            private String banquet_type;
            private String type;
            private String money;
            private String pay_type;
            private String pay_time;
            private String code;
            private String pay_user;
            private String payee;
            private String payee_time;
            private String finance_confirmed;
            private String refund_time;
            private String refund_user;
            private String intent_man_id;
            private String is_display;
            private String b_type;
            private String b_date;
            private String final_amount;
            private String is_lost;
            private String status;
            private String step;
            private String real_name;
            private String mobile;
            private String payee_name;
            private String refund_realname;
            private String title;
            private String pay_type_name;
            private String status_name;
            private String date_list;
            private String hall_list;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }


            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getPay_type() {
                return pay_type;
            }

            public void setPay_type(String pay_type) {
                this.pay_type = pay_type;
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

            public String getPay_user() {
                return pay_user;
            }

            public void setPay_user(String pay_user) {
                this.pay_user = pay_user;
            }

            public void setPayee_time(String payee_time) {
                this.payee_time = payee_time;
            }

            public String getFinance_confirmed() {
                return finance_confirmed;
            }

            public void setFinance_confirmed(String finance_confirmed) {
                this.finance_confirmed = finance_confirmed;
            }




            public String getB_date() {
                return b_date;
            }

            public void setB_date(String b_date) {
                this.b_date = b_date;
            }

            public String getFinal_amount() {
                return final_amount;
            }

            public void setFinal_amount(String final_amount) {
                this.final_amount = final_amount;
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

            public String getPayee_name() {
                return payee_name;
            }

            public void setPayee_name(String payee_name) {
                this.payee_name = payee_name;
            }

            public String getRefund_realname() {
                return refund_realname;
            }

            public void setRefund_realname(String refund_realname) {
                this.refund_realname = refund_realname;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPay_type_name() {
                return pay_type_name;
            }

            public void setPay_type_name(String pay_type_name) {
                this.pay_type_name = pay_type_name;
            }

            public String getStatus_name() {
                return status_name;
            }

            public void setStatus_name(String status_name) {
                this.status_name = status_name;
            }

            public String getDate_list() {
                return date_list;
            }

            public void setDate_list(String date_list) {
                this.date_list = date_list;
            }

            public String getHall_list() {
                return hall_list;
            }

            public void setHall_list(String hall_list) {
                this.hall_list = hall_list;
            }

            public String getBanquet_id() {
                return banquet_id;
            }

            public void setBanquet_id(String banquet_id) {
                this.banquet_id = banquet_id;
            }

            public String getBanquet_type() {
                return banquet_type;
            }

            public void setBanquet_type(String banquet_type) {
                this.banquet_type = banquet_type;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPayee() {
                return payee;
            }

            public void setPayee(String payee) {
                this.payee = payee;
            }

            public String getPayee_time() {
                return payee_time;
            }

            public String getRefund_time() {
                return refund_time;
            }

            public void setRefund_time(String refund_time) {
                this.refund_time = refund_time;
            }

            public String getRefund_user() {
                return refund_user;
            }

            public void setRefund_user(String refund_user) {
                this.refund_user = refund_user;
            }

            public String getIntent_man_id() {
                return intent_man_id;
            }

            public void setIntent_man_id(String intent_man_id) {
                this.intent_man_id = intent_man_id;
            }

            public String getIs_display() {
                return is_display;
            }

            public void setIs_display(String is_display) {
                this.is_display = is_display;
            }

            public String getB_type() {
                return b_type;
            }

            public void setB_type(String b_type) {
                this.b_type = b_type;
            }

            public String getIs_lost() {
                return is_lost;
            }

            public void setIs_lost(String is_lost) {
                this.is_lost = is_lost;
            }

            public String getStatus() {
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

            @Override
            public int getItemType() {
                return 2;
            }
        }
    }
}
