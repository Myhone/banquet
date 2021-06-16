package com.lingyan.banquet.ui.apply.bean;

import java.util.List;

/**
 * Created by _hxb on 2021/2/16.
 */

public class NetApplyDetailInfo {


    /**
     * code : 200
     * msg : ok
     * data : {"id":"10","banquet_id":"42","title":"雷提交的宴会丢单申请","code":"EX2021020717434762945","reason":"豪横","create_time":"2021-02-07 17:43:47","check_status":"3","check_status_name":"撤回","contract_no":"Ht202102081732025738128","real_name":"哈哈","intent_man_name":"张三","date_r":"2021-02-10 晚餐 / 2021-02-10 午餐","hall_list":"宴会厅一/宴会厅二","is_check":"1","is_recheck":"0","stepList":[{"real_name":"雷","avatar":"","avatar_name":"雷","title":"发起审批","time":"2021-02-07 17:43:47"},{"real_name":"雷","avatar":"","avatar_name":"雷","title":"撤销申请","time":"2021-02-08 18:12:52"}]}
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
         * id : 10
         * banquet_id : 42
         * title : 雷提交的宴会丢单申请
         * code : EX2021020717434762945
         * reason : 豪横
         * create_time : 2021-02-07 17:43:47
         * check_status : 3
         * check_status_name : 撤回
         * contract_no : Ht202102081732025738128
         * real_name : 哈哈
         * intent_man_name : 张三
         * date_r : 2021-02-10 晚餐 / 2021-02-10 午餐
         * hall_list : 宴会厅一/宴会厅二
         * is_check : 1
         * is_recheck : 0
         * stepList : [{"real_name":"雷","avatar":"","avatar_name":"雷","title":"发起审批","time":"2021-02-07 17:43:47"},{"real_name":"雷","avatar":"","avatar_name":"雷","title":"撤销申请","time":"2021-02-08 18:12:52"}]
         */

        private String id;
        private String banquet_id;
        private String title;
        private String code;
        private String reason;
        private String create_time;
        private String check_status;
        private String check_status_name;
        private String contract_no;
        private String real_name;
        private String intent_man_name;
        private String date_r;
        private String hall_list;
        private String is_check;
        private String is_recheck;
        private String type;
        private String banquet_type;
        private List<StepListDTO> stepList;

        public String getBanquet_type() {
            return banquet_type;
        }

        public void setBanquet_type(String banquet_type) {
            this.banquet_type = banquet_type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBanquet_id() {
            return banquet_id;
        }

        public void setBanquet_id(String banquet_id) {
            this.banquet_id = banquet_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getCheck_status() {
            return check_status;
        }

        public void setCheck_status(String check_status) {
            this.check_status = check_status;
        }

        public String getCheck_status_name() {
            return check_status_name;
        }

        public void setCheck_status_name(String check_status_name) {
            this.check_status_name = check_status_name;
        }

        public String getContract_no() {
            return contract_no;
        }

        public void setContract_no(String contract_no) {
            this.contract_no = contract_no;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getIntent_man_name() {
            return intent_man_name;
        }

        public void setIntent_man_name(String intent_man_name) {
            this.intent_man_name = intent_man_name;
        }

        public String getDate_r() {
            return date_r;
        }

        public void setDate_r(String date_r) {
            this.date_r = date_r;
        }

        public String getHall_list() {
            return hall_list;
        }

        public void setHall_list(String hall_list) {
            this.hall_list = hall_list;
        }

        public String getIs_check() {
            return is_check;
        }

        public void setIs_check(String is_check) {
            this.is_check = is_check;
        }

        public String getIs_recheck() {
            return is_recheck;
        }

        public void setIs_recheck(String is_recheck) {
            this.is_recheck = is_recheck;
        }

        public List<StepListDTO> getStepList() {
            return stepList;
        }

        public void setStepList(List<StepListDTO> stepList) {
            this.stepList = stepList;
        }

        public static class StepListDTO {
            /**
             * real_name : 雷
             * avatar :
             * avatar_name : 雷
             * title : 发起审批
             * time : 2021-02-07 17:43:47
             */

            private String real_name;
            private String avatar;
            private String avatar_name;
            private String title;
            private String time;
            private String check_type;
            private String check_content;
            private List<UserInfoClass> user_id_info;

            public String getCheck_content() {
                return check_content;
            }

            public void setCheck_content(String check_content) {
                this.check_content = check_content;
            }

            public List<UserInfoClass> getUser_id_info() {
                return user_id_info;
            }

            public void setUser_id_info(List<UserInfoClass> user_id_info) {
                this.user_id_info = user_id_info;
            }

            public static class UserInfoClass{
                private String  id;
                private String  realname;
                private String  check_type;
                private String  check_time;
                private String  check_content;
                private String  time;

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getRealname() {
                    return realname;
                }

                public void setRealname(String realname) {
                    this.realname = realname;
                }

                public String getCheck_type() {
                    return check_type;
                }

                public void setCheck_type(String check_type) {
                    this.check_type = check_type;
                }

                public String getCheck_time() {
                    return check_time;
                }

                public void setCheck_time(String check_time) {
                    this.check_time = check_time;
                }

                public String getCheck_content() {
                    return check_content;
                }

                public void setCheck_content(String check_content) {
                    this.check_content = check_content;
                }
            }

            public String getCheck_type() {
                return check_type;
            }

            public void setCheck_type(String check_type) {
                this.check_type = check_type;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

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

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }
    }
}
