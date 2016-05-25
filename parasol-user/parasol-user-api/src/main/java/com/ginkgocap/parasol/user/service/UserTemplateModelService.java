package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.model.UserTemplateModel;

public interface UserTemplateModelService extends Common2Service<UserTemplateModel> {
	
	public List<UserTemplateModel> getObjectsByTemplateId(Long id) throws Exception;

}
