package com.xly.utils;/**
 * @author DELL
 * @date 2018/5/29
 */

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.Properties;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author DELL
 * @date 2018/5/29 9:22
 **/
public class SendEmail {

  /*public static void main(String[] args) {
    Properties properties = new Properties();
    properties.setProperty("mail.host", "smtp.qq.com");
    properties.setProperty("mail.transport.protocol", "smtp");
    properties.setProperty("mail.smtp.auth", "true");
    properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    properties.setProperty("mail.smtp.port", "465");
    properties.setProperty("mail.smtp.socketFactory.port", "465");
    Session session = Session.getInstance(properties);  //创建应用会话

    Message message = new MimeMessage(session);   //消息摘要，是邮件的主体
    message.setSubject("测试");       //设置主题
    message.setText("你好！");    //邮件内容
    message.setSentDate(new Date());  //发送日期
    message.setFrom(new InternetAddress(FROM_MAIL)); //发送方
    message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO_MAIL)); //接受方
    message.saveChanges();  //保存邮件主体对象内容
    Transport transport = session.getTransport();    //传输对象
    transport.connect(SEND_HOST, FROM_MAIL, SEND_PWD);  //连接服务器中的邮箱
    transport.sendMessage(message, message.getAllRecipients());  //发送
    try {
      transport.close();  //关闭传输
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    System.out.println("Successfully  send mail to all user");
  }

  private static MimeMessage createSimpleMail(Session session) {
    // 创建邮件对象
    MimeMessage message = new MimeMessage(session);
    // 指明发件人
    try {
      message.setFrom(new InternetAddress(""));
      message.setRecipients(Message.RecipientType.TO, "");
      //邮件的标题
      message.setSubject("只包含文本的简单邮件");
      //邮件的文本内容
      message.setContent("你好啊！", "text/html;charset=UTF-8");
      //返回创建好的邮件对象
    } catch (MessagingException e) {
      e.printStackTrace();
    }
    return message;
  }*/

}
