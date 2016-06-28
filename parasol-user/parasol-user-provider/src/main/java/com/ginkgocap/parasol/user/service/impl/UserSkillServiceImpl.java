package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserSkill;
import com.ginkgocap.parasol.user.service.UserSkillService;
@Service("userSkillService")
public class UserSkillServiceImpl extends BaseService<UserSkill> implements
		     UserSkillService {
	/**
	 * 检查数据
	 * @param UserSkill
	 * @return
	 * @throws Exception
	 */
	private UserSkill checkValidity(UserSkill UserSkill,int type)throws Exception {
		if(UserSkill==null) throw new Exception("UserSkill can not be null.");
		if(UserSkill.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(UserSkill.getUserId())==null)throw new Exception("userId not exists in UserSkill");
//		if(StringUtils.isEmpty(UserSkill.getName()))throw new Exception("The value of  name is null or empty.");
		if(UserSkill.getCtime()==null) UserSkill.setCtime(System.currentTimeMillis());
		if(UserSkill.getUtime()==null) UserSkill.setUtime(System.currentTimeMillis());
		if(type==1)UserSkill.setUtime(System.currentTimeMillis());
		return UserSkill;
	}
	

	@Override
	public Long createObject(UserSkill object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new Exception("创建失败！ ");
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean updateObject(UserSkill objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public UserSkill getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new Exception("userId is null or empty");
			UserSkill UserSkill =getEntity(id);
			return UserSkill;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<UserSkill> getObjects(List<Long> ids) throws Exception {
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
