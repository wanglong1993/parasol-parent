package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserExtServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserExt;
import com.ginkgocap.parasol.user.service.UserExtService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.util.PinyinUtils;
@Service("userExtService")
public class UserExtServiceImpl extends BaseService<UserExt> implements UserExtService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	private static Logger logger = Logger.getLogger(UserExtServiceImpl.class);
	private static String UserExt_List_Id_ProvinceId="UserExt_List_Id_ProvinceId";
	private static String UserExt_List_Id_CountyId="UserExt_List_Id_CountyId";
	/**
	 * 检查数据
	 * @param userExt
	 * @return
	 * @throws UserExtServiceException
	 */
	private UserExt checkValidity(UserExt userExt,int type)throws UserExtServiceException,UserLoginRegisterServiceException {
		if(userExt==null) throw new UserExtServiceException("UserExt can not be null.");
		if(userExt.getUserId()<=0l) throw new UserExtServiceException("The value of userId is null or empty.");
		if(userLoginRegisterService.getUserLoginRegister(userExt.getUserId())==null) throw new UserLoginRegisterServiceException("userId not exists in userLoginRegister");
		if(type!=0)
		if(getUserExt(userExt.getUserId())==null)throw new UserExtServiceException("userId not exists in UserExt");
		if(StringUtils.isEmpty(userExt.getName()))throw new UserExtServiceException("The value of  name is null or empty.");
		if(StringUtils.isEmpty(userExt.getIp()))throw new UserExtServiceException("The value of ip is null or empty.");
		if(userExt.getCtime()==null) userExt.setCtime(System.currentTimeMillis());
		if(userExt.getUtime()==null) userExt.setUtime(System.currentTimeMillis());
		if(type==1)userExt.setUtime(System.currentTimeMillis());
		userExt.setNameFirst(StringUtils.substring(PinyinUtils.stringToHeads(userExt.getName()), 0, 1));
		userExt.setNameIndexAll(PinyinUtils.stringToQuanPin(userExt.getName()));
		return userExt;
	}
	
	@Override
	public Long createUserExt(UserExt userExt)throws UserExtServiceException,UserLoginRegisterServiceException{
		try {
			Long id=(Long)saveEntity(checkValidity(userExt,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new UserExtServiceException("createUserLoginRegister failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserExtServiceException(e);
		}
	}

	@Override
	public boolean updateUserExt(UserExt userExt)throws UserExtServiceException,UserLoginRegisterServiceException {
		try {
			if(updateEntity(checkValidity(userExt,1)))return true;
			else throw new UserExtServiceException("updateUserExt failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserExtServiceException(e);
		}
	}

	@Override
	public UserExt getUserExt(Long userId) throws UserExtServiceException {
		try {
			if(userId==null || userId<=0l)throw new UserExtServiceException("userId is null or empty");
			UserExt UserExt =getEntity(userId);
			if(!ObjectUtils.isEmpty(UserExt))return UserExt;
			else throw new UserExtServiceException("userId is not exists in useBasic");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserExtServiceException(e);
		}
	}

	@Override
	public List<UserExt> getUserExtList(List<Long> userIds)throws UserExtServiceException {
		try {
			if(userIds==null || userIds.size()==0)throw new UserExtServiceException("userIds is null or empty");
			return getEntityByIds(userIds);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserExtServiceException(e);
		}
	}

	@Override
	public List<UserExt> getUserExtListByCountryId(int start,int count,Long countyId)throws UserExtServiceException {
		try {
			if(countyId==null || countyId<=0l)throw new UserExtServiceException("countyId is null or empty");
			return getEntityByIds(getIds(UserExt_List_Id_CountyId, start, count, new Object[]{countyId}));
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserExtServiceException(e);
		}
	}

	@Override
	public List<UserExt> getUserExtListByProvinceId(int start,int count,Long provinceId)throws UserExtServiceException {
		try {
			if(provinceId==null || provinceId<=0l)throw new UserExtServiceException("provinceId is null or empty");
			return getEntityByIds(getIds(UserExt_List_Id_ProvinceId, start, count, new Object[]{provinceId}));
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserExtServiceException(e);
		}
	}

	@Override
	public Boolean realDeleteUserExt(Long id)throws UserExtServiceException {
		try {
			if(id==null || id<=0l) throw new UserExtServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserExtServiceException(e);
		}
	}
}
