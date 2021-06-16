package com.lingyan.banquet.ui.follow.bean;

import java.util.List;

/**
 * Created by wyq on 2021/6/9.
 */
public class FollowList {

    /**
     * code : 200
     * msg : ok
     * data : [{"id":1,"type_name":"当面跟进","content":"xxxx","img_url":["upload/banquet/xxx.jpge"],"create_user_name":"独秀","create_time":"2021-03-31 16:11:06","banquet_status_name":"商机","is_notice":0,"notice_time":"","notice_content":""},{"id":1,"type_name":"当面跟进","content":"xxxx","img_url":["upload/banquet/xxx.jpge"],"create_user_name":"独秀","create_time":"2021-03-31 16:11:06","order_status_name":"商机","is_notice":1,"notice_time":"2021-04-31 16:11:06","notice_content":"提醒提醒提提醒"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * type_name : 当面跟进
         * content : xxxx
         * img_url : ["upload/banquet/xxx.jpge"]
         * create_user_name : 独秀
         * create_time : 2021-03-31 16:11:06
         * banquet_status_name : 商机
         * is_notice : 0
         * notice_time :
         * notice_content :
         * order_status_name : 商机
         */

        private int id;
        private String type_name;
        private String content;
        private List<String> img_url;
        private String create_user_name;
        private String create_time;
        private String banquet_status_name;
        private int is_notice;
        private String notice_time;
        private String notice_content;
        private String order_status_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<String> getImg_url() {
            return img_url;
        }

        public void setImg_url(List<String> img_url) {
            this.img_url = img_url;
        }

        public String getCreate_user_name() {
            return create_user_name;
        }

        public void setCreate_user_name(String create_user_name) {
            this.create_user_name = create_user_name;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getBanquet_status_name() {
            return banquet_status_name;
        }

        public void setBanquet_status_name(String banquet_status_name) {
            this.banquet_status_name = banquet_status_name;
        }

        public int getIs_notice() {
            return is_notice;
        }

        public void setIs_notice(int is_notice) {
            this.is_notice = is_notice;
        }

        public String getNotice_time() {
            return notice_time;
        }

        public void setNotice_time(String notice_time) {
            this.notice_time = notice_time;
        }

        public String getNotice_content() {
            return notice_content;
        }

        public void setNotice_content(String notice_content) {
            this.notice_content = notice_content;
        }

        public String getOrder_status_name() {
            return order_status_name;
        }

        public void setOrder_status_name(String order_status_name) {
            this.order_status_name = order_status_name;
        }
    }
}
