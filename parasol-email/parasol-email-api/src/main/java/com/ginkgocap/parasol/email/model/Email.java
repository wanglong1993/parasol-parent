package com.ginkgocap.parasol.email.model;

import java.io.IOException;
import java.io.Serializable;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;
/**
 * 邮件实体
 * 
 */
public class Email implements Serializable {
	
	private static final long serialVersionUID = -4682648708274648461L;
	private String id = UUID.randomUUID().toString();
    private String to;
    private String from;
    private String fromName;
    private String smtpServer;
    private String username;
    private String password;
    private String subject;
    private String content;
    private String attachment;
    private String isaqrz;
    private String sfqyssl;
    private String port;
    private int retryTimes = 0;

    public Email() {
    }

    public Email(String to, String from,String subject,String content,String attachment) {
        Properties props = new Properties();
        InputStream is = new Email().getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            props.load(is);
            this.to = to;
            this.from = from;
            this.smtpServer = props.getProperty("email.SMTP");
            this.username = props.getProperty("email.userName");
            this.password = props.getProperty("email.pwd");
            this.subject = subject;
            this.content = content;
            this.attachment = attachment;
            this.isaqrz = props.getProperty("email.auth");
            this.sfqyssl = props.getProperty("email.SSL");
            this.port = props.getProperty("email.port");
            if(this.from == null){
                this.from = this.username;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Email(String to, String from,String fromName,String subject,String content,String attachment) {
        Properties props = new Properties();
        InputStream is = new Email().getClass().getClassLoader().getResourceAsStream("application.properties");
        try {
            props.load(is);
            this.to = to;
            this.from = from;
            this.fromName = fromName;
            this.smtpServer = props.getProperty("email.SMTP");
            this.username = props.getProperty("email.userName");
            this.password = props.getProperty("email.pwd");
            this.subject = subject;
            this.content = content;
            this.attachment = attachment;
            this.isaqrz = props.getProperty("email.auth");
            this.sfqyssl = props.getProperty("email.SSL");
            this.port = props.getProperty("email.port");
            if(this.from == null){
                this.from = this.username;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Email(String to, String from, String smtpServer, String username, String password, String subject, String content, String attachment,
            String isaqrz, String sfqyssl, String port) {
        super();
        this.to = to;
        this.from = from;
        this.smtpServer = smtpServer;
        this.username = username;
        this.password = password;
        this.subject = subject;
        this.content = content;
        this.attachment = attachment;
        this.isaqrz = isaqrz;
        this.sfqyssl = sfqyssl;
        this.port = port;
        if(this.from == null){
            this.from = this.username;
        }
    }


    public String getId() {
        return id; 
    }

    public void setId(String id) {
        this.id = id; 
    }

    public String getTo() {
        return to; 
    }

    public void setTo(String to) {
        this.to = to; 
    }

    public String getFrom() {
        return from; 
    }

    public void setFrom(String from) {
        this.from = from; 
    }

    public String getSmtpServer() {
        return smtpServer; 
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer; 
    }

    public String getUsername() {
        return username; 
    }

    public void setUsername(String name) {
        this.username = name; 
    }

    public String getPassword() {
        return password; 
    }

    public void setPassword(String word) {
        this.password = word; 
    }

    public String getSubject() {
        return subject; 
    }

    public void setSubject(String subject) {
        this.subject = subject; 
    }

    public String getContent() {
        return content; 
    }

    public void setContent(String content) {
        this.content = content; 
    }

    public String getAttachment() {
        return attachment; 
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment; 
    }

    public String getIsaqrz() {
        return isaqrz; 
    }

    public void setIsaqrz(String isaqrz) {
        this.isaqrz = isaqrz; 
    }

    public String getSfqyssl() {
        return sfqyssl; 
    }

    public void setSfqyssl(String ssl) {
        this.sfqyssl = ssl; 
    }

    public String getPort() {
        return port; 
    }

    public void setPort(String port) {
        this.port = port; 
    }

    public int getRetryTimes() {
        return retryTimes; 
    }

    public void setRetryTimes(int time) {
        this.retryTimes = time; 
    }

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}
    
}
