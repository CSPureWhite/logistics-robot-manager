package com.example.logistics_robot_manager.utils;

import com.example.logistics_robot_manager.common.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * 邮箱工具类
 */
@Component
public class MailUtil {
    @Autowired
    JavaMailSender mailSender;

    public void sendValidateCodeMail(String email,String validateCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        // 邮件标题
        mailMessage.setSubject("物流机器人管理系统-注册验证码");
        // 邮件内容
        mailMessage.setText("您的验证码为："+validateCode+"\n该验证码将在5分钟后失效！");
        // 发送人
        mailMessage.setFrom(Constant.MAIL_SENDER_ADDRESS);
        // 收件人
        mailMessage.setTo(email);
        // 发送
        mailSender.send(mailMessage);
    }
}
