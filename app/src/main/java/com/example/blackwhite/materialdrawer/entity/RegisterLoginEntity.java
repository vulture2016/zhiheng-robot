package com.example.blackwhite.materialdrawer.entity;

/**
 * Created by Administrator on 2017/6/18.
 */

public class RegisterLoginEntity {

    /**
     * code : 200
     * msg : register_success
     * data : 59452e30ea71468722ada263fd4619b7148bfb8db6cbf
     * email :
     */

    private int code;
    private String msg;
    private String data;
    private String email;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
