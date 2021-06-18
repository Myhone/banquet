package com.lingyan.banquet.ui.data.july;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wyq on 2021/6/17.
 */
public class PkItemBean implements Parcelable {

    /**
     * code : 200
     * msg : ok
     * data : [{"title":"回款王","key":"income"},{"title":"签单王","key":"data1"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable{
        /**
         * title : 回款王
         * key : income
         */

        private String title;
        private String key;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.key);
        }

        public void readFromParcel(Parcel source) {
            this.title = source.readString();
            this.key = source.readString();
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.title = in.readString();
            this.key = in.readString();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel source) {
                return new DataBean(source);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.msg);
        dest.writeList(this.data);
    }

    public void readFromParcel(Parcel source) {
        this.code = source.readInt();
        this.msg = source.readString();
        this.data = new ArrayList<DataBean>();
        source.readList(this.data, DataBean.class.getClassLoader());
    }

    public PkItemBean() {
    }

    protected PkItemBean(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
        this.data = new ArrayList<DataBean>();
        in.readList(this.data, DataBean.class.getClassLoader());
    }

    public static final Creator<PkItemBean> CREATOR = new Creator<PkItemBean>() {
        @Override
        public PkItemBean createFromParcel(Parcel source) {
            return new PkItemBean(source);
        }

        @Override
        public PkItemBean[] newArray(int size) {
            return new PkItemBean[size];
        }
    };
}
