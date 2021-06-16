package com.lingyan.banquet.ui.main.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by _hxb on 2021/2/4.
 */

public class NetMonthData implements Serializable {


    /**
     * code : 200
     * msg : ok
     * data : {"banquetlist":[{"status":"1","date":"2021-02-05","count":1,"date_time":1612454400,"segment_type":"1"},{"status":"1","date":"2021-02-10","count":6,"date_time":1612886400,"segment_type":"1"},{"status":"1","date":"2021-02-11","count":2,"date_time":1612972800,"segment_type":"1"},{"status":"1","date":"2021-02-13","count":1,"date_time":1613145600,"segment_type":"1"},{"status":"1","date":"2021-02-26","count":3,"date_time":1614268800,"segment_type":"1"}],"gooddayList":[{"id":"89","goodday_time":1613664000,"type":1,"type_name":null,"color":"","date":"2021-02-19","date_time":1613664000},{"id":"90","goodday_time":1614182400,"type":7,"type_name":null,"color":"","date":"2021-02-25","date_time":1614182400}],"status_arr":{"商机":"5","意向":"0","锁台":"0","签定":"0","执行":"0","完成":"0"}}
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

    public static class DataDTO implements Serializable {
        /**
         * banquetlist : [{"status":"1","date":"2021-02-05","count":1,"date_time":1612454400,"segment_type":"1"},{"status":"1","date":"2021-02-10","count":6,"date_time":1612886400,"segment_type":"1"},{"status":"1","date":"2021-02-11","count":2,"date_time":1612972800,"segment_type":"1"},{"status":"1","date":"2021-02-13","count":1,"date_time":1613145600,"segment_type":"1"},{"status":"1","date":"2021-02-26","count":3,"date_time":1614268800,"segment_type":"1"}]
         * gooddayList : [{"id":"89","goodday_time":1613664000,"type":1,"type_name":null,"color":"","date":"2021-02-19","date_time":1613664000},{"id":"90","goodday_time":1614182400,"type":7,"type_name":null,"color":"","date":"2021-02-25","date_time":1614182400}]
         * status_arr : {"商机":"5","意向":"0","锁台":"0","签定":"0","执行":"0","完成":"0"}
         */

        private StatusArrDTO status_arr;
        private List<BanquetlistDTO> banquetlist;
        private List<GooddayListDTO> gooddayList;

        public StatusArrDTO getStatus_arr() {
            return status_arr;
        }

        public void setStatus_arr(StatusArrDTO status_arr) {
            this.status_arr = status_arr;
        }

        public List<BanquetlistDTO> getBanquetlist() {
            return banquetlist;
        }

        public void setBanquetlist(List<BanquetlistDTO> banquetlist) {
            this.banquetlist = banquetlist;
        }

        public List<GooddayListDTO> getGooddayList() {
            return gooddayList;
        }

        public void setGooddayList(List<GooddayListDTO> gooddayList) {
            this.gooddayList = gooddayList;
        }

        public static class StatusArrDTO implements Serializable {
            /**
             * 商机 : 5
             * 意向 : 0
             * 锁台 : 0
             * 签定 : 0
             * 执行 : 0
             * 完成 : 0
             */
            @SerializedName("商机")
            private String chance;
            @SerializedName("意向")
            private String intent;
            @SerializedName("锁台")
            private String lock;
            @SerializedName("签定")
            private String sign;
            @SerializedName("执行")
            private String exec;
            @SerializedName("完成")
            private String complete;

            public String getChance() {
                return chance;
            }

            public void setChance(String chance) {
                this.chance = chance;
            }

            public String getIntent() {
                return intent;
            }

            public void setIntent(String intent) {
                this.intent = intent;
            }

            public String getLock() {
                return lock;
            }

            public void setLock(String lock) {
                this.lock = lock;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getExec() {
                return exec;
            }

            public void setExec(String exec) {
                this.exec = exec;
            }

            public String getComplete() {
                return complete;
            }

            public void setComplete(String complete) {
                this.complete = complete;
            }
        }

        public static class BanquetlistDTO implements Serializable {
            /**
             * status : 1
             * date : 2021-02-05
             * count : 1
             * date_time : 1612454400
             * segment_type : 1
             */

            private String status;
            private String date;
            private String count;
            private String date_time;
            private String segment_type;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }


            public String getDate_time() {
                return date_time;
            }

            public void setDate_time(String date_time) {
                this.date_time = date_time;
            }

            public String getSegment_type() {
                return segment_type;
            }

            public void setSegment_type(String segment_type) {
                this.segment_type = segment_type;
            }
        }

        public static class GooddayListDTO implements Serializable {
            /**
             * id : 89
             * goodday_time : 1613664000
             * type : 1
             * type_name : null
             * color :
             * date : 2021-02-19
             * date_time : 1613664000
             */

            private String id;
            private String goodday_time;
            private String type;
            private String type_name;
            private String color;
            private String date;
            private String date_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }


            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getGoodday_time() {
                return goodday_time;
            }

            public void setGoodday_time(String goodday_time) {
                this.goodday_time = goodday_time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getType_name() {
                return type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }

            public String getDate_time() {
                return date_time;
            }

            public void setDate_time(String date_time) {
                this.date_time = date_time;
            }
        }
    }
}
