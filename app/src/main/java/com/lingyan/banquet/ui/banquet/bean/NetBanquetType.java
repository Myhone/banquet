package com.lingyan.banquet.ui.banquet.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by _hxb on 2021/2/4.
 */

public class NetBanquetType implements Serializable {


    /**
     * code : 200
     * msg : ok
     * data : [{"id":"4","restaurant_id":16,"pid":"0","name":"婚宴","status":1,"sort":"0","create_time":"2020-12-25 12:56:23","children":[{"id":"6","restaurant_id":16,"pid":"4","name":"分类1","status":1,"sort":"0","create_time":"2021-01-07 17:05:23"},{"id":"5","restaurant_id":16,"pid":"4","name":"444","status":1,"sort":"0","create_time":"2020-12-25 12:56:32"}]}]
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

    public static class DataDTO implements Serializable{
        /**
         * id : 4
         * restaurant_id : 16
         * pid : 0
         * name : 婚宴
         * status : 1
         * sort : 0
         * create_time : 2020-12-25 12:56:23
         * children : [{"id":"6","restaurant_id":16,"pid":"4","name":"分类1","status":1,"sort":"0","create_time":"2021-01-07 17:05:23"},{"id":"5","restaurant_id":16,"pid":"4","name":"444","status":1,"sort":"0","create_time":"2020-12-25 12:56:32"}]
         */

        private String id;
        private String restaurant_id;
        private String pid;
        private String name;
        private String status;
        private String sort;
        private String create_time;
        private List<ChildrenDTO> children;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }



        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRestaurant_id() {
            return restaurant_id;
        }

        public void setRestaurant_id(String restaurant_id) {
            this.restaurant_id = restaurant_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public List<ChildrenDTO> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenDTO> children) {
            this.children = children;
        }

        public static class ChildrenDTO  implements Serializable{
            /**
             * id : 6
             * restaurant_id : 16
             * pid : 4
             * name : 分类1
             * status : 1
             * sort : 0
             * create_time : 2021-01-07 17:05:23
             */

            private String id;
            private String restaurant_id;
            private String pid;
            private String name;
            private String status;
            private String sort;
            private String create_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }



            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getRestaurant_id() {
                return restaurant_id;
            }

            public void setRestaurant_id(String restaurant_id) {
                this.restaurant_id = restaurant_id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getSort() {
                return sort;
            }

            public void setSort(String sort) {
                this.sort = sort;
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
