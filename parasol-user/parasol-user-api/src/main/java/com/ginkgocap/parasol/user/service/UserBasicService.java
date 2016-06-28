package com.ginkgocap.parasol.user.service;

import java.util.List;

import com.ginkgocap.parasol.user.model.UserBasic;

public interface UserBasicService extends CommonService<UserBasic> {
	/**
	 * 根据省id查询
	 * @param start
	 * @param count
	 * @param provinceId 
	 * @return
	 * @throws Exception
	 */
	public List<UserBasic> getUserBasicListByProvinceId(int start, int count, Long provinceId) throws Exception;
	/**
	 * 根据市id查询
	 * @param start
	 * @param count
	 * @param provinceId 
	 * @return
	 * @throws Exception
	 */
	public List<UserBasic> getUserBasicListByCityId(int start, int count, Long cityId) throws Exception;
	/**
	 * 根据县id查询
	 * @param start
	 * @param count
	 * @param provinceId 
	 * @return
	 * @throws Exception
	 */
	public List<UserBasic> getUserBasicListByCountyId(int start, int count, Long countyId) throws Exception;
	/**
	 * 根据用户名模糊查询
	 * @param start
	 * @param count
	 * @param provinceId 
	 * @return
	 * @throws Exception
	 */
	public List<UserBasic> getUserBasicListByUserName(int start, int count, String userName) throws Exception;
}
