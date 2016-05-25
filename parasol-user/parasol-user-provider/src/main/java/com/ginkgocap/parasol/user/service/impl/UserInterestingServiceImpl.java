package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserInteresting;
import com.ginkgocap.parasol.user.service.UserInterestingService;
@Service("userInterestingService")
public class UserInterestingServiceImpl extends BaseService<UserInteresting> implements
			 UserInterestingService {
	/**
	 * 检查数据
	 * @param UserInteresting
	 * @return
	 * @throws Exception
	 */
	private UserInteresting checkValidity(UserInteresting UserInteresting,int type)throws Exception {
		if(UserInteresting==null) throw new Exception("UserInteresting can not be null.");
		if(UserInteresting.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(UserInteresting.getUserId())==null)throw new Exception("userId not exists in UserInteresting");
//		if(StringUtils.isEmpty(UserInteresting.getName()))throw new Exception("The value of  name is null or empty.");
		if(UserInteresting.getCtime()==null) UserInteresting.setCtime(System.currentTimeMillis());
		if(UserInteresting.getUtime()==null) UserInteresting.setUtime(System.currentTimeMillis());
		if(type==1)UserInteresting.setUtime(System.currentTimeMillis());
		return UserInteresting;
	}
	

	@Override
	public Long createObject(UserInteresting object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new Exception("创建失败！ ");
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean updateObject(UserInteresting objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public UserInteresting getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new Exception("userId is null or empty");
			UserInteresting UserInteresting =getEntity(id);
			return UserInteresting;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<UserInteresting> getObjects(List<Long> ids) throws Exception {
		try {
			if(ids==null || ids.size()==0)throw new Exception("userIds is null or empty");
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l) throw new Exception("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
