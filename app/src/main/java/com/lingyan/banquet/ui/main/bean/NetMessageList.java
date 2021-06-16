package com.lingyan.banquet.ui.main.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/19.
 */

public class NetMessageList {


    /**
     * code : 200
     * msg : ok
     * data : [{"id":"1","title":"宴会锁台定金需要财务确认收款","content":"宴会订单编号:61,宴会厅：大江大河宴会厅/宴会厅二/宴会厅二/宴会厅一,宴会时间：2021-03-01 晚餐 / 2021-02-27 午餐,需要财务确认收款","banquet_id":"61","banquet_type":"1","create_time":"2021-02-18 10:17:18","is_read":"0","time":"1天前"},{"id":"5","title":"宴会锁台定金需要财务确认收款","content":"宴会订单编号:61,宴会厅：大江大河宴会厅/宴会厅二/宴会厅二/宴会厅一,宴会时间：2021-03-01 晚餐 / 2021-02-27 午餐,需要财务确认收款","banquet_id":"61","banquet_type":"1","create_time":"2021-02-18 11:15:09","is_read":"1","time":"1天前"},{"id":"3","title":"宴会锁台定金需要财务确认收款","content":"宴会订单编号:61,宴会厅：大江大河宴会厅/宴会厅二/宴会厅二/宴会厅一,宴会时间：2021-03-01 晚餐 / 2021-02-27 午餐,需要财务确认收款","banquet_id":"61","banquet_type":"1","create_time":"2021-02-18 10:42:26","is_read":"1","time":"1天前"}]
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
         * id : 1
         * title : 宴会锁台定金需要财务确认收款
         * content : 宴会订单编号:61,宴会厅：大江大河宴会厅/宴会厅二/宴会厅二/宴会厅一,宴会时间：2021-03-01 晚餐 / 2021-02-27 午餐,需要财务确认收款
         * banquet_id : 61
         * banquet_type : 1
         * create_time : 2021-02-18 10:17:18
         * is_read : 0
         * time : 1天前
         */

        private String id;
        private String title;
        private String content;
        private String banquet_id;
        private String banquet_type;
        private String create_time;
        private String is_read;
        private String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getIs_read() {
            return is_read;
        }

        public void setIs_read(String is_read) {
            this.is_read = is_read;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
