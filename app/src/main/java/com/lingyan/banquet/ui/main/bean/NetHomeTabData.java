package com.lingyan.banquet.ui.main.bean;

/**
 * Created by _hxb on 2021/6/8.
 */
public class NetHomeTabData {

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

        private QdBean qd;
        private YhBean yh;

        public QdBean getQd() {
            return qd;
        }

        public void setQd(QdBean qd) {
            this.qd = qd;
        }

        public YhBean getYh() {
            return yh;
        }

        public void setYh(YhBean yh) {
            this.yh = yh;
        }

        public static class QdBean {

            private DataChildBean data1;
            private DataChildBean data2;
            private DataChildBean data11;
            private DataChildBean data12;
            private DataChildBean data13;

            public DataChildBean getData1() {
                return data1;
            }

            public void setData1(DataChildBean data1) {
                this.data1 = data1;
            }

            public DataChildBean getData2() {
                return data2;
            }

            public void setData2(DataChildBean data2) {
                this.data2 = data2;
            }

            public DataChildBean getData11() {
                return data11;
            }

            public void setData11(DataChildBean data11) {
                this.data11 = data11;
            }

            public DataChildBean getData12() {
                return data12;
            }

            public void setData12(DataChildBean data12) {
                this.data12 = data12;
            }

            public DataChildBean getData13() {
                return data13;
            }

            public void setData13(DataChildBean data13) {
                this.data13 = data13;
            }
        }

        public static class YhBean {

            private DataChildBean data1;
            private DataChildBean data2;
            private DataChildBean data11;
            private DataChildBean data12;
            private DataChildBean data13;

            public DataChildBean getData1() {
                return data1;
            }

            public void setData1(DataChildBean data1) {
                this.data1 = data1;
            }

            public DataChildBean getData2() {
                return data2;
            }

            public void setData2(DataChildBean data2) {
                this.data2 = data2;
            }

            public DataChildBean getData11() {
                return data11;
            }

            public void setData11(DataChildBean data11) {
                this.data11 = data11;
            }

            public DataChildBean getData12() {
                return data12;
            }

            public void setData12(DataChildBean data12) {
                this.data12 = data12;
            }

            public DataChildBean getData13() {
                return data13;
            }

            public void setData13(DataChildBean data13) {
                this.data13 = data13;
            }
        }

        public static class DataChildBean {
            /**
             * count : 50
             * intent_man_id :
             * number_date :
             * status : 1
             * sx_intent_man_id :
             */

            private String count;
            private String intent_man_id;
            private String number_date;
            private String status;
            private String sx_intent_man_id;

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

            public String getNumber_date() {
                return number_date;
            }

            public void setNumber_date(String number_date) {
                this.number_date = number_date;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getSx_intent_man_id() {
                return sx_intent_man_id;
            }

            public void setSx_intent_man_id(String sx_intent_man_id) {
                this.sx_intent_man_id = sx_intent_man_id;
            }
        }
    }
}

