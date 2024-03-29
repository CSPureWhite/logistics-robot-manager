package com.example.logistics_robot_manager.common;

public class Constant {

    // Http Code Constant
    public static final String CODE_OK="200";
    public static final String CODE_BAD_REQUEST="400";

    // Redis Constant
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL=60L;
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL= 60L;
    public static final String REGISTER_CODE_KEY ="register:code:";
    public static final Long REGISTER_CODE_TTL =300L;

    /**
     * 用户日活量前缀
     */
    public static final String DAU_KEY="DAU:";

    // others
    /**
     * 邮件验证码发送请求最小时间间隔：60秒（此处单位为毫秒）
     */
    public static final int REGISTER_MAIL_TTL=60000;
    /**
     * 验证码邮件发件人地址
     */
    public static final String MAIL_SENDER_ADDRESS="1461459810@qq.com";
    /**
     * 用户日活量统计时间，设为30天（包括今日）
     */
    public static final long DAU_PERIOD=30L;
}
