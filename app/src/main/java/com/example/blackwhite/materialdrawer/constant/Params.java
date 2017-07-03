package com.example.blackwhite.materialdrawer.constant;

public class Params {

    public static final String RegisterLogin_URL = "http://119.29.107.15/Chat_project/public/index.php/login/Login/";
    public static final String Chat_URL = "http://119.29.107.15/Chat_project/public/index.php/main/Main/";


    public static class Code {
        public static final int TEXT = 100000;//文本类数据
        public static final int URL = 200000;//网址类数据

        public static final int ERRORKEY_LEN = 40001;//key的长度错误（32位）
        public static final int ERROR_TEXT_NULL = 40002;//请求内容为空
        public static final int ERROR_KEY_ERROR = 40003;//key错误或帐号未激活
        public static final int ERROR_OUT_OF_TIMES = 40004;//当天请求次数已用完
        public static final int ERROR_NOT_SUPPORT = 40005;//暂不支持该功能
        public static final int ERROR_SERVER_UPDATE = 40006;//服务器升级中
        public static final int ERROR_DATA_FORMAT = 40007;//服务器数据格式异常
    }
}
