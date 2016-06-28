package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserInfo;
import com.ginkgocap.parasol.user.service.UserInfoService;
@Service("userInfoService")
public class UserInfoServiceImpl extends BaseService<UserInfo> implements UserInfoService  {
	/**
	 * 检查数据
	 * @param UserInfo
	 * @return
	 * @throws Exception
	 */
	private UserInfo checkValidity(UserInfo UserInfo,int type)throws Exception {
		if(UserInfo==null) throw new Exception("UserInfo can not be null.");
		if(UserInfo.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(UserInfo.getUserId())==null)throw new Exception("userId not exists in UserInfo");
//		if(StringUtils.isEmpty(UserInfo.getName()))throw new Exception("The value of  name is null or empty.");
		if(UserInfo.getCtime()==null) UserInfo.setCtime(System.currentTimeMillis());
		if(UserInfo.getUtime()==null) UserInfo.setUtime(System.currentTimeMillis());
		if(type==1)UserInfo.setUtime(System.currentTimeMillis());
		return UserInfo;
	}
	

	@Override
	public Long createObject(UserInfo object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new Exception("创建失败！ ");
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean updateObject(UserInfo objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public UserInfo getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new Exception("userId is null or empty");
			UserInfo UserInfo =getEntity(id);
			return UserInfo;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<UserInfo> getObjects(List<Long> ids) throws Exception {
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
