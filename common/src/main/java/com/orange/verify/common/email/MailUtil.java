package com.orange.verify.common.email;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.util.Properties;

/**
 * 用于QQ邮件封装
 * @author Orange
 * @date 2018/11/1
 */
public class MailUtil {

    private String senderAccount;

    private String senderPassword;

    /**
     * @param senderAccount 发件人邮箱号
     * @param senderPassword 发件人密码
     */
    public MailUtil(String senderAccount, String senderPassword) {
        this.senderAccount = senderAccount;
        this.senderPassword = senderPassword;
    }

    /**
     * @param senderObject 接收者
     * @param title 发送标题
     * @param content 发送内容
     */
    public void send(String senderObject,String title,String content) throws MessagingException,
            GeneralSecurityException {

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", "smtp.qq.com");

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);

        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            @Override
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(senderAccount, senderPassword); //发件人邮件用户名、密码
            }
        });

        // 创建默认的 MimeMessage 对象
        MimeMessage message = new MimeMessage(session);

        // Set From: 头部头字段
        message.setFrom(new InternetAddress(senderAccount));

        // Set To: 头部头字段
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(senderObject));

        // Set Subject: 头部头字段
        message.setSubject(title);

        // 设置消息体
        message.setText(content);

        // 发送消息
        Transport.send(message);

    }

}
