package com.ling.commonutils;
import java.util.Date;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
public class SendEmailUtil {
    public static String myEmailAccount = "******@qq.com";//*发送邮件的账号
    public static String myEmailPassword = "***8****i";//*第三方邮使用的授权码（下面会说到）
     // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
     // QQ邮箱的 SMTP 服务器地址为: smtp.qq.com
    public static String myEmailSMTPHost = "smtp.qq.com";
    public static String receiveMailAccount = "";//收送人邮箱
    //controller层调用此方法
	public static boolean sendMessage(String mail, String code){
	//将传过来的邮箱赋给receiveMailAccount 
		receiveMailAccount=mail;
		System.out.println("这里是发邮件-------------------------------"+mail);
		System.out.println("这里是验证码-------------------------------"+code);
		//ver是controller层生成的6位随机验证码传到发送邮件
		return send(code);
	}

	public static boolean send(String code){
	try{
		// 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        final String smtpPort = "465";
        props.setProperty("mail.smtp.port", smtpPort);
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.port", smtpPort);
        Session session = Session.getInstance(props);
        MimeMessage message = createMimeMessage(session, myEmailAccount, receiveMailAccount,code);
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        return true;
	}catch(Exception e){
		e.printStackTrace();
		return false;
	}
}
	public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String code) throws Exception {

    	// 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail, "我的谷粒学院", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "用户", "UTF-8"));
        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject("验证码", "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        //将随机生成的验证码与写好的模板结合
        message.setContent("    尊敬的用户您好,这是您的验证码："+code+",如果不是本人操作请忽略，验证码5分钟内有效。", "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(new Date());
        // 7. 保存设置
        message.saveChanges();
        return message;
    }
}
