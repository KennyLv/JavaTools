package com.java.tools.mail;

import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;

//import java.net.Authenticator;
//import java.net.PasswordAuthentication;
//import java.security.GeneralSecurityException;
//import java.util.Date;
//import java.util.Properties;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

public class MailTest {

	// private static String mail_host = "rsp.platform.noreply@gmail.com";
	// private static String mail_pw = "rsp_noreply@sdm";
	// imap.gmail.com Port: 993 Use SSL: Yes
	// smtp.gmail.com Port for TLS/STARTTLS: 587 Port for SSL: 465
	// pop.gmail.com Port: 995 Use SSL: Yes

	public static void main(String[] args) {

//		gmailSender();
		
		try {
			sendMail("rsp.platform.noreply@gmail.com", "rsp.platform.noreply@gmail.com", "rsp_noreply@sdm", "328566297@qq.com", "Java Mail 测试邮件", "<a>html 元素</a>：<b>邮件内容</b>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// private static void sendGmail(){
	// String host = "smtp.gmail.com";
	// int port = 587;
	// String username = "RSP_NoReply";
	// String password = "rsp_noreply@sdm";
	//
	// Properties props = new Properties();
	// props.put("mail.smtp.auth", "true");
	// props.put("mail.smtp.starttls.enable", "true");
	//
	// Session session = Session.getInstance(props);
	//
	// try {
	// Message message = new MimeMessage(session);
	// message.setFrom(new InternetAddress("rsp.platform.noreply@gmail.com"));
	// message.setRecipients(Message.RecipientType.TO,
	// InternetAddress.parse("328566297@qq.com"));
	// message.setSubject("Testing Subject");
	// message.setText("Dear Mail Crawler," + "\n\n No spam to my email,
	// please!");
	//
	// Transport transport = session.getTransport("smtp");
	// transport.connect(host, port, username, password);
	//
	// Transport.send(message);
	//
	// System.out.println("Done");
	//
	// } catch (MessagingException e) {
	// throw new RuntimeException(e);
	// }
	// }

	// gmail邮箱SSL方式
	private static void gmailssl(Properties props) {
		final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
		props.put("mail.debug", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.socketFactory.port", "465");
		// props.put("mail.smtp.socketFactory.fallback", "false");
		props.put("mail.smtp.auth", "true");
	}

	// gmail邮箱的TLS方式
	private static void gmailtls(Properties props) {
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	}

	/*
	 * 通过gmail邮箱发送邮件
	 */
	public static void gmailSender() {
		// Get a Properties object
		Properties props = new Properties();
		// 选择ssl方式
		gmailtls(props);

		final String username = "rsp.platform.noreply@gmail.com";
		final String password = "rsp_noreply@sdm";
		Session session = Session.getDefaultInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(username));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("328566297@qq.com"));
			msg.setSubject("Hello");
			msg.setText("How are you");
			msg.setSentDate(new Date());
			Transport.send(msg);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Message sent.");
	}

	/*
	 * 通过qq邮箱发送邮件,qq邮箱需要在设置里开启POP3/SMTP的授权，通过用户名+授权码方式才能发邮件
	 */
	public static void qqSender() {
		MailSSLSocketFactory msf = null;
		try {
			msf = new MailSSLSocketFactory();
			msf.setTrustAllHosts(true);
		} catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Properties props = new Properties();
		// 开启调试
		props.setProperty("mail.debug", "true");
		// 是否需要验证
		props.setProperty("mail.smtp.auth", "true");
		// 发送邮件服务器
		props.setProperty("mail.smtp.host", "smtp.qq.com");
		// 发送邮件协议名称
		props.setProperty("mail.transport.protocol", "smtp");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.ssl.socketFactory", msf);

		// 使用匿名内部类，用邮箱进行验证
		Session session = Session.getInstance(props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 通过用户名和密码进行验证
				return new PasswordAuthentication("from@qq.com", "qq邮箱生成的授权码authcode");
			}

		});
		Message message = new MimeMessage(session);
		try {
			// 设置邮件发送方
			message.setFrom(new InternetAddress("from@qq.com"));
			// 设置邮件标题
			message.setSubject("测试");
			// 设置邮件内容
			message.setContent("测试", "text/html;charset=utf-8");
			// 设置邮件接收方
			message.addRecipient(RecipientType.TO, new InternetAddress("to@gmail.com"));

			// 发送邮件
			Transport.send(message);

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	public static void sendMail(String fromMail, String user, String password, String toMail, String mailTitle,
			String mailContent) throws Exception {
		Properties props = new Properties(); // 可以加载一个配置文件
		// 使用smtp：简单邮件传输协议
		props.put("mail.smtp.host", "smtp.gmail.com");// 存储发送邮件服务器的信息
		props.put("mail.smtp.auth", "true");// 同时通过验证

		Session session = Session.getInstance(props);// 根据属性新建一个邮件会话
		// session.setDebug(true); //有他会打印一些调试信息。

		MimeMessage message = new MimeMessage(session);// 由邮件会话新建一个消息对象
		message.setFrom(new InternetAddress(fromMail));// 设置发件人的地址
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(toMail));// 设置收件人,并设置其接收类型为TO
		message.setSubject(mailTitle);// 设置标题
		// 设置信件内容
		// message.setText(mailContent); //发送 纯文本 邮件 todo
		message.setContent(mailContent, "text/html;charset=gbk"); // 发送HTML邮件，内容样式比较丰富
		message.setSentDate(new Date());// 设置发信时间
		message.saveChanges();// 存储邮件信息

		// 发送邮件
		// Transport transport = session.getTransport("smtp");
		Transport transport = session.getTransport();
		transport.connect(user, password);
		transport.sendMessage(message, message.getAllRecipients());// 发送邮件,其中第二个参数是所有已设好的收件人地址
		transport.close();
	}
	
	
	
	// public static void sendGMail2(){
	//
	// String d_email = "address@gmail.com",
	// d_uname = "Name",
	// d_password = "urpassword",
	// d_host = "smtp.gmail.com",
	// d_port = "465",
	// m_to = "toAddress@gmail.com",
	// m_subject = "Indoors Readable File: " + params[0].getName(),
	// m_text = "This message is from Indoor Positioning App. Required file(s)
	// are attached.";
	// Properties props = new Properties();
	// props.put("mail.smtp.user", d_email);
	// props.put("mail.smtp.host", d_host);
	// props.put("mail.smtp.port", d_port);
	// props.put("mail.smtp.starttls.enable","true");
	// props.put("mail.smtp.debug", "true");
	// props.put("mail.smtp.auth", "true");
	// props.put("mail.smtp.socketFactory.port", d_port);
	// props.put("mail.smtp.socketFactory.class",
	// "javax.net.ssl.SSLSocketFactory");
	// props.put("mail.smtp.socketFactory.fallback", "false");
	//
	// SMTPAuthenticator auth = new SMTPAuthenticator();
	// Session session = Session.getInstance(props, auth);
	// session.setDebug(true);
	//
	// MimeMessage msg = new MimeMessage(session);
	// try {
	// msg.setSubject(m_subject);
	// msg.setFrom(new InternetAddress(d_email));
	// msg.addRecipient(Message.RecipientType.TO, new InternetAddress(m_to));
	//
	// Transport transport = session.getTransport("smtps");
	// transport.connect(d_host, Integer.valueOf(d_port), d_uname, d_password);
	// transport.sendMessage(msg, msg.getAllRecipients());
	// transport.close();
	//
	// } catch (AddressException e) {
	// e.printStackTrace();
	// return false;
	// } catch (MessagingException e) {
	// e.printStackTrace();
	// return false;
	// }
	//
	// }
	
	
	
}