package com.example.logistics_robot_manager.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 邮箱工具类
 */
public class MailUtil {
    static JavaMailSender mailSender;

    public static void sendValidateCodeMail(String email,String validateCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 邮件标题
        mailMessage.setSubject("物流机器人管理系统-注册验证码");
        // 邮件内容
        mailMessage.setText("您的验证码为："+validateCode+"\n该验证码将在5分钟后失效！");
        // 发送人
        mailMessage.setFrom("1461459810@qq.com");
        // 收件人
        mailMessage.setTo(email);
        // 发送
        mailSender.send(mailMessage);
    }
}
