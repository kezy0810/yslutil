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
                helper.setFrom("543483026@qq.com");// ???????????????
                helper.setTo("845597614@qq.com");// ???????????????
//                helper.setCc(cc);// ????????????
                helper.setSubject("?????????");// ????????????
                helper.setText("?????????:???"+code+"???");// ?????????
                javaMailSender.send(mailMessage);// ????????????
                System.out.println("??????????????????...");
            } catch (Exception e) {
                System.out.println("????????????????????????:" + e.getMessage());
                try {
                    Thread.sleep(1000 * 1000);
                    this.sendMail(code);
                } catch (InterruptedException e1) {
                    System.out.println("????????????????????????:" + e1.getMessage());
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