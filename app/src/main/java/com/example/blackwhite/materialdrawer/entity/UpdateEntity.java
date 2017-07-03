package com.example.blackwhite.materialdrawer.entity;

/**
 * Created by blackwhite on 2017/6/28.
 */

public class UpdateEntity {

    /**
     * code : 200
     * msg : 查询成功
     * data : {"_id":"57","username":"black","password":"202cb962ac59075b964b07152d234b70","nickname":"BlackWhite","user_ip":"","user_pic":null,"add_time":"0000-00-00 00:00:00","gender":"0","email":null,"phone":"1588868886","local":"广东珠海","power":"0","tag":null,"birth_place":null,"real_name":null,"blood_group":null,"qq":null,"wechat":null,"sign":null,"intro":null,"desc":null,"role_id":"2","status":"0"}
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
         * _id : 57
         * username : black
         * password : 202cb962ac59075b964b07152d234b70
         * nickname : BlackWhite
         * user_ip :
         * user_pic : null
         * add_time : 0000-00-00 00:00:00
         * gender : 0
         * email : null
         * phone : 1588868886
         * local : 广东珠海
         * power : 0
         * tag : null
         * birth_place : null
         * real_name : null
         * blood_group : null
         * qq : null
         * wechat : null
         * sign : null
         * intro : null
         * desc : null
         * role_id : 2
         * status : 0
         */

        private String _id;
        private String username;
        private String password;
        private String nickname;
        private String user_ip;
        private String user_pic;
        private String add_time;
        private String gender;
        private String email;
        private int phone;
        private String local;
        private String power;
        private String tag;
        private String birth_place;
        private String real_name;
        private String blood_group;
        private String qq;
        private String wechat;
        private String sign;
        private String intro;
        private String desc;
        private String role_id;
        private String status;

        public void set_id(String _id) {
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

        public void setUser_pic(String user_pic) {
            this.user_pic = user_pic;
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

        public void setPower(String power) {
            this.power = power;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public void setBirth_place(String birth_place) {
            this.birth_place = birth_place;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public void setBlood_group(String blood_group) {
            this.blood_group = blood_group;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public void setWechat(String wechat) {
            this.wechat = wechat;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String get_id() {
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

        public String getUser_pic() {
            return user_pic;
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

        public String getPower() {
            return power;
        }

        public String getTag() {
            return tag;
        }

        public String getBirth_place() {
            return birth_place;
        }

        public String getReal_name() {
            return real_name;
        }

        public String getBlood_group() {
            return blood_group;
        }

        public String getQq() {
            return qq;
        }

        public String getWechat() {
            return wechat;
        }

        public String getSign() {
            return sign;
        }

        public String getIntro() {
            return intro;
        }

        public String getDesc() {
            return desc;
        }

        public String getRole_id() {
            return role_id;
        }

        public String getStatus() {
            return status;
        }
    }
}
