package com.lingyan.banquet.ui.data.july;

import java.util.List;

/**
 * Created by wyq on 2021/6/17.
 */
public class PkDataBean {

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * gs : {"income":[],"data1":[],"continuation":[],"data3":[],"data4":[]}
         * qg : {"income":[],"data1":[],"continuation":[],"data3":[],"data4":[]}
         */

        private DataChildBean gs;
        private DataChildBean qg;

        public DataChildBean getGs() {
            return gs;
        }

        public void setGs(DataChildBean gs) {
            this.gs = gs;
        }

        public DataChildBean getQg() {
            return qg;
        }

        public void setQg(DataChildBean qg) {
            this.qg = qg;
        }

        public static class DataChildBean {
            private List<PersonBean> income;
            private List<PersonBean> data1;
            private List<PersonBean> data2;
            private List<PersonBean> continuation;
            private List<PersonBean> data3;
            private List<PersonBean> data4;

            public List<PersonBean> getIncome() {
                return income;
            }

            public void setIncome(List<PersonBean> income) {
                this.income = income;
            }

            public List<PersonBean> getData1() {
                return data1;
            }

            public void setData1(List<PersonBean> data1) {
                this.data1 = data1;
            }

            public List<PersonBean> getData2() {
                return data2;
            }

            public void setData2(List<PersonBean> data2) {
                this.data2 = data2;
            }

            public List<PersonBean> getContinuation() {
                return continuation;
            }

            public void setContinuation(List<PersonBean> continuation) {
                this.continuation = continuation;
            }

            public List<PersonBean> getData3() {
                return data3;
            }

            public void setData3(List<PersonBean> data3) {
                this.data3 = data3;
            }

            public List<PersonBean> getData4() {
                return data4;
            }

            public void setData4(List<PersonBean> data4) {
                this.data4 = data4;
            }

            public static class PersonBean {

                /**
                 * avatar :
                 * avatar_name : 21
                 * count : 0
                 * intent_man_id : 109
                 * intent_man_name : 121
                 * long_count : 0
                 * sort : 41
                 * user_name : 121
                 */

                private String avatar;
                private String avatar_name;
                private String count;
                private String intent_man_id;
                private String intent_man_name;
                private String long_count;
                private int sort;
                private String user_name;

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

                public String getCount() {
                    return count;
                }

                public void setCount(String count) {
                    this.count = count;
                }

                public String getIntent_man_id() {
                    return intent_man_id;
                }

                public void setIntent_man_id(String intent_man_id) {
                    this.intent_man_id = intent_man_id;
                }

                public String getIntent_man_name() {
                    return intent_man_name;
                }

                public void setIntent_man_name(String intent_man_name) {
                    this.intent_man_name = intent_man_name;
                }

                public String getLong_count() {
                    return long_count;
                }

                public void setLong_count(String long_count) {
                    this.long_count = long_count;
                }

                public int getSort() {
                    return sort;
                }

                public void setSort(int sort) {
                    this.sort = sort;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }
            }
        }
    }
}
