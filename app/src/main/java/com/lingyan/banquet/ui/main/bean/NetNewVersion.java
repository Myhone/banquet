package com.lingyan.banquet.ui.main.bean;

/**
 * Created by _hxb on 2018/10/9 0009.
 */
public class NetNewVersion {


    /**
     * status : 40001
     * data : {"version":"1.0.0.1","description":"1.涉及到佛山京东方\n2.涉及到佛时间段","url":"http://wwew.cn/com"}
     * msg : 查询成功
     */

    private String status;
    private DataBean data;
    private String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {

        private String android_vname;
        private int android_vcode;
        private String description;
        private String android_url;
        private String force;

        public String getAndroid_vname() {
            return android_vname;
        }

        public void setAndroid_vname(String android_vname) {
            this.android_vname = android_vname;
        }

        public int getAndroid_vcode() {
            return android_vcode;
        }

        public void setAndroid_vcode(int android_vcode) {
            this.android_vcode = android_vcode;
        }

        public String getAndroid_url() {
            return android_url;
        }

        public void setAndroid_url(String android_url) {
            this.android_url = android_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getForce() {
            return force;
        }

        public void setForce(String force) {
            this.force = force;
        }
    }
}
