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
	public boolean saveInfo(Map<Integer,Object> param) throws Exception;
	/**
	 * 获取模块信息，分模块获取
	 * @param userId
	 * @param models 一组模块
	 * @param isSelf 是不是自己，不是自己的话要进行权限过滤
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getInfo(Long userId,Integer[] models) throws Exception;
	/**
	 * 修改用户的信息，分模块修改。
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Boolean updateInfo(Map<Integer, Object> param) throws Exception;
	/**
	 * 直接删除模块信息
	 * @param userId
	 * @param types
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteInfo(Long userId,List<Integer> modelType) throws Exception;
}

