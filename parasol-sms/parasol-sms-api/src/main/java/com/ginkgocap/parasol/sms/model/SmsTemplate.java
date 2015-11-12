package com.ginkgocap.parasol.sms.model;

public class SmsTemplate {
	
	
	private final static String IS_OPEN = "false";
	private final static String SEND_MESSAGE_URL ="";
    private final static String SEND_MESSAGE_APIKEY = "";
    private final static String SEND_MESSAGE_USERNAME = "";
    private final static String SEND_MESSAGE_PASSWORD = "";
    
	public static String getIsOpen() {
		return IS_OPEN;
	}
	public static String getSendMessageUrl() {
		return SEND_MESSAGE_URL;
	}
	public static String getSendMessageApikey() {
		return SEND_MESSAGE_APIKEY;
	}
	public static String getSendMessageUsername() {
		return SEND_MESSAGE_USERNAME;
	}
	public static String getSendMessagePassword() {
		return SEND_MESSAGE_PASSWORD;
	}
    
}
