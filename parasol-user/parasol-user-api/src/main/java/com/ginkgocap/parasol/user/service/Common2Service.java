package com.ginkgocap.parasol.user.service;

import java.util.List;

public interface Common2Service<T> extends CommonService<T> {
	/**
	 * 批量创建
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public List<T> createObjects(List<T> objects) throws Exception;
	/**
	 * 根据用户id获取信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<T> getObjectsByUserId(Long userId) throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteObjects(List<Long> ids) throws Exception;
	
	/**
	 * 批量更新
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public Boolean updateObjects(List<T> objects) throws Exception;
	

}
