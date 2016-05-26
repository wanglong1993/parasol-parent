package com.ginkgocap.parasol.user.service;

import java.util.List;
import java.util.Map;

import com.ginkgocap.parasol.user.model.ModelType;

public interface UserInfoOperateService {
	/**
	 * 新增用户信息，分模块保存
	 * @param param ModelType 是用户模块类型，Map<String,Object>对应的模块信息
	 * @return
	 * @throws Exception
	 */
	public boolean saveInfo(Map<Integer,Map<String,Object>> param) throws Exception;
	/**
	 * 获取模块信息，分模块获取
	 * @param userId
	 * @param types 一组模块
	 * @return
	 * @throws Exception
	 */
	public Map<ModelType,Map<String,Object>> getInfo(Long userId,ModelType[] types) throws Exception;
	/**
	 * 修改用户的信息，分模块修改。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Boolean updateInfo(Map<ModelType,Map<String,Object>> param) throws Exception;
	/**
	 * 直接删除模块信息
	 * @param userId
	 * @param types
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteInfo(Long userId,ModelType[] types) throws Exception;
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
}

