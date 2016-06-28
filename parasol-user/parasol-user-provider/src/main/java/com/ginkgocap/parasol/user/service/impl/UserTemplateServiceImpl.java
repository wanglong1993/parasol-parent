package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserTemplate;
import com.ginkgocap.parasol.user.service.UserTemplateService;
@Service("userTemplateService")
public class UserTemplateServiceImpl extends BaseService<UserTemplate> implements
			 UserTemplateService {
	private UserTemplate checkValidity(UserTemplate UserTemplate,int type)throws Exception {
		if(UserTemplate==null) throw new Exception("UserTemplate can not be null.");
		if(UserTemplate.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(UserTemplate.getId())==null)throw new Exception("userId not exists in UserTemplate");
//		if(StringUtils.isEmpty(userBasic.getName()))throw new UserBasicServiceException("The value of  name is null or empty.");
		if(UserTemplate.getCtime()==null) UserTemplate.setCtime(System.currentTimeMillis());
		if(UserTemplate.getUtime()==null) UserTemplate.setUtime(System.currentTimeMillis());
		if(type==1)UserTemplate.setUtime(System.currentTimeMillis());
		return UserTemplate;
	}
	@Override
	public Long createObject(UserTemplate object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建用户联系失败！ ");
	}

	@Override
	public Boolean updateObject(UserTemplate objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public UserTemplate getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		UserTemplate UserTemplate =getEntity(id);
		return UserTemplate;
	}

	@Override
	public List<UserTemplate> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<UserTemplate> UserTemplates =this.getEntityByIds(ids);
		return UserTemplates;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<UserTemplate> createObjects(List<UserTemplate> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(UserTemplate UserTemplate : objects){
			this.checkValidity(UserTemplate, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<UserTemplate> getObjectsByUserId(Long userId) throws Exception {
		if(userId==null || userId<=0l) userId=0l;
		List<Long> ids = this.getIds("UserTemplate_List_UserId", userId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<UserTemplate> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(UserTemplate UserTemplate : objects){
			this.checkValidity(UserTemplate, 1);
		}
		return this.updateEntitys(objects);
	}
	@Override
	public Boolean deleteObjectsByUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public UserTemplate selectTemplateByCode(String template_code) throws Exception {
		UserTemplate userTemplate = null;
		List<Long> ids = this.getIds("UserTemplate_List_code",template_code);
		if(ids!=null){
			Long id = ids.get(0);
			userTemplate = this.getEntity(id);
		}
		return userTemplate;
	}
}
