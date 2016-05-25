package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserDescription;
import com.ginkgocap.parasol.user.service.UserDescriptionService;
@Service("userDescriptionService")
public class UserDescriptionServiceImpl extends BaseService<UserDescription> implements
			 UserDescriptionService {
	/**
	 * 检查数据
	 * @param UserDescription
	 * @return
	 * @throws Exception
	 */
	private UserDescription checkValidity(UserDescription UserDescription,int type)throws Exception {
		if(UserDescription==null) throw new Exception("UserDescription can not be null.");
		if(UserDescription.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(UserDescription.getUserId())==null)throw new Exception("userId not exists in UserDescription");
//		if(StringUtils.isEmpty(UserDescription.getName()))throw new Exception("The value of  name is null or empty.");
		if(UserDescription.getCtime()==null) UserDescription.setCtime(System.currentTimeMillis());
		if(UserDescription.getUtime()==null) UserDescription.setUtime(System.currentTimeMillis());
		if(type==1)UserDescription.setUtime(System.currentTimeMillis());
		return UserDescription;
	}
	

	@Override
	public Long createObject(UserDescription object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new Exception("创建失败！ ");
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean updateObject(UserDescription objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public UserDescription getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new Exception("userId is null or empty");
			UserDescription UserDescription =getEntity(id);
			return UserDescription;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<UserDescription> getObjects(List<Long> ids) throws Exception {
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
