package com.ginkgocap.parasol.email.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ginkgocap.parasol.email.model.Email;

/**
 * 发送Html邮件
 * @author liu
 *
 */
public class SendHtmlMail implements Runnable {
    private String to = " ";//收件人 
    private String from = " ";//发件人 
    private String fromName="";//发件人昵称
    private String host = " ";//smtp主机 
    private String username = " ";
    private String password = " ";
    private String subject = " ";//邮件主题 
    private String content = " ";//邮件正文 
    /**
     * 是否认证用户
     */
    private String isrzyh = "1";
    /**
     * 是否SSl安全连接
     */
    private String isqyssl = "0";
    private String port = "25";
    private final Logger logger = Logger.getLogger(SendHtmlMail.class);
    private ArrayList<String> fileNames = new ArrayList<String>();//附件文件集合 

    public SendHtmlMail() {
    }

    public void run() {
        logger.info("begin to send email.");
            sendMail();
    }

    public SendHtmlMail(Email email) throws Exception {
        this.to = email.getTo();
        this.from = email.getFrom();
        this.host = email.getSmtpServer();
        this.username = email.getUsername();
        this.password = email.getPassword();
        this.subject = email.getSubject();
        this.content = email.getContent();
        this.isrzyh = email.getIsaqrz();
        this.isqyssl = email.getSfqyssl();
        this.port = email.getPort();
        this.fromName=email.getFromName();
//        parseAttach();
    }

  /*  //字符集转换
    public String transferChinese(String strText) {
        try {
            strText = MimeUtility.encodeText(new String(strText.getBytes(), "GB2312"), "GB2312", "B");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strText;
    }
*/
    //设置要发送的附件，多个附件名用逗号隔开，附件的路径以站点的根/开头  例如： /upload/xxxx.jpg
    public void setAttach(String fileNameStrings) {
        StringTokenizer stk = new StringTokenizer(fileNameStrings, ",");
        while (stk.hasMoreElements()) {
            fileNames.add(((String) stk.nextElement()).trim());
        }
    }

    //解析html文件中的内容，并将图片替换为id,将文件名存储在fileNames中
//    public void parseAttach() throws Exception {
//        if (content == null)
//            return;
//        this.fileNames = new ArrayList<String>();//附件文件集合 
//        int i = content.toLowerCase().indexOf("<img ");
//        int j = 0;
//        int k = 0;
//        while (i >= 0) {
//            j = content.toLowerCase().indexOf(">", i);
//            if (j < 0)
//                throw new Exception("无法解析发送内容,<img 标记不匹配。");
//            String imgString = content.substring(i + 5, j).trim();
//            //去掉最后的斜杠
//            if (imgString.endsWith("/"))
//                imgString = imgString.substring(0, imgString.length() - 1);
//            //找到src部分
//            int m = imgString.toLowerCase().indexOf("src=");
//            int n = imgString.toLowerCase().indexOf(" ", m);
//            if (n < 0)
//                n = imgString.length();
//            String srcString = imgString.substring(m + 4, n).trim();
//            //去掉两边的分隔符
//            if (srcString.startsWith("'") || srcString.startsWith("\""))
//                srcString = srcString.substring(1);
//            if (srcString.endsWith("'") || srcString.endsWith("\""))
//                srcString = srcString.substring(0, srcString.length() - 1);
//
//            //如果是本站点的图片，则进行处理
//            if (srcString.toLowerCase().indexOf("http:") < 0) {
//                //添加到文件名中
//                this.fileNames.add(srcString);
//                //替换内容中的src标记
//                content = content.substring(0, i + 5) + " " + imgString.substring(0, m) + " src=\"cid:img" + String.valueOf(k) + "\" "
//                        + imgString.substring(n) + "/" + content.substring(j);
//                k++;
//            }
//            //处理下一个
//            i = content.toLowerCase().indexOf("<img ", j);
//        }
//
//    }

    public boolean sendMail() {
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        //构造mail   session 
        Properties props = System.getProperties();
        props.put("mail.smtp.host", host);
        if (isqyssl.equals("1")) {
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        }
        props.put("mail.smtp.port", port);
        if (isrzyh.equals("0")) {
            props.put("mail.smtp.auth", "false");
        } else {
            props.put("mail.smtp.auth", "true");
        }
        Authenticator auth = new MyAuthenticator(username, password);

        Session session = Session.getInstance(props, auth);
        session.setDebug(true);
        try {
            //构造MimeMessage   并设定基本的值 
            MimeMessage msg = new MimeMessage(session);
          //设置发信人
//    		msg.setFrom(new InternetAddress(from));
    		//设置自定义发件人昵称
            if(StringUtils.isNotBlank(fromName)){
            	String nick="";
            	try {
            		nick=javax.mail.internet.MimeUtility.encodeText(fromName);
            	} catch (UnsupportedEncodingException e) {
            		e.printStackTrace();
            	} 
            	msg.setFrom(new InternetAddress(nick+" <"+from+">"));
            }else{
            	msg.setFrom(new InternetAddress(from));	
            }
            InternetAddress[] address = { new InternetAddress(to) };
            msg.setRecipients(Message.RecipientType.TO, address);
            //subject   =   transferChinese(subject); 
            msg.setSubject(subject, "UTF-8");

            //构造Multipart 
            MimeMultipart mp = new MimeMultipart("related");

            //向Multipart添加正文 
            MimeBodyPart mbpContent = new MimeBodyPart();
            mbpContent.setContent((content), "text/html;charset=UTF-8");
            //******************************************** 
            //背景图片id 
            //mbpContent.setHeader( "Content-ID", "page"); 

            //******************************************** 
            //向MimeMessage添加（Multipart代表正文） 
            mp.addBodyPart(mbpContent);

            ////////////////////////////////// 
            ////////////////////////////////// 
            //向Multipart添加附件 
            for (int i = 0; i < this.fileNames.size(); i++) {
                MimeBodyPart mbpFile = new MimeBodyPart();
                FileDataSource fds = new FileDataSource(this.fileNames.get(i));
                mbpFile.setDataHandler(new DataHandler(fds));
                mbpFile.setFileName(fds.getName());
                mbpFile.setHeader("Content-ID", "img" + String.valueOf(i));
                //向MimeMessage添加（Multipart代表附件） 
                mp.addBodyPart(mbpFile);
            }

            //向Multipart添加MimeMessage 
            msg.setContent(mp);
            msg.setSentDate(new Date());

            //发送邮件 
            Transport.send(msg);
        } catch (Exception mex) {
            mex.printStackTrace();

            return false;
        }
        return true;
    }

    class MyAuthenticator extends Authenticator {
        String user = "";
        String pw = "";

        MyAuthenticator(String user, String pw) {
            this.user = user;
            this.pw = pw;
        }

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(user, pw);
        }
    }
}
