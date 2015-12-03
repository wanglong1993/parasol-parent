package com.ginkgocap.parasol.user.thirdlogin.utils;


public enum ThirdLoginConstantCode {
	
	LOGIN_QQ(100,"LOGIN_QQ","用户通过QQ方式登录"),
	LOGIN_QQ_BIND(101,"","用户QQ账号已经绑定，跳转首页"),
	LOGIN_QQ_NO_BIND(102,"","用户QQ账号未绑定，跳转完善资料页"),
	LOGIN_QQ_ERROR(103,"redirect:/login","用户QQ账号登录异常，请重新登录"),
	QQ_USER_INFO(104,"QQ_USER_INFO","QQ用户信息"),
		
	LOGIN_WEIBO(200,"LOGIN_WEIBO","用户通过微博方式登录"),
	LOGIN_WEIBO_BIND(201,"","用户微博账号已经绑定，跳转首页"),
	LOGIN_WEIBO_NO_BIND(202,"","用户微博账号未绑定,跳转完善资料页"),
	LOGIN_WEIBO_ERROR(203,"redirect:/login","用户微博账号登录异常，请重新登录"),
	LOGIN_WEIBO_USER(204,"LOGIN_WEIBO_USER","微博用户信息"),
	
	LOGIN_UID(300,"LOGIN_UID","第三方登录uid"),
	ACCESS_TOKEN(301,"ACCESS_TOKEN","第三方登录accessToken"),
	LOGIN_TOKENEXPIREIN(302,"LOGIN_TOKENEXPIREIN","第三方登token到期时间"),
	
	LOGIN_TYPE(400,"LOGIN_TYPE","第三方等类型100|200"),
	LOGIN_BING_ERROR(401,"LOGIN_BIND_ERROR","绑定第三方登录失败"),
	LOGIN_BING_ORG_ERROR(402,"LOGIN_BING_ORG_ERROR","组织不能绑定第三方");
	
	private int key;
	private String value;
	private String description;
	
	private ThirdLoginConstantCode(int key,String value, String description){
		this.key       = key;
		this.value     = value;
		this.description = description; 
	}
	
    /**
     * @param key
     * @return ThirdLoginConstantCode
     */
    public static ThirdLoginConstantCode getCommand(int key){
    	ThirdLoginConstantCode[] values = ThirdLoginConstantCode.values();
        for (int i = 0; i < values.length; i++) {
        	ThirdLoginConstantCode eInfo = values[i];
            if (eInfo.getKey() == key) {
                return eInfo;
            }
        }
        return null;
    }	
    
    
	
	public int getKey() {return key;}
	public void setKey(int key) {this.key = key;}
	
	public String getValue() {return value;}
	public void setValue(String value) {this.value = value;}

	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	
	public static void main(String[] args) {
		System.out.println(ThirdLoginConstantCode.LOGIN_QQ.getDescription());
	}
	
}
