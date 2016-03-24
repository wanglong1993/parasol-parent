package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.user.exception.UserContactWayServiceException;
import com.ginkgocap.parasol.user.model.UserContactWay;
import com.ginkgocap.parasol.user.service.UserContactWayService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
@Service("userContactWayService")
public class UserContactWayServiceImpl extends BaseService<UserContactWay> implements UserContactWayService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private FileIndexService fileIndexService;
	private static Logger logger = Logger.getLogger(UserContactWayServiceImpl.class);
	/**
	 * 检查数据
	 * @param userContactWay
	 * @return
	 * @throws UserContactWayServiceException
	 */
	private UserContactWay checkValidity(UserContactWay userContactWay,int type)throws UserContactWayServiceException {
		if(userContactWay==null) throw new UserContactWayServiceException("userContactWay can not be null.");
		if(userContactWay.getUserId()<=0l) throw new UserContactWayServiceException("The value of userId is null or empty.");
		if(type!=0)
		if(getUserContactWay(userContactWay.getUserId())==null)throw new UserContactWayServiceException("userId not exists in userContactWay");
		if(type==0){
		if(userContactWay.getCtime()==null) userContactWay.setCtime(System.currentTimeMillis());
		if(userContactWay.getUtime()==null) userContactWay.setUtime(System.currentTimeMillis());
		}
		if(type==1)userContactWay.setUtime(System.currentTimeMillis());
		return userContactWay;
	}
	
	@Override
	public Long createUserContactWay(UserContactWay userContactWay)throws UserContactWayServiceException{
		try {
			Long id=(Long)saveEntity(checkValidity(userContactWay,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new UserContactWayServiceException("createUserContactWay failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserContactWayServiceException(e);
		}
	}

	@Override
	public boolean updateUserContactWay(UserContactWay userContactWay)throws UserContactWayServiceException {
		try {
			if(updateEntity(checkValidity(userContactWay,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserContactWayServiceException(e);
		}
	}

	@Override
	public UserContactWay getUserContactWay(Long id) throws UserContactWayServiceException {
		try {
			if(id==null || id<=0l)throw new UserContactWayServiceException("id is null or empty");
			UserContactWay userContactWay =getEntity(id);
			return userContactWay;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserContactWayServiceException(e);
		}
	}

	@Override
	public List<UserContactWay> getUserContactWayList(List<Long> ids)throws UserContactWayServiceException {
		try {
			if(ids==null || ids.size()==0)throw new UserContactWayServiceException("ids is null or empty");
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserContactWayServiceException(e);
		}
	}

	@Override
	public Boolean realDeleteUserContactWay(Long id)throws UserContactWayServiceException {
		try {
			if(id==null || id<=0l) throw new UserContactWayServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserContactWayServiceException(e);
		}
	}
}
