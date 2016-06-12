package com.ginkgocap.parasol.person.service;

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
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public List<T> getObjectsByPersonId(Long personId) throws Exception;
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteObjects(List<Long> ids) throws Exception;
	/**
	 * 根据用户批量删除
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteObjectsByPersonId(Long personId) throws Exception;
	
	/**
	 * 批量更新
	 * @param objects
	 * @return
	 * @throws Exception
	 */
	public Boolean updateObjects(List<T> objects) throws Exception;
	

}
