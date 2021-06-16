package com.lingyan.banquet.ui.main.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by _hxb on 2021/2/16.
 */

public class NetHomeBottomHallData implements Serializable {


    /**
     * code : 200
     * msg : ok
     * data : [{"name":"大江大河宴会厅","info":{"商机":"0","意向":"1","锁台":"1","签定":"0","执行":"0","完成":"0"}},{"name":"宴会厅二","info":{"商机":"0","意向":"1","锁台":"1","签定":"1","执行":"0","完成":"0"}},{"name":"宴会厅一","info":{"商机":"0","意向":"0","锁台":"2","签定":"1","执行":"0","完成":"0"}}]
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

    public static class DataDTO implements Serializable {
        /**
         * name : 大江大河宴会厅
         * info : {"商机":"0","意向":"1","锁台":"1","签定":"0","执行":"0","完成":"0"}
         */

        private String name;
        private String id;
        private InfoDTO info;

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

        public InfoDTO getInfo() {
            return info;
        }

        public void setInfo(InfoDTO info) {
            this.info = info;
        }

        public static class InfoDTO  implements Serializable{
            /**
             * 商机 : 0
             * 意向 : 1
             * 锁台 : 1
             * 签定 : 0
             * 执行 : 0
             * 完成 : 0
             */

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
    }
}
