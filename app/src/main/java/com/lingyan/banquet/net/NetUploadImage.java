package com.lingyan.banquet.net;

/**
 * Created by _hxb on 2021/2/8.
 */

public class NetUploadImage {

    /**
     * code : 200
     * msg : 图片上传成功!
     * data : {"name":"4e8326f29756413e87a29d267fbdf396.jpeg","dir":"/uploads/banquet/16/20210208/4e8326f29756413e87a29d267fbdf396.jpeg","url":"http://api.forvery.top/uploads/banquet/16/20210208/4e8326f29756413e87a29d267fbdf396.jpeg"}
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
         * name : 4e8326f29756413e87a29d267fbdf396.jpeg
         * dir : /uploads/banquet/16/20210208/4e8326f29756413e87a29d267fbdf396.jpeg
         * url : http://api.forvery.top/uploads/banquet/16/20210208/4e8326f29756413e87a29d267fbdf396.jpeg
         */

        private String name;
        private String dir;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
