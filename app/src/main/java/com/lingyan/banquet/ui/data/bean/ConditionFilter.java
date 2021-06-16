package com.lingyan.banquet.ui.data.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by _hxb on 2021/2/20.
 */

public class ConditionFilter {
    public int banquet_type;
    public List<String> dept_id;
    public List<String> user_id;
    public String time_type;
    public String start_time;
    public String end_time;
    public List<String> title_list;
    public String hall_id;
    public String hall_id_name;

    public int getBanquet_type() {
        return banquet_type;
    }

    public void setBanquet_type(int banquet_type) {
        this.banquet_type = banquet_type;
    }

    public List<String> getDept_id() {
        return dept_id;
    }

    public void setDept_id(List<String> dept_id) {
        this.dept_id = dept_id;
    }

    public List<String> getUser_id() {
        return user_id;
    }

    public void setUser_id(List<String> user_id) {
        this.user_id = user_id;
    }

    public String getTime_type() {
        return time_type;
    }

    public void setTime_type(String time_type) {
        this.time_type = time_type;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getHall_id() {
        return hall_id;
    }

    public void setHall_id(String hall_id) {
        this.hall_id = hall_id;
    }

    public String getHall_id_name() {
        return hall_id_name;
    }

    public void setHall_id_name(String hall_id_name) {
        this.hall_id_name = hall_id_name;
    }

    public List<String> getTitle_list() {
        return title_list;
    }

    public void setTitle_list(List<String> title_list) {
        this.title_list = title_list;
    }
}
