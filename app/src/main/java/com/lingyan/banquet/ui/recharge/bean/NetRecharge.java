package com.lingyan.banquet.ui.recharge.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/14.
 */

public class NetRecharge {


    /**
     * code : 200
     * msg : ok
     * data : {"tel":{"xiashou":"1342211212121212","kefu":"400-400-3332"},"list":[{"money":"12.00","remarks":"","expire_time":"140天","create_time":"2021-01-29 22:02:45"},{"money":"1.00","remarks":"q","expire_time":"21天","create_time":"2021-01-29 22:01:28"},{"money":"2000.00","remarks":"","expire_time":"302天","create_time":"2021-01-29 21:57:30"},{"money":"2000.00","remarks":"","expire_time":"178天","create_time":"2021-01-17 22:07:39"},{"money":"10000.00","remarks":"","expire_time":"249天","create_time":"2021-01-17 22:01:08"},{"money":"12.00","remarks":"","expire_time":"2天","create_time":"2020-12-29 23:42:54"},{"money":"12.00","remarks":"","expire_time":"1天","create_time":"2020-12-29 23:27:20"},{"money":"12.00","remarks":"","expire_time":"1天","create_time":"2020-12-29 23:25:48"},{"money":"12.00","remarks":"2","expire_time":"2天","create_time":"2020-12-29 23:17:29"},{"money":"12.00","remarks":"","expire_time":"9天","create_time":"2020-12-22 00:27:11"},{"money":"12.00","remarks":"","expire_time":"6天","create_time":"2020-12-20 21:26:46"}]}
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
         * tel : {"xiashou":"1342211212121212","kefu":"400-400-3332"}
         * list : [{"money":"12.00","remarks":"","expire_time":"140天","create_time":"2021-01-29 22:02:45"},{"money":"1.00","remarks":"q","expire_time":"21天","create_time":"2021-01-29 22:01:28"},{"money":"2000.00","remarks":"","expire_time":"302天","create_time":"2021-01-29 21:57:30"},{"money":"2000.00","remarks":"","expire_time":"178天","create_time":"2021-01-17 22:07:39"},{"money":"10000.00","remarks":"","expire_time":"249天","create_time":"2021-01-17 22:01:08"},{"money":"12.00","remarks":"","expire_time":"2天","create_time":"2020-12-29 23:42:54"},{"money":"12.00","remarks":"","expire_time":"1天","create_time":"2020-12-29 23:27:20"},{"money":"12.00","remarks":"","expire_time":"1天","create_time":"2020-12-29 23:25:48"},{"money":"12.00","remarks":"2","expire_time":"2天","create_time":"2020-12-29 23:17:29"},{"money":"12.00","remarks":"","expire_time":"9天","create_time":"2020-12-22 00:27:11"},{"money":"12.00","remarks":"","expire_time":"6天","create_time":"2020-12-20 21:26:46"}]
         */

        private TelDTO tel;
        private List<ListDTO> list;

        public TelDTO getTel() {
            return tel;
        }

        public void setTel(TelDTO tel) {
            this.tel = tel;
        }

        public List<ListDTO> getList() {
            return list;
        }

        public void setList(List<ListDTO> list) {
            this.list = list;
        }

        public static class TelDTO {
            /**
             * xiashou : 1342211212121212
             * kefu : 400-400-3332
             */

            private String xiashou;
            private String kefu;

            public String getXiashou() {
                return xiashou;
            }

            public void setXiashou(String xiashou) {
                this.xiashou = xiashou;
            }

            public String getKefu() {
                return kefu;
            }

            public void setKefu(String kefu) {
                this.kefu = kefu;
            }
        }

        public static class ListDTO {
            /**
             * money : 12.00
             * remarks :
             * expire_time : 140天
             * create_time : 2021-01-29 22:02:45
             */

            private String money;
            private String remarks;
            private String expire_time;
            private String create_time;

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String remarks) {
                this.remarks = remarks;
            }

            public String getExpire_time() {
                return expire_time;
            }

            public void setExpire_time(String expire_time) {
                this.expire_time = expire_time;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
