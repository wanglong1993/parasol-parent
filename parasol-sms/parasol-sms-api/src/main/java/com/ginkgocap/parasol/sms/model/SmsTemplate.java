package com.ginkgocap.parasol.sms.model;

public class SmsTemplate {
	
	
	private String isOpen = "false";
	private String sendMessageUrl ="";
    private String sendMessageApiKey = "";
    private String sendMessageUsername = "";
    private String sendMessagePassword = "";
    private String sendMessageUserId = "";
    
	public String getSendMessageUserId() {
		return sendMessageUserId;
	}
	public void setSendMessageUserId(String sendMessageUserId) {
		this.sendMessageUserId = sendMessageUserId;
	}
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getSendMessageUrl() {
		return sendMessageUrl;
	}
	public void setSendMessageUrl(String sendMessageUrl) {
		this.sendMessageUrl = sendMessageUrl;
	}
	public String getSendMessageApiKey() {
		return sendMessageApiKey;
	}
	public void setSendMessageApiKey(String sendMessageApiKey) {
		this.sendMessageApiKey = sendMessageApiKey;
	}
	public String getSendMessageUsername() {
		return sendMessageUsername;
	}
	public void setSendMessageUsername(String sendMessageUsername) {
		this.sendMessageUsername = sendMessageUsername;
	}
	public String getSendMessagePassword() {
		return sendMessagePassword;
	}
	public void setSendMessagePassword(String sendMessagePassword) {
		this.sendMessagePassword = sendMessagePassword;
	}
    
    
}
