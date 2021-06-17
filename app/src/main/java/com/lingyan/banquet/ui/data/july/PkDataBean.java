package com.lingyan.banquet.ui.data.july;

import android.os.Parcel;
import android.os.Parcelable;

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

        public static class DataChildBean implements Parcelable {
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

            public static class PersonBean implements Parcelable {

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

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(this.avatar);
                    dest.writeString(this.avatar_name);
                    dest.writeString(this.count);
                    dest.writeString(this.intent_man_id);
                    dest.writeString(this.intent_man_name);
                    dest.writeString(this.long_count);
                    dest.writeInt(this.sort);
                    dest.writeString(this.user_name);
                }

                public void readFromParcel(Parcel source) {
                    this.avatar = source.readString();
                    this.avatar_name = source.readString();
                    this.count = source.readString();
                    this.intent_man_id = source.readString();
                    this.intent_man_name = source.readString();
                    this.long_count = source.readString();
                    this.sort = source.readInt();
                    this.user_name = source.readString();
                }

                public PersonBean() {
                }

                protected PersonBean(Parcel in) {
                    this.avatar = in.readString();
                    this.avatar_name = in.readString();
                    this.count = in.readString();
                    this.intent_man_id = in.readString();
                    this.intent_man_name = in.readString();
                    this.long_count = in.readString();
                    this.sort = in.readInt();
                    this.user_name = in.readString();
                }

                public static final Creator<PersonBean> CREATOR = new Creator<PersonBean>() {
                    @Override
                    public PersonBean createFromParcel(Parcel source) {
                        return new PersonBean(source);
                    }

                    @Override
                    public PersonBean[] newArray(int size) {
                        return new PersonBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeTypedList(this.income);
                dest.writeTypedList(this.data1);
                dest.writeTypedList(this.data2);
                dest.writeTypedList(this.continuation);
                dest.writeTypedList(this.data3);
                dest.writeTypedList(this.data4);
            }

            public void readFromParcel(Parcel source) {
                this.income = source.createTypedArrayList(PersonBean.CREATOR);
                this.data1 = source.createTypedArrayList(PersonBean.CREATOR);
                this.data2 = source.createTypedArrayList(PersonBean.CREATOR);
                this.continuation = source.createTypedArrayList(PersonBean.CREATOR);
                this.data3 = source.createTypedArrayList(PersonBean.CREATOR);
                this.data4 = source.createTypedArrayList(PersonBean.CREATOR);
            }

            public DataChildBean() {
            }

            protected DataChildBean(Parcel in) {
                this.income = in.createTypedArrayList(PersonBean.CREATOR);
                this.data1 = in.createTypedArrayList(PersonBean.CREATOR);
                this.data2 = in.createTypedArrayList(PersonBean.CREATOR);
                this.continuation = in.createTypedArrayList(PersonBean.CREATOR);
                this.data3 = in.createTypedArrayList(PersonBean.CREATOR);
                this.data4 = in.createTypedArrayList(PersonBean.CREATOR);
            }

            public static final Creator<DataChildBean> CREATOR = new Creator<DataChildBean>() {
                @Override
                public DataChildBean createFromParcel(Parcel source) {
                    return new DataChildBean(source);
                }

                @Override
                public DataChildBean[] newArray(int size) {
                    return new DataChildBean[size];
                }
            };
        }
    }
}
