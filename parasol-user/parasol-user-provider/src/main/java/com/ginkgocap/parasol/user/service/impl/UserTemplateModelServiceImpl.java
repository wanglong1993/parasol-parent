package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserTemplateModel;
import com.ginkgocap.parasol.user.service.UserTemplateModelService;
@Service("userTemplateModelService")
public class UserTemplateModelServiceImpl extends BaseService<UserTemplateModel> implements
	UserTemplateModelService {

	@Override
	public List<UserTemplateModel> createObjects(List<UserTemplateModel> objects)
			throws Exception {
		return this.saveEntitys(objects);
	}

	@Override
	public List<UserTemplateModel> getObjectsByUserId(Long userId)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateObjects(List<UserTemplateModel> objects)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long createObject(UserTemplateModel object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean updateObject(UserTemplateModel objcet) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserTemplateModel getObject(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserTemplateModel> getObjects(List<Long> ids) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserTemplateModel> getObjectsByTemplateId(Long templateId)
			throws Exception {
		if(templateId==null || templateId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("UserTemplateModel_List_UserId", templateId);
		return this.getEntityByIds(ids);
	}

	@Override
	public Boolean deleteObjectsByUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

		
}
