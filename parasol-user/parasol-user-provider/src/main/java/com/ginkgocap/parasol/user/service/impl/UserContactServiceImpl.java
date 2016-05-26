package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserContact;
import com.ginkgocap.parasol.user.service.UserContactService;
@Service("userContactService")
public class UserContactServiceImpl extends BaseService<UserContact> implements
	UserContactService {
	private UserContact checkValidity(UserContact userContact,int type)throws Exception {
		if(userContact==null) throw new Exception("userContact can not be null.");
		if(userContact.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(userContact.getId())==null)throw new Exception("userId not exists in UserContact");
//		if(StringUtils.isEmpty(userBasic.getName()))throw new UserBasicServiceException("The value of  name is null or empty.");
		if(userContact.getCtime()==null) userContact.setCtime(System.currentTimeMillis());
		if(userContact.getUtime()==null) userContact.setUtime(System.currentTimeMillis());
		if(type==1)userContact.setUtime(System.currentTimeMillis());
		return userContact;
	}
	@Override
	public Long createObject(UserContact object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建用户联系失败！ ");
	}

	@Override
	public Boolean updateObject(UserContact objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public UserContact getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		UserContact UserContact =getEntity(id);
		return UserContact;
	}

	@Override
	public List<UserContact> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<UserContact> UserContacts =this.getEntityByIds(ids);
		return UserContacts;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<UserContact> createObjects(List<UserContact> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(UserContact userContact : objects){
			this.checkValidity(userContact, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<UserContact> getObjectsByUserId(Long userId) throws Exception {
		if(userId==null || userId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("UserContact_List_UserId", userId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<UserContact> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(UserContact userContact : objects){
			this.checkValidity(userContact, 1);
		}
		return this.updateEntitys(objects);
	}
	@Override
	public Boolean deleteObjectsByUserId(Long userId) throws Exception {
		if(userId==null) throw new Exception("userId is null or empty");
		this.deleteList("UserContact_List_UserId", userId);
		return true;
	}


}
