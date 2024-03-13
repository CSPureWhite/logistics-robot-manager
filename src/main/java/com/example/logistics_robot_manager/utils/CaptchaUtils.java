package com.example.logistics_robot_manager.utils;

import cn.hutool.captcha.LineCaptcha;
import org.apache.commons.lang3.RandomUtils;

import java.awt.*;

/**
 * 图片验证码工具类
 */
public class CaptchaUtils {
    private static final int WIDTH=100;
    private static final int HEIGHT=55;
    private static final int CODE_COUNT=4;
    private static final int LINE_COUNT=20;
    private static final int COLOR_RANDOM_START=240;
    private static final int COLOR_RANDOM_END=250;

    /**
     * 生成图片验证码对象
     */
    public static LineCaptcha generateCaptcha(){
        // 生成线段干扰图形验证码
        LineCaptcha captcha= cn.hutool.captcha.CaptchaUtil.createLineCaptcha(WIDTH,HEIGHT,CODE_COUNT,LINE_COUNT);
        int r= RandomUtils.nextInt(COLOR_RANDOM_START,COLOR_RANDOM_END);
        int g=RandomUtils.nextInt(COLOR_RANDOM_START,COLOR_RANDOM_END);
        int b=RandomUtils.nextInt(COLOR_RANDOM_START,COLOR_RANDOM_END);
        captcha.setBackground(new Color(r,g,b));
       return captcha;
    }
}
