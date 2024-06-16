package com.tiutiu.prodiff.listener;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "mail")
public class MailQueueListener {
    @Resource
    JavaMailSender javaMailSender;

    @RabbitHandler
    public void sendMailMessage(Map<String, Object> data){
        String email = data.get("email").toString();
        Integer code = (Integer) data.get("code");

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("欢迎注册ProDiff");
        message.setText("您的验证码是："+code+ " 有效时间3分钟，为了保障您的账户安全，请勿向他人泄露验证码信息。");
        message.setTo(email);
        message.setFrom("atiutiu@qq.com");

        javaMailSender.send(message);
    }
}
