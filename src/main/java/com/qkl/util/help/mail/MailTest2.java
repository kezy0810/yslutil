/*package com.qkl.util.help.mail;

import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailTest2 {

    
//    @Bean(name = "javaMailSender")
    public JavaMailSenderImpl javaMailSender(){
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setPort(465);
        javaMailSender.setUsername("543483026@qq.com");
        javaMailSender.setPassword("cduemgpmymurbfbb");
        Properties properties = new Properties();
        properties.setProperty("mail.host", "smtp.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");

        javaMailSender.setJavaMailProperties(properties);
        return javaMailSender;
    }
    
//    @Autowired
//    JavaMailSenderImpl javaMailSender;

    public  void sendMail(String code) {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.qq.com");
        javaMailSender.setPort(465);
        javaMailSender.setUsername("543483026@qq.com");
        javaMailSender.setUsername("543483026@qq.com");
        javaMailSender.setPassword("cduemgpmymurbfbb");
        Properties properties = new Properties();
        properties.setProperty("mail.host", "smtp.qq.com");
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");

        javaMailSender.setJavaMailProperties(properties);
        
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true, "utf-8");
                helper.setFrom("543483026@qq.com");// 设置发件人
                helper.setTo("845597614@qq.com");// 设置收件人
//                helper.setCc(cc);// 设置抄送
                helper.setSubject("验证码");// 设置主题
                helper.setText("验证码:【"+code+"】");// 邮件体
                javaMailSender.send(mailMessage);// 发送邮件
                System.out.println("邮件发送成功...");
            } catch (Exception e) {
                System.out.println("邮件发送发生异常:" + e.getMessage());
                try {
                    Thread.sleep(1000 * 1000);
                    this.sendMail(code);
                } catch (InterruptedException e1) {
                    System.out.println("重发邮件发生异常:" + e1.getMessage());
                }
            }
    }
    public static void main(String[] args) {
        MailTest2 mail = new MailTest2();
        mail.sendMail("123456");
    }
    @Test
    public void sendSimpleMail() throws Exception {
        MailTest2 mail = new MailTest2();
        mail.sendMail("123456");
    }
}
*/