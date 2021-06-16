package com.lingyan.banquet.ui.report.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by _hxb on 2021/2/19.
 */

public class NetReportDetail implements Serializable {

    /**
     * code : 200
     * msg : ok
     * data : {"id":"10","img_url":["http://api.forvery.top/uploads/banquet/28/20210219/ddf17206901f5a5a63ffa6997e13daad.jpg","http://api.forvery.top/uploads/banquet/28/20210219/49d6b25e93cfb97cf2701e2e06803e0d.jpg"],"content":"（ '▿ ' ）","reply":"","reply_time":"1970-01-01 08:00:00","create_time":"2021-02-19 17:22:23","site_url":"http://api.forvery.top"}
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

    public static class DataDTO  implements Serializable {
        /**
         * id : 10
         * img_url : ["http://api.forvery.top/uploads/banquet/28/20210219/ddf17206901f5a5a63ffa6997e13daad.jpg","http://api.forvery.top/uploads/banquet/28/20210219/49d6b25e93cfb97cf2701e2e06803e0d.jpg"]
         * content : （ '▿ ' ）
         * reply :
         * reply_time : 1970-01-01 08:00:00
         * create_time : 2021-02-19 17:22:23
         * site_url : http://api.forvery.top
         */

        private String id;
        private String content;
        private String reply;
        private String reply_time;
        private String create_time;
        private String site_url;
        private List<String> img_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getReply() {
            return reply;
        }

        public void setReply(String reply) {
            this.reply = reply;
        }

        public String getReply_time() {
            return reply_time;
        }

        public void setReply_time(String reply_time) {
            this.reply_time = reply_time;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getSite_url() {
            return site_url;
        }

        public void setSite_url(String site_url) {
            this.site_url = site_url;
        }

        public List<String> getImg_url() {
            return img_url;
        }

        public void setImg_url(List<String> img_url) {
            this.img_url = img_url;
        }
    }
}
