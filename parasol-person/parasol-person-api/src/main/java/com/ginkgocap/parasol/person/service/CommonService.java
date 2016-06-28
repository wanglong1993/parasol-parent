package com.ginkgocap.parasol.person.service;

import java.util.List;

public interface CommonService<T> {
	/**
	 * 创建
	 * @param object
	 * @return
	 * @throws Exception
	 */
	public Long createObject(T object) throws Exception;
	/**
	 * 更新
	 * @param objcet
	 * @return
	 * @throws Exception
	 */
	public Boolean updateObject(T objcet) throws Exception;
	/**
	 * 获取
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T getObject(Long id) throws Exception;
	/**
	 * 批量获取
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public List<T> getObjects(List<Long> ids) throws Exception;
	/**
	 * 删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteObject(Long id) throws Exception;

}
