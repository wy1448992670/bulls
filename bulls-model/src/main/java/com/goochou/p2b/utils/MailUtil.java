package com.goochou.p2b.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 
 * @author xueqi
 *
 */
public class MailUtil {

	/**
	 * Message对象将存储我们实际发送的电子邮件信息，
	 * Message对象被作为一个MimeMessage对象来创建并且需要知道应当选择哪一个JavaMail session。
	 */
	private MimeMessage message;

	/**
	 * Session类代表JavaMail中的一个邮件会话。
	 * 每一个基于JavaMail的应用程序至少有一个Session（可以有任意多的Session）。
	 * 
	 * JavaMail需要Properties来创建一个session对象。 寻找"mail.smtp.host" 属性值就是发送邮件的主机
	 * 寻找"mail.smtp.auth" 身份验证，目前免费邮件服务器都需要这一项
	 */
	private Session session;

	
	private String mailHost = "smtp.exmail.qq.com";
	private String senderUserName = "admin@xinjucai.com";
	private String senderPassword = "I4n8qbHX";
	private String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	private Properties properties = null;

	/**
	 * 
	 * <p>
	 * Discription:[初始化]
	 * </p>
	 * 
	 * @coustructor 方法.
	 */
	public MailUtil() {
		
		String mailAuth = "true";
		if (null == properties) {
			properties = new Properties();
		}
		properties.put("mail.smtp.host", this.mailHost);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.sender.username", this.senderUserName);
		properties.put("mail.sender.password", this.senderPassword);
		
		properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
		properties.setProperty("mail.smtp.port", "465");
		properties.setProperty("mail.smtp.socketFactory.port", "465");
		

		MailAuthenticator authenticator = null;
		if (null != mailAuth && "true".equals(mailAuth)) {
			authenticator = new MailAuthenticator(senderUserName,
					senderPassword);
		}

		session = Session.getInstance(properties, authenticator);
		session.setDebug(true); // 开启后有调试信息
		message = new MimeMessage(session);
	}

	/**
	 * 
	 * Created on 2015-3-23
	 * <p>
	 * Discription:[发送邮件]
	 * </p>
	 * 
	 * @author:[武勇吉]
	 * @update:[日期2015-3-23] [武勇吉]
	 * @return void .
	 * @param subject
	 *            主题
	 * @param content
	 *            内容，可以是Html
	 * @param receiveUser
	 *            发送人
	 * @param attachment
	 *            附件，没有则传null
	 */
	public void sendEmail(String subject, String content, String receiveUser,
			File attachment) {
		
		try {
			// 发件人
			InternetAddress from = new InternetAddress(this.senderUserName);
			message.setFrom(from);

			// 收件人
			InternetAddress to = new InternetAddress(receiveUser);
			message.setRecipient(Message.RecipientType.TO, to);

			// 邮件主题
			try {
				message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			// 如果包含附件
			if (null != attachment) {
				// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
				Multipart multipart = new MimeMultipart();

				// 添加邮件正文
				BodyPart contentPart = new MimeBodyPart();
				contentPart.setContent(content, "text/html;charset=UTF-8");
				multipart.addBodyPart(contentPart);

				// 添加附件的内容
				if (attachment != null) {
					BodyPart attachmentBodyPart = new MimeBodyPart();
					DataSource source = new FileDataSource(attachment);
					attachmentBodyPart.setDataHandler(new DataHandler(source));

					// 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
					// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
					// sun.misc.BASE64Encoder enc = new
					// sun.misc.BASE64Encoder();
					// messageBodyPart.setFileName("=?GBK?B?" +
					// enc.encode(attachment.getName().getBytes()) + "?=");

					// MimeUtility.encodeWord可以避免文件名乱码
					try {
						attachmentBodyPart.setFileName(MimeUtility
								.encodeWord(attachment.getName()));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();

					}
					multipart.addBodyPart(attachmentBodyPart);
					// 将multipart对象放到message中
					message.setContent(multipart);
				}
			} else { // 不包含附件
				// 邮件内容,也可以使纯文本"text/plain"
				message.setContent(content, "text/html;charset=UTF-8");
			}

			// 保存邮件
			message.saveChanges();

			Transport.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
	/**
     * 
     * Created on 2015-3-23
     * <p>
     * Discription:[发送邮件]
     * </p>
     * 
     * @author:[武勇吉]
     * @update:[日期2015-3-23] [武勇吉]
     * @return void .
     * @param subject
     *            主题
     * @param content
     *            内容，可以是Html
     * @param receiveUser
     *            发送人
     * @param attachment
     *            附件，没有则传null
     */
    public void sendEmail(String subject, String content, String receiveUser,String copyToMails,
            File attachment) {
        
        try {
            // 发件人
            InternetAddress from = new InternetAddress(this.senderUserName);
            message.setFrom(from);

            // 收件人
            InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);

             //抄送人
            message.setRecipients(Message.RecipientType.CC, new InternetAddress().parse(copyToMails));

            
            // 邮件主题
            try {
                message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            // 如果包含附件
            if (null != attachment) {
                // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
                Multipart multipart = new MimeMultipart();

                // 添加邮件正文
                BodyPart contentPart = new MimeBodyPart();
                contentPart.setContent(content, "text/html;charset=UTF-8");
                multipart.addBodyPart(contentPart);

                // 添加附件的内容
                if (attachment != null) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachment);
                    attachmentBodyPart.setDataHandler(new DataHandler(source));

                    // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
                    // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                    // sun.misc.BASE64Encoder enc = new
                    // sun.misc.BASE64Encoder();
                    // messageBodyPart.setFileName("=?GBK?B?" +
                    // enc.encode(attachment.getName().getBytes()) + "?=");

                    // MimeUtility.encodeWord可以避免文件名乱码
                    try {
                        attachmentBodyPart.setFileName(MimeUtility
                                .encodeWord(attachment.getName()));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }
                    multipart.addBodyPart(attachmentBodyPart);
                    // 将multipart对象放到message中
                    message.setContent(multipart);
                }
            } else { // 不包含附件
                // 邮件内容,也可以使纯文本"text/plain"
                message.setContent(content, "text/html;charset=UTF-8");
            }

            // 保存邮件
            message.saveChanges();

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * <p>批量发送</p> 
     * @param subject
     * @param content
     * @param receiveUser
     * @param copyToMails
     * @param attachment
     * @author: lxfeng  
     * @date: Created on 2018-8-8 下午1:16:36
     */
    public void sendEmails(String subject, String content, String [] receiveUsers,String copyToMails,
            File attachment) {
        
        try {
            // 发件人
            InternetAddress from = new InternetAddress(this.senderUserName);
            message.setFrom(from);

            // 收件人
            /*InternetAddress to = new InternetAddress(receiveUser);
            message.setRecipient(Message.RecipientType.TO, to);*/
            
            InternetAddress [] arr = new InternetAddress[receiveUsers.length];
            for (int i = 0; i < receiveUsers.length; i++) {
            	InternetAddress to = new InternetAddress(receiveUsers[i]);
            	arr[i] = to;
			}
            message.setRecipients(Message.RecipientType.TO, arr);

             //抄送人
            message.setRecipients(Message.RecipientType.CC, new InternetAddress().parse(copyToMails));

            
            // 邮件主题
            try {
                message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
            }
            // 如果包含附件
            if (null != attachment) {
                // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
                Multipart multipart = new MimeMultipart();

                // 添加邮件正文
                BodyPart contentPart = new MimeBodyPart();
                contentPart.setContent(content, "text/html;charset=UTF-8");
                multipart.addBodyPart(contentPart);

                // 添加附件的内容
                if (attachment != null) {
                    BodyPart attachmentBodyPart = new MimeBodyPart();
                    DataSource source = new FileDataSource(attachment);
                    attachmentBodyPart.setDataHandler(new DataHandler(source));

                    // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
                    // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
                    // sun.misc.BASE64Encoder enc = new
                    // sun.misc.BASE64Encoder();
                    // messageBodyPart.setFileName("=?GBK?B?" +
                    // enc.encode(attachment.getName().getBytes()) + "?=");

                    // MimeUtility.encodeWord可以避免文件名乱码
                    try {
                        attachmentBodyPart.setFileName(MimeUtility
                                .encodeWord(attachment.getName()));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }
                    multipart.addBodyPart(attachmentBodyPart);
                    // 将multipart对象放到message中
                    message.setContent(multipart);
                }
            } else { // 不包含附件
                // 邮件内容,也可以使纯文本"text/plain"
                message.setContent(content, "text/html;charset=UTF-8");
            }

            // 保存邮件
            message.saveChanges();

            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


	public static void main(String[] args) {
		MailUtil mailUtil = new MailUtil();
		
		String str = "xueqi@xinjucai.com";
		String [] arr = str.split(";");
		
		mailUtil.sendEmails(
				"测试邮件",
				"你好！<br> 点击下面的链接跳转<a href='www.baidu.com'>www.baidu.com</a>感谢对本公司的支持！www.baidu.com",
				arr,"", null);
	}
}
