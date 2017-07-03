package com.example.blackwhite.materialdrawer.entity;

/**
 * Created by blackwhite on 2017/6/28.
 */

public class InfoEntity {


    /**
     * code : 200
     * msg : 查询成功
     * data : {"_id":69,"username":"dadadad","password":"71558221f34141a83335fd5460327756","nickname":"mimi","user_ip":"14.119.122.179","add_time":"2017-06-24 17:05:00","gender":null,"email":"daddad@xad.cp","phone":123636473,"local":null,"power":0,"role_id":"2","status":0}
     */

    private String code;
    private String msg;
    private DataBean data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public DataBean getData() {
        return data;
    }

    public static class DataBean {
        /**
         * _id : 69
         * username : dadadad
         * password : 71558221f34141a83335fd5460327756
         * nickname : mimi
         * user_ip : 14.119.122.179
         * add_time : 2017-06-24 17:05:00
         * gender : null
         * email : daddad@xad.cp
         * phone : 123636473
         * local : null
         * power : 0
         * role_id : 2
         * status : 0
         */

        private int _id;
        private String username;
        private String password;
        private String nickname;
        private String user_ip;
        private String add_time;
        private String gender;
        private String email;
        private int phone;
        private String local;
        private int power;
        private String role_id;
        private int status;

        public void set_id(int _id) {
            this._id = _id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public void setUser_ip(String user_ip) {
            this.user_ip = user_ip;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPhone(int phone) {
            this.phone = phone;
        }

        public void setLocal(String local) {
            this.local = local;
        }

        public void setPower(int power) {
            this.power = power;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int get_id() {
            return _id;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getNickname() {
            return nickname;
        }

        public String getUser_ip() {
            return user_ip;
        }

        public String getAdd_time() {
            return add_time;
        }

        public String getGender() {
            return gender;
        }

        public String getEmail() {
            return email;
        }

        public int getPhone() {
            return phone;
        }

        public String getLocal() {
            return local;
        }

        public int getPower() {
            return power;
        }

        public String getRole_id() {
            return role_id;
        }

        public int getStatus() {
            return status;
        }
    }
}
