package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserDefined;
import com.ginkgocap.parasol.user.service.Common2Service;
@Service("userDefinedService")
public class UserDefinedServiceImpl extends BaseService<UserDefined> implements Common2Service<UserDefined> {
	private UserDefined checkValidity(UserDefined userDefined,int type)throws Exception {
		if(userDefined==null) throw new Exception("UserDefined can not be null.");
		if(userDefined.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(userDefined.getId())==null)throw new Exception("userId not exists in UserDefined");
//		if(StringUtils.isEmpty(userBasic.getName()))throw new UserBasicServiceException("The value of  name is null or empty.");
		if(userDefined.getCtime()==null) userDefined.setCtime(System.currentTimeMillis());
		if(userDefined.getUtime()==null) userDefined.setUtime(System.currentTimeMillis());
		if(type==1)userDefined.setUtime(System.currentTimeMillis());
		return userDefined;
	}
	@Override
	public Long createObject(UserDefined object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建用户联系失败！ ");
	}

	@Override
	public Boolean updateObject(UserDefined objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public UserDefined getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		UserDefined UserDefined =getEntity(id);
		return UserDefined;
	}

	@Override
	public List<UserDefined> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<UserDefined> UserDefineds =this.getEntityByIds(ids);
		return UserDefineds;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<UserDefined> createObjects(List<UserDefined> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(UserDefined UserDefined : objects){
			this.checkValidity(UserDefined, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<UserDefined> getObjectsByUserId(Long userId) throws Exception {
		if(userId==null || userId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("UserDefined_List_UserId", userId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<UserDefined> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(UserDefined UserDefined : objects){
			this.checkValidity(UserDefined, 1);
		}
		return this.updateEntitys(objects);
	}

}
