package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserRTemplate;
import com.ginkgocap.parasol.user.service.UserRTemplateService;
@Service("userRTemplateService")
public class UserRTemplateServiceImpl extends BaseService<UserRTemplate> implements UserRTemplateService {

	/**
	 * 检查数据
	 * @param UserRTemplate
	 * @return
	 * @throws Exception
	 */
	private UserRTemplate checkValidity(UserRTemplate UserRTemplate,int type)throws Exception {
		if(UserRTemplate==null) throw new Exception("UserRTemplate can not be null.");
		if(UserRTemplate.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(UserRTemplate.getUserId())==null)throw new Exception("userId not exists in UserRTemplate");
		return UserRTemplate;
	}
	

	@Override
	public Long createObject(UserRTemplate object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new Exception("创建失败！ ");
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean updateObject(UserRTemplate objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public UserRTemplate getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new Exception("userId is null or empty");
			UserRTemplate UserRTemplate =getEntity(id);
			return UserRTemplate;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<UserRTemplate> getObjects(List<Long> ids) throws Exception {
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
