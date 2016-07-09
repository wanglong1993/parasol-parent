package com.ginkgocap.parasol.organ.web.jetty.web.utils.cache;

/**
 * 放到缓存里的数据类型
 * @author lishiwei
 *
 */
public enum CacheType {
	
	LOGIN_TICKET{
		public String getName(){
			return "允许提交登录票据";
		}
	},
	
	TGT{
		public String getName(){
			return "登录验证票据";
		}
	},
	
	SERVICE_TICKET{
		public String getName(){
			return "服务票据";
		}
	},
	
	LOGOFF_SERVICE_TICKET{
		public String getName(){
			return "单点退出验证使用的ServiceTicket";
		}
	},
	
	//单点退出时使用 把已经登录的系统的sessionid保存到缓存
	SESSION_ID{
		public String getName(){
			return "session Id";
		}
	},
	
	ACCESSING_SYS{
		public String getName(){
			return "单点登录后哪些系统正在被访问";
		}
	},
	
	//验证码数字字符串
	IDENTIFY_CODE{
		public String getName(){
			return "identify code";
		}
	};
	
	public abstract String getName();
}
