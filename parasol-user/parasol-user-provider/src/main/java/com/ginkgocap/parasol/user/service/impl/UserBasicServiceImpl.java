package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserBasicServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.util.PinyinUtils;
@Service("userBasicService")
public class UserBasicServiceImpl extends BaseService<UserBasic> implements UserBasicService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	private static Logger logger = Logger.getLogger(UserBasicServiceImpl.class);
	/**
	 * 检查数据
	 * @param userBasic
	 * @return
	 * @throws UserBasicServiceException
	 */
	private UserBasic checkValidity(UserBasic userBasic,int type)throws Exception {
		if(userBasic==null) throw new UserBasicServiceException("userBasic can not be null.");
		if(userBasic.getUserId()<0l) throw new UserBasicServiceException("The value of userId is null or empty.");
		if(userLoginRegisterService.getUserLoginRegister(userBasic.getUserId())==null) throw new UserLoginRegisterServiceException("userId not exists in userLoginRegister");
		if(type!=0)
		if(getObject(userBasic.getUserId())==null)throw new UserBasicServiceException("userId not exists in userBasic");
//		if(StringUtils.isEmpty(userBasic.getName()))throw new UserBasicServiceException("The value of  name is null or empty.");
		if(userBasic.getSex().intValue()!=0 && userBasic.getSex().intValue()!=1 && userBasic.getSex().intValue()!=2)throw new UserBasicServiceException("The value of sex must be 0 or 1 or 2.");
		if(userBasic.getCtime()==null) userBasic.setCtime(System.currentTimeMillis());
		if(userBasic.getUtime()==null) userBasic.setUtime(System.currentTimeMillis());
		if(type==1)userBasic.setUtime(System.currentTimeMillis());
		if(!StringUtils.isEmpty(userBasic.getName()))userBasic.setNameIndex(PinyinUtils.stringToHeads(userBasic.getName()));
		return userBasic;
	}
	

	@Override
	public Long createObject(UserBasic object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new UserBasicServiceException("创建用户基本信息失败！ ");
		} catch (BaseServiceException e) {
			logger.error("创建用户基本信息失败！", e);
			throw new UserBasicServiceException(e);
		}
	}

	@Override
	public Boolean updateObject(UserBasic objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			logger.error("更新用户基本信息失败！", e);
			throw new UserBasicServiceException(e);
		}
	}

	@Override
	public UserBasic getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new UserBasicServiceException("userId is null or empty");
			UserBasic userBasic =getEntity(id);
			return userBasic;
		} catch (BaseServiceException e) {
			logger.error("获取用户基本信息失败！", e);
			throw new UserBasicServiceException(e);
		}
	}

	@Override
	public List<UserBasic> getObjects(List<Long> ids) throws Exception {
		try {
			if(ids==null || ids.size()==0)throw new UserBasicServiceException("userIds is null or empty");
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			logger.error("批量获取用户基本信息失败！", e);
			throw new UserBasicServiceException(e);
		}
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l) throw new UserBasicServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			logger.error("删除用户基本信息失败！", e);
			throw new UserBasicServiceException(e);
		}
	}
}
