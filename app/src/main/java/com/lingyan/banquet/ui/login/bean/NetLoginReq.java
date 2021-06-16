package com.lingyan.banquet.ui.login.bean;

/**
 * Created by _hxb on 2021/1/17.
 */

public class NetLoginReq {


    /**
     * code : 200
     * msg : 登录成功
     * data : {"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjU3LCJsb2dpbl90aW1lIjoxNjE0ODQ2MTQyLCJhdWQiOiIiLCJleHAiOjE2NDA3NjYxNDIsImlhdCI6MTYxNDg0NjE0MiwiaXNzIjoiIiwianRpIjoiOGQ0OTBjMGM5OWNiOWNlOTNhYjFjYWJiMmIzMDFjZDMiLCJuYmYiOjE2MTQ4NDYxNDIsInN1YiI6IiJ9.QSKKvAMVXWCSKu3XTjuAbX-dcpq0bolJKKmzUvttF4Q","user":{"id":"57","restaurant_id":28,"username":"17636655276","password":"aed29506bc62035aa73290f08bae53e1","realname":"雷","status":1,"nickname":"雷","first":"L","gender":3},"img_url":"http://api.forvery.top","push_id":"57"}
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
         * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1aWQiOjU3LCJsb2dpbl90aW1lIjoxNjE0ODQ2MTQyLCJhdWQiOiIiLCJleHAiOjE2NDA3NjYxNDIsImlhdCI6MTYxNDg0NjE0MiwiaXNzIjoiIiwianRpIjoiOGQ0OTBjMGM5OWNiOWNlOTNhYjFjYWJiMmIzMDFjZDMiLCJuYmYiOjE2MTQ4NDYxNDIsInN1YiI6IiJ9.QSKKvAMVXWCSKu3XTjuAbX-dcpq0bolJKKmzUvttF4Q
         * user : {"id":"57","restaurant_id":28,"username":"17636655276","password":"aed29506bc62035aa73290f08bae53e1","realname":"雷","status":1,"nickname":"雷","first":"L","gender":3}
         * img_url : http://api.forvery.top
         * push_id : 57
         */

        private String token;
        private UserDTO user;
        private String img_url;
        private String push_id;


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public UserDTO getUser() {
            return user;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getPush_id() {
            return push_id;
        }

        public void setPush_id(String push_id) {
            this.push_id = push_id;
        }

        public static class UserDTO {
            /**
             * id : 57
             * restaurant_id : 28
             * username : 17636655276
             * password : aed29506bc62035aa73290f08bae53e1
             * realname : 雷
             * status : 1
             * nickname : 雷
             * first : L
             * gender : 3
             */

            private String id;
            private String restaurant_id;
            private String username;
            private String password;
            private String realname;
            private String status;
            private String nickname;
            private String first;
            private String gender;
            private String is_admin;
            private String all_depte_id;

            public String getIs_admin() {
                return is_admin;
            }

            public void setIs_admin(String is_admin) {
                this.is_admin = is_admin;
            }

            public String getAll_depte_id() {
                return all_depte_id;
            }

            public void setAll_depte_id(String all_depte_id) {
                this.all_depte_id = all_depte_id;
            }
            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getRestaurant_id() {
                return restaurant_id;
            }

            public void setRestaurant_id(String restaurant_id) {
                this.restaurant_id = restaurant_id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getRealname() {
                return realname;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getFirst() {
                return first;
            }

            public void setFirst(String first) {
                this.first = first;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }
        }
    }
}
