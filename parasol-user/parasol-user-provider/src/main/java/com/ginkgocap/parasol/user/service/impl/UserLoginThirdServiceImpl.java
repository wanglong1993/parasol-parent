package com.ginkgocap.parasol.user.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserBasicServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginThirdServiceException;
import com.ginkgocap.parasol.user.model.UserLoginThird;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserLoginThirdService;
@Service("userLoginThirdService")
public class UserLoginThirdServiceImpl extends BaseService<UserLoginThird>  implements UserLoginThirdService {
	
	private static Logger logger = Logger.getLogger(UserLoginThirdServiceImpl.class);
	@Resource
	UserBasicService userBasicService;
	@Resource
	UserLoginRegisterService userLoginRegisterService;
	public static final String USER_DEFAULT_PIC_PATH_FAMALE = "/web/pic/user/default.jpeg";//个人默认头像：女
	private static final String UserLoginThird_Map_OpenId_LoginType = "UserLoginThird_Map_OpenId_LoginType"; 
	private static final String UserLoginThird_Map_OpenId = "UserLoginThird_Map_OpenId"; 
	private static final String UserLoginThird_Map_UserId_LoginType = "UserLoginThird_Map_UserId_LoginType"; 

	@Override
	public String getLoginThirdUrl(int type)throws UserLoginThirdServiceException {
		try {
			String url=null;
			if(type!=1 && type !=2 ) throw new UserLoginThirdServiceException("value of type is must be 1 or 2.");
			if(type==1)url = new weibo4j.Oauth().authorize("code", "", "");
//			else if(type==2) url = new Autho2().getRedirect();
			return  url;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}

	@Override
	public String saveUserLoginThird(int login_type, String access_token,String nikeName, String headpic)throws UserLoginThirdServiceException {
		if(login_type!=100 && login_type!=200)  throw new UserLoginThirdServiceException("login_type mus be 100 or 200.");
		if(StringUtils.isEmpty(access_token)) throw  new UserLoginThirdServiceException("access_token is null or empty.");
		if(StringUtils.isEmpty(nikeName)) throw  new UserLoginThirdServiceException("nickName is null or empty.");
		if(StringUtils.isEmpty(headpic)) throw  new UserLoginThirdServiceException("headpic is null or empty.");
		
		return null;
	}	
	private UserLoginThird checkValidity(UserLoginThird userLoginThird,int type) throws UserLoginThirdServiceException, UserLoginRegisterServiceException{
		if(userLoginThird==null)throw new UserLoginThirdServiceException("userLoginThird is null.");
		if(userLoginThird.getUserId()==null || userLoginThird.getUserId()<=0l) throw new UserLoginThirdServiceException("userId is null or less than zero.");
		if(userLoginRegisterService.getUserLoginRegister(userLoginThird.getUserId())==null) throw new UserLoginThirdServiceException("userId is not exists in UserLoginRegister.");
		if(StringUtils.isEmpty(userLoginThird.getOpenId())) throw new UserLoginThirdServiceException("openId is null or empty.");
		if(type==0)
		if(getUserLoginThirdByOpenId(userLoginThird.getOpenId())!=null) throw new UserLoginRegisterServiceException("openId is exists in UserLoginThird.");
		if(type==1)
		if(getUserLoginThirdByOpenId(userLoginThird.getOpenId())==null) throw new UserLoginRegisterServiceException("openId is not exists in UserLoginThird.");
		if(userLoginThird.getLoginType()!=100 && userLoginThird.getLoginType()!=200) throw new UserLoginThirdServiceException("loginType is Must be equal to 100 or 200.");
		if(StringUtils.isEmpty(userLoginThird.getAccesstoken()))throw new UserLoginThirdServiceException("accesstoken is null or empty.");
		if(StringUtils.isEmpty(userLoginThird.getIp()))throw new UserLoginThirdServiceException("ip is null or empty.");
		if(StringUtils.isEmpty(userLoginThird.getHeadPic()))throw new UserLoginThirdServiceException("headPic is null or empty.");
		if(userLoginThird.getCtime()==null || userLoginThird.getCtime()<=0l)userLoginThird.setCtime(System.currentTimeMillis());
		if(userLoginThird.getUtime()==null || userLoginThird.getUtime()<=0l)userLoginThird.setUtime(System.currentTimeMillis());	
		if(type==1)userLoginThird.setUtime(System.currentTimeMillis());	
		return userLoginThird;
	}
	
	public boolean isBinding(String openId,Byte loginType) throws UserLoginThirdServiceException{
		try {
			UserLoginThird userLoginThird = selectUserByOpenId(openId, loginType);
			if(userLoginThird!=null)
				return true;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
		return false;		
	}

	@Override
	public UserLoginThird selectUserByOpenId(String openId, int loginType) throws UserLoginThirdServiceException{
		try {
			if(StringUtils.isEmpty(openId))throw new UserLoginThirdServiceException("openId is null or empty.");
			if(loginType!=100 && loginType!=200) throw new UserLoginThirdServiceException("loginType is Must be equal to 100 or 200.");
			return getEntity((Long)getMapId(UserLoginThird_Map_OpenId_LoginType, openId,loginType));
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}


	@Override
	public Long saveUserLoginThird(UserLoginThird userLoginThird) throws UserLoginThirdServiceException{
		try {
			Long id=(Long) saveEntity(checkValidity(userLoginThird,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new UserLoginThirdServiceException("saveUserLoginThird failed.");
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}

	@Override
	public UserLoginThird getUserLoginThirdByOpenId(String openId) throws UserLoginThirdServiceException{
		try{
			if(StringUtils.isEmpty(openId)) throw new UserLoginThirdServiceException("openId is null or empty.");
			Long id=(Long)getMapId(UserLoginThird_Map_OpenId, openId);
			if(id==null || id<=0l) return null;
			UserLoginThird userLoginThird=getEntity(id);
			if(userLoginThird==null) return null;
			try {
				if(userLoginRegisterService.getUserLoginRegister(userLoginThird.getUserId())==null) throw new UserLoginThirdServiceException("userId is not exists in UserLoginRegister.");
			} catch (UserLoginRegisterServiceException e) {
				e.printStackTrace();
			}
			return userLoginThird;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new UserLoginThirdServiceException(e);
		}
	}
	public UserLoginThird getUserLoginThirdByUserId(Long userId) throws UserLoginThirdServiceException{
		try{
			if(userId==null || userId<=0l) throw new UserLoginThirdServiceException("userId is null or less than zero.");
			if(userLoginRegisterService.getUserLoginRegister(userId)==null) throw new UserLoginThirdServiceException("userId is not exists in UserLoginRegister.");
			return getEntity((Long)getMapId(UserLoginThird_Map_UserId_LoginType, userId));
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}
	public UserLoginThird getUserLoginThirdById(Long id) throws UserLoginThirdServiceException{
		try{
			if(id==null || id<=0l) throw new UserLoginThirdServiceException("id is null or less than zero.");
			UserLoginThird userLoginThird=getEntity(id);
			if(userLoginRegisterService.getUserLoginRegister(userLoginThird.getUserId())==null) throw new UserLoginThirdServiceException("userId is not exists in UserLoginRegister.");
			return userLoginThird; 
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}
	@Override
	public Boolean updateAccesstoken(String openId, String accesstoken) throws UserLoginThirdServiceException{
		try{
			UserLoginThird userLoginThird=getUserLoginThirdByOpenId(openId);
			userLoginThird.setAccesstoken(accesstoken);
			return updateEntity(userLoginThird);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}
	@Override
	public Boolean updateUserLoginThird(UserLoginThird userLoginThird) throws UserLoginThirdServiceException{
		try {
			if(updateEntity((checkValidity(userLoginThird,1))))return  true;
			else throw new UserLoginThirdServiceException("updateUserLoginThird failed.");
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginThirdServiceException(e);
		}
	}

	@Override
	public Boolean realDeleteUserLoginThird(Long id)throws UserLoginRegisterServiceException {
		try {
			if(id==null || id<=0l) throw new UserBasicServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserLoginRegisterServiceException(e);
		}
	}
	/**
     * @param 下载图片
     * @throws Exception
     */
    private static boolean getImages(String urlPath,String fileName) {
        URL url;
        boolean flag=true;
		try {
			url = new URL(urlPath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setReadTimeout(6 * 10000);
			if (conn.getResponseCode() < 10000) {
				InputStream inputStream = conn.getInputStream();
				byte[] data = readStream(inputStream);
				if (data.length > 0) {
					FileOutputStream outputStream = new FileOutputStream(fileName);
					outputStream.write(data);
//					log.debug("图片下载成功");
					outputStream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag=false;
		}
		return flag;
    }
    /**
	 * 读取url中数据，并以字节的形式返回
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
    private static byte[] readStream(InputStream inputStream) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while((len = inputStream.read(buffer)) !=-1){
            outputStream.write(buffer, 0, len);
        }
        outputStream.close();
        inputStream.close();
        return outputStream.toByteArray();
    }
    public static void main(String[] args) throws IOException {
    	String surl ="http://ww1.sinaimg.cn/thumbnail/9573641ejw1eumrd6vviyj20go0b474s.jpg";
    	URL url = new URL(surl);
    	HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setReadTimeout(6 * 10000);
    	InputStreamBody isb = new InputStreamBody(conn.getInputStream(),"d://c.jpg");
    	
    	getImages("http://ww1.sinaimg.cn/thumbnail/9573641ejw1eumrd6vviyj20go0b474s.jpg","d://b.jpg");
    	
	}
}