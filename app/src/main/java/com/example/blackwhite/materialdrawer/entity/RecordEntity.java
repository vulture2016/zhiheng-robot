package com.example.blackwhite.materialdrawer.entity;

import java.util.List;

/**
 * Created by blackwhite on 2017/6/21.
 */

public class RecordEntity {

    /**
     * code : 200
     * msg : 查询成功
     * data : [{"id":"16","username":"black","chatobject":"black","speak_data":"珠海新闻","time_date":"2017-06-20 13:49:16"},{"id":"17","username":"black","chatobject":"black","speak_data":"{\"code\":200000,\"text\":\"亲，已帮你找到新闻信息\",\"url\":\"http://m.toutiao.com/search/?keyword=%E7%8F%A0%E6%B5%B7&from=search_page_input\"}","time_date":"2017-06-20 13:49:17"},{"id":"18","username":"black","chatobject":"black","speak_data":"附近酒店","time_date":"2017-06-20 13:49:41"},{"id":"19","username":"black","chatobject":"black","speak_data":"{\"code\":100000,\"text\":\"目前还不会呀，和我聊点别的吧!\"}","time_date":"2017-06-20 13:49:41"},{"id":"20","username":"black","chatobject":"black","speak_data":"你会什么","time_date":"2017-06-20 14:06:16"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public static class DataBean {
        /**
         * id : 16
         * username : black
         * chatobject : black
         * speak_data : 珠海新闻
         * time_date : 2017-06-20 13:49:16
         */

        private String id;
        private String username;
        private String chatobject;
        private String speak_data;
        private String time_date;

        public void setId(String id) {
            this.id = id;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public void setChatobject(String chatobject) {
            this.chatobject = chatobject;
        }

        public void setSpeak_data(String speak_data) {
            this.speak_data = speak_data;
        }

        public void setTime_date(String time_date) {
            this.time_date = time_date;
        }

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getChatobject() {
            return chatobject;
        }

        public String getSpeak_data() {
            return speak_data;
        }

        public String getTime_date() {
            return time_date;
        }
    }
}
