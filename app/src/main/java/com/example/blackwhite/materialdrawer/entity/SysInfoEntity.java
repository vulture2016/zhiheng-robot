package com.example.blackwhite.materialdrawer.entity;

import java.util.List;

/**
 * Created by blackwhite on 2017/6/28.
 */

public class SysInfoEntity {


    /**
     * code : 200
     * msg : 获取信息成功
     * data : [{"id":"3","from":"admin","to":"black","context":"dashjdkshafkj","time":"2017-06-27 17:22:00"}]
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
         * id : 3
         * from : admin
         * to : black
         * context : dashjdkshafkj
         * time : 2017-06-27 17:22:00
         */

        private String id;
        private String from;
        private String to;
        private String context;
        private String time;

        public void setId(String id) {
            this.id = id;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getId() {
            return id;
        }

        public String getFrom() {
            return from;
        }

        public String getTo() {
            return to;
        }

        public String getContext() {
            return context;
        }

        public String getTime() {
            return time;
        }
    }
}
