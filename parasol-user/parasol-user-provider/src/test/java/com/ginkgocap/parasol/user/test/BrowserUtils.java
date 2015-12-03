package com.ginkgocap.parasol.user.test;

import java.lang.reflect.Method;

public class BrowserUtils {

	private static String  W = "Windows";
	private static String  M = "MAC OS";
	private static String  L = "Linux";
	private static String  U = "Unix";
	
	public static void openURL(String url) throws Exception{
		String os = System.getProperty("os.name");
		if(os.startsWith(W)){
			Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url); 
		}
		else if (os.startsWith(M)){
			Class fileMgr = Class.forName("com.apple.eio.FileManager"); 
	        Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class }); 
	        openURL.invoke(null, new Object[] { url }); 
		}
		else{
			String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" }; 
	        String browser = null; 
	        for (int count = 0; count < browsers.length && browser == null; count++) 
	            //执行代码，在brower有值后跳出， 
	          //这里是如果进程创建成功了，==0是表示正常结束。 
	            if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0) 
	                browser = browsers[count]; 
	        if (browser == null) 
	            throw new Exception("Could not find web browser"); 
	        else
	        	Runtime.getRuntime().exec(new String[] { browser, url }); 
		}
	}
}
