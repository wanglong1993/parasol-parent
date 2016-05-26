package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserDefinedTemplate;
import com.ginkgocap.parasol.user.service.UserDefinedTemplateService;
@Service("userDefinedModelService")
public class UserDefinedTemplateServiceImpl extends BaseService<UserDefinedTemplate> implements
			UserDefinedTemplateService {
	private UserDefinedTemplate checkValidity(UserDefinedTemplate UserDefinedModel,int type)throws Exception {
		if(UserDefinedModel==null) throw new Exception("UserDefinedModel can not be null.");
		if(UserDefinedModel.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(UserDefinedModel.getId())==null)throw new Exception("userId not exists in UserDefinedModel");
		if(UserDefinedModel.getCtime()==null) UserDefinedModel.setCtime(System.currentTimeMillis());
		if(UserDefinedModel.getUtime()==null) UserDefinedModel.setUtime(System.currentTimeMillis());
		if(type==1)UserDefinedModel.setUtime(System.currentTimeMillis());
		return UserDefinedModel;
	}
	@Override
	public Long createObject(UserDefinedTemplate object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建失败！ ");
	}

	@Override
	public Boolean updateObject(UserDefinedTemplate objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public UserDefinedTemplate getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		UserDefinedTemplate UserDefinedModel =getEntity(id);
		return UserDefinedModel;
	}

	@Override
	public List<UserDefinedTemplate> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<UserDefinedTemplate> UserDefinedModels =this.getEntityByIds(ids);
		return UserDefinedModels;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<UserDefinedTemplate> createObjects(List<UserDefinedTemplate> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(UserDefinedTemplate UserDefinedModel : objects){
			this.checkValidity(UserDefinedModel, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<UserDefinedTemplate> getObjectsByUserId(Long userId) throws Exception {
		if(userId==null || userId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("UserDefinedModel_List_UserId", userId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<UserDefinedTemplate> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(UserDefinedTemplate UserDefinedModel : objects){
			this.checkValidity(UserDefinedModel, 1);
		}
		return this.updateEntitys(objects);
	}
	@Override
	public Boolean deleteObjectsByUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
