package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserAttachment;
import com.ginkgocap.parasol.user.service.UserAttachmentService;
@Service("userAttachmentService")
public class UserAttachmentServiceImpl extends BaseService<UserAttachment> implements UserAttachmentService {
	
	private UserAttachment checkValidity(UserAttachment userAttachment,int type)throws Exception {
		if(userAttachment==null) throw new Exception("UserAttachment can not be null.");
		if(userAttachment.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
//		if(StringUtils.isEmpty(userBasic.getName()))throw new UserBasicServiceException("The value of  name is null or empty.");
		if(userAttachment.getCtime()==null) userAttachment.setCtime(System.currentTimeMillis());
		if(userAttachment.getUtime()==null) userAttachment.setUtime(System.currentTimeMillis());
		if(type==1)userAttachment.setUtime(System.currentTimeMillis());
		return userAttachment;
	}
	@Override
	public Long createObject(UserAttachment object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建用户基本信息失败！ ");
	}

	@Override
	public Boolean updateObject(UserAttachment objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public UserAttachment getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		UserAttachment userAttachment =getEntity(id);
		return userAttachment;
	}

	@Override
	public List<UserAttachment> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<UserAttachment> userAttachments =this.getEntityByIds(ids);
		return userAttachments;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}

/*	@Override
	public List<UserAttachment> createObjects(List<UserAttachment> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(UserAttachment userAttachment : objects){
			this.checkValidity(userAttachment, 0);
		}
		return this.saveEntitys(objects);
	}*/
/*	@Override
	public List<UserAttachment> getObjectsByUserId(Long userId)
			throws Exception {
		if(userId==null || userId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("UserAttachment_List_UserId", userId);
		return this.getEntityByIds(ids);
	}*/
	/*@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}*/
	/*@Override
	public Boolean updateObjects(List<UserAttachment> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(UserAttachment userAttachment : objects){
			this.checkValidity(userAttachment, 1);
		}
		return this.updateEntitys(objects);
	}*/
	
	/*@Override
	public Boolean deleteObjectsByUserId(Long userId) throws Exception {
		if(userId==null) throw new Exception("userId is null or empty");
		this.deleteList("UserAttachment_List_UserId", userId);
		return true;
	}*/

}
