package com.ginkgocap.parasol.user.test;

import java.io.InputStream;

import javax.annotation.Resource;

import junit.framework.Test;
import junit.framework.TestResult;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Assert;

import com.ginkgocap.parasol.user.model.UserLoginThird;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserLoginThirdService;

public class UserLoginThirdServiceTest  extends TestBase implements Test  {

	@Resource
	private UserLoginThirdService userLoginThirdService;
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	public int countTestCases() { 
		return 0;  
	}

	public void run(TestResult result) {
		
	}
	@org.junit.Test
	public void testCreateLotinThird() {
		try {
			Long id=userLoginRegisterService.getId("13677687623");
			Long id2 =userLoginThirdService.saveUserLoginThird(setUserLoginThird(id));
			Assert.assertTrue(id2!=null && id2>0l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testUpdateUserLoginThird() {
		try {
			Long userId=userLoginRegisterService.getId("13677687623");
			UserLoginThird userLoginThird=setUserLoginThird(userId);
			userLoginThird.setId(3916004866654212l);
			Boolean bl =userLoginThirdService.updateUserLoginThird(userLoginThird);
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testIsBinding() {
		try {
			Long id=userLoginRegisterService.getId("13677687623");
			Boolean bl =userLoginThirdService.updateUserLoginThird(setUserLoginThird(id));
			Assert.assertTrue(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	/**
	 * 初始化用户基本信息对象
	 * @return userLoginRegister
	 */
	public UserLoginThird setUserLoginThird(Long userId){
		try {
			Long ctime=System.currentTimeMillis();
			UserLoginThird userLoginThird = new UserLoginThird();
			userLoginThird.setUserId(userId);
			userLoginThird.setOpenId("65454325432");
			userLoginThird.setAccesstoken("32134314");
			userLoginThird.setLoginType(200);
			userLoginThird.setCtime(ctime);
			userLoginThird.setUtime(ctime);
			userLoginThird.setIp("192.168.110.119");
			return userLoginThird;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@org.junit.Test
	public void testWeiboLogin() {
		String local_login_weibo_url = "http://www.gintong.com/userthirdlogin/weibo";
		String login_weibo_url = getTargetUrl(local_login_weibo_url);
		try {
			BrowserUtils.openURL(login_weibo_url);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@org.junit.Test
	public void testQqLogin() {
		String local_login_qq_url = "http://www.gintong.com/userthirdlogin/qq";
		String login_qq_url = getTargetUrl(local_login_qq_url);
		try {
			BrowserUtils.openURL(login_qq_url);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private String getTargetUrl(String local_login_weibo_url) {
		String login_weibo_url = "";
		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet get = new HttpGet(local_login_weibo_url);
			HttpResponse  response = client.execute(get);
			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity entity = response.getEntity();
				InputStream in = entity.getContent();
				String result_json = IOUtils.toString(in);
				JSONObject j = JSONObject.fromObject(result_json);
				login_weibo_url = j.getJSONObject("responseData").get("redirect").toString();
				System.out.println(login_weibo_url);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return login_weibo_url;
	}	
}
