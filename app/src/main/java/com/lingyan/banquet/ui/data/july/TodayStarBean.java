package com.lingyan.banquet.ui.data.july;

/**
 * Created by wyq on 2021/6/19.
 */
public class TodayStarBean {

    /**
     * code : 200
     * data : {"title":"陈独秀(美的宴会厅)","content":"回款王","avatar":"http://www.baidu.com/img/user.jpg","avatar_name":"陈独秀"}
     * msg : ok
     */

    private int code;
    private DataBean data;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
        /**
         * title : 陈独秀(美的宴会厅)
         * content : 回款王
         * avatar : http://www.baidu.com/img/user.jpg
         * avatar_name : 陈独秀
         */

        private String title;
        private String content;
        private String avatar;
        private String avatar_name;

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

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getAvatar_name() {
            return avatar_name;
        }

        public void setAvatar_name(String avatar_name) {
            this.avatar_name = avatar_name;
        }
    }
}
