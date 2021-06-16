package com.lingyan.banquet.ui.report.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/19.
 */

public class NetReportList {

    /**
     * code : 200
     * msg : ok
     * data : [{"id":"10","content":"（ '▿ ' ）","reply":"","create_time":"2021-02-19 17:22:23"},{"id":"9","content":"，，，","reply":"","create_time":"2021-02-19 12:52:04"},{"id":"8","content":"明哦哦哦哦了","reply":"嘎嘎","create_time":"2021-01-18 22:00:09"}]
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
         * id : 10
         * content : （ '▿ ' ）
         * reply :
         * create_time : 2021-02-19 17:22:23
         */

        private String id;
        private String content;
        private String reply;
        private String create_time;

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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
