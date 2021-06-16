package com.lingyan.banquet.ui.data.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class NetPkData {


    /**
     * code : 200
     * msg : ok
     * data : {"data1":[{"create_user_id":55,"count":2,"user_name":"小花","avatar":"","sort":"1","avatar_name":"小花"}],"data2":[{"create_user_id":55,"count":2,"user_name":"小花","avatar":"","sort":"1","avatar_name":"小花"}],"data3":[{"create_user_id":55,"count":2,"user_name":"小花","avatar":"","sort":"1","avatar_name":"小花"},{"create_user_id":1,"count":1,"user_name":"管理员","avatar":"","sort":"2","avatar_name":"理员"},{"create_user_id":54,"count":1,"user_name":"小明","avatar":"","sort":"3","avatar_name":"小明"}],"data4":[{"create_user_id":55,"count":6,"user_name":"小花","avatar":"","sort":"1","avatar_name":"小花"},{"create_user_id":54,"count":3,"user_name":"小明","avatar":"","sort":"2","avatar_name":"小明"},{"create_user_id":1,"count":1,"user_name":"管理员","avatar":"","sort":"3","avatar_name":"理员"}]}
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
        private List<DataInfoDTO> data1;
        private List<DataInfoDTO> data2;
        private List<DataInfoDTO> data3;
        private List<DataInfoDTO> data4;
        private List<DataInfoDTO> income;
        private List<DataInfoDTO> final_amount;
        private List<DataInfoDTO> avg_amount;

        public List<DataInfoDTO> getIncome() {
            return income;
        }

        public void setIncome(List<DataInfoDTO> income) {
            this.income = income;
        }

        public List<DataInfoDTO> getFinal_amount() {
            return final_amount;
        }

        public void setFinal_amount(List<DataInfoDTO> final_amount) {
            this.final_amount = final_amount;
        }

        public List<DataInfoDTO> getAvg_amount() {
            return avg_amount;
        }

        public void setAvg_amount(List<DataInfoDTO> avg_amount) {
            this.avg_amount = avg_amount;
        }

        public List<DataInfoDTO> getData1() {
            return data1;
        }

        public void setData1(List<DataInfoDTO> data1) {
            this.data1 = data1;
        }

        public List<DataInfoDTO> getData2() {
            return data2;
        }

        public void setData2(List<DataInfoDTO> data2) {
            this.data2 = data2;
        }

        public List<DataInfoDTO> getData3() {
            return data3;
        }

        public void setData3(List<DataInfoDTO> data3) {
            this.data3 = data3;
        }

        public List<DataInfoDTO> getData4() {
            return data4;
        }

        public void setData4(List<DataInfoDTO> data4) {
            this.data4 = data4;
        }

        public static class DataInfoDTO {
            /**
             * create_user_id : 55
             * count : 2
             * user_name : 小花
             * avatar :
             * sort : 1
             * avatar_name : 小花
             */

            private String create_user_id;
            private String count;
            private String user_name;
            private String avatar;
            private int sort;
            private String avatar_name;



            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public int getSort() {
                return sort;
            }

            public void setSort(int sort) {
                this.sort = sort;
            }

            public String getAvatar_name() {
                return avatar_name;
            }

            public void setAvatar_name(String avatar_name) {
                this.avatar_name = avatar_name;
            }

            public String getCreate_user_id() {
                return create_user_id;
            }

            public void setCreate_user_id(String create_user_id) {
                this.create_user_id = create_user_id;
            }

            public String getCount() {
                return count;
            }

            public void setCount(String count) {
                this.count = count;
            }
        }



    }
}
