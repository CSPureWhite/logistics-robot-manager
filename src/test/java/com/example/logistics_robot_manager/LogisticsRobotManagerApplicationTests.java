package com.example.logistics_robot_manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class LogisticsRobotManagerApplicationTests {
    @Autowired
    JavaMailSender mailSender;

    @Test
    public void sendValidateCodeMail() {
        String email="ysx1461459810@163.com";
        String validateCode="123456";

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
