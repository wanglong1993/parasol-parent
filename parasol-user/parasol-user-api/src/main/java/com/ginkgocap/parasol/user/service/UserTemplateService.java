package com.ginkgocap.parasol.user.service;

import com.ginkgocap.parasol.user.model.UserTemplate;

public interface UserTemplateService extends Common2Service<UserTemplate> {
	/**
	 * 选择默认模板
	 * @return
	 * @throws Exception
	 */
	public UserTemplate selectTemplateByCode(String template_code) throws Exception;

}
