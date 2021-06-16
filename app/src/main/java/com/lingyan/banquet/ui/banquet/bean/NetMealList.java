package com.lingyan.banquet.ui.banquet.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by _hxb on 2021/2/6.
 */

public class NetMealList implements Serializable {


    /**
     * code : 200
     * msg : ok
     * data : [{"id":"5","name":"套餐一111","price":"1211.00","desc":"是11111","status":1,"create_time":"2020-12-26 21:18:08"}]
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

    public static class DataDTO  implements Serializable{
        /**
         * id : 5
         * name : 套餐一111
         * price : 1211.00
         * desc : 是11111
         * status : 1
         * create_time : 2020-12-26 21:18:08
         */

        private String id;
        private String name;
        private String price;
        private String desc;
        private int status;
        private String create_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
