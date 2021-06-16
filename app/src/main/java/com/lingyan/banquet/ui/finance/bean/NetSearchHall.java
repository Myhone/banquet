package com.lingyan.banquet.ui.finance.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by _hxb on 2021/2/16.
 */

public class NetSearchHall {
    /**
     * code : 200
     * msg : ok
     * data : [{"id":"10","name":"宴会厅一"}]
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

    public static class DataDTO implements MultiItemEntity {
        /**
         * id : 10
         * name : 宴会厅一
         */

        private String id;
        private String name;

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

        @Override
        public int getItemType() {
            return 1;
        }
    }
}
