package com.ginkgocap.parasol.user.service;

import java.util.List;
import java.util.Map;

public interface UserTemplateOpenService {
	/**
	 * 获取模板，包括用户自定义模板和系统默认模板。
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String,List<Map>> getTemplates(Long userId) throws Exception;
	/**
	 * 默认模板只会获取模块名称和编码，自定义会获取自定义格式
	 * @param templateId
	 * @param type 0默认，1自定义
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<Map>> getModelByTemplateId(Long templateId,String type) throws Exception;
	/**
	 * 用户选定模板
	 * @param userId
	 * @param templateId
	 * @throws Exception
	 */
	public void selectUserTemplate(Long userId,Long templateId) throws Exception;
}
