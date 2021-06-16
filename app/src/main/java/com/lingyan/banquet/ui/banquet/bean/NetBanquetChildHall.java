package com.lingyan.banquet.ui.banquet.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by _hxb on 2021/2/6.
 */

public class NetBanquetChildHall implements Serializable {


    /**
     * code : 200
     * msg : ok
     * data : [{"id":"1","name":"宴会厅","children":[{"id":"9","name":"光明顶","type":"1","pic":"/uploads/banquet/16/20210107/f8d5ebd57f1338d9e8e628596acefddc.jpg","min_number":"10","max_number":"20","create_time":"2021-01-07 16:10:59","status":1,"full_pic":"http://api.forvery.top/uploads/banquet/16/20210107/f8d5ebd57f1338d9e8e628596acefddc.jpg"},{"id":"6","name":"22","type":"1","pic":"","min_number":"1","max_number":"3","create_time":"2021-01-06 13:09:21","status":1,"full_pic":"http://api.forvery.top"}]},{"id":"2","name":"包间","children":[{"id":"8","name":"33","type":"2","pic":"","min_number":"10","max_number":"30","create_time":"2021-01-06 13:33:20","status":1,"full_pic":"http://api.forvery.top"},{"id":"4","name":"5","type":"2","pic":"sdfsd","min_number":"1","max_number":"2","create_time":"2020-12-29 19:16:49","status":1,"full_pic":"http://api.forvery.topsdfsd"},{"id":"3","name":"33333","type":"2","pic":"sdfsd","min_number":"1","max_number":"2","create_time":"2020-11-28 19:25:27","status":1,"full_pic":"http://api.forvery.topsdfsd"}]}]
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
         * id : 1
         * name : 宴会厅
         * children : [{"id":"9","name":"光明顶","type":"1","pic":"/uploads/banquet/16/20210107/f8d5ebd57f1338d9e8e628596acefddc.jpg","min_number":"10","max_number":"20","create_time":"2021-01-07 16:10:59","status":1,"full_pic":"http://api.forvery.top/uploads/banquet/16/20210107/f8d5ebd57f1338d9e8e628596acefddc.jpg"},{"id":"6","name":"22","type":"1","pic":"","min_number":"1","max_number":"3","create_time":"2021-01-06 13:09:21","status":1,"full_pic":"http://api.forvery.top"}]
         */

        private String id;
        private String name;
        private List<ChildrenDTO> children;

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

        public List<ChildrenDTO> getChildren() {
            return children;
        }

        public void setChildren(List<ChildrenDTO> children) {
            this.children = children;
        }

        public static class ChildrenDTO implements Serializable {
            /**
             * id : 9
             * name : 光明顶
             * type : 1
             * pic : /uploads/banquet/16/20210107/f8d5ebd57f1338d9e8e628596acefddc.jpg
             * min_number : 10
             * max_number : 20
             * create_time : 2021-01-07 16:10:59
             * status : 1
             * full_pic : http://api.forvery.top/uploads/banquet/16/20210107/f8d5ebd57f1338d9e8e628596acefddc.jpg
             */

            private String id;
            private String name;
            private String type;
            private String pic;
            private String min_number;
            private String max_number;
            private String create_time;
            private int status;
            private String full_pic;

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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getMin_number() {
                return min_number;
            }

            public void setMin_number(String min_number) {
                this.min_number = min_number;
            }

            public String getMax_number() {
                return max_number;
            }

            public void setMax_number(String max_number) {
                this.max_number = max_number;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getFull_pic() {
                return full_pic;
            }

            public void setFull_pic(String full_pic) {
                this.full_pic = full_pic;
            }
        }
    }
}
