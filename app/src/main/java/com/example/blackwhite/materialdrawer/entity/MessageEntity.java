package com.example.blackwhite.materialdrawer.entity;

import java.util.List;

/**
 * Created by blackwhite on 2017/6/8.
 */

public class MessageEntity {

    private int type;
    private long time;
    private int code;
    private String text;
    private String url;

    public MessageEntity() {
    }

    public MessageEntity(int type, long time) {
        this.type = type;
        this.time = time;
    }

    public MessageEntity(int type, long time, String text) {
        this.type = type;
        this.time = time;
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
